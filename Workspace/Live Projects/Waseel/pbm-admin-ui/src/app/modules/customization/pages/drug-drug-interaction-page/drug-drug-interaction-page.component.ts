import { Component } from '@angular/core';
import { DrugToDrugInteractionRequest } from '../../models/drug-drug-request.model';
import { DrugToDrugDetails } from '../../models/drug-drug-details.model';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { Subscription } from 'rxjs';
import { DssCustomizationService } from '../../services/customization-service/dsscustomization.service';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { TranslateService } from '@ngx-translate/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
@Component({
    selector: 'app-drug-drug-interaction-page',
    templateUrl: './drug-drug-interaction-page.component.html',
    styleUrls: ['./drug-drug-interaction-page.component.css']
})
export class DrugDrugInteractionPageComponent {

    data: ListViewModel<DrugToDrugDetails> = new ListViewModel();
    dataControl: DrugToDrugInteractionRequest = new DrugToDrugInteractionRequest();
    listPrototype = DrugToDrugDetails.prototype;
    listIsLoading: boolean = false;
    hidePayerColumn: boolean = true;
    currentUser: any;
    subscription: Subscription[] = [];
    isFilterDrawerOpen: boolean = false;
    errorCode: string = '';
    addEditDrugToDrugDialogOpen = false;
    addEditDrugToDrugDialogMode: 'add' | 'edit' = 'add';
    viewDrugToDrugDialogOpen = false;
    uploadDrugToDrugDialogOpen = false;
    noContentSubtitle: boolean = true;
    drugtodrugDialogsData?: DrugToDrugDetails;
    id?: number;

    filterForm: FormGroup = new FormGroup({
        serviceCode: new FormControl('', { validators: Validators.required }),
        interactedServiceCode: new FormControl('', { validators: Validators.required }),
        payerId: new FormControl('', { validators: Validators.required }),
        serviceStatus: new FormControl('', { validators: Validators.required }),
        moduleName: new FormControl('', { validators: Validators.required })
    })


    constructor(private dsscustomization: DssCustomizationService,
        public dssLovService: DssLovService,
        private dialogService: DialogService,
        private translate: TranslateService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private authService: AuthService,
        public lovService: LovService) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit() {
        if (this.currentUser.authorities.some((data: any) => data.authority.includes("PBM_ADMIN"))) {
            this.hidePayerColumn = false;
        }
        this.authService.hideSystemLoader();
        this.subscription.push(this.dsscustomization.drugToDrugInteractionList$.subscribe(data => {
            if (!data) {
                this.listIsLoading = false;
                this.noContentSubtitle = true;
                this.data = new ListViewModel();
            } else {
                this.data = data;
                this.data.content.forEach(element => {
                    if (element.lastUpdateDateAndTime) {
                        let date = new Date(element.lastUpdateDateAndTime);
                        element.lastUpdateDateAndTime = moment(date).format("MM/DD/YYYY hh:mm a")
                    }
                    if (element.serviceStatus === "APPROVED" || element.serviceStatus === "REJECTED") {
                        element.serviceStatus = element.serviceStatus === "APPROVED" ? "Approved" : "Rejected";
                    }

                })
                this.listIsLoading = false;
            }
            if (this.hidePayerColumn) {
                this.data.content.forEach(element => {
                    delete element.payerId;
                });
            }
        }
        ))
        this.fetchAllDropdownValue();
    }

