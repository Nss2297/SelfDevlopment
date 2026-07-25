import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { PrescriptionDetailsDiagnosis } from '../../models/prescription-details-diagnosis.model';
import { PrescriptionDetailsDrugs } from '../../models/prescription-details-drugs.model';
import { PrescriptionDetailsPayerMemberPhysicianInfoModel } from '../../models/prescription-details-payer-member-physician-info.model';
import { PrescriptionRequest } from '../../models/prescription-request.model';
import { PrescriptionService } from '../../service/prescription.service';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { Subscription } from 'rxjs';
import { PrescriptionProviderService } from '../../service/prescription-provider.service';
import { DrugModifyDecisionModel } from '../../models/drug-modify-decision.model';
import { User } from 'src/app/modules/authentication/models/user.model';
import { HttpException } from 'src/app/util/default-http-client';

@Component({
    selector: 'app-modify-decision',
    templateUrl: './modify-decision.component.html',
    styleUrls: ['./modify-decision.component.css']
})
export class ModifyDecisionComponent implements OnInit {

    subscriptions: Subscription[] = [];

    addReasonForChangeDecisionDialogOpen = false;
    showCommentButton: boolean = true;

    statusType = [
        {
            key: 'APPROVED',
            value: 'APPROVED',
            selected: true
        },
        {
            key: 'REJECTED',
            value: 'REJECTED',
        },
        {
            key: 'PENDING',
            value: 'PENDING'
        }
    ]

    drugData: ListViewModel<PrescriptionDetailsDrugs> = new ListViewModel();
    updatedDrugData: ListViewModel<PrescriptionDetailsDrugs> = new ListViewModel();
    drugListPrototype = PrescriptionDetailsDrugs.prototype;
    drugsDataControl: PrescriptionRequest = new PrescriptionRequest();
    diagnosisDataControl: PrescriptionRequest = new PrescriptionRequest();
    diagnosisData: ListViewModel<PrescriptionDetailsDiagnosis> = new ListViewModel();
    diagnosisListPrototype = PrescriptionDetailsDiagnosis.prototype;
    payerMemberPhysicianInfoData: PrescriptionDetailsPayerMemberPhysicianInfoModel = new PrescriptionDetailsPayerMemberPhysicianInfoModel();
    ePrescriptionReferenceNumber!: string;
    currentUser!: User;
    listIsLoading: boolean = false;
    selectedDrugCode?: string;
    selectedDecisionDescription?: string;
    modifyDecissionValidationMessage = "";
    errorMsgForModifyDescrip = "";
    successMsgForModifyDescrip = "";
    drugStatusDescriptionArray: { drugCode: string, status: string, decisionDescription: string, showCommentIcon: boolean }[] = [];
    drugArray: DrugModifyDecisionModel[] = [];
    pendingDrugCodes: any[] = [];


    constructor(
        private prescriptionService: PrescriptionService,
        private activeRouter: ActivatedRoute, private router: Router,
        private translate: TranslateService,
        private dialogService: DialogService,
        private authService: AuthService,
        public providerService: PrescriptionProviderService
    ) {
        this.authService.user$.subscribe(data => {
            this.currentUser = data;
        })
    }


