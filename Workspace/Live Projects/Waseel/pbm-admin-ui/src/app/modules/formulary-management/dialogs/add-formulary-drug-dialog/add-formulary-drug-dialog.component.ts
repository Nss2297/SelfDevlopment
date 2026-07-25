import { Component, Input } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AddPrescriptionStoreService } from 'src/app/modules/prescription/service/add-prescription-store/add-prescription-store.service';
import { DrugListModel, PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { DrugsDetails } from 'src/app/modules/prescription/models/prescription-drug.model';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';

@Component({
    selector: 'add-formulary-drug-dialog',
    templateUrl: './add-formulary-drug-dialog.component.html',
    styles: [
    ]
})
export class AddFormularyDrugDialogComponent {
    searchFormControl: FormControl = new FormControl();
    shareFormControl = new FormControl();

    getDrugsSubscription?: Subscription;
    selectedDrug?: DrugsDetails;
    selectedDrugName?: string
    selectedDrugCode?: string;
    selectedgenericName?: string;
    selectedprice?: string;
    drugValidationMsg!: string;
    paitentShareValidationMsg!: string;

    searchFormTouched: boolean = false;
    @Input('onClose')
    onClose?: any;

    @Input('fromPage')
    fromPage?: string;

    @Input() formularyId: any;

    constructor(
        public storeService: AddPrescriptionStoreService,
        public prescriptionLOVs: PrescriptionlovService,
        private translate: TranslateService,
        private authService: AuthService
    ) { }


    ngOnInit(): void {
        this.prescriptionLOVs.initializeAllLists();
    }

    getDrugs(event: any) {
        if (this.searchFormControl.value != null) {
            if (this.getDrugsSubscription != null) {
                this.getDrugsSubscription.unsubscribe();
                this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
            }
            this.getDrugsSubscription = this.prescriptionLOVs.getDrugs({ drugValue: this.searchFormControl.value, idNumber: this.storeService.selectedMemberInfo?.idNumber || '' })
                .subscribe({
                    next: (value) => this.getDrugsSubscription = undefined,
                    error: (value) => this.getDrugsSubscription = undefined,
                });
        } else {
            this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
        }
    }

    selectDrug(drug: { unitPrice: string, sfdaCode: string, sfdaDescription: string, scientificName: string, scientificCode: string, dosageForm: string, strengthUnit: string }) {
        this.selectedDrug = { id: drug.sfdaCode, drugCode: drug.sfdaCode, unitPrice: Number.parseInt(drug.unitPrice), drugName: drug.sfdaDescription, scientificName: drug.scientificName, dosageForm: drug.dosageForm, strengthUnit: drug.strengthUnit };
        this.searchFormControl.setValue(`${drug.sfdaCode} | ${drug.sfdaDescription} `);
        this.searchFormControl.get('drugCode')?.setValue(drug.sfdaCode);
        this.searchFormControl.get('drugName')?.setValue(drug.sfdaDescription);
        this.selectedDrugName = drug.sfdaDescription
        this.selectedDrugCode = drug.sfdaCode
        this.selectedgenericName = drug.scientificName
        this.selectedprice = drug.unitPrice
        this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
        this.drugValidationMsg = "";
    }

    //   onDrugSelectionChange() {

    //     this.drugValidationMsg = '';
    // }

    saveDrugData() {
        this.drugValidationMsg = "";
        if (this.searchFormControl.invalid || this.shareFormControl.value > 100) {
            return;
        }
        if (this.searchFormControl.value && this.selectedDrug) {
            let drug = {
                drugCode: this.selectedDrugCode,
                drugName: this.selectedDrugName,
                genericName: this.selectedgenericName,
                price: this.selectedprice,
                patientShare: this.shareFormControl.value
            }
            if (this.fromPage == "create") {
                this.authService.showSystemLoader();
                setTimeout(() => {
                    this.onClose(drug);
                    this.authService.hideSystemLoader();
                }, 1000)

            } else {
                this.onClose(drug);
            }
        } else {
            this.translate.get('prescription.drugValidationMsg').subscribe(text => this.drugValidationMsg = text);
        }
    }

    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }

    onKeyUp(event: Event) {
        const inputValue = (event.target as HTMLInputElement).value;
        const isValid = /^\d{0,2}(\.\d{0,2})?$/.test(inputValue) && parseFloat(inputValue) <= 100;

        if (!isValid) {
            const value = parseFloat(inputValue);
            if (!isNaN(value) && value > 100) {
                this.translate.get('prescription.paitentShareValidationMsg').subscribe(text => this.paitentShareValidationMsg = text);
            } else {
                this.paitentShareValidationMsg = "";
            }

            const parts = inputValue.split('.');
            const lastChar = inputValue[inputValue.length - 1];

            if (!/^\d$/.test(lastChar) || (parts[1] && parts[1].length > 2)) {
                (event.target as HTMLInputElement).value = inputValue.slice(0, -1);
                this.shareFormControl.setValue(inputValue.slice(0, -1));
            }
        } else {
            this.paitentShareValidationMsg = "";
        }
    }


}
