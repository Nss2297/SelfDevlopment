import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BasedOnAllExclusion, ExclusionDrugDetailsModel } from '../../drug-exclusion-models/exclusion-details.model';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { ExclusionServiceTsService } from '../../services/exclusion-service.ts.service';
import { DrugExclusionDetailsFilter } from '../../drug-exclusion-models/drugExclusionDetails-filter.model';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { exclusionDetailsModel } from '../../drug-exclusion-models/exclusion-details.model';
import { HttpException } from 'src/app/util/default-http-client';

@Component({
    selector: 'app-exclusion-list-details',
    templateUrl: './exclusion-list-details.component.html',
    styles: [
    ]
})
export class ExclusionListDetailsComponent implements OnInit {
    exclusionDrugId!: string;

    exclusionTypeList: ListViewModel<exclusionDetailsModel> = new ListViewModel<exclusionDetailsModel>();
    localExclusionTypeList: ListViewModel<exclusionDetailsModel> = new ListViewModel<exclusionDetailsModel>();
    exclusionTypeListDataControl: BasedOnAllExclusion = new BasedOnAllExclusion();
    exclusionTypeListPrototype = exclusionDetailsModel.prototype;
    exclusionDetail: { exclusionName: string, exclusionId: string } = {} as { exclusionName: string, exclusionId: string };
    basedOnDetail: { exclusionAsscId: string, exclusionType: string } = {} as { exclusionAsscId: string, exclusionType: string };
    trimmedExclusionType!: string;
    trimmednetworkExclusionName!: string;
    networkIdErrorMsg!: string;
    trimmedProviderExclusionName!: string;
    trimmedSpecialityExclusionName!: string;
    selectedExclusionType?: string;
    exclusionDetailName!: string;
    drugDeleteErrorMsg!: string;
    basedonDeleteErrorMsg!: string;
    addDrugDialogOpen = false;
    errorCode: string = '';
    updatedTo: any;
    filterDrugCode: any;
    trimmedDrugName: any;
    trimmedScientificName: any;
    trimmedScientificCode: any;
    addBasedOnDialogOpen = false;
    editExclusionListNameDialogOpen = false;
    isBasedOnFilterDrawerOpen = false;
    isDrugFilterDrawerOpen = false;
    previousDrugTotalCount?: number;
    previousBasedOnTotalCount?: number;
    subscriptions: Subscription[] = [];
    drugExclusionDetailsList: ListViewModel<ExclusionDrugDetailsModel> = new ListViewModel();
    drugExclusionListPrototype = ExclusionDrugDetailsModel.prototype;
    drugDataControl: DrugExclusionDetailsFilter = new DrugExclusionDetailsFilter();

    drugFilterForm: FormGroup = new FormGroup({
        drugName: new FormControl(),
        drugCode: new FormControl(),
        scientificName: new FormControl(),
        scientificCode: new FormControl(),
        updatedDateFrom: new FormControl(),
        updatedDateTo: new FormControl(),

    })


    exclusionFilterForm: FormGroup = new FormGroup({
        exclusionType: new FormControl(''),
        networkExclusionName: new FormControl(),
        providerExclusionName: new FormControl(),
        specialityExclusionName: new FormControl()
    })

    exclusionTypesOptions: { key: string, value: string }[] = [
        { key: "High Cost Medicine", value: "High Cost Medicine" },
        { key: "Network Exclusion", value: "Network Exclusion" },
        { key: "Provider Exclusion", value: "Provider Exclusion" },
        { key: "Speciality Exclusion", value: "Speciality Exclusion" }
    ];

    constructor(
        private router: Router,
        public activatedRoute: ActivatedRoute,
        private authService: AuthService,
        private translate: TranslateService,
        private dialogService: DialogService,
        public networkExclusionSvc: ExclusionServiceTsService

    ) { }