    ngOnInit(): void {

        this.drugsDataControl.recordSize = 5;
        this.diagnosisDataControl.recordSize = 5;

        this.subscriptions.push(this.activeRouter.params.subscribe(params => {
            this.ePrescriptionReferenceNumber = params['ePrescriptionReferenceNumber'];
        }));

        this.subscriptions.push(this.prescriptionService.prescriptionDetailsDrugs$.subscribe(data => {
            this.drugData = data;
            if (this.drugData.content.length > 0) {
                this.drugData.content.map(res => {
                    if (!res.drugCode) {
                        res.drugCode = res.scientificCode;
                        res.drugName = res.scientificName;
                    }
                    return res;
                })
                this.fetchAllDrugs(this.drugData.totalElements);

                this.drugData.content.forEach((drug: PrescriptionDetailsDrugs) => {
                    if(drug.status == "PENDING"){
                        if(this.pendingDrugCodes.findIndex(x=>x.drugCode == drug.drugCode && x.scientificCode == drug.scientificCode?.toString()) == -1){
                            this.pendingDrugCodes.push({drugCode:drug.drugCode, scientificCode: drug.scientificCode?.toString()})
                        }
                    }
                    drug.decisionDescription = "";
                    drug.showCommentIcon = false;
                    this.drugStatusDescriptionArray.push({
                        drugCode: drug.drugCode!.toString(),
                        status: drug.status!,
                        decisionDescription: drug.decisionDescription,
                        showCommentIcon: drug.showCommentIcon
                    })
                })
            }
        }));

        this.subscriptions.push(this.prescriptionService.prescriptionDetailsDiagnosis$.subscribe(data => {
            this.diagnosisData = data;
        }));

        this.subscriptions.push(this.prescriptionService.prescriptionDetailsPayerMemberPhysicianInfo$.subscribe(data => {
            this.payerMemberPhysicianInfoData = data;

        }));

        this.listIsLoading = false;
        this.fetchData();


        this.modifyDecissionValidationMessage = "";
        this.translate.get('prescription.modifyDecisionErrorMessage').subscribe((text: string) => this.modifyDecissionValidationMessage = text);

        this.errorMsgForModifyDescrip = "";
        this.translate.get('prescription.errorMsgForModifyDescrip').subscribe((text: string) => this.errorMsgForModifyDescrip = text);


    }

    fetchData(tableName?: string) {
        this.listIsLoading = false;
        switch (tableName) {
            case 'DRUGS':
                this.prescriptionService.getPrescriptionDetailsDrugs(this.ePrescriptionReferenceNumber, this.drugsDataControl.pageNumber, this.drugsDataControl.recordSize).subscribe(() => {
                    this.drugData.content.map((drug) => {
                        let updatedDrug = this.drugStatusDescriptionArray.filter(x => x.drugCode == drug.drugCode?.toString())[0];
                        drug.status = updatedDrug.status;
                        drug.decisionDescription = updatedDrug.decisionDescription;
                        drug.showCommentIcon = updatedDrug.showCommentIcon;
                    });

                    this.drugArray.map((drug) => {
                        let updatedDrug = this.drugStatusDescriptionArray.filter(x => x.drugCode == drug.drugCode?.toString())[0];
                        drug.status = updatedDrug.status;
                        drug.decisionDescription = updatedDrug.decisionDescription;
                    })
                });
                break;

            case 'DIAGNOSIS':
                this.prescriptionService.getPrescriptionDetailsDiagnosis(this.ePrescriptionReferenceNumber, this.diagnosisDataControl.pageNumber, this.diagnosisDataControl.recordSize).subscribe();
                break;

            default:
                this.prescriptionService.getPrescriptionDetailsDrugs(this.ePrescriptionReferenceNumber, this.drugsDataControl.pageNumber, this.drugsDataControl.recordSize).subscribe();
                this.prescriptionService.getPrescriptionDetailsDiagnosis(this.ePrescriptionReferenceNumber, this.diagnosisDataControl.pageNumber, this.diagnosisDataControl.recordSize).subscribe();
                break;
        }
        this.prescriptionService.getPayerMemberPhysicianInfo(this.ePrescriptionReferenceNumber).subscribe((x: PrescriptionDetailsPayerMemberPhysicianInfoModel) => {
            this.authService.hideSystemLoader();
        });
    }

    fetchAllDrugs(totalDrugCount?: number) {
        this.prescriptionService.getPrescriptionDetailsAllDrugs(this.ePrescriptionReferenceNumber, 0, totalDrugCount).subscribe((data: any) => {
            if (data.content.length > 0) {
                data.content.forEach((drug: PrescriptionDetailsDrugs) => {
                    let drugIndex = this.drugArray.findIndex(x => x.drugCode == drug.drugCode?.toString());
                    if (drugIndex == -1) {
                        this.drugArray.push({
                            drugCode: drug.drugCode?.toString(),
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
                            drugListId:drug.drugListId
                        
                        });
                    }
                });
            }
        });

    }

    showItemPerPageDropdown(totalRecords: number): boolean {
        return totalRecords >= 5;
    }

