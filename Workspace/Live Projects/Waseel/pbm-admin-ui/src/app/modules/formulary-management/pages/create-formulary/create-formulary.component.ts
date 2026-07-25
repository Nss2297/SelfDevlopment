import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from '../../../shared/services/dialog-service/dialog.service';
import { formularyProviderService } from '../../Services/formulary-provider-service';
import { HttpException } from 'src/app/util/default-http-client';
import { Subscription, map } from 'rxjs';
import { FormularyDrugDetailsModel, FormularyMemberDetailsModel } from '../../models/formulary-details.model';
import { ListViewModel } from '../../../shared/components/list-view/models/list-view.model';
import { AuthService } from '../../../authentication/services/auth-service/auth.service';
import { FormularyPolicyDetailsModel } from '../../models/formulary-details.model';
import { BehaviorSubject } from 'rxjs';
import { PolicyCreateRequestModel } from '../../dialogs/add-based-on-dialog/add-based-on-dialog.component';
@Component({
    selector: 'app-create-formulary',
    templateUrl: './create-formulary.component.html',
    styles: [
    ]
})
export class CreateFormularyComponent {
    addBasedOnDialogOpen = false;
    addFormularyDrugOpen = false;

    @ViewChild('formularyName') formularyName!: any;

    formularyId!: string;
    subscriptions: Subscription[] = [];

    formularyPolicyList$: BehaviorSubject<ListViewModel<FormularyPolicyDetailsModel>> = new BehaviorSubject(new ListViewModel<FormularyPolicyDetailsModel>());
    formularyPolicyList: ListViewModel<FormularyPolicyDetailsModel> = new ListViewModel<FormularyPolicyDetailsModel>();
    localFormularyPolicyList: ListViewModel<FormularyPolicyDetailsModel> = new ListViewModel<FormularyPolicyDetailsModel>();
    formularyPolicyListPrototype = FormularyPolicyDetailsModel.prototype;
    policyDataControl: FormularyPolicyDetailsModel = new FormularyPolicyDetailsModel();

    formularyDrugList$: BehaviorSubject<ListViewModel<FormularyDrugDetailsModel>> = new BehaviorSubject(new ListViewModel<FormularyDrugDetailsModel>());
    formularyDrugList: ListViewModel<FormularyDrugDetailsModel> = new ListViewModel<FormularyDrugDetailsModel>();
    localFormularyDrugList: ListViewModel<FormularyDrugDetailsModel> = new ListViewModel<FormularyDrugDetailsModel>();
    formularyDrugListPrototype = FormularyDrugDetailsModel.prototype;
    drugDataControl: FormularyDrugDetailsModel = new FormularyDrugDetailsModel();

    memberdetails: FormularyMemberDetailsModel = {};

    constructor(
        private router: Router,
        private authService: AuthService,
        private translate: TranslateService,
        private dialogService: DialogService,
        private formularyProviderSvc: formularyProviderService

    ) { }

    ngOnInit(): void {
        this.drugDataControl.recordSize = 5;
        this.policyDataControl.recordSize = 5;

        this.populatePolicyList();
        this.populateDrugList();
    }

    onDrugDelete(id: any) {
        let canceltitle = "";
        let cancelSubtitle = "";
        this.translate.get('prescription.cancelFormularyTitle').subscribe(text => canceltitle = text);
        this.translate.get('prescription.cancelFormularySubTitle').subscribe(text => cancelSubtitle = text);

        this.dialogService.showConfirmDialog(canceltitle, cancelSubtitle, (data: any) => {
            if (data) {
                this.formularyProviderSvc.deleteDrug(this.formularyId).subscribe({
                    next: (data) => {
                        const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                        let formularyRequestcancel = '';
                        this.translate.get('prescription.formularyRequestcancel').subscribe(text => formularyRequestcancel = text);
                        this.dialogService.showSuccessDialog(formularyRequestcancel, (data: any) => {
                            if (data) {
                            }
                        })
                    },
                    error: (exception) => {
                        if (exception instanceof HttpException) {
                            this.dialogService.showErrorDialog(exception.response.error.errorDescription, () => { })
                        }
                    }
                })

            } else {
                return;
            }
        })
    }