    ngOnInit(): void {
        this.authService.showSystemLoader();
        this.drugDataControl.recordSize = 5;
        this.exclusionTypeListDataControl.recordSize = 5;

        this.subscriptions.push(this.activatedRoute.params.subscribe(parameters => {
            this.exclusionDrugId = parameters['id'];
        }));

        this.subscriptions.push(this.networkExclusionSvc.drugDetailsList$.subscribe(data => {
            this.drugExclusionDetailsList = data;
        }));

        this.subscriptions.push(this.networkExclusionSvc.basedOnAllExclusion$.subscribe((data: any) => {
            this.exclusionTypeList = data;

            this.exclusionTypeList.content.forEach(exclusion => {
                exclusion.type = exclusion.exclusionType;
                exclusion.networkName = exclusion.exclusionType == "Network Exclusion" ? exclusion.exclusionName : "-";
                exclusion.providerName = exclusion.exclusionType == "Provider Exclusion" ? exclusion.exclusionName : "-";
                exclusion.speciality = exclusion.exclusionType == "Speciality Exclusion" ? exclusion.exclusionName : "-";
                exclusion.exclusionAsscId = exclusion.exclusionAsscId;
            });
        }));


        this.bindDrugExclusionName();

        //this.fetchData();
        this.authService.hideSystemLoader();

    }

    bindDrugExclusionName() {
        this.subscriptions.push(this.networkExclusionSvc.bindDrugExclusionListName(this.exclusionDrugId).subscribe((data: any) => {
            this.exclusionDetailName = data?.exclusionName;
            if (data) {
                this.exclusionDetail = {
                    exclusionId: this.exclusionDrugId!,
                    exclusionName: data?.exclusionName
                }
            }
        }));
    }



    applyFilterForDrug() {
        this.errorCode = '';
        this.previousDrugTotalCount = this.drugExclusionDetailsList.totalElements!;
        let drugCode = this.drugFilterForm.controls['drugCode'].value;
        let trimmedDrugCode = drugCode?.replace(/^\s+|\s+$/g, '');
        this.filterDrugCode = trimmedDrugCode;
        this.drugDataControl.drugCode = trimmedDrugCode;
        this.drugFilterForm.controls['drugCode'].setValue(trimmedDrugCode);

        this.drugDataControl.updatedDateFrom = moment(this.drugFilterForm.controls['updatedDateFrom'].value).format("DD-MM-yyyy");
        this.drugDataControl.updatedDateTo = moment(this.drugFilterForm.controls['updatedDateTo'].value).format("DD-MM-yyyy");
        let drugName = this.drugFilterForm.controls['drugName'].value;
        this.trimmedDrugName = drugName?.replace(/^\s+|\s+$/g, '');
        this.drugDataControl.drugName = this.trimmedDrugName;
        this.drugFilterForm.controls['drugName'].setValue(this.trimmedDrugName);

        let scientificName = this.drugFilterForm.controls['scientificName'].value;
        this.trimmedScientificName = scientificName?.replace(/^\s+|\s+$/g, '');
        this.drugDataControl.scientificName = this.trimmedScientificName;
        this.drugFilterForm.controls['scientificName'].setValue(this.trimmedScientificName);
        let scientificCode = this.drugFilterForm.controls['scientificCode'].value;
        this.trimmedScientificCode = scientificCode?.replace(/^\s+|\s+$/g, '');
        this.drugDataControl.scientificCode = this.trimmedScientificCode;
        this.drugFilterForm.controls['scientificCode'].setValue(this.trimmedScientificCode);





        if ((this.drugDataControl.updatedDateFrom > this.drugDataControl.updatedDateTo)) {
            this.translate.get('prescription.updatedDateFromGreaterThanupdatedDateTo').subscribe(text => this.errorCode = text);
        }
        if ((this.drugDataControl.updatedDateFrom === "Invalid date" && this.drugDataControl.updatedDateTo !== "Invalid date") ||
            (this.drugDataControl.updatedDateFrom !== "Invalid date" && this.drugDataControl.updatedDateTo === "Invalid date")) {
            this.translate.get('prescription.missigDatePairs').subscribe(text => this.errorCode = text);
        }
        if (this.errorCode === '') {
            this.fetchData();
            this.isDrugFilterDrawerOpen = false;
        }
        //  this.fetchData('DRUGS');

    }


    resetFilter() {

        this.drugDataControl.drugName = undefined;
        this.drugDataControl.drugCode = undefined;
        this.drugDataControl.scientificName = undefined;
        this.drugDataControl.scientificCode = undefined;
        this.drugDataControl.updatedDateFrom = undefined;
        this.drugDataControl.updatedDateTo = undefined;

        this.drugFilterForm.setValue({
            'drugName': null,
            'drugCode': null,
            'scientificName': null,
            'scientificCode': null,
            'updatedDateFrom': null,
            'updatedDateTo': null,

        })
    }


