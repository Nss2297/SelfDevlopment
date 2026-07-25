import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { CustomizationService } from '../../services/customization-service/customization.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { TranslateService } from '@ngx-translate/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { DrugGenderDetails } from '../../models/drug-gender-details.model';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DrugGenderRequest } from '../../models/drug-gender-request.model';
import { DssCustomizationService } from '../../services/customization-service/dsscustomization.service';
import { HttpParams } from '@angular/common/http';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';
import * as moment from 'moment';

@Component({
    selector: 'app-drug-gender-customization',
    templateUrl: './drug-gender-customization.component.html',
    styles: [
    ]
})
export class DrugGenderCustomizationComponent {
    data: ListViewModel<DrugGenderDetails> = new ListViewModel();
    drugGenderdataControl: DrugGenderRequest = new DrugGenderRequest();
    listIsLoading: boolean = false;
    isFilterDrawerOpen = false;
    addEditDrugGenderDialogOpen = false;
    uploadDrugGenderDialogOpen = false;
    viewDrugGenderDialogOpen = false;
    subscription: Subscription[] = [];
    currentUser: any;
    errorCode: string = '';
    noContentSubtitle: boolean = true
    listPrototype = DrugGenderDetails.prototype;
    hidePayerColumn: boolean = true;
    drugGenderDialogsData?: DrugGenderDetails
    addEditDrugGenderDialogMode: 'add' | 'edit' = 'add';
    gender!: string
    genderList = [
        {
            key: "MALE",
            value: "MALE"
        },
        {
            key: "FEMALE",
            value: "FEMALE"
        }
    ];
    filterForm: FormGroup = new FormGroup({
        serviceCode: new FormControl('', { validators: Validators.required }),
        gender: new FormControl('', { validators: Validators.required }),
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

        this.subscription.push(this.dsscustomization.drugToGender$.subscribe(data => {
            console.log("data", data)
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
        this.drugGenderdataControl.serviceCode = params['serviceCode'] || '';
        this.drugGenderdataControl.gender = params['gender'] || '';
        this.drugGenderdataControl.payerId = params['payerId'] || '';
        this.drugGenderdataControl.moduleName = params['moduleName'] || '';
        this.drugGenderdataControl.serviceStatus = params['serviceStatus'] || '';
        this.fetchData();
        this.applyFilter()
        this.lovService.initializeAllLists({ serviceCode: undefined, icdCode: undefined }).subscribe();
        this.dssLovService.initializeAllLists({ serviceCode: undefined, icdCode: undefined }).subscribe();
    }
    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;
        // this.dssLovService.getgenderlist("gender")?.subscribe()
        this.dsscustomization.getDrugToGenderCustomizations(this.drugGenderdataControl).subscribe(data => {
            console.log(data)
        })
        this.authService.hideSystemLoader();

        this.isFilterDrawerOpen = false;

        this.updateQueryParams()

    }
    filtergender(query: string) {
        this.dssLovService.getgenderlist()?.subscribe()
    }
    filterDrugs(query: string) {
        this.lovService.getDrugs({ serviceCode: query }).subscribe();
    }

    filterPayers(query: string) {
        this.lovService.getPayers({ payerId: query }).subscribe((data) => {

        });
    }
    applyFilter() {
        this.drugGenderdataControl.serviceCode = this.filterForm.controls['serviceCode'].value;
        this.drugGenderdataControl.gender = this.filterForm.controls['gender'].value;
        this.drugGenderdataControl.payerId = this.filterForm.controls['payerId'].value;
        this.drugGenderdataControl.serviceStatus = this.filterForm.controls['serviceStatus'].value;
        this.drugGenderdataControl.moduleName = this.filterForm.controls['moduleName'].value;
        this.drugGenderdataControl.pageNumber = 0;
        this.fetchData();
        this.isFilterDrawerOpen = false;
    }

    resetFilter() {
        this.errorCode = '';
        this.drugGenderdataControl.serviceCode = undefined;
        this.drugGenderdataControl.gender = undefined;
        this.drugGenderdataControl.payerId = undefined;
        this.drugGenderdataControl.serviceStatus = undefined;
        this.drugGenderdataControl.moduleName = undefined;
        this.filterForm.setValue({
            'serviceCode':'',
            'gender': '',
            'payerId': '',
            'serviceStatus': '',
            'moduleName':''
        });
        // this.fetchData();
    }
    updateQueryParams() {
        const queryParams = new HttpParams({
            fromObject: {
                'serviceCode': this.drugGenderdataControl.serviceCode || '',
                'gender': this.drugGenderdataControl.gender || '',
                'payerId': this.drugGenderdataControl.payerId || '',
                'moduleName': this.drugGenderdataControl.moduleName || '',
                'serviceStatus': this.drugGenderdataControl.serviceStatus || '',
                'pageNumber': this.drugGenderdataControl.pageNumber || '',
                'recordSize': this.drugGenderdataControl.recordSize || ''
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
                this.dsscustomization.deleteDrugToGenderData(itemId).subscribe({
                    next: (data) => {

                        let drugGenderdeletemsg = '';
                        this.translate.get('customization.drugGenderdeletemsg').subscribe(text => drugGenderdeletemsg = text);
                        this.dialogService.showSuccessDialog(drugGenderdeletemsg, (data: any) => {
                            if (data) {
                                this.resetFilter();
                                this.fetchData();
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

    closeAddDrugGenderDialog = (data: { status: 'cancel' } | { status: 'saved', id: string }) => {
        this.addEditDrugGenderDialogOpen = false;
        if (data.status == 'saved') {
            this.translate.get(this.addEditDrugGenderDialogMode == 'add' ? 'customization.drugGenderCustomizationAddedSuccessfully' : 'customization.drugGenderCustomizationEditedSuccessfully')
                .subscribe((text) =>
                    this.dialogService.showSuccessDialog(text as string, () => {
                        this.resetFilter();
                        this.fetchData();
                    })
                );
        }
    }

    closeUploadDrugGenderDialog = (data: any) => {
        this.uploadDrugGenderDialogOpen = false;
        this.resetFilter();
        this.fetchData()
    }

    openAddDrugGenderDialog() {
        this.addEditDrugGenderDialogMode = 'add';
        this.drugGenderDialogsData = undefined;
        this.addEditDrugGenderDialogOpen = true;
    }

    openEditDrugGenderDialog(customizationId: string) {
        const customization = this.data.content.find(customization => customization.id == customizationId);
        console.log(customization)
        if (customization) {
            this.addEditDrugGenderDialogMode = 'edit';
            this.drugGenderDialogsData = customization;
            this.addEditDrugGenderDialogOpen = true;
        }
    }
    openViewDrugGenderDialog(id: string) {
        this.drugGenderDialogsData = this.data.content.find(item => item.id == id);
        if (this.drugGenderDialogsData == undefined) {
            this.dsscustomization.getDrugToGenderCustomizationsById(id).subscribe({
                next: result => {
                    this.drugGenderDialogsData = result;
                    this.viewDrugGenderDialogOpen = true;
                },
                error: (error) => { }
            })
        } else {
            this.viewDrugGenderDialogOpen = true;
        }
    }

    closeViewDrugGenderDialog = (data: any) => {
        this.viewDrugGenderDialogOpen = false;
    }

    ngOnDestroy() {
        this.dsscustomization.drugToGender$.next(new ListViewModel<DrugGenderDetails>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}
