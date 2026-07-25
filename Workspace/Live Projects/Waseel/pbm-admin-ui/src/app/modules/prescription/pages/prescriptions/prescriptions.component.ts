import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import * as moment from 'moment';
import { Subscription } from 'rxjs';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { CancelPrescriptionRequestModel } from '../../../../modules/prescription/models/cancel-prescription-request.model';
import { CancelPrescriptionResponseModel } from '../../../../modules/prescription/models/cancel-prescription-response.model';
import { PrescriptionDetailsDiagnosis } from '../../models/prescription-details-diagnosis.model';
import { PrescriptionValidationsModel } from '../../models/prescription-details-drugs-rejection.model';
import { PrescriptionDetailsDrugs } from '../../models/prescription-details-drugs.model';
import { PrescriptionDetailsPayerMemberPhysicianInfoModel } from '../../models/prescription-details-payer-member-physician-info.model';
import { PrescriptionDetails } from '../../models/prescription-details.model';
import { PrescriptionRequest } from '../../models/prescription-request.model';
import { PrescriptionProviderService } from '../../service/prescription-provider.service';
import { PrescriptionService } from '../../service/prescription.service';
import { HttpException } from 'src/app/util/default-http-client';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';

@Component({
    selector: 'app-prescriptions',
    templateUrl: './prescriptions.component.html',
    styles: [
    ]
})

export class PrescriptionsComponent implements OnInit {
    data: ListViewModel<PrescriptionDetails> = new ListViewModel();
    dataControl: PrescriptionRequest = new PrescriptionRequest();
    drugData: ListViewModel<PrescriptionDetailsDrugs> = new ListViewModel();


    diagnosisData: ListViewModel<PrescriptionDetailsDiagnosis> = new ListViewModel();

    payerMemberPhysicianInfoData: PrescriptionDetailsPayerMemberPhysicianInfoModel = new PrescriptionDetailsPayerMemberPhysicianInfoModel();

    drugRejectionData: PrescriptionValidationsModel[] = [new PrescriptionValidationsModel];
    listIsLoading: boolean = false;
    showEditButton: boolean = false;
    showDeleteButton: boolean = false;
    showMoreActionsMenu: boolean = true;
    moreActionsList: { action: string, displayCode: string, isVisible: (item: PrescriptionDetails) => boolean, allowedAuthorities?: string[] }[] = [
        { action: 'CANCEL', displayCode: 'CANCEL', isVisible: (item) => !['DISPENSED', 'PARTIAL_DISPENSED', 'CANCELLED', 'REJECTED', 'INVALID', 'FAILED', 'PENDING'].includes((item.status || '').toUpperCase()), allowedAuthorities: ['PBM_PRESCRIPTION', 'PRESCRIPTION_SUBMISSION', 'PRESCRIPTION_CANCELLATION'] },
        { action: 'EDIT', displayCode: 'EDIT', isVisible: (item) => ['REJECTED', 'APPROVED', 'PARTIAL_APPROVED'].includes((item.status || '')), allowedAuthorities: ['PBM_PRESCRIPTION', 'PRESCRIPTION_SUBMISSION', 'FOLLOW_UP_PRESCRIPTION'] },
        {
            action: 'DISPENSE', displayCode: 'DISPENSE', isVisible: (item) => ['APPROVED', 'PARTIAL_APPROVED', 'PARTIAL_DISPENSED'].includes((item.status || '')), allowedAuthorities: ['PRESCRIPTION_DISPENSE']
        }
    ]
    dispensePrescriptionDialogOpen = false;
    ePrescriptionReferenceNumber?: string;
    action?: string;

    referenceNoList: any[] = []
    values: any;
    statusValue: any[] = []
    membersList: any[] = []

    status: any[] = []

