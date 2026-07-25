import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Subscription } from 'rxjs';
import { SfdaManagementService } from '../../service/sfda-management.service';
import { SfdaDrugDetailsModel, SfdaDrugListDetailsModel, SfdaDrugListRequestModel } from '../../models/sfda-drug-details.model';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { TranslateService } from '@ngx-translate/core';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';
import { FormControl } from '@angular/forms';
@Component({
    selector: 'app-sfda-details',
    templateUrl: './sfda-details.component.html',
    styles: [
    ]
})
export class SfdaDetailsComponent {
    sfdaDrugDetailsDialogOpen = false;
    addSfdaDrugDialogOpen = false;
    sfdaDrugDetailsDialogData?: SfdaDrugListDetailsModel;
    subscriptions: Subscription[] = [];
    sfdaDrugListData$: BehaviorSubject<ListViewModel<SfdaDrugListDetailsModel>> = new BehaviorSubject(new ListViewModel<SfdaDrugListDetailsModel>());
    sfdaDrugListData: ListViewModel<SfdaDrugListDetailsModel> = new ListViewModel();
    sfdaDrugListDataForSearch: ListViewModel<SfdaDrugListDetailsModel> = new ListViewModel();
    sfdaDrugListPrototype = SfdaDrugListDetailsModel.prototype;
    dataControl: SfdaDrugListRequestModel = new SfdaDrugListRequestModel();
    listIsLoading: boolean = false;
    noContentSubtitle: boolean = true;
    drugListId!: number;
    drugInfoData: SfdaDrugDetailsModel = new SfdaDrugDetailsModel;
    addEditSfdaDrugDetailsDialogMode: 'add' | 'edit' = 'add';
    selectedWaseelDrugId?: string;
    updateSfdaDetailsMsg!: string;
    addSfdaDetailsMsg!: string;
    searchFormControl: FormControl = new FormControl();
    getDrugsSubscription?: Subscription;
    searchButtonClicked = false;

    constructor(
        private dialogService: DialogService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private sfdaManagementSVC: SfdaManagementService,
        private authService: AuthService,
        private translate: TranslateService,
        public dssLovService: DssLovService
    ) { }

    ngOnInit(): void {
        this.dataControl.pageNumber = 0;
        this.dataControl.recordSize = 10;
        this.subscriptions.push(this.activatedRoute.params.subscribe(params => {
            this.drugListId = params['id'];
        }));

        this.authService.showSystemLoader();
        this.fetchData();
        this.fetchAllDropdownValues();
    }

    fetchData() {
        this.sfdaManagementSVC.getSfdaDrugListDetails(this.drugListId, this.dataControl).subscribe((data: any) => {
            this.sfdaDrugListData.content = [];
            this.sfdaDrugListDataForSearch.content = [];

            this.drugInfoData.id = data.id;
            this.drugInfoData.effectiveDate = data.effectiveDate;
            this.drugInfoData.uploadDate = data.uploadDate;

            if (data.drugs.content && data.drugs.content.length > 0) {
                this.sfdaDrugListData.totalPages = data.drugs.totalPages;
                this.sfdaDrugListData.number = data.drugs.number;
                data.drugs.content.forEach((drugData: any) => {
                    let sfdaDrugData = new SfdaDrugListDetailsModel();
                    sfdaDrugData.sfdaCode = drugData.sfdaCode;
                    sfdaDrugData.gtinCode = drugData.gtinCode;
                    sfdaDrugData.tradeName = drugData.tradeName;
                    sfdaDrugData.scientificName = drugData.scientificName;
                    sfdaDrugData.price = drugData.price;
                    sfdaDrugData.waseelDrugId = drugData.waseelDrugId;
                    sfdaDrugData.scientificCode = drugData.scientificCode;
                    if (this.sfdaDrugListData.content.findIndex(x => x.sfdaCode == drugData.sfdaCode) == -1) {
                        this.sfdaDrugListData.content.push(sfdaDrugData);
                    }
                    this.sfdaDrugListDataForSearch.content.push(sfdaDrugData);
                });
                this.sfdaDrugListData$.next(this.sfdaDrugListData);
                this.authService.hideSystemLoader();
            }
        })
    }

    onBackClick = () => {
        this.router.navigateByUrl('/sfda/list');
    }


    searchDrug(event: any) {
        if (this.searchFormControl.value != null) {
            if (this.getDrugsSubscription != null) {
                this.getDrugsSubscription.unsubscribe();
                this.sfdaManagementSVC.drugsList$.next(new ListViewModel<SfdaDrugListDetailsModel>());
            }
            this.authService.showSystemLoader();
            this.dataControl.pageNumber = 0;
            this.dataControl.searchValue = this.searchFormControl.value;
            this.getDrugsSubscription = this.sfdaManagementSVC.getSfdaDrugListDetails(this.drugListId, this.dataControl)
                .subscribe({
                    next: (value) => { this.getDrugsSubscription = undefined; this.fetchData(); },
                    error: (value) => {
                        this.getDrugsSubscription = undefined;
                        this.sfdaDrugListData$.next(new ListViewModel<SfdaDrugListDetailsModel>());
                        this.authService.hideSystemLoader();
                    },
                });
        } else {
            this.sfdaManagementSVC.drugsList$.next(new ListViewModel<SfdaDrugListDetailsModel>());
        }
        this.searchButtonClicked = true;
    }

