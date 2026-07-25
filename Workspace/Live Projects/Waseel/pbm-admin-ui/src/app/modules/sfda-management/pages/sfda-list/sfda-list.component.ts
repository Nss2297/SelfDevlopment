import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { SfdaListModel } from '../../models/sfda-list.model';
import { SfdaRequestModel } from '../../models/sfda-list-request.model';
import { SfdaManagementService } from '../../service/sfda-management.service';
import * as moment from 'moment';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';

@Component({
    selector: 'app-sfda-list',
    templateUrl: './sfda-list.component.html',
    styles: [
    ]
})

export class SfdaListComponent {
    drugListId!: string;
    sfdaData: ListViewModel<SfdaListModel> = new ListViewModel();
    listPrototype = SfdaListModel.prototype;
    dataControl: SfdaRequestModel = new SfdaRequestModel();
    listIsLoading: boolean = false;
    subscription: Subscription[] = [];
    noContentSubtitle: boolean = true;
    isFilterDrawerOpen = false;
    uploadSfdaListDialogOpen = false;
    errorCode: string = '';
    dateCode: string = "";
    greaterDate: string = '';

    filterForm: FormGroup = new FormGroup({
        drugListId: new FormControl('', { validators: Validators.required }),
        fileName: new FormControl('', { validators: Validators.required }),
        effectiveDateFrom: new FormControl('', { validators: Validators.required }),
        effectiveDateTo: new FormControl('', { validators: Validators.required }),
        uploadDateFrom: new FormControl('', { validators: Validators.required }),
        uploadDateTo: new FormControl('', { validators: Validators.required })
    });

    constructor(
        private dialogService: DialogService,
        private sfdaManagementSVC: SfdaManagementService,
        private authService: AuthService,
        private router: Router,
        private translate: TranslateService
    ) { }

    ngOnInit(): void {
        this.dataControl.pageNumber = 0;
        this.dataControl.recordSize = 10
        this.subscription.push(this.sfdaManagementSVC.sfdaList$.subscribe(data => {
            if (!data) {
                this.noContentSubtitle = true;
                this.sfdaData = new ListViewModel();
            } else {
                this.sfdaData = data;
                this.sfdaData.content.forEach(element => {
                    this.listIsLoading = false;
                    if (element.effectiveDate) {
                        let date = new Date(element.effectiveDate);
                        element.effectiveDate = moment(date).format("MM/DD/YYYY ")
                    }
                    if (element.uploadDate) {
                        let date = new Date(element.uploadDate);
                        element.uploadDate = moment(date).format("MM/DD/YYYY hh:mm a")
                    }
                })
            }
            this.listIsLoading = false;
        })
        )
    }

    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;
        this.sfdaManagementSVC.getSfdaList(this.dataControl).subscribe();
        this.authService.hideSystemLoader();
    }

    applyFilter() {
        this.errorCode = '';
        this.greaterDate = '';
        this.dateCode = '';

        let idNumber = this.filterForm.controls['drugListId'].value;
        let trimmeddrugListId = idNumber?.replace(/^\s+|\s+$/g, '');
        idNumber = trimmeddrugListId;
        this.dataControl.drugListId = trimmeddrugListId;
        this.filterForm.controls['drugListId'].setValue(trimmeddrugListId);

        let fileName = this.filterForm.controls['fileName'].value;
        let trimmedFileName = fileName?.replace(/^\s+|\s+$/g, '');
        fileName = trimmedFileName;
        this.dataControl.fileName = trimmedFileName;
        this.filterForm.controls['fileName'].setValue(trimmedFileName);

        this.dataControl.effectiveDateFrom = moment(this.filterForm.controls['effectiveDateFrom'].value).format("DD-MM-yyyy");
        this.dataControl.effectiveDateTo = moment(this.filterForm.controls['effectiveDateTo'].value).format("DD-MM-yyyy");

        if ((this.dataControl.effectiveDateFrom > this.dataControl.effectiveDateTo)) {
            this.translate.get('prescription.startDateGreaterThanEndDate').subscribe(text => this.errorCode = text);
            console.log("start:", this.errorCode)
        }
        if ((this.dataControl.effectiveDateFrom === "Invalid date" && this.dataControl.effectiveDateTo !== "Invalid date") ||
            (this.dataControl.effectiveDateFrom !== "Invalid date" && this.dataControl.effectiveDateTo === "Invalid date")) {
            this.translate.get('prescription.missigDatePairs').subscribe(text => this.errorCode = text);
        }


        this.dataControl.uploadDateFrom = moment(this.filterForm.controls['uploadDateFrom'].value).format("DD-MM-yyyy");
        this.dataControl.uploadDateTo = moment(this.filterForm.controls['uploadDateTo'].value).format("DD-MM-yyyy");

        if ((this.dataControl.uploadDateFrom > this.dataControl.uploadDateTo)) {
            this.translate.get('prescription.startDateGreaterThanEndDate').subscribe(text => this.errorCode = text);
            console.log("start:", this.errorCode)
        }
        if ((this.dataControl.uploadDateFrom === "Invalid date" && this.dataControl.uploadDateTo !== "Invalid date") ||
            (this.dataControl.uploadDateFrom !== "Invalid date" && this.dataControl.uploadDateTo === "Invalid date")) {
            this.translate.get('prescription.missigDatePairs').subscribe(text => this.errorCode = text);
        }

        this.dataControl.pageNumber = 0;
        if (this.errorCode === '') {
            this.isFilterDrawerOpen = false;
            this.listIsLoading = true;
            this.fetchData();
        }
    }

    resetFilter() {
        this.dataControl.drugListId = undefined;
        this.dataControl.fileName = undefined;
        this.dataControl.effectiveDateFrom = undefined;
        this.dataControl.effectiveDateTo = undefined;
        this.dataControl.uploadDateFrom = undefined;
        this.dataControl.uploadDateTo = undefined;
        this.filterForm.setValue({
            'drugListId': null,
            'fileName': null,
            'effectiveDateFrom': null,
            'effectiveDateTo': null,
            'uploadDateFrom': null,
            'uploadDateTo': null
        });
    }

    navigateToSfdaDetails(drugListId: string) {
        this.router.navigate(["sfda/details", Number(drugListId)]);
    }


    closeUploadSfdaListDialog = (data: any) => {
        this.uploadSfdaListDialogOpen = false;
        this.fetchData();
    }

    deleteData(drugListId: string) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader()
                this.sfdaManagementSVC.deleteSFDA(drugListId).subscribe({
                    next: (data) => {

                        let sfdaDeletemsg = '';
                        this.translate.get('prescription.sfdaDeletemsg').subscribe(text => sfdaDeletemsg = text);
                        this.dialogService.showSuccessDialog(sfdaDeletemsg, (data: any) => {
                            if (data) {
                                this.resetFilter();
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

    ngOnDestroy() {
        this.sfdaManagementSVC.sfdaList$.next(new ListViewModel<SfdaListModel>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}