    changeExclusionType(selectType: any) {
        this.exclusionFilterForm.get('networkExclusionName')?.reset();
        this.exclusionFilterForm.get('providerExclusionName')?.reset();
        this.exclusionFilterForm.get('specialityExclusionName')?.reset();
    }

    applyFilterForExclusion() {
        this.previousBasedOnTotalCount = this.exclusionTypeList.totalElements!;
        let exclusionType = this.exclusionFilterForm.controls['exclusionType'].value;
        this.exclusionTypeListDataControl.exclusionType = exclusionType;
        this.exclusionTypeListDataControl.exclusionName = "";


        let networkExclusionName = this.exclusionFilterForm.controls['networkExclusionName'].value;
        if (networkExclusionName && networkExclusionName != "") {
            this.trimmednetworkExclusionName = networkExclusionName?.replace(/^\s+|\s+$/g, '');
            this.exclusionTypeListDataControl.exclusionName = this.trimmednetworkExclusionName;
            this.exclusionFilterForm.controls['networkExclusionName'].setValue(this.trimmednetworkExclusionName);
        }

        let providerExclusionName = this.exclusionFilterForm.controls['providerExclusionName'].value;
        if (providerExclusionName && providerExclusionName != "") {
            this.trimmedProviderExclusionName = providerExclusionName?.replace(/^\s+|\s+$/g, '');
            this.exclusionTypeListDataControl.exclusionName = this.trimmedProviderExclusionName;
            this.exclusionFilterForm.controls['providerExclusionName'].setValue(this.trimmedProviderExclusionName);
        }

        let specialityExclusionName = this.exclusionFilterForm.controls['specialityExclusionName'].value;
        if (specialityExclusionName && specialityExclusionName != "") {
            this.trimmedSpecialityExclusionName = specialityExclusionName?.replace(/^\s+|\s+$/g, '');
            const words = this.trimmedSpecialityExclusionName?.split(' ');
            this.exclusionTypeListDataControl.exclusionName = "";
            this.exclusionTypeListDataControl.exclusionName = this.trimmedSpecialityExclusionName;
        }

        this.fetchData('BASEDON');
        this.isBasedOnFilterDrawerOpen = false;
    }

    exclusionResetFilter() {
        this.exclusionTypeListDataControl.exclusionType = undefined;
        this.exclusionTypeListDataControl.exclusionName = undefined;
        this.exclusionFilterForm.setValue({
            'exclusionType': null,
            'networkExclusionName': null,
            'providerExclusionName': null,
            'specialityExclusionName': null
        })
    }