    dateArrival1: any
    dateArrival2: any
    startdate = moment();
    enddate = moment();
    dateErrorMessage: string = ''
    cancelPrescriptionRequestModel: CancelPrescriptionRequestModel = new CancelPrescriptionRequestModel();
    cancelPrescriptionResponseModel: CancelPrescriptionResponseModel = new CancelPrescriptionResponseModel();
    errorCode: string = '';
    refNo!: number;
    memberN!: string;
    getProvidersSubscription!: Subscription;

    isMemberIdError: boolean = false;
    memberIdErrorMsg: string = "";
    selectedProviderName?: string;


    selectedprovider!: { providerId: string, providerName: string, code: string };
    approvalStatus: any = [
        { key: "APPROVED", value: "APPROVED" },
        { key: "REJECTED", value: "REJECTED" },
        { key: "CANCELLED", value: "CANCELLED" },
        { key: "DISPENSED", value: "DISPENSED" },
        { key: "PARTIALLY DISPENSED", value: "PARTIALLY DISPENSED" }]
    insurance: any = [
        { key: "TAWUNIYA", value: "TAWUNIYA" }
    ]

    providers: any[] = [];


    subscription: Subscription[] = [];

    startDate: any = new Date()
    endDate: any = new Date()

    isFilterDrawerOpen: boolean = false;
    filterForm: FormGroup = new FormGroup({
        referenceNo: new FormControl(),
        dateAndTime: new FormControl(),
        startdate: new FormControl(),
        enddate: new FormControl(),
        memberId: new FormControl(),
        memberName: new FormControl(),

        // policyNumber: new FormControl(),
        insurance: new FormControl(),
        provider: new FormControl(),
        status: new FormControl()
    })
    listPrototype = PrescriptionDetails.prototype;

    currentUser: any;

    constructor(
        private translate: TranslateService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private prescriptionProviderService: PrescriptionProviderService,
        public prescriptionLovService: PrescriptionlovService,
        public prescriptionService: PrescriptionService,
        public dialogService: DialogService,
        private authService: AuthService
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }

    ngOnInit(): void {

        this.prescriptionLovService.initializeAllLists();
        this.prescriptionProviderService.prescription$.subscribe(data => {
            if (!data) {
                this.data = new ListViewModel();
            } else {
                this.data = data;
            }
            this.listIsLoading = false;
            if (this.data.content.length > 0) {
                this.data.content.forEach(element => {
                    this.listIsLoading = false;
                    if (element.dateAndTime) {
                        let date = new Date(element.dateAndTime);
                        element.dateAndTime = moment(date).format("MM/DD/YYYY hh:mm a")
                    }
                });
            }
        })

        this.listIsLoading = false;
        this.showEditButton = false;
        this.showDeleteButton = false;

        this.dateArrival1 = new Date();
        this.dateArrival2 = new Date();

        const params = this.activatedRoute.snapshot.queryParams;
        this.dataControl.referenceNo = params['referenceNo'] || '';
        this.dataControl.enddate = params['enddate'] || '';
        this.dataControl.startdate = params['startdate'] || '';
        this.dataControl.dateAndTime = params['dataAndTime'] || '';
        this.dataControl.memberId = params['memberId'] || '';
        this.dataControl.memberName = params['memberName'] || '';
        this.dataControl.policyNumber = params['policyNumber'] || '';
        this.dataControl.status = params['status'] || '';
        this.dataControl.idNumber = params['idNumber'] || '';
        this.dataControl.insurance = params['insurance'] || '';
        this.dataControl.provider = params['providerId'] || '';
        // this.dataControl.pageNumber = Number.parseInt(params['pageNumber'] || 0);
        // this.dataControl.recordSize = Number.parseInt(params['recordSize'] || 10);
        this.fetchData();
        
    }



    getProviders(event: any) {
        if (this.filterForm.value != null) {
            if (this.getProvidersSubscription != null) {
                this.getProvidersSubscription.unsubscribe();
                this.prescriptionLovService.providersList$.next([]);
            }
            this.getProvidersSubscription = this.prescriptionLovService.getProviders({ value: event }).subscribe(providers => {
                this.providers = providers
            });

        } else {
            this.prescriptionLovService.providersList$.next([]);
        }
    }

