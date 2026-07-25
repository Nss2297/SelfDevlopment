import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { BehaviorSubject, Subscription, map, withLatestFrom } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { CancelPrescriptionRequestModel } from '../../../../modules/prescription/models/cancel-prescription-request.model';
import { CancelPrescriptionResponseModel } from '../../../../modules/prescription/models/cancel-prescription-response.model';
import { HttpException } from '../../../../util/default-http-client';
import { EligibilityValidation } from '../../models/eligibility-validations.model';
import { PrescriptionDetailsDiagnosis } from '../../models/prescription-details-diagnosis.model';
import { PrescriptionValidationsModel } from '../../models/prescription-details-drugs-rejection.model';
import { PrescriptionDetailsDrugs } from '../../models/prescription-details-drugs.model';
import { PrescriptionDetailsPayerMemberPhysicianInfoModel } from '../../models/prescription-details-payer-member-physician-info.model';
import { PrescriptionRequest } from '../../models/prescription-request.model';
import { PrescriptionService } from '../../service/prescription.service';
import { AddPrescriptionStoreService } from '../../service/add-prescription-store/add-prescription-store.service';
import { CustomizationService } from '../../service/customization.service';
import { environment } from 'src/environments/environment';
import { DrugModifyDecisionModel } from '../../models/drug-modify-decision.model';




@Component({
    selector: 'app-view-prescription-details',
    templateUrl: './view-prescription-details.component.html',
    styles: [
    ]
})
export class ViewPrescriptionDetailsComponent implements OnInit, OnDestroy {

    listIsLoading: boolean = false;
    selectedDrugCode?: any;
    selectedoverridingReason?: string;
    currentTab = 0;

    providerOverrideCommentDialogDialogOpen = false;

    ePrescriptionReferenceNumber!: string;//

    payerMemberPhysicianInfoData: PrescriptionDetailsPayerMemberPhysicianInfoModel = new PrescriptionDetailsPayerMemberPhysicianInfoModel();//

    diagnosisData: ListViewModel<PrescriptionDetailsDiagnosis> = new ListViewModel();//

    drugData: ListViewModel<PrescriptionDetailsDrugs> = new ListViewModel();//



    updatedDrugData: ListViewModel<PrescriptionDetailsDrugs> = new ListViewModel();

    drugListPrototype = PrescriptionDetailsDrugs.prototype;

    drugsdataControl: PrescriptionRequest = new PrescriptionRequest();

    diagnosisdataControl: PrescriptionRequest = new PrescriptionRequest();

    diagnosisListPrototype = PrescriptionDetailsDiagnosis.prototype;

    medicalValidations: PrescriptionValidationsModel[] = [new PrescriptionValidationsModel()];

    businessValidations: PrescriptionValidationsModel[] = [new PrescriptionValidationsModel()];

    action?: string;

    cancelPrescriptionRequestModel: CancelPrescriptionRequestModel = new CancelPrescriptionRequestModel();

    cancelPrescriptionResponseModel: CancelPrescriptionResponseModel = new CancelPrescriptionResponseModel();

    eligibilityValidation: EligibilityValidation[] = [new EligibilityValidation()];

    drugCode: any

    dispenseDialogOpen = false;

    customizationDialogOpen = false;

    drugStatusDescriptionArray: { drugCode: string, status: string, overridingReason: string, showCommentIcon: boolean }[] = [];
    drugArray: DrugModifyDecisionModel[] = [];

    selectedDrug: any;
    selectedMedicalValidations: any



    drugToAgeList: PrescriptionValidationsModel[] = [];

    drugToDiseaseContraIndicationsList: PrescriptionValidationsModel[] = [];

    drugToDiseaseIndicationsList: PrescriptionValidationsModel[] = [];

    drugToGenderList: PrescriptionValidationsModel[] = [];

    duplicateTherapyList: PrescriptionValidationsModel[] = [];

    quantityLimitCheckList: PrescriptionValidationsModel[] = [];

    refillToSoonList: PrescriptionValidationsModel[] = [];