    getSelectedStatusValue(event: any, drugCode: string, showCommentIcon: boolean) {
        let drugDataDrug = this.drugData.content.filter(drug => drug.drugCode?.toString() == drugCode)[0];
        let drugArrayDrug = this.drugArray.filter(drug => drug.drugCode == drugCode)[0];
        let drugStatusDescriptionArrayDrug = this.drugStatusDescriptionArray.filter(drug => drug.drugCode == drugCode)[0];
        if (!showCommentIcon) {
            this.openAddReasonForChangeDecisionDialog(drugCode);
        } else {
            drugDataDrug.decisionDescription = "";
            drugStatusDescriptionArrayDrug.decisionDescription = "";
            drugArrayDrug.decisionDescription = "";
        }
        if(this.pendingDrugCodes.findIndex(x => x.drugCode == drugDataDrug.drugCode && x.scientificCode == drugDataDrug.scientificCode) == -1){
            drugDataDrug.showCommentIcon = showCommentIcon ? false : true;
            drugStatusDescriptionArrayDrug.showCommentIcon = showCommentIcon ? false : true;
        } else {
            drugDataDrug.showCommentIcon = true;
            drugStatusDescriptionArrayDrug.showCommentIcon = true;
            if (showCommentIcon) {
                this.openAddReasonForChangeDecisionDialog(drugCode);
            }
        }

        drugDataDrug.status = event.target.value;
        drugStatusDescriptionArrayDrug.status = event.target.value;
        drugArrayDrug.status = event.target.value;
    }

    cancelModifyDecision() {
        //this.isDialogOpened$.next(false);
        this.authService.showSystemLoader();
        this.router.navigate(["prescription/details", this.ePrescriptionReferenceNumber]);
    }

    openAddReasonForChangeDecisionDialog(drugcode: string) {
        this.selectedDrugCode = drugcode;
        this.selectedDecisionDescription = this.drugData.content.filter(drug => drug.drugCode?.toString() == drugcode)[0].decisionDescription;
        this.addReasonForChangeDecisionDialogOpen = true;
        console.log("decision",this.selectedDecisionDescription)
    }

    closeAddReasonForChangeDecisionDialog = (data: { drugCode?: string, comment: string, decisionDescription: string, button: string }) => {
        this.addReasonForChangeDecisionDialogOpen = false;
        if (data && data.button == "save") {
            this.drugData.content.filter(drug => drug.drugCode == data.drugCode)[0].decisionDescription = data.comment;
            this.drugStatusDescriptionArray.filter(drug => drug.drugCode == data.drugCode)[0].decisionDescription = data.comment;
            this.drugArray.filter(drug => drug.drugCode == data.drugCode)[0].decisionDescription = data.comment;
        }
    }

    saveChanges() {
        if (this.drugStatusDescriptionArray.findIndex(x => x.showCommentIcon == true && x.decisionDescription == "") == -1) {
            this.authService.showSystemLoader();
            this.drugArray.forEach((element, index) => {
                if (element.status == "DISPENSED") {
                    this.drugArray.splice(index, 1);
                }
            });
            let requestData = {
                drugList: this.drugArray
            }

            this.prescriptionService.saveModifiedDecision(this.currentUser.accId!, this.ePrescriptionReferenceNumber, requestData).subscribe({
                next: (data: any) => {
                    this.successMsgForModifyDescrip = "";
                    this.translate.get('prescription.successMsgForModifyDescription', { referencenumber: this.ePrescriptionReferenceNumber, status: data.status }).subscribe((text: any) => this.successMsgForModifyDescrip = text);
                    this.authService.hideSystemLoader();
                    this.dialogService.showSuccessDialog(this.successMsgForModifyDescrip, (event: any) => {
                        if (event) {
                            this.authService.showSystemLoader();
                            this.router.navigate(["prescription/details", data.ePrescriptionReferenceNumber]);
                        }
                    })
                },
                error: (exception) => {
                    this.authService.hideSystemLoader();
                    if (exception instanceof HttpException) {
                        this.dialogService.showErrorDialog(exception.response.error.errorDescription, () => { })
                    }
                }
            });
        } else {
            this.dialogService.showWarningDialog(this.modifyDecissionValidationMessage, () => { });
        }

    }



    ngOnDestroy(): void {
        this.subscriptions.forEach(subscription => subscription.unsubscribe());
        this.prescriptionService.prescriptionDetailsDrugs$.next(new ListViewModel());
        this.prescriptionService.prescriptionDetailsDiagnosis$.next(new ListViewModel());
        this.prescriptionService.prescriptionDetailsPayerMemberPhysicianInfo$.next(new PrescriptionDetailsPayerMemberPhysicianInfoModel());
    }
}