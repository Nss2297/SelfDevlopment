import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { PrescriptionProviderService } from '../../service/prescription-provider.service';
import { User } from 'src/app/modules/authentication/models/user.model';
import { PrescriptionService } from '../../service/prescription.service';
import { HttpException } from 'src/app/util/default-http-client';
import { TranslateService } from '@ngx-translate/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'provider-override-comment-dialog',
    templateUrl: './provider-override-comment-dialog.component.html',
    styles: [
    ]
})
export class ProviderOverrideCommentDialogComponent implements OnInit {
    @Input()
    ePrescriptionReferenceNumber?: any;
    @Input('onClose')
    onClose?: (data: any) => void;
    @Input() payerMemberPhysicianInfoData: any;
    @Input('drugCode')
    drugCode?: any;
    @Input('drugArray')
    drugArray: [] = [];
    @Input('selectedDrug')
    selectedDrug: any;
@Input() selectedMedicalValidations:any;
    commentboxError = false;

    modifyDecissionValidationMessage = "";
    errorMsgForModifyDescrip = "";
    successMsgForOverridestatus = "";
    currentUser!: User;
    overRiding = '';
    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }

    constructor(
        private prescriptionService: PrescriptionService,
        private activeRouter: ActivatedRoute, private router: Router,
        private translate: TranslateService,
        private dialogService: DialogService,
        private authService: AuthService,
        public providerService: PrescriptionProviderService
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit(): void {
        console.log("drug data  --- ", this.selectedDrug)
    }

    // saveChanges() {
    //         console.log("drugstatus ", this.selectedDrug)
    //         console.log("selectedMedicalValidations",this.selectedMedicalValidations)
    //         this.authService.showSystemLoader();
    //         console.log("------> ", this.drugCode)
    //         if (this.drugCode) {
    //             let requestData = {
    //                 drugCode: this.selectedDrug.drugCode,
    //                 drugName: this.selectedDrug.drugName,
    //                 denialCode: this.selectedMedicalValidations.denialCode,
    //                 rejectionReason: this.selectedMedicalValidations.rejectionReason,
    //                 overridingReason: this.overRiding,
    //                 scientificCode: this.selectedMedicalValidations.scientificCode,
    //                 scientificName: this.selectedMedicalValidations.scientificName
    //             }

    //             console.log("final data ", requestData)
    //             this.providerService.saveData(this.currentUser.accId!, this.ePrescriptionReferenceNumber, requestData).subscribe({
    //                 next: (data: any) => {
    //                     const apiResponseStatus = data.prescriptionStatus;
    //                     this.successMsgForOverridestatus = "";
    //                     this.translate.get('prescription.successMsgForOverridestatus', { drugCode: this.drugCode.drugCode, referencenumber: this.ePrescriptionReferenceNumber, status: apiResponseStatus }).subscribe((text: any) => this.successMsgForOverridestatus = text);
    //                     this.authService.hideSystemLoader();
    //                     this.dialogService.showSuccessDialog(this.successMsgForOverridestatus, (data) => {
    //                         if (data) {
    //                             this.closeDialog()
    //                             this.router.navigate(["prescription/details", this.ePrescriptionReferenceNumber]);
    //                         }
    //                     })
    //                 },
    //                 error: (exception) => {
    //                     this.authService.hideSystemLoader();
    //                     if (exception instanceof HttpException) {
    //                         console.log("EXCEPTION ", exception)
    //                         this.dialogService.showErrorDialog(exception.response.error.httpStatusDescription
    //                             , (data) => {
    //                             if (data) {
    //                                 this.closeDialog();
    //                             }
    //                         })
    //                     }
    //                 }
    //             });
    //         } else {
    //             this.dialogService.showWarningDialog(this.modifyDecissionValidationMessage, () => { });
    //         }
        
    // }

    saveChanges() {
        console.log("drugstatus ", this.selectedDrug)
        console.log("selectedMedicalValidations", this.selectedMedicalValidations)
        this.authService.showSystemLoader();
        console.log("------> ", this.drugCode)
        if (this.drugCode) {
            let requestData = {
                drugCode: this.selectedDrug.drugCode,
                drugName: this.selectedDrug.drugName,
                denialCode: this.selectedMedicalValidations.denialCode,
                rejectionReason: this.selectedMedicalValidations.rejectionReason,
                overridingReason: this.overRiding,
                scientificCode: this.selectedMedicalValidations.scientificCode,
                scientificName: this.selectedMedicalValidations.scientificName
            }
    
            console.log("final data ", requestData)
            this.providerService.saveData(this.currentUser.accId!, this.ePrescriptionReferenceNumber, requestData).subscribe({
                next: (data: any) => {
                    const apiResponseStatus = data.prescriptionStatus;
                    this.successMsgForOverridestatus = "";
                    if (this.selectedDrug.drugCode) {
                        this.translate.get('prescription.successMsgForOverridestatus', {
                            drugCode: this.selectedDrug.drugCode,
                            referencenumber: this.ePrescriptionReferenceNumber,
                            status: apiResponseStatus
                        }).subscribe((text: any) => this.successMsgForOverridestatus = text);
                    } else {
                        this.translate.get('prescription.successMsgForOverridestatus', {
                            drugCode: this.selectedMedicalValidations.scientificCode,
                            referencenumber: this.ePrescriptionReferenceNumber,
                            status: apiResponseStatus
                        }).subscribe((text: any) => this.successMsgForOverridestatus = text);
                    }
    
                    this.authService.hideSystemLoader();
                    this.dialogService.showSuccessDialog(this.successMsgForOverridestatus, (data) => {
                        if (data) {
                            this.closeDialog()
                            this.router.navigate(["prescription/details", this.ePrescriptionReferenceNumber]);
                        }
                    })
                },
                error: (exception) => {
                    this.authService.hideSystemLoader();
                    if (exception instanceof HttpException) {
                        console.log("EXCEPTION ", exception)
                        this.dialogService.showErrorDialog(exception.response.error.httpStatusDescription
                            , (data) => {
                                if (data) {
                                    this.closeDialog();
                                }
                            })
                    }
                }
            });
        } else {
            this.dialogService.showWarningDialog(this.modifyDecissionValidationMessage, () => { });
        }
    }
    


}
