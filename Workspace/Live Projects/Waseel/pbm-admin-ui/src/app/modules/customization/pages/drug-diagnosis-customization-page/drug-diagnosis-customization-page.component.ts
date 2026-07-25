import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { DrugDiagnosisDetails } from '../../models/drug-diagnosis-details.model';
import { DrugToDiagnosisRequest } from '../../models/drug-diagnosis-request.model';
import { CustomizationService } from '../../services/customization-service/customization.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import * as moment from 'moment';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-drug-diagnosis-customization-page',
    templateUrl: './drug-diagnosis-customization-page.component.html'
})
export class DrugDiagnosisCustomizationPageComponent implements OnInit {

    data: ListViewModel<DrugDiagnosisDetails> = new ListViewModel();
    dataControl: DrugToDiagnosisRequest = new DrugToDiagnosisRequest();
    listIsLoading: boolean = false;
    hidePayerColumn: boolean = true;
    currentUser: any;
    subscription: Subscription[] = [];

    isFilterDrawerOpen: boolean = false;
    errorCode: string = '';
    filterForm: FormGroup = new FormGroup({
        serviceCode: new FormControl('', { validators: Validators.required }),
        icdCode: new FormControl('', { validators: Validators.required }),
        payer: new FormControl('', { validators: Validators.required }),
        categoryOfApproval: new FormControl('', { validators: Validators.required }),
        rejectionCategory: new FormControl('', { validators: Validators.required }),
        serviceStatus: new FormControl('', { validators: Validators.required }),
        moduleName: new FormControl('', { validators: Validators.required }),
        // updateDateAndTime: new FormControl()
    })
    addEditDrugDiagnosisDialogOpen = false;
    addEditDrugDiagnosisDialogMode: 'add' | 'edit' = 'add';
    drugDiagnosisDialogsData?: DrugDiagnosisDetails;
    listPrototype = DrugDiagnosisDetails.prototype;
    viewDrugDiagnosisDialogOpen = false;
    uploadDrugDiagnosisCustomizationDialogOpen = false;
    noContentSubtitle: boolean = true
    constructor(
        private customizationService: CustomizationService,
        public lovService: LovService,
        private dialogService: DialogService,
        private translate: TranslateService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private authService: AuthService
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit(): void {
        if (this.currentUser.authorities.some((data: any) => data.authority.includes('PBM_ADMIN'))) {
            this.hidePayerColumn = false;
        }

        //this.customizationService.drugToDiagnosis$.subscribe(data => {
        this.subscription.push(this.customizationService.drugToDiagnosis$.subscribe(data => {

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
                    delete element.payer;
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
        this.dataControl.serviceCode = params['serviceCode'] || '';
        // this.dataControl.updateDateAndTime = params['updateDateAndTime'] || '';
        this.dataControl.icdCode = params['icdCode'] || '';
        this.dataControl.payerId = params['payerId'] || '';
        this.dataControl.moduleName = params['moduleName'] || '';
        this.dataControl.categoryOfApproval = params['categoryOfApproval'] || '';
        this.dataControl.rejectionCategory = params['rejectionCategory'] || '';
        this.dataControl.serviceStatus = params['serviceStatus'] || '';
        // this.dataControl.pageNumber = Number.parseInt(params['pageNumber'] || 0);
        // this.dataControl.recordSize = Number.parseInt(params['recordSize'] || 10);
        this.fetchData();
        this.applyFilter();
        this.lovService.initializeAllLists({ serviceCode: undefined, icdCode: undefined }).subscribe();
    }

    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;
        this.customizationService.getDrugToDiagnosisCustomizations(this.dataControl).subscribe();
        this.authService.hideSystemLoader();
        // if (this.errorCode === '') {
        //   this.isFilterDrawerOpen = false;

        // }
        // else {
        this.isFilterDrawerOpen = false;
        // }
        this.updateQueryParams()

    }

    applyFilter() {
        this.dataControl.serviceCode = this.filterForm.controls['serviceCode'].value;
        // this.dataControl.updateDateAndTime = moment(this.filterForm.controls['updateDateAndTime'].value).format("DD-MM-yyyy");
        this.dataControl.icdCode = this.filterForm.controls['icdCode'].value;
        this.dataControl.payerId = this.filterForm.controls['payer'].value;
        this.dataControl.categoryOfApproval = this.filterForm.controls['categoryOfApproval'].value;
        this.dataControl.rejectionCategory = this.filterForm.controls['rejectionCategory'].value;
        this.dataControl.serviceStatus = this.filterForm.controls['serviceStatus'].value;
        this.dataControl.moduleName = this.filterForm.controls['moduleName'].value;
        this.dataControl.pageNumber = 0;
        this.fetchData();
        this.isFilterDrawerOpen = false;
    }

    resetFilter() {
        this.errorCode = '';
        this.dataControl.serviceCode = undefined;
        // this.dataControl.updateDateAndTime = undefined;
        this.dataControl.icdCode = undefined;
        this.dataControl.payerId = undefined;
        this.dataControl.categoryOfApproval = undefined;
        this.dataControl.rejectionCategory = undefined;
        this.dataControl.serviceStatus = undefined;
        this.dataControl.moduleName = undefined;
        this.filterForm.setValue({
            'serviceCode':'',
            // 'updateDateAndTime': null,
            'icdCode': '',
            'payer': '',
            'categoryOfApproval': '',
            'rejectionCategory': '',
            'serviceStatus': '',
            'moduleName': ''
        });
        // this.fetchData();
    }
    deleteData(itemId: String) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader()
                this.customizationService.deleteDrugToDiagnosisData(itemId).subscribe({
                    next: (data) => {

                        let drugDignosisdeletemsg = '';
                        this.translate.get('customization.drugDignosisdeletemsg').subscribe(text => drugDignosisdeletemsg = text);
                        this.dialogService.showSuccessDialog(drugDignosisdeletemsg, (data: any) => {
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
    // deleteData(itemId: String) {
    //   this.dialogService.showDeleteConfirmDialog((data: any) => {
    //     this.dialogService.hideAlertConfirmDialog();
    //     if (data) {
    //       this.deleteApiCall(itemId);
    //     }
    //   });
    // }

    // deleteApiCall(itemId: String) {
    //   this.customizationService.deleteDrugToDiagnosisData(itemId).subscribe({
    //     next: () => {
    //       let successMsg = '';
    //       this.translate.get('DELETE-SUCCESS').subscribe(text => successMsg = text);
    //       this.dialogService.showSuccessDialog(successMsg, (data: any) => { })
    //       this.fetchData();
    //     },
    //     error: (error) => {
    //       this.dialogService.showErrorDialog(error.error.responseDescription, (data: any) => { })
    //     }
    //   });
    // }

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
                return this.hidePayerColumn ? { [index + ':5']: 'text-success dark:text-success-300 body-2-medium' } : { [index + ':6']: 'text-success dark:text-success-300 body-2-medium' }
            } else if (item.serviceStatus?.toLowerCase().includes("rejected")) {
                return this.hidePayerColumn ? { [index + ':5']: 'text-error dark:text-error-400 body-2-medium' } : { [index + ':6']: 'text-error dark:text-error-400 body-2-medium' }
            } else {
                return this.hidePayerColumn ? { [index + ':5']: 'text-text' } : { [index + ':6']: 'text-text' }
            }
        }).reduce((row1, row2) => ({ ...row1, ...row2 }), {});
    }

    filterDrugs(query: string) {
        this.lovService.getDrugs({ serviceCode: query }).subscribe();
    }

    filterDiagnosis(query: string) {
        this.lovService.getDiagnosis({ icdCode: query }).subscribe();
    }
    filterPayers(query: string) {
        this.lovService.getPayers({ payerId: query }).subscribe((data) => {

        });
    }

    openAddDrugDiagnosisDialog() {
        this.addEditDrugDiagnosisDialogMode = 'add';
        this.drugDiagnosisDialogsData = undefined;
        this.addEditDrugDiagnosisDialogOpen = true;
    }

    openUploadDrugDiagnosisCustomizationDialog() {
        this.uploadDrugDiagnosisCustomizationDialogOpen = true;
    }

    openEditDrugDiagnosisDialog(customizationId: string) {
        const customization = this.data.content.find(customization => customization.id == customizationId);
        console.log(customization)
        if (customization) {
            this.addEditDrugDiagnosisDialogMode = 'edit';
            this.drugDiagnosisDialogsData = customization;
            this.addEditDrugDiagnosisDialogOpen = true;
        }
    }

    closeAddDrugDiagnosisDialog = (data: { status: 'cancel' } | { status: 'saved', id: string }) => {
        this.addEditDrugDiagnosisDialogOpen = false;
        if (data.status == 'saved') {
            this.translate.get(this.addEditDrugDiagnosisDialogMode == 'add' ? 'customization.customizationAddedSuccessfully' : 'customization.customizationEditedSuccessfully')
                .subscribe((text) =>
                    this.dialogService.showSuccessDialog(text as string, () => {
                        this.resetFilter();
                        this.fetchData();
                    })
                );
        }
    }

    openViewDrugDiagnosisDialog(id: string) {
        this.drugDiagnosisDialogsData = this.data.content.find(item => item.id == id);
        if (this.drugDiagnosisDialogsData == undefined) {
            this.customizationService.getDrugToDiagnosisCustomizationsById(id).subscribe({
                next: result => {
                    this.drugDiagnosisDialogsData = result;
                    this.viewDrugDiagnosisDialogOpen = true;
                },
                error: (error) => { }
            })
        } else {
            this.viewDrugDiagnosisDialogOpen = true;
        }
    }

    closeViewDrugDiagnosisDialog = (data: any) => {
        this.viewDrugDiagnosisDialogOpen = false;
    }

    closeUploadDrugDiagnosisCustomizationDialog = (data: any) => {
        this.uploadDrugDiagnosisCustomizationDialogOpen = false;
        this.fetchData();
    }

    updateQueryParams() {
        const queryParams = new HttpParams({
            fromObject: {
                'serviceCode': this.dataControl.serviceCode || '',
                // 'updateDateAndTime': this.dataControl.updateDateAndTime || '',
                'icdCode': this.dataControl.icdCode || '',
                'payerId': this.dataControl.payerId || '',
                'moduleName': this.dataControl.moduleName || '',
                'categoryOfApproval': this.dataControl.categoryOfApproval || '',
                'rejectionCategory': this.dataControl.rejectionCategory || '',
                'serviceStatus': this.dataControl.serviceStatus || '',
                'pageNumber': this.dataControl.pageNumber || '',
                'recordSize': this.dataControl.recordSize || ''
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

    selectedFile: File | undefined;

    onFileSelected(event: any) {
        this.selectedFile = event.target.files[0];
    }

    uploadFile() {
        if (this.selectedFile) {
            this.customizationService.uploadFile(this.selectedFile).subscribe(
                (response) => {
                    console.log('File uploaded successfully:', response);
                    // Handle the response as needed
                },
                (error) => {
                    console.error('Error uploading file:', error);
                    // Handle the error as needed
                }
            );
        }
    }

    ngOnDestroy() {
        this.customizationService.drugToDiagnosis$.next(new ListViewModel<DrugDiagnosisDetails>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}