    fetchData(tableName?: string) {
        this.authService.hideSystemLoader();
        switch (tableName) {
            case 'DRUGS':
                this.networkExclusionSvc.getExclusionDrugList(this.exclusionDrugId, this.drugDataControl).subscribe();
                break;
            case 'BASEDON':
                this.networkExclusionSvc.getAllExclusions(this.exclusionDrugId, this.exclusionTypeListDataControl).subscribe();
                break;

            default:
                this.networkExclusionSvc.getExclusionDrugList(this.exclusionDrugId, this.drugDataControl).subscribe();
                this.networkExclusionSvc.getAllExclusions(this.exclusionDrugId, this.exclusionTypeListDataControl).subscribe();
                break;
        }
    }

    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 10;
    }

    onBackClick = () => {
        this.router.navigateByUrl('/drug-exclusion-management/list');
    }

    closeAddDrugDialog = (drugData: any) => {
        if (drugData) {
            let drugSuccessMsg = '';
            this.authService.showSystemLoader();
            this.networkExclusionSvc.addDrug(this.exclusionDrugId, drugData).subscribe(res => {
                this.translate.get('prescription.drugSuccessMsg', { value: drugData.drugName + "<br>" + drugData.drugCode }).subscribe((text: string) => drugSuccessMsg = text);
                this.fetchData('DRUGS');
                this.addDrugDialogOpen = false;
                this.dialogService.showSuccessDialog(drugSuccessMsg, (data: any) => {
                    this.authService.hideSystemLoader();
                })
            }, (error) => {
                if (error.response.error.errors) {
                    this.addDrugDialogOpen = false;
                    this.dialogService.showErrorDialog(error.response.error.errors, (data: any) => {
                        this.authService.hideSystemLoader();
                    })
                } else {
                    this.addDrugDialogOpen = false;
                    this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => {
                        this.authService.hideSystemLoader();
                    })
                }
            })
        } else {
            this.addDrugDialogOpen = false;
        }
    }

    closeAddBasedOnDialog = (data: any) => {
        if (!data) {
            this.addBasedOnDialogOpen = false;
        }
        else {
            if (data.basedOn == "network-exclusion") {
                let networkExclusionSuccessMsg = '';
                this.authService.showSystemLoader();
                this.networkExclusionSvc.addNetworkExclusion(this.exclusionDrugId, data.formData).subscribe(res => {
                    this.translate.get('prescription.networkExclusionSuccessMsg').subscribe((text: string) => networkExclusionSuccessMsg = text);
                    this.fetchData('BASEDON');
                    this.dialogService.showSuccessDialog(networkExclusionSuccessMsg, (data: any) => {
                        if (data) {
                            this.addBasedOnDialogOpen = false;
                            this.authService.hideSystemLoader();
                        }
                    })
                }, (error) => {
                    if (error.response.error.errors) {
                        this.dialogService.showErrorDialog(error.response.error.errors[0], (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    } else {
                        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    }
                })
            }
            else if (data.basedOn == "provider-exclusion") {
                let providerExclusionSuccessMsg = '';
                this.authService.showSystemLoader();
                this.networkExclusionSvc.addProviderExclusion(this.exclusionDrugId, data.formData).subscribe(res => {
                    this.translate.get('prescription.providerExclusionSuccessMsg').subscribe((text: string) => providerExclusionSuccessMsg = text);
                    this.fetchData('BASEDON');
                    this.dialogService.showSuccessDialog(providerExclusionSuccessMsg, (data: any) => {
                        if (data) {
                            this.addBasedOnDialogOpen = false;
                            this.authService.hideSystemLoader();
                        }
                    })
                }, (error) => {
                    if (error.response.error.errors) {
                        this.dialogService.showErrorDialog(error.response.error.errors[0], (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    } else {
                        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    }
                })
            }

            else if (data.basedOn == "high-cost") {
                let highCostMedicineSuccessMsg = '';
                this.authService.showSystemLoader();
                this.networkExclusionSvc.addHighCostMedicine(this.exclusionDrugId).subscribe(res => {
                    this.translate.get('prescription.highCostMedicineSuccessMsg').subscribe((text: string) => highCostMedicineSuccessMsg = text);
                    this.fetchData('BASEDON');
                    this.dialogService.showSuccessDialog(highCostMedicineSuccessMsg, (data: any) => {
                        if (data) {
                            this.addBasedOnDialogOpen = false;
                            this.authService.hideSystemLoader();
                        }
                    })
                }, (error) => {
                    if (error.response.error.errors) {
                        this.dialogService.showErrorDialog(error.response.error.errors[0], (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    } else {
                        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    }
                })
            } else if (data.basedOn == "speciality-exclusion") {
                let specialityExclusionSuccessMsg = '';
                this.authService.showSystemLoader();
                this.networkExclusionSvc.addSpecialityExclusion(this.exclusionDrugId, data.formData).subscribe(res => {
                    this.translate.get('prescription.specialityExclusionSuccessMsg').subscribe((text: string) => specialityExclusionSuccessMsg = text);
                    this.fetchData('BASEDON');
                    this.dialogService.showSuccessDialog(specialityExclusionSuccessMsg, (data: any) => {
                        if (data) {
                            this.addBasedOnDialogOpen = false;
                            this.authService.hideSystemLoader();
                        }
                    })
                }, (error) => {
                    if (error.response.error.errors) {
                        this.dialogService.showErrorDialog(error.response.error.errors[0], (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    } else {
                        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    }
                })
            }

        }

        this.addBasedOnDialogOpen = false;
    }

    closeEditExclusionListNameDialog = (data: any) => {
        this.bindDrugExclusionName();
        this.editExclusionListNameDialogOpen = false;
    }

    onDrugDelete(id: any) {
        if ((this.previousDrugTotalCount && this.previousDrugTotalCount > this.drugExclusionDetailsList.totalElements!) || this.drugExclusionDetailsList.totalElements! > 1) {
            this.dialogService.showDeleteConfirmDialog((data: any) => {
                if (data) {
                    this.authService.showSystemLoader();
                    this.networkExclusionSvc.deleteDrug(id).subscribe({
                        next: (data) => {
                            const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                            let drugDetailDeleteMsg = '';
                            this.translate.get('prescription.drugDetailDeleteMsg').subscribe(text => drugDetailDeleteMsg = text);
                            let drugDataIndex = this.drugExclusionDetailsList.content.findIndex(x => x.drugExclusionDetailsId == id)
                            if (drugDataIndex != -1) {
                                this.drugExclusionDetailsList.content.splice(drugDataIndex, 1);
                                if (this.drugExclusionDetailsList.content.length == 0 && this.drugDataControl.pageNumber > 0) {
                                    this.drugExclusionDetailsList.number -= 1;
                                    this.drugDataControl.pageNumber = this.drugExclusionDetailsList.number;
                                }
                            }
                            this.resetFilter();
                            this.fetchData('DRUGS')
                            //this.applyFilterForDrug();
                            if (this.previousDrugTotalCount) {
                                this.previousDrugTotalCount = undefined;
                            }
                            this.dialogService.showSuccessDialog(drugDetailDeleteMsg, (data: any) => {
                                if (data) {
                                    this.authService.hideSystemLoader();
                                }
                            })
                        },
                        error: (exception) => {
                            if (exception instanceof HttpException) {
                                this.dialogService.showErrorDialog(exception.response.error.errorDescription, () => {
                                    this.authService.hideSystemLoader();
                                })
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

    deleteBasedOnDetail(id: any) {
        if ((this.previousBasedOnTotalCount && this.previousBasedOnTotalCount > this.exclusionTypeList.totalElements!) || this.exclusionTypeList.totalElements! > 1) {
            this.dialogService.showDeleteConfirmDialog((data: any) => {
                if (data) {
                    this.authService.showSystemLoader();
                    let payload: { exclusionType: string, exclusionAsscId: string } = {} as { exclusionType: string, exclusionAsscId: string };
                    let exclusiontobeDeleted = this.exclusionTypeList.content.filter(x => x.exclusionAsscId == id)[0];
                    payload.exclusionAsscId = exclusiontobeDeleted.exclusionAsscId!;
                    payload.exclusionType = exclusiontobeDeleted.exclusionType!;
                    this.networkExclusionSvc.deleteBasedOnDetailData(payload).subscribe({
                        next: (data) => {
                            const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                            let drugExclusionDetailDeleteMsg = '';
                            this.translate.get('prescription.drugExclusionDetailDeleteMsg').subscribe(text => drugExclusionDetailDeleteMsg = text);
                            let exclusionDataIndex = this.exclusionTypeList.content.findIndex(x => x.exclusionAsscId == id)
                            if (exclusionDataIndex != -1) {
                                this.exclusionTypeList.content.splice(exclusionDataIndex, 1);
                                if (this.exclusionTypeList.content.length == 0 && this.exclusionTypeListDataControl.pageNumber! > 0) {
                                    this.exclusionTypeList.number -= 1;
                                    this.exclusionTypeListDataControl.pageNumber = this.exclusionTypeList.number;
                                }
                            }
                            this.exclusionResetFilter();
                            this.fetchData('BASEDON');
                            if (this.previousBasedOnTotalCount) {
                                this.previousBasedOnTotalCount = undefined;
                            }
                            this.dialogService.showSuccessDialog(drugExclusionDetailDeleteMsg, (data: any) => {
                                if (data) {
                                    this.authService.hideSystemLoader();
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
        } else {
            this.translate.get('prescription.basedonDeleteErrorMsg').subscribe(text => this.basedonDeleteErrorMsg = text);
            this.dialogService.showWarningDialog(this.basedonDeleteErrorMsg, () => { })
        }
    }


    deleteExclusion() {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.networkExclusionSvc.deleteExclusion(this.exclusionDrugId).subscribe({
                    next: (data) => {
                        let drugExclusionTypeDeletemsg = '';
                        this.translate.get('prescription.drugExclusionTypeDeletemsg').subscribe(text => drugExclusionTypeDeletemsg = text);
                        this.dialogService.showSuccessDialog(drugExclusionTypeDeletemsg, (data: any) => {
                            if (data) {
                                this.authService.showSystemLoader();
                                this.router.navigate(['/drug-exclusion-management/list']);
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

}