    populatePolicyList() {
        this.formularyPolicyList$.subscribe(data => {
            if (data.content.length > 0) {
                // this.formularyPolicyList = data;
                // this.formularyPolicyList.totalElements = data.content.length;
                // this.formularyPolicyList.size = this.policyDataControl.recordSize;

                const list: FormularyPolicyDetailsModel[] = (data.content ?? []);
                this.formularyPolicyList.totalElements = list.length;
                while (this.formularyPolicyList.number != 0 && (this.formularyPolicyList.number * this.formularyPolicyList.size) > this.formularyPolicyList.totalElements!) {
                    this.formularyPolicyList.number--;
                }

                this.formularyPolicyList.size = this.policyDataControl.recordSize;
                this.formularyPolicyList.content = list.slice(this.formularyPolicyList.number * this.formularyPolicyList.size, this.formularyPolicyList.size + (this.formularyPolicyList.number * this.formularyPolicyList.size));
                this.formularyPolicyList.first = this.formularyPolicyList.number == 0;
                this.formularyPolicyList.last = (list.length - 1) <= (this.formularyPolicyList.size + (this.formularyPolicyList.number * this.formularyPolicyList.size));
                this.formularyPolicyList.totalElements = list.length;
                this.formularyPolicyList.totalPages = Math.ceil(list.length / this.formularyPolicyList.size);
            } else {
                this.formularyPolicyList = new ListViewModel();
            }
        })
    }

    populateDrugList() {
        this.formularyDrugList$.subscribe(data => {
            if (data.content.length > 0) {
                // this.formularyPolicyList = data;
                // this.formularyPolicyList.totalElements = data.content.length;
                // this.formularyPolicyList.size = this.policyDataControl.recordSize;

                const list: FormularyDrugDetailsModel[] = (data.content ?? []);
                this.formularyDrugList.totalElements = list.length;
                while (this.formularyDrugList.number != 0 && (this.formularyDrugList.number * this.formularyDrugList.size) > this.formularyDrugList.totalElements!) {
                    this.formularyDrugList.number--;
                }

                this.formularyDrugList.size = this.drugDataControl.recordSize;
                this.formularyDrugList.content = list.slice(this.formularyDrugList.number * this.formularyDrugList.size, this.formularyDrugList.size + (this.formularyDrugList.number * this.formularyDrugList.size));
                this.formularyDrugList.first = this.formularyDrugList.number == 0;
                this.formularyDrugList.last = (list.length - 1) <= (this.formularyDrugList.size + (this.formularyDrugList.number * this.formularyDrugList.size));
                this.formularyDrugList.totalElements = list.length;
                this.formularyDrugList.totalPages = Math.ceil(list.length / this.formularyDrugList.size);
            } else {
                this.formularyDrugList = new ListViewModel();
            }
        })
    }

    fetchData(tableName?: string) {
        // this.authService.showSystemLoader();
        // switch (tableName) {
        //   case 'DRUGS':
        //     this.formularyProviderSvc.createFormularyDrugList(this.formularyId,).subscribe();
        //     break;

        //  default:
        //     this.formularyProviderSvc.createFormularyDrugList(this.formularyId,).subscribe();

        //     break;
        // }

        //   this.authService.hideSystemLoader();

    }