    resetSearch() {
        this.authService.showSystemLoader();
        this.searchFormControl.reset();
        this.dataControl.pageNumber = 0;
        this.dataControl.searchValue = "";
        this.fetchData();
        this.searchButtonClicked = false;
    }

    fetchAllDropdownValues() {
        this.dssLovService.getListOfValuesByListKey("packageTypes")?.subscribe();
        this.dssLovService.getListOfValuesByListKey("strengthUnit")?.subscribe();
        this.dssLovService.getListOfValuesByListKey("dosageForm")?.subscribe();

    }

    openSfdaDrugDetailsDialog(waseelDrugId: string) {
        this.authService.showSystemLoader();
        this.sfdaManagementSVC.getSpecificDrugDetailsData(waseelDrugId, this.drugListId).subscribe({
            next: (result: any) => {
                this.sfdaDrugDetailsDialogData = result;
                this.sfdaDrugDetailsDialogOpen = true;
                this.authService.hideSystemLoader();
            },
            error: (error) => { }
        })
    }

    openEditSfdaDrugDetailsDialog(waseelDrugId: string) {
        this.selectedWaseelDrugId = waseelDrugId;
        this.authService.showSystemLoader();
        this.sfdaManagementSVC.getSpecificDrugDetailsData(waseelDrugId, this.drugListId).subscribe({
            next: (result: any) => {
                console.log(result);
                this.sfdaDrugDetailsDialogData = result;
                this.addSfdaDrugDialogOpen = true;
                this.addEditSfdaDrugDetailsDialogMode = 'edit';
                this.authService.hideSystemLoader();
            },
            error: (error) => { }
        })
    }

    closeSfdaDrugDetailsDialog = (data: any) => {
        this.sfdaDrugDetailsDialogOpen = false;
    }

    closeAddSfdaDrugDialog = (data: any) => {
        if (this.addEditSfdaDrugDetailsDialogMode == "edit") {
            if (data) {
                this.authService.showSystemLoader();
                this.sfdaManagementSVC.updateSfdaDrugDetails(this.selectedWaseelDrugId!, this.drugListId, data).subscribe(data => {
                    this.sfdaDrugListData.content = [];
                    this.fetchData();
                    this.addSfdaDrugDialogOpen = false;
                    this.translate.get('SFDA-LIST-DETAILS-UPDATE').subscribe(text => this.updateSfdaDetailsMsg = text);
                    this.dialogService.showSuccessDialog(this.updateSfdaDetailsMsg, (event: any) => {
                        this.authService.hideSystemLoader();
                    })
                }, (error) => {
                    if (error.response.error.errors) {
                        this.addSfdaDrugDialogOpen = false;
                        this.dialogService.showErrorDialog(error.response.error.errors, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    } else {
                        this.addSfdaDrugDialogOpen = false;
                        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    }
                })
            } else {
                this.addSfdaDrugDialogOpen = false;
            }
        } else {
            if (data) {
                this.authService.showSystemLoader();
                this.sfdaManagementSVC.addNewSfdaDrugDetails(this.drugListId, data).subscribe(data => {
                    this.sfdaDrugListData.content = [];
                    this.fetchData();
                    this.addSfdaDrugDialogOpen = false;
                    this.translate.get('SFDA-LIST-DETAILS-ADD').subscribe(text => this.addSfdaDetailsMsg = text);
                    this.dialogService.showSuccessDialog(this.addSfdaDetailsMsg, (event: any) => {
                        this.authService.hideSystemLoader();
                    })
                }, (error) => {
                    if (error.response.error.errors) {
                        this.addSfdaDrugDialogOpen = false;
                        this.dialogService.showErrorDialog(error.response.error.errors, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    } else {
                        this.addSfdaDrugDialogOpen = false;
                        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => {
                            this.authService.hideSystemLoader();
                        })
                    }
                })
            } else {
                this.addSfdaDrugDialogOpen = false;
            }
        }
    }

    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 10;
    }
    deleteData() {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader()
                this.sfdaManagementSVC.deleteSFDADetails(this.drugListId).subscribe({
                    next: (data) => {

                        let sfdaDeletemsg = '';
                        this.translate.get('prescription.sfdaDeletemsg').subscribe(text => sfdaDeletemsg = text);
                        this.dialogService.showSuccessDialog(sfdaDeletemsg, (data: any) => {
                            if (data) {
                                this.authService.showSystemLoader();
                                this.router.navigate(['/sfda/list']);
                                this.fetchData()
                            }
                        })
                    },
                    error: (error) => {
                        this.dialogService.showErrorDialog(error.error.errors, (data: any) => { })
                    }
                })

            } else {
                return;
            }
        })
    }


    deleteDrugdata(waseelDrugId: string) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader()
                this.sfdaManagementSVC.deletedrug(this.drugListId, waseelDrugId).subscribe({
                    next: (data) => {

                        let drugDetailDeleteMsg = '';
                        this.translate.get('prescription.sfdaDrugDetailsDeleteMsg').subscribe(text => drugDetailDeleteMsg = text);
                        this.dialogService.showSuccessDialog(drugDetailDeleteMsg, (data: any) => {
                            if (data) {
                                this.fetchData()
                            }
                        })
                    },
                    error: (error) => {
                        this.dialogService.showErrorDialog(error.error.errors, (data: any) => { })
                    }
                })

            } else {
                return;
            }
        })
    }
}
