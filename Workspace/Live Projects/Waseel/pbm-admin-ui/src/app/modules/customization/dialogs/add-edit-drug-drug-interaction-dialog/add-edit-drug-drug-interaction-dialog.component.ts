import { Component, Input } from '@angular/core';
import { DrugToDrugDetails } from '../../models/drug-drug-details.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DssCustomizationService } from '../../services/customization-service/dsscustomization.service';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { HttpException } from 'src/app/util/default-http-client';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';

@Component({
    selector: 'app-add-edit-drug-drug-interaction-dialog',
    templateUrl: './add-edit-drug-drug-interaction-dialog.component.html',
    styleUrls: ['./add-edit-drug-drug-interaction-dialog.component.css']
})
export class AddEditDrugDrugInteractionDialogComponent {
    @Input('dialogMode')
    dialogMode: 'add' | 'edit' = 'add';

    @Input()
    data?: DrugToDrugDetails;

    @Input('onClose')
    onClose?: any;


    initialDataHash?: number;
    selectedRejectionReason: any = "";
    selectedServiceCode: any = "";
    selectedPayersId: any = "";
    serviceCodeInitialOption?: { key: string, value: string };
    payerIdInitialOption?: { key: string, value: string };
    errorCode!: string;
    currentUser?: any;
    isAdminUser: boolean = false;


    drugToDrugCustomizationForm: FormGroup = new FormGroup({
        serviceCode: new FormControl('', { validators: Validators.required }),
        interactedServiceCode: new FormControl('', { validators: Validators.required }),
        payerId: new FormControl(''),
        serviceStatus: new FormControl('', { validators: Validators.required }),
        additionalRejectionReason: new FormControl('', { validators: [Validators.minLength(0), Validators.maxLength(500)] }),
        moduleName: new FormControl('', { validators: Validators.required }),

    });

    constructor(
        private dsscustomization: DssCustomizationService,
        private authService: AuthService,
        public dssLovService: DssLovService,
        public lovService: LovService
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit(): void {

        this.isAdminUser = this.currentUser.authorities.findIndex((x: any) => x.authority.includes("PBM_ADMIN")) != -1;
        if (this.isAdminUser) {
            this.drugToDrugCustomizationForm.get("payerId")?.setValidators(Validators.required);
            if (this.dialogMode === 'add') {
                this.drugToDrugCustomizationForm.get("payerId")?.setValue(this.currentUser.accId);
            }
        }

        if (this.data != undefined) {
            this.drugToDrugCustomizationForm.setValue({
                serviceCode: this.data.serviceCode ?? '',
                interactedServiceCode: this.data.interactedServiceCode ?? '',
                payerId: this.data.payerId ?? '',
                serviceStatus: (this.data.serviceStatus?.includes('Approved') ? 'APPROVED' : 'REJECTED') ?? '',
                additionalRejectionReason: this.data.additionalRejectionReason ?? '',
                moduleName: this.data.moduleName ?? ''
            });

            // if (!this.drugToDrugCustomizationForm.get('payerId')?.value) {
            //     this.drugToDrugCustomizationForm.get('payerId')?.setValue(this.currentUser.accId);
            // }

            // this.selectedRejectionReason = this.data.additionalRejectionReason || '';
            // if (this.data.serviceCode != undefined) {
            //     this.serviceCodeInitialOption = { key: this.data.serviceCode, value: this.data.serviceCode };
            //     this.selectedServiceCode = this.data.serviceCode
            // }

            // if (this.data.payerId != undefined) {
            //     this.payerIdInitialOption = { key: this.data.payerId, value: this.data.payerId };
            //     this.selectedPayersId = this.data.payerId;
            // }

            this.initialDataHash = this.hashCode(this.drugToDrugCustomizationForm.value);
        }


    }

    filterServiceCode(query: string) {
        this.lovService.getDrugs({ serviceCode: query }).subscribe();
    }

    filterPayers(query: string) {
        this.lovService.getPayers({ payerId: query }).subscribe((data) => {

        });
    }

    saveData() {
        if (this.authService.systemLoaderVisible || this.drugToDrugCustomizationForm.invalid) {
            return;
        }

        this.authService.showSystemLoader();
        let body = this.drugToDrugCustomizationForm.value;
        let subscription = this.dialogMode == 'add' ? this.dsscustomization.addNewDrugToDrugInteractionData(body) : this.dsscustomization.updateDrugToDrugInteractionData(Number(this.data?.id), body);
        subscription.subscribe({
            next: (data) => {
                this.onClose(data);
                this.authService.hideSystemLoader();
            },
            error: (exception) => {
                if (exception instanceof HttpException) {
                    this.errorCode = exception.response.error.errors;

                }
                this.authService.hideSystemLoader();
            }
        });
    }

    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
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
        return this.hashCode(this.drugToDrugCustomizationForm.value) != this.initialDataHash;
    }

}
