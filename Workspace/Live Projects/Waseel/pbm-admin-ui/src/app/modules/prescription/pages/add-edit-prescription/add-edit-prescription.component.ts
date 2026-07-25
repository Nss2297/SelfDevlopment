import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { BehaviorSubject } from 'rxjs';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { HttpException } from 'src/app/util/default-http-client';
import { AddPrescriptionStoreService } from '../../service/add-prescription-store/add-prescription-store.service';
import { PrescriptionService } from '../../service/prescription.service';
import { PrescriptionResponseModel } from '../../models/prescription-response.model';
import { environment } from 'src/environments/environment';

@Component({
    selector: 'app-add-edit-prescription',
    templateUrl: './add-edit-prescription.component.html',
    styles: [
    ]
})
export class AddEditPrescriptionComponent implements OnInit, OnDestroy {

    ePrescriptionReferenceNumber?: string;

    isLoading$: BehaviorSubject<boolean> = new BehaviorSubject(false);


    showCommentButton: boolean = false;
    constructor(
        public storeService: AddPrescriptionStoreService,
        private translateService: TranslateService,
        private prescriptionService: PrescriptionService,
        private dialogService: DialogService,
        private router: ActivatedRoute,
        private route: Router,
    ) { }



    ngOnInit(): void {
        this.router.params.subscribe(params => {
            this.ePrescriptionReferenceNumber = params['ePrescriptionReferenceNumber'];
            if (this.ePrescriptionReferenceNumber) {
                this.storeService.changeMode('followUp');
                this.fetchPrescriptionDataForFollowUp(this.ePrescriptionReferenceNumber);
            }
        });
    }

    ngOnDestroy(): void {
        this.storeService.reset();
    }

    fetchPrescriptionDataForFollowUp(ePrescriptionReferenceNumber: string) {
        this.isLoading$.next(true);
        this.prescriptionService.getPayerMemberPhysicianInfo(ePrescriptionReferenceNumber).subscribe({
            next: (data) => {
                this.storeService.updatePrescriptionData({
                    dateOfBirth: data.memberInfoModel?.dob,
                    IdNumber: data.memberInfoModel?.idNumber,
                    memberGender: data.memberInfoModel?.gender,
                    memberName: data.memberInfoModel?.memberName,
                    payerId: data.payerId,
                    physicianLicenseNumber: data.physicianModel?.physicianLicenseNumber,
                    physicianName: data.physicianModel?.physicianName,
                    physicianCategory: data.physicianModel?.physicianCategory,
                    physicianSpeciality: data.physicianModel?.physicianSpeciality,
                    caseType: data.caseType,
                    memberNationality: data.memberInfoModel?.nationality
                });
                this.storeService.selectedMemberInfo = {
                    idNumber: data.memberInfoModel?.idNumber || '',
                    memberName: data.memberInfoModel?.memberName || '',
                    gender: data.memberInfoModel?.gender || '',
                    dob: data.memberInfoModel?.dob || '',
                    age: data.memberInfoModel?.age || '',
                    nationality: data.memberInfoModel?.nationality || ''
                };
                this.storeService.selectedPhysicianInfo = {
                    name: data.physicianModel?.physicianName || '',
                    registrationNumber: data.physicianModel?.physicianLicenseNumber || '',
                    category: data.physicianModel?.physicianCategory || '',
                    physicianSpeciality: data.physicianModel?.physicianSpeciality || ''
                };
                this.storeService.changeStepStatus(1, true);
                this.storeService.changeStepStatus(2, true);
                this.storeService.changeStepStatus(3, true);
                this.isLoading$.next(false);
                this.fetchDiagnosisDataForFollowUp(ePrescriptionReferenceNumber);
                this.fetchDrugDataForFollowUp(ePrescriptionReferenceNumber);
            },
            error: (error) => {
                this.isLoading$.next(false);
            }
        });
    }

    fetchDiagnosisDataForFollowUp(ePrescriptionReferenceNumber: string) {
        this.prescriptionService.getPrescriptionDetailsDiagnosis(ePrescriptionReferenceNumber, 0, 5).subscribe({
            next: (diagnosisData) => {
                console
                this.storeService.updatePrescriptionData({
                    ...this.storeService.state.prescriptionData,
                    diagnosisCodes: diagnosisData.content.map(diagnosis => ({ diagnosisCode: diagnosis.diagnosisCode, diagnosisDescription: diagnosis.diagnosisCodeDescription, diagnosisType: diagnosis.diagnosisType }))
                });
            },
            error: (error) => {

            }
        });
    }

    fetchDrugDataForFollowUp(ePrescriptionReferenceNumber: string) {

        this.prescriptionService.getPrescriptionDrugsForFollowUp(ePrescriptionReferenceNumber).subscribe({
            next: (drugData) => {
                this.storeService.updatePrescriptionData({
                    ...this.storeService.state.prescriptionData,
                    drugList: drugData

                });

            },
            error: (error) => {

            }
        });
    }


    nextStep() {
        if (this.storeService.state.activeStep == 1) {
            if (this.storeService.state.step1IsValid) {
                this.storeService.setStep(2);
            } else {
                if (this.storeService.selectedMemberInfo == null) {
                    const message = this.translateService.instant('prescription.memberError');
                    this.dialogService.showWarningDialog(message, (data: any) => { });
                } else if (this.storeService.selectedPhysicianInfo == null) {
                    const message = this.translateService.instant('prescription.physicianError');
                    this.dialogService.showWarningDialog(message, (data: any) => { });
                }
                else if (this.storeService.selectedCaseType == null) {
                    const message = this.translateService.instant('prescription.caseTypeerror');
                    this.dialogService.showWarningDialog(message, (data: any) => { });
                }
            }
        } else if (this.storeService.state.activeStep == 2) {
            if (this.storeService.state.step2IsValid) {
                this.storeService.setStep(3);
            } else {
                const message = this.translateService.instant('prescription.onePrimaryDiagnosisError');
                this.dialogService.showWarningDialog(message, (data: any) => { });
            }
        } else if (this.storeService.state.activeStep == 3) {
            if (this.storeService.state.step3IsValid) {
                this.storeService.setStep(4);
            } else {
                const message = this.translateService.instant('prescription.oneDrugError');
                this.dialogService.showWarningDialog(message, (data: any) => { });
            }
        }
    }