    deletePolicyDetailData(policyData: any) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader();
                this.deletePolicyData(policyData)
            } else {
                return;
            }
        })
    }

    deletePolicyData(policyData: any) {
        setTimeout(() => {
            let policyDetailsData = this.localFormularyPolicyList.content.findIndex(x => x.policyNumber == policyData)
            if (policyDetailsData == -1) {
                this.localFormularyPolicyList.content.splice(policyDetailsData, 1);
                this.formularyPolicyList$.next(this.localFormularyPolicyList);
                if (this.formularyPolicyList.content.length == 0) {
                    this.formularyPolicyList.number -= 1;
                }
                this.populatePolicyList();
                let policyDetailDeleteMsg = '';
                this.translate.get('prescription.policyDetailsDeleteMsg').subscribe(text => policyDetailDeleteMsg = text);
                this.dialogService.showSuccessDialog(policyDetailDeleteMsg, (data: any) => {
                    if (data) {
                        this.authService.hideSystemLoader();
                    }
                })
            }
        }, 500);

    }

    deleteDrugDetailData(drugCode: string) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader();
                this.deleteDrugData(drugCode)
            } else {
                return;
            }
        })
    }

    deleteDrugData(drugCode: string) {
        setTimeout(() => {
            let rowId = this.localFormularyDrugList.content.findIndex(x => x.drugCode == drugCode)
            if (rowId != -1) {
                this.localFormularyDrugList.content.splice(rowId, 1);
                this.formularyDrugList$.next(this.localFormularyDrugList);
                if (this.formularyDrugList.content.length == 0) {
                    this.formularyDrugList.number -= 1;
                }
                this.populateDrugList();
                let drugDetailDeleteMsg = '';
                this.translate.get('prescription.drugDetailDeleteMsg').subscribe(text => drugDetailDeleteMsg = text);
                this.dialogService.showSuccessDialog(drugDetailDeleteMsg, (data: any) => {
                    if (data) {
                        this.authService.hideSystemLoader();
                    }
                })
            }
        }, 1000);
    }


    onBackClick = () => {
        this.router.navigateByUrl('/formulary/list');
    }

    closeAddBasedOnDialog = (data: any) => {
        let duplicatePolicyMsg = '';
        let duplicatePolicyAssociationMsg = '';
        this.translate.get('prescription.duplicatePolicyMsg').subscribe(text => duplicatePolicyMsg = text);
        this.translate.get('prescription.duplicatePolicyAssociationMsg').subscribe(text => duplicatePolicyAssociationMsg = text);
        if (!data) {
            this.addBasedOnDialogOpen = false;
            return;
        }
        if (data.basedOn == "memberId") {

            data.policyDetails.forEach((policy: any) => {
                if (this.localFormularyPolicyList.content.length > 0) {
                    this.localFormularyPolicyList.content.forEach((element: any) => {
                        if ((element.idNumber === policy.idNumber || element.policyNumber === policy.policyNumber) && element.basedOn == data.basedOn) {
                            this.dialogService.showErrorDialog(duplicatePolicyMsg, (data: any) => { });
                            return;
                        } else if ((element.idNumber === policy.idNumber || element.policyNumber === policy.policyNumber) && element.basedOn != data.basedOn) {
                            this.dialogService.showErrorDialog(duplicatePolicyAssociationMsg, (data: any) => { });
                            return;
                        } else {
                            policy.basedOn = data.basedOn;
                            this.localFormularyPolicyList.content.push(policy);
                        }
                    });
                }
                else {
                    policy.basedOn = data.basedOn;
                    this.localFormularyPolicyList.content.push(policy);
                }
            });
            debugger;
            this.memberdetails = data.memberDetails;
            this.localFormularyPolicyList.content.forEach((element: any, index: number) => {
                element.id = index;
            });
            this.formularyPolicyList$.next(this.localFormularyPolicyList);
        } else {
            if (this.localFormularyPolicyList.content.length > 0) {
                this.localFormularyPolicyList.content.forEach((element: any) => {
                    if (element.policyNumber === data.policyDetails.policyNumber && element.basedOn == data.basedOn) {
                        this.dialogService.showErrorDialog(duplicatePolicyMsg, (data: any) => { });
                        return;
                    } else if (element.policyNumber === data.policyDetails.policyNumber && element.basedOn != data.basedOn) {
                        this.dialogService.showErrorDialog(duplicatePolicyAssociationMsg, (data: any) => { });
                        return;
                    } else {
                        data.policyDetails.basedOn = data.basedOn;
                        this.localFormularyPolicyList.content.push(data.policyDetails);
                    }
                });
            } else {
                data.policyDetails.basedOn = data.basedOn;
                this.localFormularyPolicyList.content.push(data.policyDetails);
            }
            this.localFormularyPolicyList.content.forEach((element: any, index: number) => {
                element.id = index;
            });
            this.formularyPolicyList$.next(this.localFormularyPolicyList);
        }
        this.addBasedOnDialogOpen = false;
    }
    closeAddFormularyDrugDialog = (data: any) => {
        if (!data) {
            this.addFormularyDrugOpen = false;
            return;
        } else {
            const isDuplicateDrug = this.localFormularyDrugList.content.some((element: any) => {
                return element === data;
            });
            if (isDuplicateDrug) {
                this.dialogService.showErrorDialog('Drug Details already exists', (data: any) => { });
                return;
            }
            data.patientShare = data.patientShare != undefined ? data.patientShare : "-"
            this.localFormularyDrugList.content.push(data);
            this.localFormularyDrugList.content.forEach((element: any) => {
                element.id = element.drugCode;
            });
            this.formularyDrugList$.next(this.localFormularyDrugList);
        }
        this.addFormularyDrugOpen = false;
    }


    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 10;
    }

    createFormulary() {
        this.authService.showSystemLoader();
        let formularyCreateSuccessMessage = "";
        this.translate.get('prescription.formularySuccessfullyCreatedMsg').subscribe(text => formularyCreateSuccessMessage = text);

        let policyList: PolicyCreateRequestModel[] = [];
        this.localFormularyPolicyList.content.forEach(policy => {
            let newPolicy: PolicyCreateRequestModel = {
                policyName: policy.policyName!,
                policyClassName: policy.policyClassName != "-" ? policy.policyClassName! : "",
                policyNumber: policy.policyNumber!,
                policyHolderName: policy.policyName,
                policyType: policy.policyType,
                issueDate: policy.issueDate,
                startDate: policy.startDate,
                endDate: policy.endDate,
                policyClasses: policy.policyClasses,
            }
            policyList.push(newPolicy);
        });

        let drugList: { drugCode: string, drugName: string, genericName: string, price: string, patientShare: string }[] = [];
        this.localFormularyDrugList.content.forEach(drug => {
            let newDrug: { drugCode: string, drugName: string, genericName: string, price: string, patientShare: string } = {
                drugCode: drug.drugCode!,
                drugName: drug.drugName!,
                genericName: drug.genericName!,
                price: drug.price!,
                patientShare: drug.patientShare != '-' ? drug.patientShare! : ''
            }
            drugList.push(newDrug);
        });

        let payload = {
            formularyName: this.formularyName.value,
            memberDetails: this.memberdetails,
            policyDetails: policyList,
            drugDetails: drugList
        }
        this.formularyProviderSvc.createFormulary(payload).subscribe(data => {
            this.dialogService.showSuccessDialog(formularyCreateSuccessMessage, (data: any) => {
                if (data) {
                    this.authService.hideSystemLoader();
                    this.router.navigateByUrl("/formulary/list");
                }
            })
        }, (error) => {
            if (error.response.error.errorDescription) {
                this.dialogService.showErrorDialog(error.response.error.errorDescription, (data: any) => {
                    this.authService.hideSystemLoader();
                })
            } else {
                this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
            }
        }
        )
    }

    onPolicyListViewPageChange(event: { event: MouseEvent, pageNumber: number }) {
        this.formularyPolicyList.number = event.pageNumber;
        this.populatePolicyList();
    }
    onPolicyListViewPageSizeChange(event: any) {
        this.formularyPolicyList.size = Number(event.pageSize);
        this.policyDataControl.recordSize = Number(event.pageSize);
        this.populatePolicyList();
    }
    onDrugListViewPageChange(event: { event: MouseEvent, pageNumber: number }) {
        this.formularyDrugList.number = event.pageNumber;
        this.populateDrugList();
    }
    ondrugListViewPageSizeChange(event: any) {
        this.formularyDrugList.size = Number(event.pageSize);
        this.drugDataControl.recordSize = Number(event.pageSize);
        this.populateDrugList();
    }
}
