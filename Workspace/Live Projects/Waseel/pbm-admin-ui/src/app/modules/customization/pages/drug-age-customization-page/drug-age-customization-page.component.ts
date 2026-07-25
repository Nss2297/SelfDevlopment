import { Component } from '@angular/core';
import { DrugAgeDetails } from '../../models/drug-age-details.model';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DrugAgeRequest } from '../../models/drug-age-request.model';
import { Subscription } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { TranslateService } from '@ngx-translate/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import * as moment from 'moment';
import { HttpParams } from '@angular/common/http';
import { DssCustomizationService } from '../../services/customization-service/dsscustomization.service';


@Component({
    selector: 'app-drug-age-customization-page',
    templateUrl: './drug-age-customization-page.component.html',
    styleUrls: ['./drug-age-customization-page.component.css']
})
export class DrugAgeCustomizationPageComponent {


    data: ListViewModel<DrugAgeDetails> = new ListViewModel();
    drugagedataControl: DrugAgeRequest = new DrugAgeRequest();
    listIsLoading: boolean = false;
    isFilterDrawerOpen = false;
    addEditDrugAgeDialogOpen = false;
    uploadDrugAgeDialogOpen = false;
    viewDrugAgeDialogOpen = false;
    subscription: Subscription[] = [];
    currentUser: any;
    errorCode: string = '';
    noContentSubtitle: boolean = true
    listPrototype = DrugAgeDetails.prototype;
    hidePayerColumn: boolean = true;
    drugAgeDialogsData?: DrugAgeDetails
    addEditDrugAgeDialogMode: 'add' | 'edit' = 'add';
    gender!: string

    filterForm: FormGroup = new FormGroup({
        serviceCode: new FormControl('', { validators: Validators.required }),
        payerId: new FormControl('', { validators: Validators.required }),
        serviceStatus: new FormControl('', { validators: Validators.required }),
        moduleName: new FormControl('', { validators: Validators.required }),

    })

    constructor(
        private dsscustomization: DssCustomizationService,
        public dssLovService: DssLovService,
        private dialogService: DialogService,
        private translate: TranslateService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private authService: AuthService,
        public lovService: LovService,
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit(): void {
        if (this.currentUser.authorities.some((data: any) => data.authority.includes('PBM_ADMIN'))) {
            this.hidePayerColumn = false;
        }

        this.subscription.push(this.dsscustomization.drugToAgelist$.subscribe(data => {
            if (!data) {
                this.listIsLoading = false;
                this.noContentSubtitle = true;
                this.data = new ListViewModel();
            } else {
                this.data = data;
            }
            if (this.hidePayerColumn) {
                //this.data.content = []
                this.data.content.forEach(element => {
                    delete element.payerId;
                });
            }
            this.transformServiceStatusToTranslationCode();
            this.listIsLoading = false;
            if (this.data.content.length > 0) {
                this.data.content.forEach(element => {
                    this.listIsLoading = false;
                    if (element.updateDateAndTime) {
                        let date = new Date(element.updateDateAndTime);
                        element.updateDateAndTime = moment(date).format("MM/DD/YYYY hh:mm a")
                    }

                });


            }
        }))
        const params = this.activatedRoute.snapshot.queryParams;
        this.drugagedataControl.serviceCode = params['serviceCode'] || '';
        this.drugagedataControl.payerId = params['payerId'] || '';
        this.drugagedataControl.moduleName = params['moduleName'] || '';
        this.drugagedataControl.serviceStatus = params['serviceStatus'] || '';
        this.fetchData();
        this.applyFilter()
        this.lovService.initializeAllLists({ serviceCode: undefined, icdCode: undefined }).subscribe();
        this.dssLovService.initializeAllLists({ serviceCode: undefined, icdCode: undefined }).subscribe();
    }
    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;

        this.dsscustomization.getDrugToAgeCustomizations(this.drugagedataControl).subscribe(data => {
        })
        this.authService.hideSystemLoader();

        this.isFilterDrawerOpen = false;

        this.updateQueryParams()

    }

    filterDrugs(query: string) {
        this.lovService.getDrugs({ serviceCode: query }).subscribe();
    }

    filterPayers(query: string) {
        this.lovService.getPayers({ payerId: query }).subscribe((data) => {

        });
    }
    applyFilter() {
        this.drugagedataControl.serviceCode = this.filterForm.controls['serviceCode'].value;
        this.drugagedataControl.payerId = this.filterForm.controls['payerId'].value;
        this.drugagedataControl.serviceStatus = this.filterForm.controls['serviceStatus'].value;
        this.drugagedataControl.moduleName = this.filterForm.controls['moduleName'].value;
        this.drugagedataControl.pageNumber = 0;
        this.fetchData();
        this.isFilterDrawerOpen = false;
    }

