import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import * as moment from 'moment';
import { CustomizationlovService } from 'src/app/modules/shared/services/customizationlov-service/customizationlov.service';
import { BehaviorSubject, Subscription } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { DrugListModel, PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { HttpException } from 'src/app/util/default-http-client';
import { CustomizationRequestModel } from '../../models/customization-request.model';
import { PrescriptionCustomizationRequest } from '../../models/prescription-customization-request.model';
import { DrugsDetails } from '../../models/prescription-drug.model';
import { CustomizationService } from '../../service/customization.service';
import { HttpParams } from '@angular/common/http';
import { PrescriptionService } from '../../service/prescription.service';


@Component({
    selector: 'app-customization-requests',
    templateUrl: './customization-requests.component.html',
    styleUrls: ['./customization-requests.component.css']
})
export class CustomizationRequestsComponent implements OnInit {
    data: ListViewModel<CustomizationRequestModel> = new ListViewModel();
    dataControl: PrescriptionCustomizationRequest = new PrescriptionCustomizationRequest();
    PrescriptionCustomizationRequest: CustomizationRequestModel = new CustomizationRequestModel();
    customizationRequest$: BehaviorSubject<ListViewModel<CustomizationRequestModel>> = new BehaviorSubject(new ListViewModel());
    listPrototype = CustomizationRequestModel.prototype;
    listIsLoading: boolean = false;
    showEditButton: boolean = false;
    showDeleteButton: boolean | ((item: any) => boolean) = true;
    showAcceptButton: boolean | ((item: any) => boolean) = false;
    showRejectButton: boolean | ((item: any) => boolean) = false;
    showMoreActionsMenu: boolean = true;
    moreActionsList: { action: string, displayCode: string, isVisible: (item: any) => boolean, allowedAuthorities?: string[] }[] = [{
        action: 'openPrescription',
        displayCode: 'prescription.viewPrescription',
        isVisible: () => true,
        allowedAuthorities: ['101;PBM_ADMIN', '101;VIEW_PRESCRIPTION']
    }];


    errorCode: string = '';
    dateCode: string = "";
    greaterDate: string = '';
    getDrugsSubscription?: Subscription;
    drug: any[] = [];
    subscription: Subscription[] = [];

    selecteddrug!: { unitPrice: string, sfdaCode: string, sfdaDescription: string, scientificName: string, scientificCode: string, drugFormulary: boolean, dosageForm: string, strengthUnit: string };
    searchFormControl: FormControl = new FormControl();
    selectedDrug?: DrugsDetails;
    selectedDrugName?: string
    selectedDrugCode?: string;
    noContentSubtitle: boolean = true


    status: any = [
        { key: "Pending", value: "PENDING" },
        { key: "Accepted", value: "ACCEPTED" },
        { key: "Rejected", value: "REJECTED" }

    ]

    isFilterDrawerOpen: boolean = false;
    filterForm: FormGroup = new FormGroup({
        status: new FormControl(),
        fromDate: new FormControl(),
        endDate: new FormControl(),
        drugCode: new FormControl(),
        drugName: new FormControl(),
        moduleName: new FormControl(),
    })
    memberN: any;
    refNo: any;

    constructor(
        private customizationService: CustomizationService,
        private activatedRoute: ActivatedRoute,
        private route: Router,
        private translate: TranslateService,
        public prescriptionLovService: PrescriptionlovService,
        private dialogService: DialogService,
        private authService: AuthService,
        public customizationLovService: CustomizationlovService,
        public PrescriptionService: PrescriptionService) { }

    ngOnInit(): void {

        this.authService.user$.subscribe(user => {
            this.showDeleteButton = !this.authService.hasAnyAuthority(user, ['101;PBM_ADMIN']) ? (item: CustomizationRequestModel) => item.status?.toUpperCase() == 'PENDING' : false;
            this.showAcceptButton = this.authService.hasAnyAuthority(user, ['101;PBM_ADMIN']) ? (item: CustomizationRequestModel) => item.status?.toUpperCase() == 'PENDING' : false;
            this.showRejectButton = this.authService.hasAnyAuthority(user, ['101;PBM_ADMIN']) ? (item: CustomizationRequestModel) => item.status?.toUpperCase() == 'PENDING' : false;
        });

        this.prescriptionLovService.initializeAllLists();
        this.customizationLovService.initializeAllLists();
        this.customizationService.customizationRequest$.subscribe(data => {
            if (!data) {
                this.noContentSubtitle = true;
                this.data = new ListViewModel();
                console.log(" no data", data)
            } else {
                this.data = data;
            }

            if (this.data.content.length > 0) {
                this.data.content.forEach(element => {
                    this.listIsLoading = false;

                    if (element.lastUpdatedDate) {
                        let date = new Date(element.lastUpdatedDate);
                        element.lastUpdatedDate = moment(date).format("MM/DD/YYYY hh:mm a")
                    }

                    if (element.moduleName) {
                        if (element.moduleName === 'Payer_Customization_DrugToDiagnosisInteraction') {
                            element.moduleName = 'DrugToDiseaseInteraction';
                        }
                        let originalString = element.moduleName;
                        const charactersToDelete = /^(Payer_Customization_|IDF_|FDB_)/;

                        const changedWord = element.moduleName.replace(charactersToDelete, '').trim();

                        element.moduleName = changedWord?.replace(/([a-z])([A-Z])/g, '$1 $2')
                    }

                });

            }
        })
        const params = this.activatedRoute.snapshot.queryParams;
        this.dataControl.requestDateAndTime = params['requestDateAndTime'] || '';
        this.dataControl.fromDate = params['fromDate'] || '';
        this.dataControl.endDate = params['endDate'] || '';
        this.dataControl.requestDateAndTime = params['requestDateAndTime'] || '';
        this.dataControl.drugCode = params['drugCode'] || '';
        this.dataControl.drugName = params['drugName'] || '';
        this.dataControl.moduleName = params['moduleName'] || '';
        this.dataControl.status = params['status'] || '';
        this.dataControl.customizationRequestId = params['customizationRequestId'] || '';
        this.dataControl.customizationDetails = params['customizationDetails'] || '';
        this.dataControl.pageNumber = Number.parseInt(params['pageNumber'] || 0);
        this.dataControl.recordSize = Number.parseInt(params['recordSize'] || 10);

        this.fetchData();

        this.applyFilter();
    }

    fetchData() {
        this.subscription.push(this.customizationService.getPayerCustomizationRequest(this.dataControl).subscribe(data => {
            if (data.content.length == 0) {
                this.listIsLoading = false;
            }

            if (this.errorCode === '') {
                this.isFilterDrawerOpen = false;
            }
            else {
                this.isFilterDrawerOpen = true;
            }
            this.updateQueryParams()
        }));

    }
    updateQueryParams() {
        const queryParams = new HttpParams({
            fromObject: {
                'requestDateAndTime': this.dataControl.requestDateAndTime || '',
                'fromDate': this.dataControl.fromDate || '',
                'endDate': this.dataControl.endDate || '',
                'drugCode': this.dataControl.drugCode || '',
                'drugName': this.dataControl.drugName || '',
                'status': this.dataControl.status || '',
                'moduleName': this.dataControl.moduleName || '',
                'customizationRequestId': this.dataControl.customizationRequestId || '',
                'customizationDetails': this.dataControl.customizationDetails || '',
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

    }


    applyFilter() {
        this.errorCode = '';
        this.greaterDate = '';
        this.dataControl.fromDate = moment(this.filterForm.controls['fromDate'].value).format("DD-MM-yyyy");
        this.dataControl.endDate = moment(this.filterForm.controls['endDate'].value).format("DD-MM-yyyy");
        this.dataControl.status = this.filterForm.controls['status'].value;
        this.dataControl.drugCode = this.selectedDrug ? this.selectedDrug.drugCode : "";
        this.dataControl.moduleName = this.filterForm.controls['moduleName'].value;
        this.dateCode = "";
        if ((this.dataControl.fromDate > this.dataControl.endDate)) {
            this.translate.get('prescription.startDateGreaterThanEndDate').subscribe(text => this.errorCode = text);
            console.log("start:", this.errorCode)
        }
        if ((this.dataControl.fromDate === "Invalid date" && this.dataControl.endDate !== "Invalid date") ||
            (this.dataControl.fromDate !== "Invalid date" && this.dataControl.endDate === "Invalid date")) {
            this.translate.get('prescription.missigDatePairs').subscribe(text => this.errorCode = text);
        }


        this.dataControl.pageNumber = 0;
        if (this.errorCode === '') {
            this.isFilterDrawerOpen = false;
            this.listIsLoading = true;
            this.fetchData();
        }

    }
    getExtraClassesForListView() {
        return this.data.content.map((item, index) => {

            if (item.status?.toUpperCase().startsWith("ACCEPTED")) {
                return { [index + ':4']: 'text-success dark:text-success-300 body-2-medium' }
            } else if (item.status?.toUpperCase().startsWith("REJECTED")) {
                return { [index + ':4']: 'text-error dark:text-error-400 body-2-medium' }
            } else if (item.status?.toUpperCase().startsWith("PENDING")) {
                return { [index + ':4']: 'text-warning dark:text-error-400 body-2-medium' }
            } else {
                return { [index + ':4']: 'text-text' }
            }
        }).reduce((row1, row2) => ({ ...row1, ...row2 }), {});
    }

    onMoreActionsMenuItemClick(action: string, id: any) {
        this.PrescriptionService.setReturnUrl("prescription/customization-requests");
        this.authService.showSystemLoader()

        let index = this.data.content.findIndex(request => request.customizationRequestId == id);

        if (index != -1) {
            const ePrescriptionReferenceNo = this.data.content[index].ePrescriptionReferenceNo
            if (action == 'openPrescription') {
                this.route.navigate(["prescription/details", ePrescriptionReferenceNo]);
            }
        }

    }


    onDrugDelete(id: any) {
        let canceltitle = "";
        let cancelSubtitle = "";
        this.translate.get('prescription.cancelCustomizationTitle').subscribe(text => canceltitle = text);
        this.translate.get('prescription.cancelPrescriptionSubTitle').subscribe(text => cancelSubtitle = text);

        this.dialogService.showConfirmDialog(canceltitle, cancelSubtitle, (data: any) => {
            if (data) {

                this.customizationService.deletePayerCustomizationRequest(id).subscribe({

                    next: (data) => {
                        let successMsg = '';
                        let referenceNoMsg = '';
                        let referenceNoText = "";
                        this.translate.get('DELETE-SUCCESS').subscribe(text => successMsg = text);
                        this.translate.get('prescription.customizationRequestCancel').subscribe(text => referenceNoMsg = text);
                        this.dialogService.showSuccessDialog(referenceNoMsg + successMsg, (data: any) => {
                            if (data) {
                                this.listIsLoading = true;
                                this.resetFilter();
                                this.fetchData();

                            }
                        })

                    },
                    error: (exception) => {
                        if (exception instanceof HttpException) {
                            console.log(exception);
                            this.dialogService.showErrorDialog(exception.response.error.statusDescription, () => { })
                        }
                        this.listIsLoading = false;
                    }
                })
            } else {
                return;
            }
        })
    }

    onDecisionMade(id: string, decision: 'Accepted' | 'Rejected', rejectionReason?: string) {
        if (this.listIsLoading) {
            return;
        }
        this.listIsLoading = true;
        let index = this.data.content.findIndex(request => request.customizationRequestId == id);
        if (index != -1) {
            let request = { status: decision, rejectionReason: rejectionReason }
            if (decision == "Accepted") {
                let acceptTitle = "";
                let acceptSubtitle = "";
                this.translate.get('prescription.acceptTitle').subscribe(text => acceptTitle = text);
                this.translate.get('prescription.acceptSubtitle').subscribe(text => acceptSubtitle = text);
                this.dialogService.showConfirmDialog(acceptTitle, acceptSubtitle, (data: any) => {
                    if (data) {
                        this.customizationService.updateCustomizationRequest(id, request).subscribe({
                            next: (data) => {

                                let successMsg = '';
                                let referenceNoMsg = '';
                                let referenceNoText = "";
                                this.translate.get('ACCEPT-SUCCESS').subscribe(text => successMsg = text);
                                this.translate.get('prescription.medicalRequestAcceptReject').subscribe(text => referenceNoMsg = text);
                                this.dialogService.showSuccessDialog(referenceNoMsg + " " + successMsg, (data: any) => {
                                    if (data) { this.fetchData() }
                                })
                            },
                            error: (exception) => {
                                if (exception instanceof HttpException) {
                                    console.log(exception);
                                    this.dialogService.showErrorDialog(exception.response.error.statusDescription, () => { })
                                }
                                this.listIsLoading = false;
                            }
                            // }
                        })
                    }
                    else {
                        this.listIsLoading = false;
                    }
                })


            }

            if (decision == "Rejected") {
                let rejectTitle = "";
                let rejectSubtitle = "";
                this.translate.get('prescription.rejectTitle').subscribe(text => rejectTitle = text);
                this.translate.get('prescription.rejectSubtitle').subscribe(text => rejectSubtitle = text);
                this.dialogService.showConfirmDialog(rejectTitle, rejectSubtitle, (data: any) => {
                    if (data) {
                        this.customizationService.updateCustomizationRequest(id, request).subscribe({
                            next: (data) => {

                                let successMsg = '';
                                let referenceNoMsg = '';
                                let referenceNoText = "";
                                this.translate.get('REJECT-SUCCESS').subscribe(text => successMsg = text);
                                this.translate.get('prescription.medicalRequestAcceptReject').subscribe(text => referenceNoMsg = text);

                                this.dialogService.showSuccessDialog(referenceNoMsg + " " + successMsg, (data: any) => { if (data) { this.fetchData() } })
                            },
                            error: (exception) => {
                                if (exception instanceof HttpException) {
                                    console.log(exception);
                                    this.dialogService.showErrorDialog(exception.response.error.statusDescription, () => { })
                                }
                                this.listIsLoading = false;
                            }
                            // }
                        })
                    }
                    else {
                        this.listIsLoading = false;;
                    }
                })


            }

        }
    }



    resetFilter() {
        this.errorCode = '';
        this.dataControl.fromDate = undefined;
        this.dataControl.endDate = undefined;
        this.dataControl.status = undefined;
        this.dataControl.drugCode = undefined;
        this.dataControl.drugName = undefined;
        this.dataControl.moduleName = undefined;
        this.filterForm.setValue({
            'fromDate': null,
            'endDate': null,
            'status': null,
            'drugCode': null,
            'drugName': null,
            'moduleName': null
        });
        this.selectedDrug = {} as { id: string, sfdaCode: string, sfdaDescription: string, dosageForm: string, strengthUnit: string };
        this.selectedDrugName = "";
        this.selectedDrugCode = "";

    }



    getDrugs(event: any) {
        if (this.filterForm.value != null) {
            if (this.getDrugsSubscription != null) {
                this.getDrugsSubscription.unsubscribe();
                this.prescriptionLovService.drugsList$.next(new ListViewModel<DrugListModel>());
            }
            this.getDrugsSubscription = this.prescriptionLovService.getDrugs({ drugValue: event, idNumber: '' })
                .subscribe({
                    next: (value) => this.getDrugsSubscription = undefined,
                    error: (value) => this.getDrugsSubscription = undefined,
                });
        } else {
            this.prescriptionLovService.drugsList$.next(new ListViewModel<DrugListModel>());
        }
    }



    selectDrug(drug: { unitPrice: string, sfdaCode: string, sfdaDescription: string, scientificName: string, scientificCode: string, dosageForm: string, strengthUnit: string }) {


        this.selectedDrug = { id: drug.sfdaCode, drugCode: drug.sfdaCode, unitPrice: Number.parseInt(drug.unitPrice), drugName: drug.sfdaDescription, scientificName: drug.scientificName, dosageForm: drug.dosageForm, strengthUnit: drug.strengthUnit };
        this.searchFormControl.setValue(`${drug.sfdaCode} | ${drug.sfdaDescription} `);
        this.filterForm.get('drugCode')?.setValue(drug.sfdaCode);
        this.filterForm.get('drugName')?.setValue(drug.sfdaDescription);
        this.selectedDrugName = drug.sfdaDescription
        this.selectedDrugCode = drug.sfdaCode
        this.prescriptionLovService.drugsList$.next(new ListViewModel<DrugListModel>());
    }

    ngOnDestroy() {
        this.customizationService.customizationRequest$.next(new ListViewModel<CustomizationRequestModel>);
        this.subscription.forEach(input => input.unsubscribe());
    }
}