    previousStep() {
        if (this.storeService.state.activeStep == 4)
            this.storeService.setStep(3);
        else if (this.storeService.state.activeStep == 3)
            this.storeService.setStep(2);
        else if (this.storeService.state.activeStep == 2)
            this.storeService.setStep(1);
    }

    // saveData() {
    //   this.isLoading$.next(true);
    //   let response = this.storeService.state.mode == 'new' ? this.prescriptionService.addNewPrescriptionData(this.storeService.state.prescriptionData)
    //     : this.prescriptionService.addNewPrescriptionData({ ...this.storeService.state.prescriptionData, ePrescriptionReferenceNumber: this.ePrescriptionReferenceNumber });
    //   response.subscribe({
    //     next: (data) => {
    //       const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
    //       this.isLoading$.next(false);
    //       let successMsg = '';
    //       let referenceNoMsg = '';
    //       this.translateService.get('ADD-SUCCESS').subscribe(text => successMsg = text);
    //       this.translateService.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoMsg = text);
    //       this.dialogService.showSuccessDialog(referenceNoMsg + ": " + prescriptionResponseModel.ePrescriptionReferenceNumber + " " + successMsg,
    //         (event: any) => { if (event) { this.route.navigate(["prescription/list"]) } });

    //     },
    //     error: (exception) => {

    //       if (exception instanceof HttpException) {
    //         console.log();
    //         const errorCode = exception.response?.error?.statusDescription || exception.messageCode;
    //         this.dialogService.showErrorDialog(errorCode, (event: any) => {  })
    //       }
    //       this.isLoading$.next(false);
    //     }
    //   });


    // }
    saveData() {
        this.isLoading$.next(true);
        let response = this.storeService.state.mode == 'new' ? this.prescriptionService.addNewPrescriptionData(this.storeService.state.prescriptionData)
            : this.prescriptionService.addNewPrescriptionData({ ...this.storeService.state.prescriptionData, ePrescriptionReferenceNumber: this.ePrescriptionReferenceNumber });
        response.subscribe({
            next: (data) => {
                const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                this.isLoading$.next(false);
                let successMsg = '';
                let referenceNoMsg = '';
                let referenceNoText = "";
                this.translateService.get('REFERENCENO').subscribe(text => referenceNoText = text);

                if (prescriptionResponseModel.status == "Invalid" || prescriptionResponseModel.status == "INVALID") {
                    console.log("invalid")
                    this.dialogService.showErrorDialog(referenceNoText + prescriptionResponseModel.ePrescriptionReferenceNumber + "<br> " + prescriptionResponseModel.statusDescription, (event: any) => {
                        console.log("2 invalid")
                        if (this.storeService.state.mode == 'new') {
                            if (event) { this.route.navigate(["prescription/list"]) }
                        }
                        else {
                            if (event) { }
                        }
                    })

                }
                else if (prescriptionResponseModel.status == "Ineligible" || prescriptionResponseModel.status == "INELIGIBLE") {
                    this.dialogService.showErrorDialog(referenceNoText + prescriptionResponseModel.ePrescriptionReferenceNumber + "<br> " + prescriptionResponseModel.statusDescription, (event: any) => {
                        if (event) { this.route.navigate(["prescription/list"]) }
                    });
                }
                else if (prescriptionResponseModel.status == "Failed" || prescriptionResponseModel.status == "FAILED") {
                    // this.dialogService.showErrorDialog(`${referenceNoText} ${prescriptionResponseModel.ePrescriptionReferenceNumber} + "<br>"+${prescriptionResponseModel.statusDescription}`  , (event: any) => {  
                    this.dialogService.showErrorDialog(referenceNoText + prescriptionResponseModel.ePrescriptionReferenceNumber + "<br> " + prescriptionResponseModel.statusDescription, (event: any) => {
                        if (this.storeService.state.mode == 'new') {
                            if (event) { this.route.navigate(["prescription/list"]) }
                        }
                    })
                }

                else {
                    this.translateService.get('SUBMIT-SUCCESS', { value: prescriptionResponseModel.status }).subscribe(text => successMsg = text);
                    this.translateService.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoMsg = text);
                    this.dialogService.showSuccessDialog(referenceNoMsg + ": " + prescriptionResponseModel.ePrescriptionReferenceNumber + "<br> " + successMsg,
                        (event: any) => { if (event) { this.route.navigate(["prescription/list"]) } });
                }



            },

            error: (exception) => {

                if (exception instanceof HttpException) {
                    console.log();
                    let referenceNoMsg = '';
                    // this.translateService.get('REFERENCENO').subscribe(text => referenceNoText = text);
                    // const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
                    const errorCode = exception.response?.error?.statusDescription || exception.messageCode;
                    this.dialogService.showErrorDialog(errorCode, (event: any) => { })
                    // this.dialogService.showErrorDialog("Reference No" + " : " + " 2023-4865 " + "<br>" + "Eligibility submission Failed"  , (event: any) => {  
                    //   if(this.storeService.state.mode == 'new'){
                    //     if (event) { }
                    //   }           
                    // })
                }
                this.isLoading$.next(false);
            }
        });


    }

}