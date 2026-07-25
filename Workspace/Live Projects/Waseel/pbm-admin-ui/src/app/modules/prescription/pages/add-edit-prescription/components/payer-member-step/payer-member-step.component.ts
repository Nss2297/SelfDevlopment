import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AddPrescriptionStoreService } from 'src/app/modules/prescription/service/add-prescription-store/add-prescription-store.service';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';

@Component({
    selector: 'add-prescription-payer-member-step',
    templateUrl: './payer-member-step.component.html',
    styleUrls: []
})
export class PayerMemberStepComponent implements OnInit {
    // caseType= [{key:'inpatient', value :'INPATIENT'},
    // {key:'outpatient',value:'OUTPATIENT'}
    // ]


    payerMemberFormGroup: FormGroup = new FormGroup({
        payerId: new FormControl('', [Validators.required]),
        member: new FormControl('', [Validators.required]),
        clinician: new FormControl('', [Validators.required]),
        caseType: new FormControl('', [Validators.required])

    });

    getMembersSubscription?: Subscription;
    getPhysiciansSubscription?: Subscription;

    get memberField(): FormControl {
        return this.payerMemberFormGroup.get('member') as FormControl;
    }



    constructor(public storeService: AddPrescriptionStoreService, public prescriptionLOVs: PrescriptionlovService, private dialogService: DialogService, private datePipe: DatePipe) {

    }

    ngOnInit(): void {
        if (this.storeService.state.step1IsValid || this.storeService.state.mode == 'followUp') {
            this.payerMemberFormGroup.setValue({
                payerId: this.storeService.state.prescriptionData.payerId,
                member: this.storeService.selectedMemberInfo?.idNumber,
                clinician: `${this.storeService.selectedPhysicianInfo?.registrationNumber} | ${this.storeService.selectedPhysicianInfo?.name} | ${this.storeService.selectedPhysicianInfo?.category}| ${this.storeService.selectedPhysicianInfo?.physicianSpeciality}`,
                //  caseType:this.payerMemberFormGroup.get('caseType')?.value.toUpperCase()
                caseType: this.storeService.state.prescriptionData.caseType?.toUpperCase()


            });
        }
        this.payerMemberFormGroup.statusChanges.subscribe(status => {
            if (status == 'VALID' && this.storeService.selectedMemberInfo != null && this.storeService.selectedPhysicianInfo != null && this.storeService.selectedCaseType != null) {
                this.storeService.updatePrescriptionData({
                    IdNumber: this.storeService.selectedMemberInfo?.idNumber,
                    dateOfBirth: this.storeService.selectedMemberInfo?.dob,
                    memberGender: this.storeService.selectedMemberInfo?.gender,
                    memberName: this.storeService.selectedMemberInfo?.memberName,
                    payerId: this.payerMemberFormGroup.get('payerId')?.value,
                    physicianLicenseNumber: this.storeService.selectedPhysicianInfo?.registrationNumber,
                    physicianName: this.storeService.selectedPhysicianInfo?.name,
                    physicianCategory: this.storeService.selectedPhysicianInfo?.category,
                    physicianSpeciality: this.storeService.selectedPhysicianInfo?.physicianSpeciality,
                    caseType: this.payerMemberFormGroup.get('caseType')?.value,
                    memberNationality: this.storeService.selectedMemberInfo.nationality


                });
                this.storeService.changeStepStatus(1, true);
            } else {
                this.storeService.changeStepStatus(1, false);
            }
        });
    }

    checkNumberType(value: any) {
        if (!Number(value)) {
            this.memberField.reset();
            this.memberField.markAsDirty();
            return;
        }
    }

    getMemberInfo(event: any) {
        if (this.storeService.state.mode == 'followUp') {
            return;
        }
        let errorMessage = "";
        if (this.payerMemberFormGroup.get('member')?.valid) {
            if (this.getMembersSubscription != null) {
                this.getMembersSubscription.unsubscribe();
                this.prescriptionLOVs.memberInfo$.next({} as { memberName: string, age: string, gender: string, dob: string, idNumber: string, nationality: string });
            }
            this.getMembersSubscription = this.prescriptionLOVs.getMemberInfo(this.payerMemberFormGroup.get('member')?.value)
                .subscribe({
                    next: (value) => {
                        let member: any = {};
                        member.memberName = value.memberName;
                        member.age = value.age;
                        member.gender = value.gender;
                        member.dob = this.datePipe.transform(value.dateOfBirth.toString().includes('-') ? value.dateOfBirth.replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3") : new Date(value.dateOfBirth), 'dd/MM/YYYY');
                        member.idNumber = value.idNumber;
                        member.nationality = value.nationality;
                        this.storeService.selectedMemberInfo = member;
                        this.payerMemberFormGroup.get('member')?.setValue(value.idNumber);
                        this.getMembersSubscription = undefined
                    },
                    error: (value) => {
                        if (value.response.error.status == "FAILED" || value.response.error.status == "INVALID") {
                            if (value.response.error.errors) {
                                errorMessage = value.response.error.errors[0];
                            } else if (value.response.error.statusDescription) {
                                errorMessage = value.response.error.statusDescription;
                            }
                        }
                        this.dialogService.showErrorDialog(errorMessage, (data: any) => {
                            this.storeService.selectedMemberInfo = undefined;
                            this.payerMemberFormGroup.get('member')?.reset();
                        })
                        this.storeService.selectedMemberInfo = undefined;
                        this.getMembersSubscription = undefined;
                    },
                });
        } else {
            this.prescriptionLOVs.memberInfo$.next({} as { memberName: string, age: string, gender: string, dob: string, idNumber: string, nationality: string });
        }
    }

    cleareMemberInfo(event: any) {
        if (event == "" || event.length < 10) {
            this.prescriptionLOVs.memberInfo$.next(<{ memberName: string, age: string, gender: string, dob: string, idNumber: string, nationality: string }>{});
            this.storeService.selectedMemberInfo = undefined;
            this.storeService.state.step1IsValid = false;
        }
    }

    getPhysicians(event: any) {
        if (this.payerMemberFormGroup.get('clinician')?.valid) {
            if (this.getPhysiciansSubscription != null) {
                this.getPhysiciansSubscription.unsubscribe();
                this.prescriptionLOVs.physiciansList$.next([]);
            }
            this.getPhysiciansSubscription = this.prescriptionLOVs.getPhysicians({ physician: this.payerMemberFormGroup.get('clinician')?.value })
                .subscribe({
                    next: (value) => this.getPhysiciansSubscription = undefined,
                    error: (value) => this.getPhysiciansSubscription = undefined,
                });
        } else {
            this.prescriptionLOVs.physiciansList$.next([]);
        }
    }

    // selectMember(member: { memberName: string, age: string, gender: string, dob: string, idNumber: string }) {
    //   this.storeService.selectedMemberInfo = member;
    //   this.payerMemberFormGroup.get('member')?.setValue(`${member.idNumber} | ${member.memberName}`);
    //   this.prescriptionLOVs.memberList$.next([])
    // }

    selectPhysician(physician: { registrationNumber: string, name: string, category: string, physicianSpeciality: string }) {
        this.storeService.selectedPhysicianInfo = physician;
        this.payerMemberFormGroup.get('clinician')?.setValue(`${physician.registrationNumber} | ${physician.name} | ${physician.category} | ${physician.physicianSpeciality}`);
        this.prescriptionLOVs.physiciansList$.next([]);
    }

    selectCaseType(selectedCaseType: any) {
        this.storeService.selectedCaseType = selectedCaseType;
        this.payerMemberFormGroup.get('caseType')?.setValue(selectedCaseType);
    }

}