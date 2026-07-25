import { Component, Input, OnInit } from '@angular/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { PrescriptionService } from '../../service/prescription.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { DispensableDrug } from '../../models/dispense-detail.model';

@Component({
    selector: 'dispense-summary-confirm-dialog',
    templateUrl: './dispense-summary-confirm-dialog.component.html',
    styles: [
    ]
})
export class DispenseSummaryConfirmDialogComponent implements OnInit {
    ePrescriptionReferenceNumber!: string;
    readyToDispense: string[] = [];
    drugNeedsApproval: string[] = [];

    @Input() totals: any;
    @Input() referenceNo!: string;
    @Input('onClose')
    onClose?: (data: any) => void;

    @Input('selectedDrugs') selectedDrugs: DispensableDrug[] = [];

    constructor(
        private dialogService: DialogService,
        private prescriptionService: PrescriptionService,
        private router: Router,
        private authService: AuthService
    ) { }

    ngOnInit() {
        if (this.selectedDrugs.length > 0) {
            this.selectedDrugs.forEach(drug => {
                if (drug && drug.suggestedDrugs) {
                    let matchingDrugs = drug.suggestedDrugs.filter((x: any) => x.sfdaCode == drug.sfdaCode);
                    if (matchingDrugs.length > 0) {
                        let drugIsInFormulary = matchingDrugs[0].drugFormulary;
                        let drugIsInExclusion = matchingDrugs[0].inExclusionList;

                        if ((drug.isBrand == true) || (drugIsInFormulary) || (!drug.isApprovalRequired)) {
                            //const drugIndex = this.readyToDispense.findIndex(x => x == drug.sfdaDescription);
                            //if (drugIndex == -1) {
                            this.readyToDispense.push(drug.sfdaDescription!);
                            //}
                        } else if ((!drugIsInFormulary) || (drug.isApprovalRequired)) {
                            //const drugIndex = this.drugNeedsApproval.findIndex(x => x == drug.sfdaDescription);
                            //if (drugIndex == -1) {
                            this.drugNeedsApproval.push(drug.sfdaDescription!);
                            //}
                        }
                    }
                }
            });
        }
    }

    dispenseDrug() {
        this.authService.showSystemLoader();
        let data = {
            totalPatientShare: Math.round(this.totals.patientShare * 100) / 100,
            totalNet: Math.round(this.totals.net * 100) / 100,
            totalPatientShareCurrency: "SAR",
            totalNetCurrency: "SAR",
            drugList: this.selectedDrugs.map(drug => ({
                ePrescriptionReferenceNo: this.referenceNo,
                scientificCode: drug.scientificCode,
                drugCode: drug.sfdaCode,
                scientificName: drug.scientificName,
                unitPrice: drug.unitPrice,
                quantity: drug.quantity,
                patientShare: Math.round(drug.patientShare! * 100) / 100,
                net: Math.round(drug.net! * 100) / 100,
                patientShareCurrency: 'SAR',
                netCurrency: 'SAR',
                isApprovalRequired: drug.isApprovalRequired
            })),
        }
        this.prescriptionService.dispenseApproveddrugs(this.referenceNo, data).subscribe((res: any) => {
            if (res.status === "Invalid" || res.status === "INVALID" || res.status === "Failed" || res.status === "FAILED") {
                this.dialogService.showErrorDialog(res.statusDescription, (data: any) => {
                    if (data) {
                        this.router.navigate(["prescription/details", this.referenceNo])
                    }
                })
            } else {
                this.dialogService.showSuccessDialog(res.statusDescription, (data: any) => {
                    if (data) {
                        this.router.navigate(["prescription/details", this.referenceNo])
                    }
                })
            }
        }, (error) => {
            this.dialogService.showErrorDialog(error.response.error.statusDescription, (data: any) => { })
            if (data) {
                this.authService.hideSystemLoader();
                this.closeDialog()
            }
        })

    }

    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }
}
