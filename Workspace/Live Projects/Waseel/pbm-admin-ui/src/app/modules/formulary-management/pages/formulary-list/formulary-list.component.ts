import { Component } from '@angular/core';
import { HttpException } from 'src/app/util/default-http-client';
import * as moment from 'moment';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpParams } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { formularyProviderService } from '../../Services/formulary-provider-service';
import { formularyRequest } from '../../models/formulary-request-model';
import { FormularyListDetailsModel } from '../../models/formularylist-details-model';

@Component({
    selector: 'app-formulary-list',
    templateUrl: './formulary-list.component.html',
    styles: [
    ]
})
export class FormularyListComponent {
    data: ListViewModel<FormularyListDetailsModel> = new ListViewModel();
    dataControl: formularyRequest = new formularyRequest();
    listPrototype = FormularyListDetailsModel.prototype;
    subscription: Subscription[] = [];
    isFilterDrawerOpen = false
    noContentSubtitle: boolean = true;
    listIsLoading: boolean = false;
    errorCode: string = '';
    formularyId!: string;
    trimmedformularyName: any
    formularyFilterForm: FormGroup = new FormGroup({
        formularyId: new FormControl(),
        formularyName: new FormControl(),
        createdDateFrom: new FormControl(),
        createdDateTo: new FormControl(),
        updatedDateFrom: new FormControl(),
        updatedDateTo: new FormControl(),
    })