    drugToDrugInteractionList: PrescriptionValidationsModel[] = [];

    errorsList: PrescriptionValidationsModel[] = [];

    returnUrl?: string;

    noContentMessage?: string;

    noContentSubtitle?: string;

    noContentIsSearchAgain = false;

    isMemberPhysicianInfoAvailable?: boolean;

    isAnyDrugApproved!: boolean;

    isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);



    canPrintUCAF$: BehaviorSubject<boolean> = new BehaviorSubject(false);

    showUCAF$: BehaviorSubject<boolean> = new BehaviorSubject(false);



    previousStatus$: BehaviorSubject<string> = new BehaviorSubject<string>('');
    dispensedDetails: any;


    categorizedBusinessValidations: { displayCode: string, startsWithPattern: string, validations: PrescriptionValidationsModel[] }[] = [

        {

            displayCode: 'PRIOR-REQUEST_DRUG_FORMULARY',

            startsWithPattern: 'BR_DF',

            validations: []

        },

        {

            displayCode: 'PRIOR-REQUEST_ELIGIBILITY',

            startsWithPattern: 'BR_ELG',

            validations: []

        },

        {

            displayCode: 'PRIOR-REQUEST_POLICY_CONSUMPTION',

            startsWithPattern: 'BR_PC',

            validations: []

        },

        {

            displayCode: 'PRIOR-REQUEST_SPECIALTY_EXCLUSION',

            startsWithPattern: 'BR_EXL',

            validations: []

        },

        {

            displayCode: 'PRIOR-REQUEST_REJECTED_BY_PAYER',

            startsWithPattern: 'PYR102',

            validations: []

        },
        {

            displayCode: 'PRIOR-REQUEST_APPROVED_BY_PAYER',

            startsWithPattern: 'PYR_APPR',

            validations: []

        },
        {
            displayCode: 'NON-SENSITIVE_DRUGS',

            startsWithPattern: 'BR_SDNF01',

            validations: []
        }

    ];



    subscriptions: Subscription[] = [];

    drugName?: any;

    drugList!: ListViewModel<PrescriptionDetailsDrugs>;
    disabledDrugs: Set<string> = new Set<string>();
    dataControl: any;
    DISABLED_DRUGS_KEY = 'disabledDrugs';
    buttonTooltipText = "Customization is already Added";

    prescribeGenericOrBrandFlow: boolean = environment.featureToggle['prescribeGenericOrBrandFlow'];

    constructor(
        public storeService: AddPrescriptionStoreService,
        private prescriptionService: PrescriptionService,
        private activeRouter: ActivatedRoute,
        private router: Router,
        private translate: TranslateService,
        private dialogService: DialogService,
        public authService: AuthService,
        private customizationS: CustomizationService
    ) {
        const storedDisabledDrugs = localStorage.getItem(this.DISABLED_DRUGS_KEY);
        if (storedDisabledDrugs) {
            this.disabledDrugs = new Set<string>(JSON.parse(storedDisabledDrugs));
        }
    }

    ngOnInit(): void {


        this.drugsdataControl.recordSize = 5;
        this.diagnosisdataControl.recordSize = 5;



        this.subscriptions.push(this.activeRouter.params.subscribe(params => {

            this.ePrescriptionReferenceNumber = params['ePrescriptionReferenceNumber'];
        }));
        this.subscriptions.push(this.prescriptionService.prescriptionDetailsDrugs$.subscribe(data => {
            this.drugData = data

            this.isAnyDrugApproved = data.content.some(x => x.status === 'APPROVED');
            this.drugData.content.forEach(res => {
                if (!res.drugCode) {
                    res.drugCode = res.scientificCode;
                    res.drugName = res.scientificName;
                }
                return res;
            })
        }));
        this.subscriptions.push(this.prescriptionService.prescriptionDetailsDiagnosis$.subscribe(data => {
            this.diagnosisData = data;


        }));
        this.subscriptions.push(
            this.prescriptionService.prescriptionDetailsPayerMemberPhysicianInfo$.pipe(
                withLatestFrom(this.authService.user$),
                map(result => ({ data: result[0], currentUser: result[1] }))
            ).subscribe(({ data, currentUser }) => {

                this.payerMemberPhysicianInfoData = data;
                this.canPrintUCAF$.next(this.payerMemberPhysicianInfoData.providerId != null
                    && this.payerMemberPhysicianInfoData.providerId == currentUser.accId
                    && ['PENDING', 'APPROVED', 'PARTIAL_APPROVED', 'DISPENSED', 'PARTIAL_DISPENSED', 'REJECTED'].includes((this.payerMemberPhysicianInfoData.status?.toUpperCase() || '')));
                if (Object.keys(this.payerMemberPhysicianInfoData).length > 0) {
                    let previousStatus = "";
                    this.previousStatus$.subscribe((status) => {
                        previousStatus = status;
                    });
                    if (this.payerMemberPhysicianInfoData.status == "APPROVED" || this.payerMemberPhysicianInfoData.status == "PARTIAL_DISPENSED" || this.payerMemberPhysicianInfoData.status == "PARTIAL_APPROVED") {
                        this.listIsLoading = true
                        this.previousStatus$.next("APPROVED")
                    }
                    this.noContentSubtitle = "     ";

                    if (currentUser.authorities?.some((data: any) => data.authority.includes("PRESCRIPTION_INQUIRY")) && (previousStatus != "APPROVED") && (this.payerMemberPhysicianInfoData.status === "DISPENSED" || this.payerMemberPhysicianInfoData.status === "CANCELLED" || this.payerMemberPhysicianInfoData.status === "REJECTED")) {
                        let message = "";
                        this.translate.get('prescription.searchAgainMessage', { referencenumber: this.ePrescriptionReferenceNumber, status: this.payerMemberPhysicianInfoData.status }).subscribe(text => message = text);
                        this.isMemberPhysicianInfoAvailable = false;
                        this.noContentMessage = message;
                    } else if (currentUser.authorities?.some((data: any) => data.authority.includes("PRESCRIPTION_INQUIRY")) && (this.payerMemberPhysicianInfoData.status === "FAILED" || this.payerMemberPhysicianInfoData.status === "INVALID" || this.payerMemberPhysicianInfoData.status === "PENDING")) {
                        let message = "";
                        this.translate.get('prescription.searchAgainMessageForInvalid', { referencenumber: this.ePrescriptionReferenceNumber, status: this.payerMemberPhysicianInfoData.status }).subscribe(text => message = text);
                        this.isMemberPhysicianInfoAvailable = false;
                        this.noContentMessage = message;
                    } else {

                        this.isMemberPhysicianInfoAvailable = true;
                    }
                }
                else {
                    this.listIsLoading = true
                    let searchAgainMessage = "";
                    this.translate.get('prescription.searchAgainSubtitle', { referencenumber: this.ePrescriptionReferenceNumber }).subscribe(text => searchAgainMessage = text);
                    this.noContentIsSearchAgain = true;
                    this.noContentSubtitle = searchAgainMessage;
                }
                this.listIsLoading = false;

            }));
        this.subscriptions.push(this.prescriptionService.prescriptionMedicalValidations$.subscribe(data => {

            this.medicalValidations = data;
            this.organizeMedicalValidations();
            this.listIsLoading = false;
        }));
        this.subscriptions.push(this.prescriptionService.prescriptionBusinessValidations$.subscribe(data => {
            this.businessValidations = data;
            this.organizeBusinessValidations();
            this.listIsLoading = false;
        }));
        this.fetchData();
        this.prescriptionService.getReturnUrl().subscribe((url: string) => {
            this.returnUrl = url;
        });

    }

    fetchData(tableName?: string) {
        this.listIsLoading = true;
        switch (tableName) {
            case 'DRUGS':
                this.prescriptionService.getPrescriptionDetailsDrugs(this.ePrescriptionReferenceNumber, this.drugsdataControl.pageNumber, this.drugsdataControl.recordSize).subscribe();
                break;

            case 'DIAGNOSIS':

                this.prescriptionService.getPrescriptionDetailsDiagnosis(this.ePrescriptionReferenceNumber, this.diagnosisdataControl.pageNumber, this.diagnosisdataControl.recordSize).subscribe();

                break;

            default:
                this.prescriptionService.getPrescriptionDetailsDrugs(this.ePrescriptionReferenceNumber, this.drugsdataControl.pageNumber, this.drugsdataControl.recordSize).subscribe();
                this.prescriptionService.getPrescriptionDetailsDiagnosis(this.ePrescriptionReferenceNumber, this.diagnosisdataControl.pageNumber, this.diagnosisdataControl.recordSize).subscribe();
                break;
        }
        this.prescriptionService.getPayerMemberPhysicianInfo(this.ePrescriptionReferenceNumber).subscribe((x: any) => {
            this.authService.hideSystemLoader();
        });
        this.prescriptionService.getPrescriptionDetailsDrugsRejection(this.ePrescriptionReferenceNumber, 'medical').subscribe();
        this.prescriptionService.getPrescriptionDetailsDrugsRejection(this.ePrescriptionReferenceNumber, 'business').subscribe();
        this.prescriptionService.fetchDispenseDetails(this.ePrescriptionReferenceNumber,).subscribe(res => {
            this.dispensedDetails = res;

        });
    }

    rejectionCategery: any;
    organizeMedicalValidations() {
        this.drugToAgeList = [];
        this.drugToGenderList = [];
        this.drugToDiseaseContraIndicationsList = [];
        this.drugToDiseaseIndicationsList = [];
        this.quantityLimitCheckList = [];
        this.duplicateTherapyList = [];
        this.drugToDrugInteractionList = [];
        this.refillToSoonList = [];
        this.errorsList = [];
        this.medicalValidations.forEach(element => {

            const denialCode = element.denialCode;


            if (denialCode) {
                if (denialCode.includes("CPAGE902")) {
                    this.drugToAgeList.push(element);

                } else if (denialCode.includes("CPGNDR403")) {
                    this.drugToGenderList.push(element);
                } else if (denialCode.includes("CPINDC001")) {
                    this.drugToDiseaseContraIndicationsList.push(element);
                } else if (denialCode.includes("CPINDI001")) {
                    this.drugToDiseaseIndicationsList.push(element);

                } else if (denialCode.includes("CPQTL912")) {
                    this.quantityLimitCheckList.push(element);
                } else if (denialCode.includes("CPTDE0001")) {
                    this.duplicateTherapyList.push(element);
                } else if (denialCode.includes("CPDDI701")) {
                    this.drugToDrugInteractionList.push(element);
                } else if (denialCode.includes("CPREF390")) {
                    if (this.refillToSoonList.findIndex(ele => ele.drugCode === element.drugCode) === -1) {
                        this.refillToSoonList.push(element);
                    }
                } else {
                    this.errorsList.push(element);
                }

            }


        });
    }

    isStatusCancelledOrDispensed(status: string | undefined): boolean {
        return status === 'CANCELLED' || status === 'DISPENSED' || status === 'INVALID' || status === 'FAILED';
    }
    organizeBusinessValidations() {
        this.categorizedBusinessValidations.forEach(category => {
            category.validations =
                this.businessValidations.filter(validation => validation.denialCode?.startsWith(category.startsWithPattern));
        });
    }

    closeDispenseDialog = () => {
        this.dispenseDialogOpen = false;
        this.fetchData();
    }

    closeDispensePrescriptionDialog() {
        this.dispenseDialogOpen = false;
        this.fetchData();
    }

    openDispenseDialog(dispenseType: string) {
        this.dispenseDialogOpen = true;
        this.action = dispenseType;
    }

    getExtraClassesForListView() {
        return this.drugData.content.map((item, index) => {
            if (item.status?.toUpperCase().startsWith("APPROVED") || item.status?.toUpperCase().startsWith("DISPENSED")) {
                if (!item.useUnitType) {
                    return { [index + ':11']: 'text-success dark:text-success-300 body-2-medium text-transform:uppercase' }
                } else {
                    return { [index + ':12']: 'text-success dark:text-success-300 body-2-medium text-transform:uppercase' }
                }
            } else if (item.status?.toUpperCase().startsWith("REJECTED") || item.status?.toUpperCase().startsWith("INVALID") || item.status?.toUpperCase().startsWith("FAILED")
                || item.status?.toUpperCase().startsWith("CANCELLED")) {
                if (!item.useUnitType) {
                    return { [index + ':11']: 'text-error dark:text-error-400 body-2-medium' }
                } else {
                    return { [index + ':12']: 'text-error dark:text-error-400 body-2-medium' }
                }
            } else if (item.status?.toUpperCase().startsWith("PENDING") || item.status?.toUpperCase().startsWith("PARTIAL_APPROVED")
                || item.status?.toUpperCase().startsWith("PARTIAL_DISPENSED")) {
                if (!item.useUnitType) {
                    return { [index + ':11']: 'text-warning dark:text-error-400 body-2-medium' }
                } else {
                    return { [index + ':12']: 'text-warning dark:text-error-400 body-2-medium' }
                }
            } else {
                return { [index + ':11']: 'text-text' }
            }
        }).reduce((row1, row2) => ({ ...row1, ...row2 }), {});
    }

    cancelPrescription() {
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
                            this.dialogService.showErrorDialog(referenceNoText + prescriptionResponseModel.ePrescriptionReferenceNumber + " " + prescriptionResponseModel.statusDescription, () => {
                                this.fetchData();
                            })
                        }
                        else if (prescriptionResponseModel.status == "Ineligible" || prescriptionResponseModel.status == "INELIGIBLE") {
                            this.dialogService.showErrorDialog(referenceNoText + prescriptionResponseModel.ePrescriptionReferenceNumber + " " + prescriptionResponseModel.statusDescription, () => {
                                this.fetchData();
                            });
                        }
                        else if (prescriptionResponseModel.status == "Failed" || prescriptionResponseModel.status == "FAILED") {
                            let referenceNoText = "";
                            this.translate.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoText = text);
                            this.dialogService.showErrorDialog(`${referenceNoText} ${prescriptionResponseModel.ePrescriptionReferenceNumber} ${prescriptionResponseModel.statusDescription}`, () => {
                                this.fetchData();
                            })
                        }

                        else {
                            this.translate.get('prescription.cancelSuccess').subscribe(text => successMsg = text);
                            this.translate.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoMsg = text);
                            this.setValueInCancelPrescriptionResponseModel(data);
                            this.dialogService.showSuccessDialog(referenceNoMsg + ": " + this.cancelPrescriptionResponseModel.ePrescriptionReferenceNumber + " " + successMsg, () => { })
                            this.fetchData();
                            this.listIsLoading = false;
                        }

                    },
                    error: (exception) => {
                        if (exception instanceof HttpException) {

                            this.dialogService.showErrorDialog(exception.response.error.statusDescription, () => { })
                        }
                        this.listIsLoading = false;
                    }
                });
            } else {
                return;
            }
        })
    }
    setValueInCancelPrescriptionRequestModel(ePrescriptionReferenceNumber: any, payerId: any) {
        this.cancelPrescriptionRequestModel.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
        this.cancelPrescriptionRequestModel.payerId = payerId;
    }

    setValueInCancelPrescriptionResponseModel(responseData: any) {
        let responseModel = <CancelPrescriptionResponseModel>responseData;
        this.cancelPrescriptionResponseModel.canCancel = responseModel.canCancel;
        this.cancelPrescriptionResponseModel.canFollowUp = responseModel.canFollowUp;
        this.cancelPrescriptionResponseModel.ePrescriptionReferenceNumber = responseModel.ePrescriptionReferenceNumber;
        this.cancelPrescriptionResponseModel.httpStatusCode = responseModel.httpStatusCode;
        this.cancelPrescriptionResponseModel.status = responseModel.status;
        this.cancelPrescriptionResponseModel.statusDescription = responseModel.statusDescription;
    }

    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 5;
    }

    openEditPrescriptionPage() {
        this.router.navigate(['prescription', this.ePrescriptionReferenceNumber, 'followUp']);
    }

    isEditBtnVisible() {
        let status = this.payerMemberPhysicianInfoData.status;
        return (status === 'APPROVED' ||
            status === 'PARTIAL_APPROVED' ||
            status === 'REJECTED')
    }


    isCancelBtnVisible() {
        let status = this.payerMemberPhysicianInfoData.status;
        return (status === 'APPROVED' ||
            status === 'PARTIAL_APPROVED')
    }

    onBackClick = () => {
        this.router.navigateByUrl(this.returnUrl!);
    }



    modifyDecisionPage(ePrescriptionReferenceNumber: string) {
        this.authService.showSystemLoader();
        this.router.navigate(["/prescription/modifyDecision", ePrescriptionReferenceNumber])

    }
    isDisabled(item: any) {

        if (item.isCustomizable) {
            return false;
        } else {
            return true;
        }
    }

    denialCodeToModuleName(item: any) {

        let modulename;
        switch (item.denialCode) {
            case 'FDB_CPAGE902': modulename = "DrugToAgeInteraction"
                break;

            case 'FDB_CPGNDR403': modulename = "DrugToGenderInteraction"
                break;

            case 'PC_CPGNDR403': modulename = "DrugToGenderInteraction"
                break;

            case 'IDF_CPGNDR403': modulename = "DrugToGenderInteraction"
                break;

            case 'FDB_CPINDC001': modulename = "DrugToDiseaseInteraction"
                this.rejectionCategery = 'Diagnosis-ContraIndication'
                break;

            case 'IDF_CPINDC001':
            case 'PC_CPINDC001':
                modulename = "DrugToDiseaseInteraction";
                this.rejectionCategery = 'Diagnosis-ContraIndication';
                break;


            case 'FDB_CPINDI001':
            case 'IDF_CPINDI001':
            case 'PC_CPINDI001':
                modulename = "DrugToDiseaseInteraction";
                this.rejectionCategery = 'Diagnosis-Indication';
                break;

            case 'CPQTL912': modulename = "QuantityLimitCheck"
                break

            case 'PC_CPTDE0001': modulename = "DuplicateTherapy"
                break

            case 'FDB_CPTDE0001': modulename = "DuplicateTherapy"
                break

            case 'IDF_CPTDE0001': modulename = "DuplicateTherapy"
                break

            case 'FDB_CPDDI701': modulename = "DrugToDrugInteraction"
                break

            case 'IDF_CPDDI701': modulename = "DrugToDrugInteraction"
                break

            case 'PC_CPDDI701': modulename = "DrugToDrugInteraction"
                break
        }
        return modulename;
    }



    onAddCustomizationBtnClick(item: any) {

        let customizationSuccessMsg = "";
        let customizationTitle = item.drugName + item.drugCode;
        let customizationSubtitle = "";
        this.translate.get('prescription.customizationTitle', { value: item.drugName + "<br>" + item.drugCode }).subscribe((text: string) => customizationTitle = text);
        this.translate.get('prescription.customizationSubtitle',).subscribe((text: string) => customizationSubtitle = text);
        this.dialogService.showConfirmDialog(customizationTitle, customizationSubtitle, (data: any) => {
            if (data) {

                let data = {
                    ePrescriptionReferenceNo: this.ePrescriptionReferenceNumber,
                    drugCode: item.drugCode,
                    drugName: item.drugName,
                    moduleName: this.denialCodeToModuleName(item),
                    rejectionCategory: this.rejectionCategery,
                    rejectionReason: item.rejectionReason,
                    gender: this.payerMemberPhysicianInfoData.memberInfoModel?.gender,




                }
                this.customizationS.addNewCustmizationData(data).subscribe(res => {
                    this.translate.get('prescription.customizationSuccessMsg', { value: item.drugName + "<br>" + item.drugCode }).subscribe((text: string) => customizationSuccessMsg = text);
                    this.dialogService.showSuccessDialog(customizationSuccessMsg, (data: any) => {
                        if (data) {
                            item.isCustomizable = false;
                            this.medicalValidations.forEach(element => {
                                if (element.drugCode == item.drugCode && element.drugName == item.drugName && element.rejectionReason == item.rejectionReason) {
                                    element.isCustomizable = item.isCustomizable;
                                }
                            });
                        }
                    })
                }, (error) => {
                    this.dialogService.showErrorDialog(error.response.error.errorMessage[0].message, (data: any) => { })
                })

            }

        })
    }

    openDispensePage() {
        this.authService.showSystemLoader();

        if (this.prescribeGenericOrBrandFlow) {

            this.router.navigate(['prescription', this.ePrescriptionReferenceNumber, 'dispense']);
        }
    }


    ngOnDestroy(): void {
        this.previousStatus$.next("");
        this.subscriptions.forEach(subscription => subscription.unsubscribe());
        this.prescriptionService.prescriptionDetailsDrugs$.next(new ListViewModel());
        this.prescriptionService.prescriptionDetailsDiagnosis$.next(new ListViewModel());
        this.prescriptionService.prescriptionDetailsPayerMemberPhysicianInfo$.next(new PrescriptionDetailsPayerMemberPhysicianInfoModel());
        this.prescriptionService.prescriptionMedicalValidations$.next([]);
        this.prescriptionService.prescriptionBusinessValidations$.next([]);
    }
    openProviderOverrideCommentDialog(drug: any) {

        this.selectedDrug = drug;
        let data = this.medicalValidations.find((res: any) => res.scientificCode == drug.scientificCode);
        this.selectedMedicalValidations = data;

        this.selectedDrugCode = data;
        this.providerOverrideCommentDialogDialogOpen = true;
    }
    closeProviderOverrideCommentDialog = (data: { drugCode?: string, comment: string, overridingReason: string, button: string }) => {
        this.providerOverrideCommentDialogDialogOpen = false;
        if (data && data.button == "save") {
            this.drugData.content.filter(drug => drug.drugCode == data.drugCode)[0].overridingReason = data.comment;
            this.drugStatusDescriptionArray.filter(drug => drug.drugCode == data.drugCode)[0].overridingReason = data.comment;
            this.drugArray.filter(drug => drug.drugCode == data.drugCode)[0].overridingReason = data.comment;
        }
    }
    fetchAllDrugs(totalDrugCount?: number) {
        this.prescriptionService.getPrescriptionDetailsAllDrugs(this.ePrescriptionReferenceNumber, 0, totalDrugCount).subscribe((data: any) => {
            if (data.content.length > 0) {
                data.content.forEach((drug: PrescriptionDetailsDrugs) => {
                    let drugIndex = this.drugArray.findIndex(x => x.drugCode == drug.drugCode?.toString());
                    if (drugIndex == -1) {
                        this.drugArray.push({
                            drugCode: drug.drugCode!.toString(),
                            status: drug.status!,
                            decisionDescription: drug.decisionDescription!,
                            drugName: drug.drugName!,
                            duration: drug.duration!,
                            frequency: drug.frequency!,
                            frequencyOthersDescription: drug.frequencyOthersDescription!,
                            net: Number(drug.net),
                            orderingClinician: "",
                            patientShare: Number(drug.patientShare),
                            quantity: Number(drug.quantity),
                            requestId: "",
                            serviceEndDate: "",
                            serviceStartDate: drug.serviceStartDate!,
                            totalOfNetAndPatientShare: Number(drug.totalOfNetAndPatientShare),
                            unitPrice: Number(drug.unitPrice),
                            unitType: drug.unitType!,
                            useUnitType: drug.useUnitType!,
                            useUnitValue: drug.useUnitValue!,
                            scientificCode: drug.scientificCode?.toString(),
                            scientificName: drug.scientificName,
                            overridingReason: drug.overridingReason
                        });
                    }
                });
            }
        });

    }
}