    selectItem(provider: { providerId: string, providerName: string, code: string }) {

        this.selectedprovider = provider;
        this.filterForm.get("provider")?.setValue(provider.providerName);
        this.selectedProviderName = provider.providerName;

        this.prescriptionLovService.providersList$.next([]);
    }

    fetchData() {
        this.listIsLoading = true;
        if (!this.currentUser.authorities.some((data: any) => data.authority.includes("VIEW_PRESCRIPTION"))) {
            this.prescriptionProviderService.getPrescriptions(this.dataControl).subscribe();
            this.showMoreActionsMenu = true;
        }

        if (this.currentUser.authorities.some((data: any) => data.authority.includes("VIEW_PRESCRIPTION", 'BUSINESS_RULE_ADMINISTRATION', 'DRUG_FORMULARY_MANAGEMENT'))) {
            this.subscription.push(this.prescriptionProviderService.getPayerPrescriptions(this.currentUser.accId, this.dataControl).subscribe());


            this.showMoreActionsMenu = false;
        }

        if (this.errorCode === '') {
            this.isFilterDrawerOpen = false;
        }
        else {
            this.isFilterDrawerOpen = true;
        }
        this.showEditButton = false;
        this.showDeleteButton = false;

        this.updateQueryParams();

    }

    updateQueryParams() {
        const queryParams = new HttpParams({
            fromObject: {
                'referenceNo': this.dataControl.referenceNo || '',
                'dateAndTime': this.dataControl.dateAndTime || '',
                'memberId': this.dataControl.memberId || '',
                'memberName': this.dataControl.memberName || '',
                'policyNumber': this.dataControl.policyNumber || '',
                'status': this.dataControl.status || '',
                'idNumber': this.dataControl.idNumber || '',
                'insurance': this.dataControl.insurance || '',
                'providerId': this.dataControl.provider || '',
                'pageNumber': this.dataControl.pageNumber || '',
                'recordSize': this.dataControl.recordSize || '',
                'startdate': this.dataControl.startdate || '',
                'enddate': this.dataControl.enddate || ''
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
        this.router.navigate([''], { replaceUrl: true, queryParams: params });
    }


    getExtraClassesForListView() {
        return this.data.content.map((item, index) => {

            if (item.status?.toUpperCase().startsWith("APPROVED") || item.status?.toUpperCase().startsWith("DISPENSED")) {
                return { [index + ':4']: 'text-success dark:text-success-300 body-2-medium' }
            } else if (item.status?.toUpperCase().startsWith("REJECTED") || item.status?.toUpperCase().startsWith("INVALID") || item.status?.toUpperCase().startsWith("FAILED")
                || item.status?.toUpperCase().startsWith("CANCELLED")) {
                return { [index + ':4']: 'text-error dark:text-error-400 body-2-medium' }
            } else if (item.status?.toUpperCase().startsWith("PENDING") || item.status?.toUpperCase().startsWith("PARTIAL_APPROVED")
                || item.status?.toUpperCase().startsWith("PARTIAL_DISPENSED")) {
                return { [index + ':4']: 'text-warning dark:text-error-400 body-2-medium' }
            } else {
                return { [index + ':4']: 'text-text' }
            }
        }).reduce((row1, row2) => ({ ...row1, ...row2 }), {});
    }

    onMoreActionsMenuItemClick(action: string, id: any) {
        this.ePrescriptionReferenceNumber = id;
        if (action == 'DISPENSE') {
            this.action = action;
            this.router.navigate(['prescription', this.ePrescriptionReferenceNumber, 'dispense']);
        } else if (action == 'CANCEL') {
            let canceltitle = "";
            let cancelSubtitle = "";
            this.translate.get('prescription.cancelPrescriptionTitle', { value: this.ePrescriptionReferenceNumber }).subscribe(text => canceltitle = text);
            this.translate.get('prescription.cancelPrescriptionSubTitle').subscribe(text => cancelSubtitle = text);

            this.dialogService.showConfirmDialog(canceltitle, cancelSubtitle, (data: any) => {
                if (data) {
                    this.listIsLoading = true;
                    this.setValueInCancelPrescriptionRequestModel(this.ePrescriptionReferenceNumber, this.payerMemberPhysicianInfoData.payerId);
                    this.prescriptionService.cancelPrescriptionData(this.cancelPrescriptionRequestModel.ePrescriptionReferenceNumber).subscribe({
                        next: (data) => {
                            const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                            let successMsg = '';
                            let referenceNoMsg = '';
                            let referenceNoText = "";
                            this.translate.get('REFERENCENO').subscribe(text => referenceNoText = text);

                            if (prescriptionResponseModel.status == "Invalid" || prescriptionResponseModel.status == "INVALID") {
                                this.dialogService.showErrorDialog(referenceNoText + prescriptionResponseModel.ePrescriptionReferenceNumber + " " + prescriptionResponseModel.statusDescription, (event: any) => {
                                    this.fetchData();
                                })
                            }

                            else if (prescriptionResponseModel.status == "Failed" || prescriptionResponseModel.status == "FAILED") {
                                let referenceNoText = "";
                                this.translate.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoText = text);
                                this.dialogService.showErrorDialog(`${referenceNoText} ${prescriptionResponseModel.ePrescriptionReferenceNumber} ${prescriptionResponseModel.statusDescription}`, (event: any) => {
                                    this.fetchData();
                                })
                            } else {

                                this.translate.get('prescription.cancelSuccess').subscribe(text => successMsg = text);
                                this.translate.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoMsg = text);
                                this.setValueInCancelPrescriptionResponseModel(data);
                                this.dialogService.showSuccessDialog(referenceNoMsg + ": " + this.cancelPrescriptionResponseModel.ePrescriptionReferenceNumber + " " + successMsg, (data: any) => { })
                                this.fetchData();
                                this.listIsLoading = false;
                            }
                        },
                        error: (exception) => {
                            if (exception instanceof HttpException) {
                                console.log(exception);
                                this.dialogService.showErrorDialog(exception.response.error.statusDescription, (data: any) => { })
                            }
                            this.listIsLoading = false;
                        }
                    });
                } else {
                    return;
                }
            })

        } else if (action == 'EDIT') {
            this.router.navigate(["prescription", this.ePrescriptionReferenceNumber, 'followUp']);
        }
    }
    closeDispensePrescriptionDialog() {
        this.dispensePrescriptionDialogOpen = false;
        this.fetchData();
    }

    openDispensePrescriptionDialog() {
        this.dispensePrescriptionDialogOpen = true;
    }


    applyFilter() {
        this.isMemberIdError = false;
        this.memberIdErrorMsg = "";
        let referenceNoValue = this.filterForm.controls['referenceNo'].value;
        const trimmedReferenceNoValue = referenceNoValue?.replace(/^\s+|\s+$/g, '');
        this.refNo = trimmedReferenceNoValue;
        let memberName = this.filterForm.controls['memberName'].value;
        let trimmedMemberName = memberName?.replace(/^\s+|\s+$/g, '');
        this.memberN = trimmedMemberName;
        if (memberName) {
            if (!isNaN(memberName)) {
                this.dataControl.idNumber = memberName;
                this.dataControl.memberName = "";
            } else {
                let memberNameWithoutSpace = memberName?.replace(/ /g, "");
                if (!isNaN(memberNameWithoutSpace)) {
                    this.isMemberIdError = true;
                    this.translate.get("prescription.idNumberErrorInFilter").subscribe((msg: any) => {
                        this.memberIdErrorMsg = msg;
                    });

                    return;
                } else {
                    const words = trimmedMemberName?.split(' ');
                    this.dataControl.memberName = "";
                    this.dataControl.memberName = words.map((word: any) => {
                        return word[0]?.toUpperCase() + word.substring(1);
                    }).join(" ");
                }
            }
        }



        this.errorCode = '';
        this.dataControl.referenceNo = trimmedReferenceNoValue
        this.dataControl.enddate = moment(this.filterForm.controls['enddate'].value).format("DD-MM-yyyy");
        this.dataControl.startdate = moment(this.filterForm.controls['startdate'].value).format("DD-MM-yyyy");
        this.dataControl.insurance = this.filterForm.controls['insurance'].value;
        this.dataControl.provider = this.selectedprovider ? this.selectedprovider.providerId : "";
        this.dataControl.status = this.filterForm.controls['status'].value;
        if ((this.dataControl.startdate > this.dataControl.enddate)) {
            this.translate.get('prescription.startDateGreaterThanEndDate').subscribe(text => this.errorCode = text);
        }
        if ((this.dataControl.startdate === "Invalid date" && this.dataControl.enddate !== "Invalid date") ||
            (this.dataControl.startdate !== "Invalid date" && this.dataControl.enddate === "Invalid date")) {
            this.translate.get('prescription.missigDatePairs').subscribe(text => this.errorCode = text);
        }
        this.dataControl.pageNumber = 0;
        if (this.errorCode === '') { this.fetchData(); }
        
    }

    resetFilter() {
        this.errorCode = '';
        this.dataControl.referenceNo = undefined;
        this.dataControl.startdate = undefined;
        this.dataControl.dateAndTime = undefined;
        this.dataControl.memberId = undefined;
        this.dataControl.memberName = undefined;
        this.dataControl.idNumber = undefined;

        this.dataControl.enddate = undefined;
        this.dataControl.status = undefined;
        this.dataControl.insurance = undefined;
        this.dataControl.provider = undefined;
        this.filterForm.setValue({
            'referenceNo': null,
            'startdate': null,
            'enddate': null,
            'memberName': null,
            'memberId': null,

            'status': null,
            'insurance': null,
            'provider': null,
            'dateAndTime': null
        });
        this.selectedprovider = {} as { providerId: string; providerName: string; code: string; };
        this.isMemberIdError = false;
        this.memberIdErrorMsg = "";
        this.selectedProviderName = "";
    }



    setValueInCancelPrescriptionRequestModel(ePrescriptionReferenceNumber: any, payerId: any) {
        this.cancelPrescriptionRequestModel!.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
        this.cancelPrescriptionRequestModel!.payerId = payerId;
    }

    setValueInCancelPrescriptionResponseModel(responseData: any) {
        let responseModel = <CancelPrescriptionResponseModel>responseData;
        this.cancelPrescriptionResponseModel!.canCancel = responseModel.canCancel;
        this.cancelPrescriptionResponseModel!.canFollowUp = responseModel.canFollowUp;
        this.cancelPrescriptionResponseModel!.ePrescriptionReferenceNumber = responseModel.ePrescriptionReferenceNumber;
        this.cancelPrescriptionResponseModel!.httpStatusCode = responseModel.httpStatusCode;
        this.cancelPrescriptionResponseModel!.status = responseModel.status;
        this.cancelPrescriptionResponseModel!.statusDescription = responseModel.statusDescription;
    }

    openViewDrugDiagnosisDialog(ePrescriptionReferenceNumber: string) {

        this.prescriptionService.setReturnUrl("prescription/list");
        this.authService.showSystemLoader()
        this.router.navigate(["prescription/details", ePrescriptionReferenceNumber])

    }

    ngOnDestroy() {
        this.prescriptionProviderService.prescription$.next(new ListViewModel<PrescriptionDetails>);
        this.subscription.forEach(input => input.unsubscribe());
    }

}