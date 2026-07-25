import { Component, Input, ViewChild } from '@angular/core';
import { formularyProviderService } from '../../Services/formulary-provider-service';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { FormularyMemberDetailsModel } from '../../models/formulary-details.model';

@Component({
    selector: 'add-based-on-dialog',
    templateUrl: './add-based-on-dialog.component.html',
    styles: []
})
export class AddBasedOnDialogComponent {

    @Input('onClose')
    onClose?: any;

    @Input('fromPage')
    fromPage?: string;

    @ViewChild('classBasedClassName') classBasedClassName!: any;
    policyNoValidationMsg!: string;
    memberIdValidationMsg!: string;
    policyNoValidationErrorMsg!: string;
    classNameValidation!: string;

    basedOn = 'policy-based';
    selectedPolicy: PolicyCreateRequestModel = {};
    selectedPolicies: PolicyCreateRequestModel[] = [];
    disablePolicyClassName: boolean = true;
    validatedPolicyNo?: string;

    constructor(
        private formularyProviderSvc: formularyProviderService,
        private translate: TranslateService,
        private authService: AuthService,
        private dialogService: DialogService
    ) { }

    validateAndAddPolicy(policyNo: string) {
        if (policyNo) {
            if (this.fromPage == "create") {
                this.authService.showSystemLoader();
                this.policyNoValidationMsg = "";
                this.formularyProviderSvc.getPolicyDetailsByPolicyNo(policyNo).subscribe((data: any) => {
                    let policy: PolicyCreateRequestModel = {
                        policyName: data.policyMetadata.policyHolderName,
                        policyClassName: "-",
                        policyNumber: data.policyMetadata.policyNumber,
                        idNumber: "-",
                        policyHolderName: data.policyMetadata.policyHolderName,
                        policyType: data.policyMetadata.policyType,
                        issueDate: data.policyMetadata.issueDate,
                        startDate: data.policyMetadata.startDate,
                        endDate: data.policyMetadata.endDate,
                        policyClasses: [],
                    }
                    data.policyMetadata.policyClasses.forEach((element: any) => {
                        let policyClass: { classCode: string, className: string } = {} as { classCode: string, className: string };
                        policyClass.classCode = element.classCode;
                        policyClass.className = element.className;
                        policy.policyClasses?.push(policyClass)
                    });
                    this.authService.hideSystemLoader();
                    let modalData = {
                        policyDetails: policy,
                        memberDetails: undefined,
                        basedOn: "policy"
                    }
                    this.onClose(modalData);
                }, (error) => {
                    if (error.error.responseDescription) {
                        this.dialogService.showErrorDialog(error.error.responseDescription, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    } else {
                        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
                    }
                }
                )
            } else {
                this.authService.showSystemLoader();
                this.policyNoValidationMsg = "";
                this.formularyProviderSvc.verifyPolicyDetails(policyNo).subscribe((data: any) => {
                    if (data) {
                        this.formularyProviderSvc.getPolicyDetailsByPolicyNo(policyNo).subscribe((data: any) => {
                            this.authService.hideSystemLoader();
                            let modalData = {
                                drugFormularyAssociationId: "",
                                policyDetails: data.policyMetadata
                            }
                            this.onClose(modalData);
                        }, (error) => {
                            if (error.error.responseDescription) {
                                this.dialogService.showErrorDialog(error.error.responseDescription, (data: any) => {
                                    this.authService.hideSystemLoader();
                                })
                            } else {
                                this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
                            }
                        })
                    }
                })
            }
        } else {
            this.translate.get('prescription.validPolicyNo').subscribe(text => this.policyNoValidationMsg = text);
        }
    }

    validatePolicyNumber(policyNo: string) {
        if (policyNo) {
            if (this.fromPage == "create") {
                this.authService.showSystemLoader();
                this.policyNoValidationErrorMsg = "";
                this.formularyProviderSvc.getPolicyDetailsByPolicyNo(policyNo).subscribe((data: any) => {
                    this.validatedPolicyNo = policyNo;
                    this.disablePolicyClassName = false;
                    this.selectedPolicy.policyNumber = data.policyMetadata.policyNumber;
                    this.selectedPolicy.policyName = data.policyMetadata.policyHolderName;
                    this.selectedPolicy.policyClassName = "-";
                    this.selectedPolicy.idNumber = "-";
                    this.selectedPolicy.policyHolderName = data.policyMetadata.policyHolderName;
                    this.selectedPolicy.policyType = data.policyMetadata.policyType;
                    this.selectedPolicy.issueDate = data.policyMetadata.issueDate;
                    this.selectedPolicy.startDate = data.policyMetadata.startDate;
                    this.selectedPolicy.endDate = data.policyMetadata.endDate;
                    this.selectedPolicy.policyClasses = [];
                    let policyClasses: { key: string, value: string }[] = [];
                    data.policyMetadata.policyClasses.forEach((element: any) => {
                        let policyClass: { key: string, value: string } = {} as { key: string, value: string };
                        policyClass.key = element.classCode;
                        policyClass.value = element.className;
                        policyClasses.push(policyClass);

                        let selectPolicyClass: { classCode: string, className: string } = {} as { classCode: string, className: string };
                        selectPolicyClass.classCode = element.classCode;
                        selectPolicyClass.className = element.className;
                        this.selectedPolicy.policyClasses?.push(selectPolicyClass)
                    });
                    this.classBasedClassName.options = policyClasses
                    this.authService.hideSystemLoader();
                }, (error) => {
                    if (error.error.responseDescription) {
                        this.dialogService.showErrorDialog(error.error.responseDescription, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    } else {
                        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
                    }
                })
            } else {
                this.authService.showSystemLoader();
                this.policyNoValidationErrorMsg = "";
                this.formularyProviderSvc.verifyPolicyDetails(policyNo).subscribe((data: any) => {
                    if (data) {
                        this.formularyProviderSvc.getPolicyDetailsByPolicyNo(policyNo).subscribe((data: any) => {
                            this.validatedPolicyNo = policyNo;
                            this.disablePolicyClassName = false;
                            this.selectedPolicy.policyNumber = data.policyMetadata.policyNumber;
                            this.selectedPolicy.policyName = data.policyMetadata.policyHolderName;
                            this.selectedPolicy.policyClassName = "-";
                            this.selectedPolicy.idNumber = "-";
                            this.selectedPolicy.policyHolderName = data.policyMetadata.policyHolderName;
                            this.selectedPolicy.policyType = data.policyMetadata.policyType;
                            this.selectedPolicy.issueDate = data.policyMetadata.issueDate;
                            this.selectedPolicy.startDate = data.policyMetadata.startDate;
                            this.selectedPolicy.endDate = data.policyMetadata.endDate;
                            this.selectedPolicy.policyClasses = [];
                            let policyClasses: { key: string, value: string }[] = [];
                            data.policyMetadata.policyClasses.forEach((element: any) => {
                                let policyClass: { key: string, value: string } = {} as { key: string, value: string };
                                policyClass.key = element.classCode;
                                policyClass.value = element.className;
                                policyClasses.push(policyClass)
                            });
                            this.classBasedClassName.options = policyClasses
                            this.authService.hideSystemLoader();

                        }, (error) => {
                            if (error.error.responseDescription) {
                                this.dialogService.showErrorDialog(error.error.responseDescription, (data: any) => {
                                    this.authService.hideSystemLoader();
                                })
                            } else {
                                this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
                            }
                        })
                    }
                })
            }
        } else {
            this.translate.get('prescription.validPolicyNo').subscribe(text => this.policyNoValidationErrorMsg = text);
        }
    }

    validateAndAddMemberId(idNumber: string) {
        if (idNumber) {
            if (isNaN(Number(idNumber))) {
                this.translate.get('prescription.validMemberId').subscribe(text => this.memberIdValidationMsg = text);
            }
            else {
                if (this.fromPage == "create") {
                    this.authService.showSystemLoader();
                    this.memberIdValidationMsg = "";
                    this.formularyProviderSvc.getPolicyDetailsByMemberId(idNumber).subscribe((data: any) => {
                        let memberdetails: FormularyMemberDetailsModel = {};
                        data.memberPolicyDetails.forEach((item: any) => {
                            let policy: PolicyCreateRequestModel = {
                                policyNumber: item.policyNumber,
                                policyName: item.policyHolderName,
                                policyClassName: item.policyClasses[0].className,
                                idNumber: item.memberId,
                                policyHolderName: item.policyHolderName,
                                policyType: item.policyType,
                                issueDate: item.issueDate,
                                startDate: item.startDate,
                                endDate: item.endDate,
                                policyClasses: [],
                            }
                            item.policyClasses.forEach((element: any) => {
                                let policyClass: { classCode: string, className: string } = {} as { classCode: string, className: string };
                                policyClass.classCode = element.classCode;
                                policyClass.className = element.className;
                                policy.policyClasses?.push(policyClass)
                            });
                            this.selectedPolicies.push(policy)
                        })

                        memberdetails.idNumber = data.idNumber;
                        memberdetails.memberName = data.memberName;
                        memberdetails.gender = data.gender;
                        memberdetails.dateOfBirth = data.dateOfBirth;
                        memberdetails.maritalStatus = data.maritalStatus;
                        memberdetails.nationality = data.nationality;
                        memberdetails.mobileNumber = data.mobileNumber;
                        memberdetails.email = data.email;

                        let modalData = {
                            policyDetails: this.selectedPolicies,
                            memberDetails: memberdetails,
                            basedOn: "memberId"
                        }

                        this.authService.hideSystemLoader();
                        this.onClose(modalData);

                    }, (error) => {
                        if (error.error.responseDescription) {
                            this.dialogService.showErrorDialog(error.error.responseDescription, (data: any) => {
                                this.authService.hideSystemLoader();
                            })
                        } else {
                            this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
                        }
                    })
                }
                else {
                    this.authService.showSystemLoader();
                    this.memberIdValidationMsg = "";
                    this.formularyProviderSvc.verifyPolicyDetails(idNumber).subscribe((data: any) => {
                        this.formularyProviderSvc.getPolicyDetailsByMemberId(idNumber).subscribe((data: any) => {
                            data.memberPolicyDetails.forEach((item: any) => {
                                let policy = {
                                    policyNumber: item.policyNumber,
                                    policyName: item.policyHolderName,
                                    policyClassName: item.className,
                                    idNumber: item.memberId
                                }
                                this.selectedPolicies.push(policy)
                            })
                            this.authService.hideSystemLoader();
                            let modalData = {
                                drugFormularyAssociationId: 0,
                                memberDetails: {
                                    memberName: data.memberName,
                                    idNumber: data.idNumber,
                                    gender: data.gender,
                                    dateOfBirth: data.dateOfBirth,
                                    maritalStatus: data.maritalStatus,
                                    nationality: data.nationality,
                                    mobileNumber: data.mobileNumber,
                                    email: data.email
                                },
                                policyDetails: data.memberPolicyDetails
                            }
                            this.onClose(modalData);
                        }, (error) => {
                            if (error.error.responseDescription) {
                                this.dialogService.showErrorDialog(error.error.responseDescription, (data: any) => {
                                    this.authService.hideSystemLoader();
                                })
                            } else {
                                this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
                            }
                        })
                    })
                }

            }
        }
        else {
            this.translate.get('prescription.validMemberId').subscribe(text => this.memberIdValidationMsg = text);
        }
    }

    setPolicyClassName(event: any) {
        this.selectedPolicy.policyClassName = this.classBasedClassName.value;
    }

    saveValidatedPolicy() {
        if (!this.selectedPolicy.policyNumber) {
            this.policyNoValidationErrorMsg = "";
            this.translate.get('prescription.validPolicyNo').subscribe(text => this.policyNoValidationErrorMsg = text);
        }
        else {
            if (!this.classBasedClassName.value) {
                this.classNameValidation = "";
                this.translate.get('prescription.validPolicyClassName').subscribe(text => this.classNameValidation = text);
            }
            else {
                this.authService.showSystemLoader();
                setTimeout(() => {
                    let modalData = {
                        policyDetails: this.selectedPolicy,
                        memberDetails: undefined,
                        basedOn: "policyClass"
                    }
                    this.onClose(modalData);
                    this.authService.hideSystemLoader();
                }, 1000)

            }
        }


    }


    clearePolicyClassName(event: any) {
        if (event.target.value == "" || event.target.value != this.validatedPolicyNo) {
            this.disablePolicyClassName = true;
            this.selectedPolicy = {} as { policyName: string; policyNumber: string; policyClassName: string; idNumber: string; }
            this.classBasedClassName.value = "";
        }
    }

    closeDialog() {
        if (this.onClose) {
            this.onClose(null);
        }
    }
}

export class PolicyCreateRequestModel {
    policyName?: string;
    policyClassName?: string;
    policyNumber?: string;
    policyHolderName?: string;
    idNumber?: string;
    policyType?: string;
    issueDate?: string;
    startDate?: string;
    endDate?: string;
    policyClasses?: { classCode: string; className: string; }[]
}