    resetFilter() {
        this.errorCode = '';
        this.drugagedataControl.serviceCode = undefined;
        this.drugagedataControl.payerId = undefined;
        this.drugagedataControl.serviceStatus = undefined;
        this.drugagedataControl.moduleName = undefined;
        this.filterForm.setValue({
            'serviceCode':'',
            'payerId':'',
            'serviceStatus':'',
            'moduleName':''
        });

    }
    updateQueryParams() {
        const queryParams = new HttpParams({
            fromObject: {
                'serviceCode': this.drugagedataControl.serviceCode || '',
                'payerId': this.drugagedataControl.payerId || '',
                'moduleName': this.drugagedataControl.moduleName || '',
                'serviceStatus': this.drugagedataControl.serviceStatus || '',
                'pageNumber': this.drugagedataControl.pageNumber || '',
                'recordSize': this.drugagedataControl.recordSize || ''
            }
        });
        const params = queryParams.keys()
            .filter(key => queryParams.get(key) != null && queryParams.get(key) != '')
            .filter(key => key != 'pageNumber' || queryParams.get(key) != '0')
            .filter(key => key != 'recordSize' || queryParams.get(key) != '10')
            .reduce((acc: { [k: string]: any }, key) => {
                acc[key] = queryParams.get(key);
                return acc;
            }, {});
        this.router.navigate([this.router.url.split('?')[0]], { replaceUrl: true, queryParams: params });
    }
    transformServiceStatusToTranslationCode() {
        this.data.content = this.data.content
            .map(service => {
                if (service.serviceStatus?.toLowerCase() == 'approved') {
                    service.serviceStatus = 'Approved';
                } else if (service.serviceStatus?.toLowerCase() == 'rejected') {
                    service.serviceStatus = 'Rejected'
                }
                return service;
            })
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

    deleteData(itemId: String) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader()
                this.dsscustomization.deleteDrugToAgeData(itemId).subscribe({
                    next: (data) => {

                        let drugAgedeletemsg = '';
                        this.translate.get('customization.drugAgedeletemsg').subscribe(text => drugAgedeletemsg = text);
                        this.dialogService.showSuccessDialog(drugAgedeletemsg, (data: any) => {
                            if (data) {
                                this.resetFilter();
                                this.fetchData()
                            }
                        })
                    },
                    error: (error) => {
                        this.dialogService.showErrorDialog(error.error.errorDescriptions, (data: any) => { })
                    }
                })

            } else {
                return;
            }
        })
    }

    closeAddDrugAgeDialog = (data: { status: 'cancel' } | { status: 'saved', id: string }) => {
        this.addEditDrugAgeDialogOpen = false;
        if (data.status == 'saved') {
            this.translate.get(this.addEditDrugAgeDialogMode == 'add' ? 'customization.drugAgeCustomizationAddedSuccessfully' : 'customization.drugAgeCustomizationEditedSuccessfully')
                .subscribe((text) =>
                    this.dialogService.showSuccessDialog(text as string, () => {
                        this.resetFilter();
                        this.fetchData()
                    })
                );
        }
    }

    closeUploadDrugAgeDialog = (data: any) => {
        this.uploadDrugAgeDialogOpen = false;
        this.fetchData();
    }

    openAddDrugAgeDialog() {
        this.addEditDrugAgeDialogMode = 'add';
        this.drugAgeDialogsData = undefined;
        this.addEditDrugAgeDialogOpen = true;
    }

    openEditDrugAgeDialog(customizationId: string) {
        const customization = this.data.content.find(customization => customization.id == customizationId);
        if (customization) {
            this.addEditDrugAgeDialogMode = 'edit';
            this.drugAgeDialogsData = customization;
            this.addEditDrugAgeDialogOpen = true;
        }
    }
    openViewDrugAgeDialog(id: string) {
        this.drugAgeDialogsData = this.data.content.find(item => item.id == id);
        if (this.drugAgeDialogsData == undefined) {
            this.dsscustomization.getDrugToAgeCustomizationsById(id).subscribe({
                next: result => {
                    this.drugAgeDialogsData = result;
                    this.viewDrugAgeDialogOpen = true;
                },
                error: (error) => { }
            })
        } else {
            this.viewDrugAgeDialogOpen = true;
        }
    }

    closeViewDrugAgeDialog = (data: any) => {
        this.viewDrugAgeDialogOpen = false;
    }

    ngOnDestroy() {
        this.dsscustomization.drugToAgelist$.next(new ListViewModel<DrugAgeDetails>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}

