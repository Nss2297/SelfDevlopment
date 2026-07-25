import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { BehaviorSubject, Subject, Subscription, map, take, withLatestFrom } from 'rxjs';
import { DrugsDetails } from 'src/app/modules/prescription/models/prescription-drug.model';
import { AddPrescriptionStoreService } from 'src/app/modules/prescription/service/add-prescription-store/add-prescription-store.service';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { DrugListModel, PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { environment } from 'src/environments/environment';

@Component({
    selector: 'add-prescription-drugs-step',
    templateUrl: './drugs-step.component.html',
    styleUrls: []
})
export class DrugsStepComponent implements OnInit, OnDestroy {

    //searchFormControl: FormControl = new FormControl();
    selectedDrug?: DrugsDetails;
    showDuplicateError: boolean = false;
    getDrugsSubscription?: Subscription;
    updateData$ = new Subject();
    drugsViewList: ListViewModel<DrugsDetails> = new ListViewModel();
    listPrototype = DrugsDetails.prototype;
    isDialogOpened$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    dialogMode: 'add' | 'edit' = 'add';
    prescriptionValue: any;
    basedOn = 'scientific-name';
    prescribeGenericOrBrandFlow: boolean = environment.featureToggle['prescribeGenericOrBrandFlow'];
    placeholderName: string = "Search for drug code, trade name or generic name";
    selectedBasedOnRadio: string = "scientificName";
    selectDrugValidationMsg!: string;

    frequencyDescriptionValidator(control: AbstractControl): ValidationErrors | null {
        if (control instanceof FormGroup) {
            if (control.get('frequency')?.value == 'others') {
                return control.get('frequencyOthersDescription')?.value.length == 0 ? { frequencyOthersDescriptionRequired: true } : null;
            }
        }
        return null;
    }

    drugForm: FormGroup = new FormGroup({
        searchFormControl: new FormControl('', { validators: Validators.required }),
        unitType: new FormControl('', { validators: Validators.required }),
        quantity: new FormControl('', { validators: [Validators.required, Validators.min(1), Validators.max(999)] }),
        useUnitValue: new FormControl('', { validators: [Validators.required, Validators.min(1), Validators.max(99)] }),
        frequency: new FormControl('', { validators: Validators.required }),
        frequencyOthersDescription: new FormControl(''),
        duration: new FormControl('', { validators: [Validators.required, Validators.min(1)] }),
        drugCode: new FormControl('', { validators: Validators.required }),
        serviceStartDate: new FormControl(new Date(), { validators: Validators.required }),
        unitPrice: new FormControl('', { validators: Validators.required }),
        drugName: new FormControl(''),
        scientificName: new FormControl(''),
        dosageForm: new FormControl(''),
        strengthUnit: new FormControl(''),
        strength: new FormControl(''),
        scientificCode: new FormControl(''),
        drugListId: new FormControl('')
    }, { validators: this.frequencyDescriptionValidator });
    initUnitType: string = '';

    constructor(
        public storeService: AddPrescriptionStoreService,
        public prescriptionLOVs: PrescriptionlovService,
        private translate: TranslateService,
        public lovService: LovService,
        private datepipe: DatePipe
    ) { }

    ngOnInit(): void {
        this.prescriptionLOVs.initializeAllLists();
        this.prescriptionLOVs.getUnitTypes$
            .subscribe(types => {
                if (types != null && types.length > 0) {
                    this.drugForm.get('unitType')?.setValue(types[0].key);
                    this.initUnitType = types[0].key;
                }
            });

        this.drugsViewList.number = 0;
        this.drugsViewList.size = 5;
        this.updateData$.pipe(
            withLatestFrom(this.storeService.state$),
            map(data => ({ codes: data[1].prescriptionData.drugList, isValid: data[1].step3IsValid }))
        ).subscribe(({ codes, isValid }) => {
            const list: DrugsDetails[] = (codes || []).map(code => ({ ...code, id: code.drugCode ? code.drugCode : code.scientificCode!, type: code.drugCode ? "tradeName" : "scientificName", drugCode: code.drugCode ? code.drugCode : code.scientificCode }));
            this.drugsViewList.totalElements = list.length;
            while (this.drugsViewList.number != 0 && (this.drugsViewList.number * this.drugsViewList.size) > this.drugsViewList.totalElements!) {
                this.drugsViewList.number--;
            }
            this.drugsViewList.content = list.slice(this.drugsViewList.number * this.drugsViewList.size, this.drugsViewList.size + (this.drugsViewList.number * this.drugsViewList.size));
            this.drugsViewList.first = this.drugsViewList.number == 0;
            this.drugsViewList.last = (list.length - 1) <= (this.drugsViewList.size + (this.drugsViewList.number * this.drugsViewList.size));
            this.drugsViewList.totalElements = list.length;
            this.drugsViewList.totalPages = Math.ceil(list.length / this.drugsViewList.size);
            this.storeService.changeStepStatus(3, (codes?.length || 0) > 0);
        });
        this.updateData$.next('');
    }


    ngOnDestroy(): void {
        this.updateData$.complete();
    }

    changeBasedOnValue(basedonRadio: string) {
        this.selectedDrug = undefined;
        this.selectDrugValidationMsg = "";
        this.drugForm.get('searchFormControl')?.reset();
        this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
        if (basedonRadio == "scientificName") {
            this.basedOn = 'scientific-name';
            this.selectedBasedOnRadio = "scientificName";
            this.placeholderName = "Search for scientific name, pharmaceutical form,route or scientific code";
        } else {
            this.basedOn = 'trade-name';
            this.selectedBasedOnRadio = "tradeName";
            this.placeholderName = "Search for drug code, trade name or generic name";
        }
    }

    getDrugs(event: any) {
        if (this.drugForm.get('searchFormControl')?.value != null) {
            if (this.getDrugsSubscription != null) {
                this.getDrugsSubscription.unsubscribe();
                this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
            }
            this.getDrugsSubscription = this.prescriptionLOVs.getDrugs({ drugValue: this.drugForm.get('searchFormControl')?.value, idNumber: this.storeService.selectedMemberInfo?.idNumber || '', payerId: this.storeService.payers[0].key, searchBy: this.selectedBasedOnRadio })
                .subscribe({
                    next: (value) => {
                        this.getDrugsSubscription = undefined
                    },
                    error: (value) => this.getDrugsSubscription = undefined,
                });
        } else {
            this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
        }
    }

    selectDrug(drug: { roaSuggested: string, unitPrice: string, sfdaCode: string, sfdaDescription: string, scientificName: string, scientificCode: string, dosageForm: string, strengthUnit: string, strength: string, activeDrugListId: number }) {
        this.selectedDrug = { id: drug.sfdaCode, drugCode: drug.sfdaCode, unitPrice: Number.parseInt(drug.unitPrice), drugName: drug.sfdaDescription, scientificName: drug.scientificName, scientificCode: drug.scientificCode, dosageForm: drug.dosageForm, strength: drug.strength, strengthUnit: drug.strengthUnit, drugListId: drug.activeDrugListId.toString() };
        if (this.basedOn === 'scientific-name') {
            this.drugForm.get('searchFormControl')?.setValue(`${drug.scientificCode} | ${drug.scientificName} | ${drug.dosageForm} | ${drug.strengthUnit}`);
        } else {
            this.drugForm.get('searchFormControl')?.setValue(`${drug.sfdaCode} | ${drug.sfdaDescription}  | ${drug.scientificName} | ${drug.dosageForm} | ${drug.strengthUnit}`);
        }
        this.drugForm.get('drugCode')?.setValue(drug.sfdaCode);
        this.drugForm.get('drugName')?.setValue(drug.sfdaDescription);
        this.drugForm.get('unitPrice')?.setValue(drug.unitPrice);
        this.drugForm.get('scientificName')?.setValue(drug.scientificName);
        this.drugForm.get('dosageForm')?.setValue(drug.dosageForm);
        this.drugForm.get('strengthUnit')?.setValue(drug.strengthUnit);
        this.drugForm.get('scientificCode')?.setValue(drug.scientificCode);
        this.drugForm.get('strength')?.setValue(drug.strength);
        this.drugForm.get('drugListId')?.setValue(drug.activeDrugListId);
        this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
        this.selectDrugValidationMsg = "";
    }

    addDrug(keepDialogOpenAndReset: boolean) {
        this.selectDrugValidationMsg = "";
        console.log('Selected Based On Radio:', this.selectedBasedOnRadio);
        this.showDuplicateError = false;
        if (this.drugForm.valid) {
            this.drugForm.get("serviceStartDate")?.setValue(this.datepipe.transform(this.drugForm.get("serviceStartDate")?.value, "dd-MM-YYYY"));
            const isUpdated = this.dialogMode == 'add' ? this.storeService.addDrug(this.drugForm.value, this.selectedBasedOnRadio)
                : this.storeService.editDrug(this.drugForm.value, this.selectedBasedOnRadio);
            if (isUpdated) {
                this.updateData$.next('');
                if (keepDialogOpenAndReset) {
                    this.resetDrugForm();
                } else {
                    this.closeDialog();
                }
            }
            else {
                this.drugForm.get("serviceStartDate")?.setValue(this.convertToDate(this.drugForm.get("serviceStartDate")?.value))
                this.showDuplicateError = true;
            }
        } else {
            this.translate.get('prescription.selectDrugValidationMsg').subscribe(text => this.selectDrugValidationMsg = text);
            this.drugForm.markAllAsTouched();
        }
    }


    openDialog(isEditMode: boolean) {
        if (isEditMode) {
            this.drugForm.get('searchFormControl')?.disable();
            this.dialogMode = 'edit';
        } else {
            this.drugForm.get('searchFormControl')?.enable();
            this.dialogMode = 'add';
        }
        this.isDialogOpened$.next(true);
    }


    closeDialog() {
        this.resetDrugForm();
        this.isDialogOpened$.next(false);
    }

    resetDrugForm() {

        this.prescriptionLOVs.initializeAllLists();

        this.prescriptionLOVs.getUnitTypes$
            .pipe(take(1))
            .subscribe(types => {
                if (types != null && types.length > 0) {
                    this.drugForm.get('unitType')?.setValue(types[0].key);
                    this.initUnitType = types[0].key;
                }
            });
        this.basedOn = 'scientific-name';
        this.selectedBasedOnRadio = "scientificName";

        this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
        // this.drugForm.setValue({
        this.drugForm.patchValue({
            unitType: this.initUnitType,
            quantity: '',
            useUnitValue: '',
            frequency: '',
            frequencyOthersDescription: '',
            duration: '',
            drugCode: '',
            serviceStartDate: new Date(),
            unitPrice: '',
            drugName: ''
        });
        this.drugForm.get('searchFormControl')?.patchValue('');
        this.drugForm.get('searchFormControl')?.enable();
        this.drugForm.get('searchFormControl')?.markAsUntouched();
        this.selectedDrug = undefined;
        this.drugForm.markAsUntouched();
        this.showDuplicateError = false;
        this.selectDrugValidationMsg = "";

    }

    deleteDrugData(drugCode: string) {
        this.storeService.removeDrug(drugCode);
        let drugData = this.drugsViewList.content.findIndex(x => x.id == drugCode || x.scientificCode == drugCode)
        if (drugData != -1) {
            this.drugsViewList.content.splice(drugData, 1);
            if (this.drugsViewList.content.length == 0 && this.drugsViewList.number > 0) {
                this.drugsViewList.number -= 1;
            }
        }
        this.updateData$.next('');
    }

    openEditDrugDiagnosisDialog(drugCode: string) {
        const index = this.drugsViewList.content.findIndex(drug => drug.drugCode == drugCode);
        if (index != -1) {
            const drug = this.drugsViewList.content[index];
            this.drugForm.setValue({
                unitType: drug.unitType,
                quantity: drug.quantity,
                useUnitValue: drug.useUnitValue,
                frequency: drug.frequency,
                frequencyOthersDescription: drug.frequencyOthersDescription,
                duration: drug.duration,
                drugCode: drug.drugCode,
                serviceStartDate: this.convertToDate(drug.serviceStartDate!),
                unitPrice: drug.unitPrice,
                drugName: drug.drugName,
                searchFormControl: `${drug.drugCode} | ${drug.drugName}`,
                scientificName: drug.scientificName || '',
                dosageForm: drug.dosageForm || '',
                strengthUnit: drug.strengthUnit || '',
                strength: drug.strength || '',
                scientificCode: drug.scientificCode || '',
                drugListId: drug.drugListId


            });

            if (drug.type === "scientificName") {
                this.basedOn = 'scientific-name';
                this.selectedBasedOnRadio = 'scientificName';
                this.drugForm.get('searchFormControl')?.setValue(`${drug.scientificCode} | ${drug.scientificName} | ${drug.strength} | ${drug.strengthUnit}`);
            } else {
                this.basedOn = 'trade-name';
                this.selectedBasedOnRadio = 'tradeName';
                this.drugForm.get('searchFormControl')?.setValue(`${drug.drugCode} | ${drug.drugName}`);
            }

            this.drugForm.get('searchFormControl')?.disable();
            this.dialogMode = 'edit';
            this.isDialogOpened$.next(true);
        }
    }

    convertToDate(dateString: string) {
        //  Convert a "dd/MM/yyyy" string into a Date object
        let d = dateString.split("-");
        let dat = new Date(d[2] + '/' + d[1] + '/' + d[0]);
        return dat;
    }


    onListViewPageChange(event: { event: MouseEvent, pageNumber: number }) {
        this.drugsViewList.number = event.pageNumber;
        this.updateData$.next('');
    }

    getFieldError(controller: AbstractControl | null) {
        if (controller) {
            debugger;
            if (controller.errors?.['required']) {
                return { error: 'prescription.requiredFieldError' };
            } else if (controller.errors?.['min']) {
                return { error: 'prescription.minError', param: { value: controller.errors?.['min'].min } };
            } else if (controller.errors?.['max']) {
                return { error: 'prescription.maxError', param: { value: controller.errors?.['max'].max } };
            }
        }
        return null;
    }

    showEditButtonForDrug(drug: DrugsDetails) {
        return drug.status == null;
    }
    onListViewPageSizeChange(event: any) {
        console.log(event);
        this.drugsViewList.size = Number(event.pageSize);
        this.updateData$.next('');
    }

    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 5;
    }
}