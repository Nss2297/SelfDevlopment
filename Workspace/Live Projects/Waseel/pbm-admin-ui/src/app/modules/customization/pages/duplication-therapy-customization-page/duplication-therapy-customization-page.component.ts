import { Component } from '@angular/core';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DuplicationDetails } from '../../models/duplication-details.model';
import { DuplicationRequest } from '../../models/duplication-request.model';
import { Subscription } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DssCustomizationService } from '../../services/customization-service/dsscustomization.service';
import { DssLovService } from 'src/app/modules/shared/services/lov-services/dss-lov.service';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import * as moment from 'moment';

@Component({
  selector: 'app-duplication-therapy-customization-page',
  templateUrl: './duplication-therapy-customization-page.component.html',
  styleUrls: ['./duplication-therapy-customization-page.component.css']
})
export class DuplicationTherapyCustomizationPageComponent {

    data: ListViewModel<DuplicationDetails> = new ListViewModel();
    dataControl: DuplicationRequest = new DuplicationRequest();
    listPrototype = DuplicationDetails.prototype;
    listIsLoading: boolean = false;
    hidePayerColumn: boolean = true;
    currentUser: any;
    subscription: Subscription[] = [];
    isFilterDrawerOpen: boolean = false;
    errorCode: string = '';
    addEditDuplicationDialogOpen = false;
    addEditDuplicationDialogMode: 'add' | 'edit' = 'add';
    viewDuplicationDialogOpen = false;
    uploadDuplicationDialogOpen = false;
    noContentSubtitle: boolean = true;
    duplicationDialogsData?: DuplicationDetails;
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
        this.subscription.push(this.dsscustomization.duplicationList$.subscribe(data => {
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
        this.dsscustomization.getDuplicationTherapyList(this.dataControl).subscribe();
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

    deleteDuplicationTherapyData(id:string) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader()
                this.dsscustomization.deleteDuplicationList(id).subscribe({
                    next: (data: any) => {

                        let deleteDuplicationTherapy = '';
                        this.translate.get('customization.deleteDuplicationTherapy').subscribe(text => deleteDuplicationTherapy = text);
                        this.dialogService.showSuccessDialog(deleteDuplicationTherapy, (data: any) => {
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

    openViewDrugToDrugDialog(id:string) {
        this.duplicationDialogsData  = this.data.content.find(item => item.id == id);
        if (this.duplicationDialogsData  == undefined) {
            this.dsscustomization.getduplicationdetails(id).subscribe({
                next: result => {
                    this.duplicationDialogsData  = result;
                    this.viewDuplicationDialogOpen = true;
                },
                error: (error) => { }
            })
        } else {
            this.viewDuplicationDialogOpen = true;
        }
    }
    openEditDuplicationDialog(id: number) {
        const customization = this.data.content.find(customization => Number(customization.id) == id);
        if (customization) {
            this.addEditDuplicationDialogMode = 'edit';
            this.duplicationDialogsData = customization;
            this.addEditDuplicationDialogOpen = true;
        }
    }
    closeViewDuplicationDialog = (data: any) => {
        this.viewDuplicationDialogOpen = false;
    }

    


    openAddDuplicationDialog() {
        this.addEditDuplicationDialogMode = 'add';
        this.duplicationDialogsData = undefined;
        this.addEditDuplicationDialogOpen = true;
    }

    


    closeAddDuplicationDialog = (data: any) => {
        if (!data) {
            this.addEditDuplicationDialogOpen = false;
        } else {
            this.addEditDuplicationDialogOpen = false;
            this.translate.get(this.addEditDuplicationDialogMode == 'add' ? 'customization.duplicationTherapyAddedSuccessfully' : 'customization.duplicationTherapyCustomizationEditedSuccessfully')
                .subscribe((text) =>
                    this.dialogService.showSuccessDialog(text as string, () => {
                        this.resetFilter();
                        this.fetchData();
                    })
                );
        }
    }

    closeUploadDuplicationDialog = (data: any) => {
        this.uploadDuplicationDialogOpen = false;
        this.fetchData();
    }

    ngOnDestroy() {
        this.dsscustomization.duplicationList$.next(new ListViewModel<DuplicationDetails>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}

