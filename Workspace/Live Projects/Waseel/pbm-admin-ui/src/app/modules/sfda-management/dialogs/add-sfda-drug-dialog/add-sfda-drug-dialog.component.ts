import { Component, Input } from '@angular/core';
import { SfdaDrugListDetailsModel } from '../../models/sfda-drug-details.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SfdaManagementService } from '../../service/sfda-management.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';

@Component({
    selector: 'add-sfda-drug-dialog',
    templateUrl: './add-sfda-drug-dialog.component.html',
    styles: [
    ]
})
export class AddSfdaDrugDialogComponent {

    @Input('dialogMode')
    dialogMode: 'add' | 'edit' = 'add';

    @Input()
    data?: SfdaDrugListDetailsModel;

    @Input('onClose')
    onClose?: any;

    initialDataHash?: number;

    editSfdaDrugDetailForm: FormGroup = new FormGroup({
        sfdaCode: new FormControl('', { validators: [Validators.required] }),
        gtinCode: new FormControl('',),
        tradeName: new FormControl('', { validators: Validators.required }),
        scientificCode: new FormControl('', { validators: Validators.required }),
        scientificName: new FormControl('', { validators: Validators.required }),
        dosageForm: new FormControl('', { validators: Validators.required }),
        administrationRoute: new FormControl('', { validators: [Validators.required] }),
        packageSize: new FormControl('', { validators: Validators.required }),
        packageType: new FormControl('', { validators: Validators.required }),
        granularUnit: new FormControl('', { validators: Validators.required }),
        strength: new FormControl('', { validators: Validators.required }),
        strengthUnit: new FormControl('', { validators: Validators.required }),
        price: new FormControl('', { validators: [Validators.required, Validators.pattern('^-?\\d+(\\.\\d{1,2})?$')] })
    });

    constructor(
        private sfdaManagementSVC: SfdaManagementService,
        private authService: AuthService,
        public dssLovService: DssLovService
    ) {
    }

    ngOnInit(): void {
        if (this.dialogMode == "edit") {
            if (this.data) {
                this.editSfdaDrugDetailForm.setValue({
                    sfdaCode: this.data.sfdaCode || '',
                    gtinCode: this.data.gtinCode || '',
                    tradeName: this.data.tradeName || '',
                    scientificCode: this.data.scientificCode || '',
                    scientificName: this.data.scientificName || '',
                    dosageForm: this.data.dosageForm || '',
                    administrationRoute: this.data.administrationRoute || '',
                    packageSize: this.data.packageSize || '',
                    packageType: this.data.packageType || '',
                    granularUnit: this.data.granularUnit || '',
                    strength: this.data.strength || '',
                    strengthUnit: this.data.strengthUnit || '',
                    price: this.data.price || '',

                });
                this.initialDataHash = this.hashCode(this.editSfdaDrugDetailForm.value);
            }
        } else {
            this.editSfdaDrugDetailForm.reset();
        }

    }

    saveData() {
        let body = this.editSfdaDrugDetailForm.value;
        body.price = (Number(body.price) * 100 / 100).toFixed(2);
        this.onClose(body);
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
        return this.hashCode(this.editSfdaDrugDetailForm.value) != this.initialDataHash;
    }

    checkNumberType(value: any) {
        if (!Number(value)) {
            this.editSfdaDrugDetailForm.get('granularUnit')?.reset();
            this.editSfdaDrugDetailForm.get('granularUnit')?.markAsDirty();
            return;
        }
    }

    onKeyUp(event: any) {
        const inputValue: string = event.target.value;
        const regex = /^\d*\.?\d{0,2}$/;

        if (!regex.test(inputValue)) {
            const truncatedValue = inputValue.match(/^\d*\.?\d{0,2}/);
            this.editSfdaDrugDetailForm.get("price")?.setValue(truncatedValue);
        }
    }


}