    getExtraClassesForListView() {
        return this.data.content.map((item, index) => {
            if (item.serviceStatus?.toLowerCase().includes("approved")) {
                return this.hidePayerColumn ? { [index + ':4']: 'text-success dark:text-success-300 body-2-medium' } : { [index + ':5']: 'text-success dark:text-success-300 body-2-medium' }
            } else if (item.serviceStatus?.toLowerCase().includes("rejected")) {
                return this.hidePayerColumn ? { [index + ':4']: 'text-error dark:text-error-400 body-2-medium' } : { [index + ':5']: 'text-error dark:text-error-400 body-2-medium' }
            } else {
                return this.hidePayerColumn ? { [index + ':4']: 'text-text' } : { [index + ':5']: 'text-text' }
            }
        }).reduce((row1, row2) => ({ ...row1, ...row2 }), {});
    }

    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;
        this.dsscustomization.getDrugToDrugInteractionList(this.dataControl).subscribe();
        this.authService.hideSystemLoader();
        this.isFilterDrawerOpen = false;

    }

    fetchAllDropdownValue() {
        this.lovService.getDrugs().subscribe();
        this.lovService.getPayers().subscribe();
        this.lovService.getCategoriesOfRejection().subscribe();
        this.lovService.getDrugsStatuses().subscribe();
        this.lovService.getModules().subscribe();




    }

    applyFilter() {
        this.dataControl.serviceCode = this.filterForm.controls['serviceCode'].value;
        this.dataControl.interactedServiceCode = this.filterForm.controls['interactedServiceCode'].value;
        this.dataControl.payerId = this.filterForm.controls['payerId'].value;
        this.dataControl.serviceStatus = this.filterForm.controls['serviceStatus'].value;
        this.dataControl.moduleName = this.filterForm.controls['moduleName'].value;
        this.dataControl.pageNumber = 0;
        this.fetchData();
        this.isFilterDrawerOpen = false;
    }

    resetFilter() {
        this.errorCode = '';
        this.dataControl.serviceCode = undefined;
        this.dataControl.interactedServiceCode = undefined;
        this.dataControl.payerId = undefined;
        this.dataControl.serviceStatus = undefined;
        this.dataControl.moduleName = undefined;
        this.filterForm.setValue({
            'serviceCode': '',
            'interactedServiceCode': '',
            'payerId': '',
            'serviceStatus': '',
            'moduleName': ''
        });
        // this.fetchData();
    }

    filterServiceCode(query: string) {
        this.lovService.getDrugs({ serviceCode: query }).subscribe();
    }

    deleteDrugToDrugListData(id: number) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader()
                this.dsscustomization.deleteDrugToDrugInteractionList(id).subscribe({
                    next: (data: any) => {

                        let drugToDrugDeleteMsg = '';
                        this.translate.get('customization.drugToDrugDeleteMsg').subscribe(text => drugToDrugDeleteMsg = text);
                        this.dialogService.showSuccessDialog(drugToDrugDeleteMsg, (data: any) => {
                            if (data) {
                                this.resetFilter();
                                this.fetchData();
                            }
                        })
                    },
                    error: (error: any) => {
                        this.dialogService.showErrorDialog(error.error.errorDescriptions, (data: any) => { })
                    }
                })

            } else {
                return;
            }
        })
    }

    openViewDrugToDrugDialog(id: number) {
        this.drugtodrugDialogsData = this.data.content.find(item => +item.id === id);
        if (this.drugtodrugDialogsData == undefined) {
            this.dsscustomization.getDrugToDrugInteractionDetails(id).subscribe({
                next: result => {
                    this.drugtodrugDialogsData = result;
                    this.viewDrugToDrugDialogOpen = true;
                },
                error: (error) => { }
            })
        } else {
            this.viewDrugToDrugDialogOpen = true;
        }
    }

    closeViewDrugToDrugDialog = (data: any) => {
        this.viewDrugToDrugDialogOpen = false;
    }

    openEditDrugToDrugDialog(id: number) {
        const customization = this.data.content.find(customization => Number(customization.id) == id);
        if (customization) {
            this.addEditDrugToDrugDialogMode = 'edit';
            this.drugtodrugDialogsData = customization;
            this.addEditDrugToDrugDialogOpen = true;
        }
    }


    openAddDrugToDrugDialog() {
        this.addEditDrugToDrugDialogMode = 'add';
        this.drugtodrugDialogsData = undefined;
        this.addEditDrugToDrugDialogOpen = true;
    }

    closeAddDrugToDrugDialog = (data: any) => {
        if (!data) {
            this.addEditDrugToDrugDialogOpen = false;
        } else {
            this.addEditDrugToDrugDialogOpen = false;
            this.translate.get(this.addEditDrugToDrugDialogMode == 'add' ? 'customization.drugToDrugCustomizationAddedSuccessfully' : 'customization.drugToDrugCustomizationEditedSuccessfully')
                .subscribe((text) =>
                    this.dialogService.showSuccessDialog(text as string, () => {
                        this.resetFilter();
                        this.fetchData();
                    })
                );
        }
    }

    closeUploadDrugToDrugDialog = (data: any) => {
        this.uploadDrugToDrugDialogOpen = false;
        this.fetchData();
    }

    ngOnDestroy() {
        this.dsscustomization.drugToDrugInteractionList$.next(new ListViewModel<DrugToDrugDetails>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}