    constructor(
        public prescriptionLovService: PrescriptionlovService,
        private formularyProviderSvc: formularyProviderService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private authService: AuthService,
        private translate: TranslateService,
        private dialogService: DialogService,
    ) {

    }
    ngOnInit(): void {
        this.subscription.push(this.formularyProviderSvc.formularyList$.subscribe(data => {
            if (!data) {
                this.noContentSubtitle = true;
                this.data = new ListViewModel();
            } else {
                // this.listIsLoading = true;
                this.data = data;
            }
            this.listIsLoading = false;
            if (this.data.content.length > 0) {
                this.data.content.forEach(element => {
                    this.listIsLoading = false;

                    if (element.createdDate) {
                        let date = new Date(element.createdDate);
                        element.createdDate = moment(date).format("MM/DD/YYYY hh:mm a")
                    }

                    if (element.updatedDate) {
                        let date = new Date(element.updatedDate);
                        element.updatedDate = moment(date).format("MM/DD/YYYY hh:mm a")
                    }
                });
            }
        }))
        this.listIsLoading = false;
        const params = this.activatedRoute.snapshot.queryParams;
        this.dataControl.formularyId = params['formularyId'] || '';
        this.dataControl.formularyName = params['formularyName'] || '';
        this.dataControl.createdDateFrom = params['createdDateFrom'] || '';
        this.dataControl.createdDateTo = params['createdDateFrom'] || '';
        this.dataControl.updatedDateFrom = params['updatedDate'] || '';
        this.dataControl.updatedDateTo = params['updatedDate'] || '';
        this.dataControl.pageNumber = Number.parseInt(params['pageNumber'] || 0);
        this.dataControl.recordSize = Number.parseInt(params['recordSize'] || 10);
        this.fetchData();
        //  this.applyFilter();

    }
    onFormularyDelete(id: any) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.authService.showSystemLoader()
                this.formularyProviderSvc.deleteFormulary(id).subscribe({
                    next: (data) => {
                        const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                        let formularyRequestcancel = '';
                        this.translate.get('prescription.formularyRequestcancel').subscribe(text => formularyRequestcancel = text);
                        this.dialogService.showSuccessDialog(formularyRequestcancel, (data: any) => {
                            if (data) {
                                this.resetFilter();
                                this.fetchData();
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

    updateQueryParams() {
        const queryParams = new HttpParams({
            fromObject: {
                'formularyId': this.dataControl.formularyId || '',
                'formularyName': this.dataControl.formularyName || '',
                'createdDateFrom': this.dataControl.createdDateFrom || '',
                'createdDateTo': this.dataControl.createdDateTo || '',
                'updatedDateFrom': this.dataControl.updatedDateFrom || '',
                'updatedDateTo': this.dataControl.updatedDateTo || '',
                'pageNumber': this.dataControl.pageNumber || '',
                'recordSize': this.dataControl.recordSize || '',
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
        // this.router.navigate(["/formulary/list"])
    }
    applyFilter() {
        this.errorCode = '';

        let formularyId = this.formularyFilterForm.controls['formularyId'].value;
        let trimmedformularyId = formularyId?.replace(/^\s+|\s+$/g, '');
        this.formularyId = trimmedformularyId;
        this.dataControl.formularyId = trimmedformularyId;
        this.formularyFilterForm.controls['formularyId'].setValue(trimmedformularyId);

        let formularyName = this.formularyFilterForm.controls['formularyName'].value;
        this.trimmedformularyName = formularyName?.replace(/^\s+|\s+$/g, '');
        this.dataControl.formularyName = this.trimmedformularyName;
        this.formularyFilterForm.controls['formularyName'].setValue(this.trimmedformularyName);


        this.dataControl.createdDateFrom = moment(this.formularyFilterForm.controls['createdDateFrom'].value).format("DD-MM-yyyy");
        this.dataControl.createdDateTo = moment(this.formularyFilterForm.controls['createdDateTo'].value).format("DD-MM-yyyy");
        this.dataControl.updatedDateFrom = moment(this.formularyFilterForm.controls['updatedDateFrom'].value).format("DD-MM-yyyy");
        this.dataControl.updatedDateTo = moment(this.formularyFilterForm.controls['updatedDateTo'].value).format("DD-MM-yyyy");
        if ((this.dataControl.createdDateFrom > this.dataControl.createdDateTo)) {
            this.translate.get('prescription.createdDateFromGreaterThancreatedDateTo').subscribe(text => this.errorCode = text);
        }
        if ((this.dataControl.createdDateFrom === "Invalid date" && this.dataControl.createdDateTo !== "Invalid date") ||
            (this.dataControl.createdDateFrom !== "Invalid date" && this.dataControl.createdDateTo === "Invalid date")) {
            this.translate.get('prescription.missigDatePairs').subscribe(text => this.errorCode = text);
        }
        if ((this.dataControl.updatedDateFrom > this.dataControl.updatedDateTo)) {
            this.translate.get('prescription.updatedDateFromGreaterThanupdatedDateTo').subscribe(text => this.errorCode = text);
        }
        if ((this.dataControl.updatedDateFrom === "Invalid date" && this.dataControl.updatedDateTo !== "Invalid date") ||
            (this.dataControl.updatedDateFrom !== "Invalid date" && this.dataControl.updatedDateTo === "Invalid date")) {
            this.translate.get('prescription.missigDatePairs').subscribe(text => this.errorCode = text);
        }
        this.dataControl.pageNumber = 0;
        if (this.errorCode === '') {
            this.fetchData();
            this.isFilterDrawerOpen = false;
        }



    }

    resetFilter() {
        this.errorCode = '';
        this.dataControl.formularyId = undefined;
        this.dataControl.formularyName = undefined;
        this.dataControl.createdDateFrom = undefined;
        this.dataControl.createdDateTo = undefined;
        this.dataControl.updatedDateFrom = undefined;
        this.dataControl.updatedDateTo = undefined;

        this.formularyFilterForm.setValue({
            'formularyId': null,
            'formularyName': null,
            'createdDateFrom': null,
            'createdDateTo': null,
            'updatedDateTo': null,
            'updatedDateFrom': null
        })


    }

    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 5;
    }



    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;
        this.formularyProviderSvc.getFormularyList(this.dataControl).subscribe((x) => {
        })

        this.authService.hideSystemLoader();
        if (this.errorCode === '') {
            this.isFilterDrawerOpen = false;

        }
        else {
            this.isFilterDrawerOpen = true;
        }
        this.updateQueryParams()


    }

    openViewformularyDialog(formularyId: string) {
        this.router.navigate(["formulary/details", formularyId])
    }
    ngOnDestroy() {
        this.formularyProviderSvc.formularyList$.next(new ListViewModel<FormularyListDetailsModel>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}