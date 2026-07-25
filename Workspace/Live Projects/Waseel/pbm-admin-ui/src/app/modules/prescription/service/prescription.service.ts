import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, tap } from 'rxjs';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DefaultHttpClient } from 'src/app/util/default-http-client';
import { environment } from 'src/environments/environment';
import { DispensableDrugsResponseModel } from '../../../modules/prescription/models/prescription-dispensable-drugs-response.model';
import { DispensableDrugsSummaryModel } from '../../../modules/prescription/models/prescription-dispensable-drugs-summary-response.model';
import { PrescriptionDispenseRequestModel } from '../../../modules/prescription/models/prescription-dispense-request.model';
import { PrescriptionDispenseResponseModel } from '../../../modules/prescription/models/prescription-dispense-response.model';
import { PrescriptionDetailsDiagnosis } from '../models/prescription-details-diagnosis.model';
import { PrescriptionValidationsModel } from '../models/prescription-details-drugs-rejection.model';
import { PrescriptionDetailsDrugs } from '../models/prescription-details-drugs.model';
import { PrescriptionDetailsPayerMemberPhysicianInfoModel } from '../models/prescription-details-payer-member-physician-info.model';
import { PrescriptionDetails } from '../models/prescription-details.model';
import { PrescriptionRequest } from '../models/prescription-request.model';
import { PrescriptionResponseModel } from '../models/prescription-response.model';
import { MedicalValidations } from '../models/medical-validations.model';
import { DispensedDetails } from '../models/dispensed-details.model';
import { DrugsDetails } from '../models/prescription-drug.model';
import { EditPrescription } from '../models/prescription-edit.model';
import { DispenseDetail } from '../models/dispense-detail.model';


@Injectable({
    providedIn: 'root'
})
export class PrescriptionService extends DefaultHttpClient {

    prescription$: BehaviorSubject<ListViewModel<PrescriptionDetails>> = new BehaviorSubject(new ListViewModel());
    prescriptionDetailsDrugs$: BehaviorSubject<ListViewModel<PrescriptionDetailsDrugs>> = new BehaviorSubject(new ListViewModel());
    prescriptionDetailsDiagnosis$: BehaviorSubject<ListViewModel<PrescriptionDetailsDiagnosis>> = new BehaviorSubject(new ListViewModel());
    prescriptionDetailsPayerMemberPhysicianInfo$: BehaviorSubject<PrescriptionDetailsPayerMemberPhysicianInfoModel> =
        new BehaviorSubject(new PrescriptionDetailsPayerMemberPhysicianInfoModel());
    prescriptionMedicalValidations$: BehaviorSubject<PrescriptionValidationsModel[]> = new BehaviorSubject([new PrescriptionValidationsModel]);
    prescriptionBusinessValidations$: BehaviorSubject<PrescriptionValidationsModel[]> = new BehaviorSubject([new PrescriptionValidationsModel]);
    dispensableDrugsSummary$: BehaviorSubject<DispensableDrugsSummaryModel> = new BehaviorSubject(new DispensableDrugsSummaryModel());
    prescriptionMedicalValidation$: BehaviorSubject<ListViewModel<MedicalValidations>> = new BehaviorSubject(new ListViewModel());
    dispensedDetails$: BehaviorSubject<ListViewModel<DispensedDetails>> = new BehaviorSubject(new ListViewModel());
    editPrescriptions$: BehaviorSubject<EditPrescription> = new BehaviorSubject(new EditPrescription());
    prescriptionDrugs$: BehaviorSubject<ListViewModel<DrugsDetails>> = new BehaviorSubject(new ListViewModel())
    dispenseDetail$: BehaviorSubject<DispenseDetail> = new BehaviorSubject(new DispenseDetail());

    //private dataSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private returnUrl: BehaviorSubject<string> = new BehaviorSubject<string>('');


    constructor(protected override httpClient: HttpClient, private datePipe: DatePipe) {
        super(httpClient, environment.backend.prescriptionservice.prescription);
    }

