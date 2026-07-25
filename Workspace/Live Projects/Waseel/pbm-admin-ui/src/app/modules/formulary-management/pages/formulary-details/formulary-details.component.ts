import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { HttpException } from 'src/app/util/default-http-client';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { AddPrescriptionStoreService } from 'src/app/modules/prescription/service/add-prescription-store/add-prescription-store.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { FormularyDetailsModel, FormularyDrugDetailsModel, FormularyPolicyDetailsModel } from '../../models/formulary-details.model';
import { FormularyDetailsFilter } from '../../models/formulary-details-filter.model';
import { formularyProviderService } from '../../Services/formulary-provider-service';

@Component({
    selector: 'app-formulary-details',
    templateUrl: './formulary-details.component.html',
    styles: [
    ]
})

export class FormularyDetailsComponent implements OnInit {
    addBasedOnDialogOpen = false;
    addFormularyDrugOpen = false;
    editFormularyNameDialogOpen = false;
    isBasedOnFilterDrawerOpen = false
    isDrugFilterDrawerOpen = false;

    formularyId!: string;
    formularyDetails!: FormularyDetailsModel;
    formularyDrugList: ListViewModel<FormularyDrugDetailsModel> = new ListViewModel();
    formularyDrugListPrototype = FormularyDrugDetailsModel.prototype;
    formularyPolicyList: ListViewModel<FormularyPolicyDetailsModel> = new ListViewModel();
    formularyPolicyListPrototype = FormularyPolicyDetailsModel.prototype;
    subscriptions: Subscription[] = [];
    policyDataControl: FormularyDetailsFilter = new FormularyDetailsFilter();
    drugDataControl: FormularyDetailsFilter = new FormularyDetailsFilter();
    formularyDetail!: { formularyName: string, formularyId: string };
    drugDeleteErrorMsg!: string;
    trimmedPolicyName: any;
    trimmedPolicyClassName: any;
    trimmedDrugName: any;
    trimmedGenericName: any;
    filterDrugCode: any;
    policyNumberErrorMsg: string = "";
    policyMemberIdErrorMsg: string = "";
    previousDrugTotalCount?: number;

    policyFilterForm: FormGroup = new FormGroup({
        policyName: new FormControl(),
        policyNumber: new FormControl(),
        policyClassName: new FormControl(),
        policyMemberId: new FormControl()
    })

    drugFilterForm: FormGroup = new FormGroup({
        drugName: new FormControl(),
        drugCode: new FormControl(),
        genericName: new FormControl()
    })



    constructor(
        private router: Router,
        private dialogService: DialogService,
        private activatedRoute: ActivatedRoute,
        private formularyProviderSvc: formularyProviderService,
        private authService: AuthService,
        private translate: TranslateService,
        public storeService: AddPrescriptionStoreService,
    ) {
    }

    ngOnInit(): void {

        this.drugDataControl.recordSize = 5;
        this.policyDataControl.recordSize = 5;

        this.subscriptions.push(this.activatedRoute.params.subscribe(params => {
            this.formularyId = params['id'];
        }));

        this.subscriptions.push(this.formularyProviderSvc.drugList$.subscribe(data => {
            if (data.content.length > 0) {
                data.content.forEach(element => {
                    element.patientShare = element.patientShare != undefined ? element.patientShare : "-"
                });
            }
            this.formularyDrugList = data;
        }));

        this.subscriptions.push(this.formularyProviderSvc.policyList$.subscribe(data => {
            this.formularyPolicyList = data;
            this.formularyPolicyList.content.forEach(policy => {
                policy.idNumber = policy.idNumber ? policy.idNumber : "-";
                policy.policyClassName = policy.policyClassName ? policy.policyClassName : "-";
            });
        }));

        this.subscriptions.push(this.formularyProviderSvc.metaDataDetails$.subscribe(data => {
            this.formularyDetails = data;
            if (data) {
                this.formularyDetail = {
                    formularyId: data.formularyId?.toString(),
                    formularyName: data.formularyName
                }
            }
        }));

        this.fetchData();

    }

