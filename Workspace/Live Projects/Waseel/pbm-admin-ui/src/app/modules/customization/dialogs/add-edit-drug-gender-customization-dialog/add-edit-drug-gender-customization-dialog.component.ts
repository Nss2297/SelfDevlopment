import { Component, EventEmitter, Input, Output } from '@angular/core';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { DssCustomizationService } from '../../services/customization-service/dsscustomization.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { DrugGenderDetails } from '../../models/drug-gender-details.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpException } from 'src/app/util/default-http-client';
import { Subscription } from 'rxjs';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';

@Component({
    selector: 'app-add-edit-drug-gender-customization-dialog',
    templateUrl: './add-edit-drug-gender-customization-dialog.component.html',
    styles: [
    ]
})
export class AddEditDrugGenderCustomizationDialogComponent {

    @Input('dialogMode')
    dialogMode: 'add' | 'edit' = 'add';

    @Input('onClose')
    onClose?: (data: any) => void;

    @Output('onClose')
    onCloseEmitter: EventEmitter<{ status: 'cancel' } | { status: 'saved', id: string }> = new EventEmitter();

    getDrugsSubscription?: Subscription;
    closeDialog(data: { status: 'cancel' } | { status: 'saved', id: string }) {
        this.onCloseEmitter.emit(data);
    }
    @Input()
    data?: DrugGenderDetails;

    selectedRejectionReason: any = "";
    selectedServiceCode: any = "";
    selectedPayersId: any = "";

    serviceCodeInitialOption?: { key: string, value: string };
    payerIdInitialOption?: { key: string, value: string };
    currentUser: any;
    errorCode: string = '';
    initialDataHash?: number;
    isAdminUser: boolean = false;
    genderCustomizationForm: FormGroup = new FormGroup({
        serviceCode: new FormControl('', { validators: Validators.required }),
        gender: new FormControl('', { validators: Validators.required }),
        payerId: new FormControl('', { validators: Validators.required }),
        serviceStatus: new FormControl('', { validators: Validators.required }),
        additionalRejectionReason: new FormControl('', { validators: [Validators.minLength(0), Validators.maxLength(500)] }),
        moduleName: new FormControl('', { validators: Validators.required }),

    });
    constructor(
        private dialogService: DialogService,

        public dssLovService: DssLovService,
        public lovService: LovService,
        private dsscustomization: DssCustomizationService,
        private authService: AuthService
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit(): void {
        console.log("data", this.data)
        // this.lovService.initializeAllLists({ serviceCode: undefined, icdCode: undefined }).subscribe();
        this.isAdminUser = this.currentUser.authorities.findIndex((x: any) => x.authority.includes("PBM_ADMIN")) != -1;

        if (this.dialogMode == "add") {
            if (this.currentUser.authorities.some((data: any) => data.authority.includes('VIEW_PRESCRIPTION')) && !this.isAdminUser) {
                this.genderCustomizationForm.get('payerId')?.setValue(this.currentUser.accId);
                this.genderCustomizationForm.get('moduleName')?.setValue("ALL");
            } else if (this.isAdminUser) {
                this.genderCustomizationForm.get('payerId')?.setValue(this.currentUser.accId);

                this.genderCustomizationForm.get('moduleName')?.setValue("ALL");
            }
        }

        if (this.data != undefined) {
            this.genderCustomizationForm.setValue({
                serviceCode: this.data.serviceCode || '',
                gender: this.data.gender || '',
                payerId: this.data.payerId || '',
                serviceStatus: (this.data.serviceStatus?.includes('Approved') ? 'APPROVED' : 'REJECTED') || '',
                additionalRejectionReason: this.data.rejectionReason || '',
                moduleName: this.data.moduleName || '',
            });

            if (!this.genderCustomizationForm.get('payerId')?.value) {
                this.genderCustomizationForm.get('payerId')?.setValue(this.currentUser.accId);
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
            this.initialDataHash = this.hashCode(this.genderCustomizationForm.value);
            console.log("inial", this.initialDataHash)
        }

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
        if (this.authService.systemLoaderVisible || this.genderCustomizationForm.invalid) {
            return;
        }
        this.authService.showSystemLoader();
        let body = this.genderCustomizationForm.value;
        let subscription = this.dialogMode == 'add' ? this.dsscustomization.addDrugToGenderData(body) : this.dsscustomization.editDrugToGenderData(this.data?.id || 'null', body);
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
            hash = hash & hash; // Convert to 32bit integer
        }
        return hash;
    }

    dataHasChanged() {
        return this.hashCode(this.genderCustomizationForm.value) != this.initialDataHash;
    }
}