    // sendData(data:boolean) {
    //   this.dataSubject.next(data);
    // }

    // getData() {
    //   return this.dataSubject.asObservable();
    // }

    setReturnUrl(pageUrl: string) {
        this.returnUrl.next(pageUrl);
    }

    getReturnUrl() {
        return this.returnUrl.asObservable();
    }

    dispensableDrugs$: BehaviorSubject<ListViewModel<DispensableDrugsResponseModel>> = new BehaviorSubject(new ListViewModel());



    addNewPrescriptionData(requestBody: {
        payerId?: string, memberId?: string, memberName?: string, idNumber?: string, dateOfBirth?: string, policyNumber?: string, memberNationality?: string,
        memberGender?: string, memberWeight?: number, memberHeight?: number, physicianLicenseNumber?: string, physicianName?: string, physicianCategory?: string,
        diagnosisCodes?: any[], drugList?: any[], ePrescriptionReferenceNumber?: string, caseType?: string, providerName?: string, providerId?: string,
    }) {
        requestBody.diagnosisCodes = requestBody.diagnosisCodes?.map(code => ({ diagnosisCode: code.diagnosisCode, diagnosisType: code.diagnosisType }));
        requestBody.drugList = requestBody.drugList?.map(code => ({
            drugCode: code.drugCode,
            frequency: code.frequency,
            frequencyOthersDescription: code.frequencyOthersDescription,
            useUnitValue: Number.parseInt(code.useUnitValue),
            duration: code.duration,
            quantity: code.quantity,
            serviceStartDate: this.datePipe.transform(code.serviceStartDate.toString().includes('-') ? code.serviceStartDate.replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3") : new Date(code.serviceStartDate), 'dd/MM/YYYY'),
            unitPrice: Number.parseInt(code.unitPrice),
            unitType: code.unitType,
            scientificCode: code.scientificCode,
            drugListId: code.drugListId
        }));
        return this.post<PrescriptionResponseModel>('', requestBody);
    }

    getPrescriptions(request: PrescriptionRequest) {
        return this.get(`?${request.toQueryParams()}?pharmacyUser=true`, {
            subjectToUpdate: this.prescription$
        });
    }

    dispensePrescriptions(request: PrescriptionDispenseRequestModel) {
        return this.post<{ body: PrescriptionDispenseResponseModel }>('/dispense', request);
    }

    getDrugsToDispense(ePrescriptionReferenceNumber: string, request?: { pageNumber: number, recordSize?: number }) {
        return this.get(`/dispense/${ePrescriptionReferenceNumber}/drugs?pageNumber=${request?.pageNumber}&recordSize=${request?.recordSize}`, {
            subjectToUpdate: this.dispensableDrugs$
        });
    }

    getPrescriptionDetailsDrugs(ePrescriptionReferenceNumber: string, pageNumber: number, recordSize?: number) {
        return this.get(`/${ePrescriptionReferenceNumber}` + "/drugs?pageNumber=" + pageNumber + "&recordSize=" + recordSize, {
            subjectToUpdate: this.prescriptionDetailsDrugs$
        });
    }

    getPrescriptionDetailsAllDrugs(ePrescriptionReferenceNumber: string, pageNumber: number, recordSize?: number) {
        return this.get(`/${ePrescriptionReferenceNumber}` + "/drugs?pageNumber=" + pageNumber + "&recordSize=" + recordSize, {
            //subjectToUpdate: this.prescriptionDetailsDrugs$
        });
    }

    getPrescriptionDrugsForFollowUp(ePrescriptionReferenceNumber: string) {
        return this.get<
            {
                drugCode?: string;
                drugName?: string;
                unitType?: string;
                quantity?: string;
                unitPrice?: number;
                useUnitValue?: string;
                useUnitType?: string;
                orderingClinician?: string;
                duration?: string;
                frequency?: string;
                frequencyOthersDescription?: string
                serviceStartDate?: string;
                serviceEndDate?: string;
                requestId?: string;
                status?: string;
            }[]>
            (`/${ePrescriptionReferenceNumber}` + "/drugs?isPaginated=false"
            );

    }

    getPrescriptionDetailsDiagnosis(ePrescriptionReferenceNumber: string, pageNumber?: number, recordSize?: number) {
        return this.get(`/${ePrescriptionReferenceNumber}` + "/diagnosis?pageNumber=" + pageNumber + "&recordSize=" + recordSize, {
            subjectToUpdate: this.prescriptionDetailsDiagnosis$

        });
    }

    getPayerMemberPhysicianInfo(ePrescriptionReferenceNumber: string) {
        return this.get<PrescriptionDetailsPayerMemberPhysicianInfoModel>(`/${ePrescriptionReferenceNumber}`, {
            subjectToUpdate: this.prescriptionDetailsPayerMemberPhysicianInfo$,
            useMock: ePrescriptionReferenceNumber == '2023-test'
        });
    }

    getPrescriptionDetailsDrugsRejection(ePrescriptionReferenceNumber: string, category: 'medical' | 'business') {
        return this.get<[]>(
            `/${ePrescriptionReferenceNumber}/validations/${category}`, {
        }).pipe(
            tap(mappedResult => category == 'medical' ? this.prescriptionMedicalValidations$.next(mappedResult) : this.prescriptionBusinessValidations$.next(mappedResult))
        );
    }

    fetchDrugsToDispenseSummary(ePrescriptionReferenceNumber: string) {
        return this.get(`/dispense/${ePrescriptionReferenceNumber}/drugs/summary`, {
            subjectToUpdate: this.dispensableDrugsSummary$
        });
    }
    fetchDispenseDetails(ePrescriptionReferenceNumber: string) {
        return this.get(`/dispense/${ePrescriptionReferenceNumber}`, {
            subjectToUpdate: this.dispensedDetails$
        });
    }

    fetchDispenseDetailsV2(ePrescriptionReferenceNo: string) {
        return this.get(`/dispense/${ePrescriptionReferenceNo}/dispensable-drugs`).pipe(
            map((result: any) => ({
                memberPolicyConsumption: {
                    "REPLACEABLE_BRAND": {
                        "maxPatientShare": 0,
                        "patientShare": 0,
                        "currentConsumption": 0
                    },
                    "IRREPLACEABLE_BRAND": {
                        "maxPatientShare": 0,
                        "patientShare": 0,
                        "currentConsumption": 0
                    }
                }, prescriptionDrugs: result.prescriptionDrugs
            } as DispenseDetail)),
            tap(mappedResult => this.dispenseDetail$.next(mappedResult))
        );;
    }


    cancelPrescriptionData(ePrescriptionReferenceNumber?: string) {
        return this.delete(`/${ePrescriptionReferenceNumber}`);
    }

    dispenseApproveddrugs(ePrescriptionReferenceNo: string, data: any) {
        console.log("Final Data", data)
        return this.post(`/dispense/${ePrescriptionReferenceNo}/dispensable-drugs`, data)
    }


    editPayerPrescriptions(ePrescriptionReferenceNumber: string, requestBody: {
        drugList?: {
            drugCode?: string;
            unitType?: string;
            quantity?: string;
            unitPrice?: string;
            useUnitValue?: string;
            frequency?: string;
            net?: string;
            patientShare?: string;
            totalOfNetAndPatientShare?: string;
            duration?: string;
            status?: string;
            decisionDescription?: string;
        }[];
    }) {

        requestBody.drugList?.map(code => ({
            drugCode: code.drugCode,
            frequency: code.frequency,

            duration: code.duration,
            quantity: code.quantity,

            unitPrice: (code.unitPrice),
            unitType: code.unitType,
            decisionDescription: "abcdefgh"
        }));

        return this.put<any>(`/${ePrescriptionReferenceNumber}/modify-decision`, requestBody);
    }

    saveModifiedDecision(payerId: string, refereneNumber: string, data: any) {
        return this.put('/' + refereneNumber + '/modify-decision', data);
    }



}