    onDrugDelete(id: any) {
        if ((this.previousDrugTotalCount && this.previousDrugTotalCount > this.formularyDrugList.totalElements!) || this.formularyDrugList.totalElements! > 1) {
            this.dialogService.showDeleteConfirmDialog((data: any) => {
                if (data) {
                    this.formularyProviderSvc.deleteDrug(id).subscribe({
                        next: (data) => {
                            const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                            let drugDataIndex = this.formularyDrugList.content.findIndex(x => x.id == id)
                            if (drugDataIndex != -1) {
                                this.formularyDrugList.content.splice(drugDataIndex, 1);
                                if (this.formularyDrugList.content.length == 0 && this.drugDataControl.pageNumber > 0) {
                                    this.drugDataControl.pageNumber -= 1;
                                }
                            }
                            let drugDetailDeleteMsg = '';
                            this.translate.get('prescription.drugDetailDeleteMsg').subscribe(text => drugDetailDeleteMsg = text);
                            this.resetFilterForDrug();
                            this.fetchData('DRUGS');
                            if (this.previousDrugTotalCount) {
                                this.previousDrugTotalCount = undefined;
                            }
                            this.dialogService.showSuccessDialog(drugDetailDeleteMsg, (data: any) => {

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
        } else {
            this.translate.get('prescription.drugDeleteErrorMsg').subscribe(text => this.drugDeleteErrorMsg = text);
            this.dialogService.showWarningDialog(this.drugDeleteErrorMsg, () => { })
        }
    }

    fetchData(tableName?: string) {
        this.authService.showSystemLoader();
        switch (tableName) {
            case 'DRUGS':
                this.formularyProviderSvc.getFormularyDrugList(this.formularyId, this.drugDataControl).subscribe();
                break;

            case 'POLICIES':
                this.formularyProviderSvc.getFormularyPolicyList(this.formularyId, this.policyDataControl).subscribe();
                break;

            default:
                this.formularyProviderSvc.getFormularyDrugList(this.formularyId, this.drugDataControl).subscribe();
                this.formularyProviderSvc.getFormularyPolicyList(this.formularyId, this.policyDataControl).subscribe();
                break;
        }
        this.formularyProviderSvc.getFormularyMetadataDetails(this.formularyId).subscribe((x: any) => {
            this.authService.hideSystemLoader();
        });
    }

    applyFilterForPolicy() {
        let policyName = this.policyFilterForm.controls['policyName'].value;
        if (policyName && policyName != "") {
            this.trimmedPolicyName = policyName?.replace(/^\s+|\s+$/g, '');
            const words = this.trimmedPolicyName?.split(' ');
            this.policyDataControl.policyName = "";
            this.policyDataControl.policyName = words.map((word: any) => {
                return word[0]?.toUpperCase() + word.substring(1);
            }).join(" ");
        }

        let policyNumber = this.policyFilterForm.controls['policyNumber'].value;
        if (policyNumber && policyNumber != "") {
            if (policyNumber && !isNaN(policyNumber)) {
                this.policyDataControl.policyNumber = policyNumber;
            }
            else {
                this.translate.get("prescription.policyNumberErrorInFilter").subscribe((msg: any) => {
                    this.policyNumberErrorMsg = msg;
                })
                this.isBasedOnFilterDrawerOpen = true;
                return;
            }
        }


        let policyClassName = this.policyFilterForm.controls['policyClassName'].value;
        if (policyClassName && policyClassName != "") {
            this.trimmedPolicyClassName = policyClassName?.replace(/^\s+|\s+$/g, '');
            this.policyDataControl.policyClassName = this.trimmedPolicyClassName;
        }

        let policyMemberId = this.policyFilterForm.controls['policyMemberId'].value;
        if (policyMemberId && policyMemberId != "") {
            if (policyMemberId && !isNaN(policyMemberId)) {
                this.policyDataControl.policyMemberId = policyMemberId;
            }
            else {
                this.translate.get("prescription.policyMemberIdErrorInFilter").subscribe((msg: any) => {
                    this.policyMemberIdErrorMsg = msg;
                })
                this.isBasedOnFilterDrawerOpen = true;
                return;
            }
        }

        this.fetchData('POLICIES');
        this.isBasedOnFilterDrawerOpen = false;

    }

    resetFilterForPolicy() {
        this.policyDataControl.policyName = undefined;
        this.policyDataControl.policyNumber = undefined;
        this.policyDataControl.policyClassName = undefined;
        this.policyDataControl.policyMemberId = undefined;

        this.policyFilterForm.setValue({
            'policyName': null,
            'policyNumber': null,
            'policyClassName': null,
            'policyMemberId': null
        })
    }

    applyFilterForDrug() {
        this.previousDrugTotalCount = this.formularyDrugList.totalElements!;
        let drugCode = this.drugFilterForm.controls['drugCode'].value;
        let trimmedDrugCode = drugCode?.replace(/^\s+|\s+$/g, '');
        this.filterDrugCode = trimmedDrugCode;
        this.drugDataControl.drugCode = trimmedDrugCode;
        this.drugFilterForm.controls['drugCode'].setValue(trimmedDrugCode);

        let drugName = this.drugFilterForm.controls['drugName'].value;
        this.trimmedDrugName = drugName?.replace(/^\s+|\s+$/g, '');
        this.drugDataControl.drugName = this.trimmedDrugName;
        this.drugFilterForm.controls['drugName'].setValue(this.trimmedDrugName);

        let genericName = this.drugFilterForm.controls['genericName'].value;
        this.trimmedGenericName = genericName?.replace(/^\s+|\s+$/g, '');
        this.drugDataControl.genericName = this.trimmedGenericName;
        this.drugFilterForm.controls['genericName'].setValue(this.trimmedGenericName);

        this.fetchData('DRUGS');
        this.isDrugFilterDrawerOpen = false;
    }

    resetFilterForDrug() {
        this.drugDataControl.drugName = undefined;
        this.drugDataControl.drugCode = undefined;
        this.drugDataControl.genericName = undefined;

        this.drugFilterForm.setValue({
            'drugName': null,
            'drugCode': null,
            'genericName': null
        })
    }


    closeAddBasedOnDialog = (data: any) => {
        this.addBasedOnDialogOpen = false;
        console.log("--------------------------------------***", data)
        if (data) {
            this.formularyProviderSvc.updatePolicyDetail(this.formularyId, data).subscribe(data => {
                console.log(data)
            })
        }
    }

    closeAddFormularyDrugDialog = (drugData: any) => {
        if (drugData) {
            let drugSuccessMsg = '';
            this.formularyProviderSvc.addDrug(this.formularyId, drugData).subscribe(res => {
                this.translate.get('prescription.drugSuccessMsg', { value: drugData.drugName + "<br>" + drugData.drugCode }).subscribe((text: string) => drugSuccessMsg = text);
                this.dialogService.showSuccessDialog(drugSuccessMsg, (data: any) => {
                    if (data) {
                        this.fetchData('DRUGS')
                        this.addFormularyDrugOpen = false;
                    }
                })
            }, (error) => {
                if (error.response.error.errorDescription) {
                    this.dialogService.showErrorDialog(error.response.error.errorDescription, (data: any) => { })
                } else {
                    this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
                }
            })
        } else {
            this.addFormularyDrugOpen = false;
        }
    }


    closeEditFormularyNameDialog = (data: any) => {
        this.fetchData();
        this.editFormularyNameDialogOpen = false;
    }

    onBackClick = () => {
        this.router.navigateByUrl('/formulary/list');
    }

    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 10;
    }

    deleteDrugFormulary() {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.formularyProviderSvc.deleteFormulary(this.formularyId).subscribe({
                    next: (data) => {
                        let drugFormularyMsg = '';
                        this.translate.get('prescription.formularyRequestcancel').subscribe(text => drugFormularyMsg = text);
                        this.dialogService.showSuccessDialog(drugFormularyMsg, (data: any) => {
                            if (data) {
                                this.authService.showSystemLoader();
                                this.router.navigate(['/formulary/list']);
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

    deleteData(itemId: String) {
        // this.dialogService.showDeleteConfirmDialog((data: any) => {
        //   this.dialogService.hideAlertConfirmDialog();
        //   if (data) {
        //     this.deleteApiCall(itemId);
        //   }
        // });
    }
}