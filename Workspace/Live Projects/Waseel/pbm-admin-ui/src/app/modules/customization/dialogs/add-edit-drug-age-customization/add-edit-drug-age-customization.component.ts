import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DrugAgeDetails } from '../../models/drug-age-details.model';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { DssCustomizationService } from '../../services/customization-service/dsscustomization.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { HttpException } from 'src/app/util/default-http-client';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-add-edit-drug-age-customization',
    templateUrl: './add-edit-drug-age-customization.component.html',
    styleUrls: ['./add-edit-drug-age-customization.component.css']
})
export class AddEditDrugAgeCustomizationComponent {

    @Input('dialogMode')
    dialogMode: 'add' | 'edit' = 'add';

    @Input('onClose')
    onClose?: (data: any) => void;

    getDrugsSubscription?: Subscription;

    closeDialog(data: { status: 'cancel' } | { status: 'saved', id: string }) {
        this.onCloseEmitter.emit(data);
    }
    @Input()
    data?: DrugAgeDetails;

    @Output('onClose')
    onCloseEmitter: EventEmitter<{ status: 'cancel' } | { status: 'saved', id: string }> = new EventEmitter();

    selectedRejectionReason: any = "";
    selectedServiceCode: any = "";
    selectedPayersId: any = "";

    serviceCodeInitialOption?: { key: string, value: string };
    payerIdInitialOption?: { key: string, value: string };
    ageList: { key: string, fromValue?: number, toValue?: number }[] = [];
    agename: string = '';

