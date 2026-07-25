import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { drugExclusionListDetailsModel } from '../../drug-exclusion-models/drugExclusionListDetails.model';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { drugExclusionRequest } from '../../drug-exclusion-models/drugExclusion-request.model';
import { ExclusionServiceTsService } from '../../services/exclusion-service.ts.service';
import * as moment from 'moment';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpParams } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { HttpException } from 'src/app/util/default-http-client';

@Component({
    selector: 'app-drug-exclusion-list',
    templateUrl: './drug-exclusion-list.component.html',
    styles: [
    ]
})
export class DrugExclusionListComponent {

    isFilterDrawerOpen = false;
    data: ListViewModel<drugExclusionListDetailsModel> = new ListViewModel();
    dataControl: drugExclusionRequest = new drugExclusionRequest();
    listPrototype = drugExclusionListDetailsModel.prototype;
    subscription: Subscription[] = [];
    noContentSubtitle: boolean = true;
    listIsLoading: boolean = false;
    errorCode: string = '';
    exclusionId!: string
    trimmedname: any;
    drugExciusionFilterForm: FormGroup = new FormGroup({
        exclusionId: new FormControl(),
        name: new FormControl(),
        createdDateFrom: new FormControl(),
        createdDateTo: new FormControl(),
        updatedDateFrom: new FormControl(),
        updatedDateTo: new FormControl(),
    })

    constructor(
        private exclusionservice: ExclusionServiceTsService,
        private activatedRoute: ActivatedRoute,
        private authService: AuthService,
        private translate: TranslateService,
        private router: Router,
        private dialogService: DialogService,
    ) {

    }

    ngOnInit(): void {
        this.subscription.push(this.exclusionservice.drugExclusionList$.subscribe(data => {
            if (!data) {
                this.noContentSubtitle = true;
                this.data = new ListViewModel();
            } else {
                this.data = data;
            }
            this.listIsLoading = false;
        }))
        this.listIsLoading = false;
        const params = this.activatedRoute.snapshot.queryParams;
        this.dataControl.exclusionId = params['exclusionId'] || '';
        this.dataControl.name = params['name'] || '';
        this.dataControl.createdDateFrom = params['createdDateFrom'] || '';
        this.dataControl.createdDateTo = params['createdDateFrom'] || '';
        this.dataControl.updatedDateFrom = params['updatedDate'] || '';
        this.dataControl.updatedDateTo = params['updatedDate'] || '';
        this.dataControl.pageNumber = Number.parseInt(params['pageNumber'] || 0);
        this.dataControl.recordSize = Number.parseInt(params['recordSize'] || 10);
        this.fetchData();
        this.applyFilter();

    }

    fetchData() {
        this.authService.showSystemLoader();
        this.listIsLoading = true;
        this.exclusionservice.getDrugExclusions(this.dataControl).subscribe((x) => {
        })

        this.authService.hideSystemLoader();
        if (this.errorCode === '') {
            this.isFilterDrawerOpen = false;

        }
        else {
            this.isFilterDrawerOpen = true;
        }
    }
    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 10;
    }
    updateQueryParams() {
        const queryParams = new HttpParams({
            fromObject: {
                'exclusionId': this.dataControl.exclusionId || '',
                'name': this.dataControl.name || '',
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
        let exclusionId = this.drugExciusionFilterForm.controls['exclusionId'].value;
        let trimmedexclusionId = exclusionId?.replace(/^\s+|\s+$/g, '');
        this.exclusionId = trimmedexclusionId;
        this.dataControl.exclusionId = trimmedexclusionId;
        this.drugExciusionFilterForm.controls['exclusionId'].setValue(trimmedexclusionId);


        let name = this.drugExciusionFilterForm.controls['name'].value;
        this.trimmedname = name?.replace(/^\s+|\s+$/g, '');
        this.dataControl.name = this.trimmedname;
        this.drugExciusionFilterForm.controls['name'].setValue(this.trimmedname);

        let date = this.drugExciusionFilterForm.controls['createdDateFrom'].value;

        this.dataControl.createdDateFrom = moment(this.drugExciusionFilterForm.controls['createdDateFrom'].value, 'MM/DD/YYYY').format("DD-MM-yyyy");
        this.dataControl.createdDateTo = moment(this.drugExciusionFilterForm.controls['createdDateTo'].value).format("DD-MM-yyyy");
        this.dataControl.updatedDateFrom = moment(this.drugExciusionFilterForm.controls['updatedDateFrom'].value).format("DD-MM-yyyy");
        this.dataControl.updatedDateTo = moment(this.drugExciusionFilterForm.controls['updatedDateTo'].value).format("DD-MM-yyyy");
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
        this.dataControl.exclusionId = undefined;
        this.dataControl.name = undefined;
        this.dataControl.createdDateFrom = undefined;
        this.dataControl.createdDateTo = undefined;
        this.dataControl.updatedDateFrom = undefined;
        this.dataControl.updatedDateTo = undefined;

        this.drugExciusionFilterForm.setValue({
            'exclusionId': null,
            'name': null,
            'createdDateFrom': null,
            'createdDateTo': null,
            'updatedDateTo': null,
            'updatedDateFrom': null
        })
    }

    openViewdrugExclusionDialog(exclusionId: string) {
        this.router.navigate(["drug-exclusion-management/details", exclusionId])
    }
    ondrugExclusioDelete(id: any) {
        this.dialogService.showDeleteConfirmDialog((data: any) => {
            if (data) {
                this.exclusionservice.deletedrugExclusion(id).subscribe({
                    next: (data) => {
                        const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                        let exclusionRequestcancel = '';
                        this.translate.get('prescription.exclusionRequestcancel').subscribe(text => exclusionRequestcancel = text);
                        this.dialogService.showSuccessDialog(exclusionRequestcancel, (data: any) => {
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

    ngOnDestroy() {
        this.exclusionservice.drugExclusionList$.next(new ListViewModel<drugExclusionListDetailsModel>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}