    currentUser: any;
    errorCode: string = '';
    initialDataHash?: number;
    isAdminUser: boolean = false;
    ageCustomizationForm: FormGroup = new FormGroup({
        serviceCode: new FormControl('', { validators: Validators.required }),
        ageGroup: new FormControl('',),
        fromAgeInDays: new FormControl('', { validators: Validators.required }),
        toAgeInDays: new FormControl('', { validators: Validators.required }),
        payerId: new FormControl('', { validators: Validators.required }),
        serviceStatus: new FormControl('', { validators: Validators.required }),
        additionalRejectionReason: new FormControl('', { validators: [Validators.minLength(0), Validators.maxLength(500)] }),
        moduleName: new FormControl('', { validators: Validators.required }),

    });
    private destroy$ = new Subject<void>();
    constructor(
        private dialogService: DialogService,

        public dssLovService: DssLovService,
        public lovService: LovService,
        private dsscustomization: DssCustomizationService,
        private authService: AuthService,
        private translate: TranslateService,
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit(): void {
        this.dssLovService.ageDetailsList$.subscribe(data => {
            this.ageList.push(...data);
            this.agename = this.ageList.find(x => (this.data?.fromAgeInDays! >= x.fromValue! && this.data?.fromAgeInDays! <= x.toValue!) && (this.data?.toAgeInDays! >= x.fromValue! && this.data?.toAgeInDays! <= x.toValue!))?.key!;
        })
        this.isAdminUser = this.currentUser.authorities.findIndex((x: any) => x.authority.includes("PBM_ADMIN")) != -1;

        if (this.dialogMode == "add") {
            if (this.currentUser.authorities.some((data: any) => data.authority.includes('VIEW_PRESCRIPTION')) && !this.isAdminUser) {
                this.ageCustomizationForm.get('payerId')?.setValue(this.currentUser.accId);
                this.ageCustomizationForm.get('moduleName')?.setValue("ALL");
            } else if (this.isAdminUser) {
                this.ageCustomizationForm.get('payerId')?.setValue(this.currentUser.accId);

                this.ageCustomizationForm.get('moduleName')?.setValue("ALL");
            }
            this.ageCustomizationForm.get('ageGroup')?.valueChanges.pipe(
                takeUntil(this.destroy$)
            ).subscribe((selectedAgeGroup: string) => {
                this.updateAgeValues(selectedAgeGroup);
            });
        } else {
            this.ageCustomizationForm.get('ageGroup')?.valueChanges.pipe(
                takeUntil(this.destroy$)
            ).subscribe((selectedAgeGroup: string) => {
                this.updateAgeValues(selectedAgeGroup);
            });
        }

        if (this.data != undefined) {
            this.ageCustomizationForm.setValue({
                serviceCode: this.data.serviceCode || '',
                ageGroup: this.agename || '',
                fromAgeInDays: this.data.fromAgeInDays == 0 ? '0' : this.data.fromAgeInDays || '',
                toAgeInDays: this.data.toAgeInDays == 0 ? '0' : this.data.toAgeInDays || '',
                payerId: this.data.payerId || '',
                serviceStatus: (this.data.serviceStatus?.includes('Approved') ? 'APPROVED' : 'REJECTED') || '',
                additionalRejectionReason: this.data.rejectionReason || '',
                moduleName: this.data.moduleName || '',
            });

            if (!this.ageCustomizationForm.get('payerId')?.value) {
                this.ageCustomizationForm.get('payerId')?.setValue(this.currentUser.accId);
            }

            this.selectedRejectionReason = this.data.rejectionReason || '';
            if (this.data.serviceCode != undefined) {
                this.serviceCodeInitialOption = { key: this.data.serviceCode, value: this.data.serviceCode };
                this.selectedServiceCode = this.data.serviceCode
            }

            if (this.data.payerId != undefined) {
                this.payerIdInitialOption = { key: this.data.payerId, value: this.data.payerId };
                this.selectedPayersId = this.data.payerId;
            }

            this.initialDataHash = this.hashCode(this.ageCustomizationForm.value);
        }

    }

    selectAgeGroupBasedOnFromToDays() {
        let fromDays = this.ageCustomizationForm.get('fromAgeInDays')?.value;
        let toDays = this.ageCustomizationForm.get('toAgeInDays')?.value;
        this.agename = this.ageList.find(x => (fromDays >= x.fromValue! && toDays <= x.toValue!) && (toDays && toDays <= x.toValue!))?.key!;
        this.ageCustomizationForm.get('ageGroup')?.setValue(this.agename);
        this.ageCustomizationForm.get('fromAgeInDays')?.setValue(fromDays);
        this.ageCustomizationForm.get('toAgeInDays')?.setValue(toDays);
    }

    allowOnlyNumbers(event: any, controlName: string) {
        const input = event.target.value;
        const numbersOnly = input.replace(/[^0-9]/g, '');

        if (controlName === 'fromAgeInDays') {
            this.ageCustomizationForm.get('fromAgeInDays')?.setValue(numbersOnly);
        } else if (controlName === 'toAgeInDays') {
            this.ageCustomizationForm.get('toAgeInDays')?.setValue(numbersOnly);
        }
        this.selectAgeGroupBasedOnFromToDays();

    }

    updateAgeValues(ageGroup: string) {
        let ageDetail: { key: string, fromValue?: number, toValue?: number } = this.ageList.find(x => x.key == ageGroup)!;
        this.ageCustomizationForm.get('fromAgeInDays')?.setValue(ageDetail.fromValue);
        this.ageCustomizationForm.get('toAgeInDays')?.setValue(ageDetail.toValue);
    }

    filterDrugs(query: string) {
        this.lovService.getDrugs({ serviceCode: query }).subscribe();
    }


    filterDiagnosis(query: string) {
        this.lovService.getDiagnosis({ icdCode: query }).subscribe();
    }
    filterPayers(query: string) {
        this.lovService.getPayers({ payerId: query }).subscribe((data) => {

        });
    }

    saveData() {
        if (this.authService.systemLoaderVisible || this.ageCustomizationForm.invalid) {
            return;
        }

        this.authService.showSystemLoader();
        let body = this.ageCustomizationForm.value;
        let subscription = this.dialogMode == 'add' ? this.dsscustomization.addDrugToAgeData(body) : this.dsscustomization.editDrugToAgeData(this.data?.id || 'null', body);
        subscription.subscribe({
            next: (data) => {
                this.closeDialog({ status: 'saved', id: (this.dialogMode == 'add' ? (data?.id || '') : (this.data?.id || '')) });
                this.authService.hideSystemLoader();
            },
            error: (exception) => {
                if (exception instanceof HttpException) {
                    this.errorCode = exception.response.error.errors

                }
                this.authService.hideSystemLoader();
            }
        });
    }
    hashCode(data: any) {
        let dataString = JSON.stringify(data);
        let hash = 0;
        for (let i = 0; i < dataString.length; i++) {
            let code = dataString.charCodeAt(i);
            hash = ((hash << 5) - hash) + code;
            hash = hash & hash;
        }
        return hash;
    }

    dataHasChanged() {
        return this.hashCode(this.ageCustomizationForm.value) != this.initialDataHash;
    }
}

