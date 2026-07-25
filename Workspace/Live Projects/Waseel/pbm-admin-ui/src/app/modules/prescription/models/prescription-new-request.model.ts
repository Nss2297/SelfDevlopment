import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class PrescriptionNewRequestModel {
    payerId?: string;
    memberId?: string;
    memberName?: string;
    IdNumber?: string;
    dateOfBirth?: string;
    policyNumber?: string;
    memberGender?: string;
    memberWeight?: number;
    memberHeight?: number;
    physicianLicenseNumber?: string;
    physicianName?: string;
    physicianCategory?: string;
    physicianSpeciality?: string;
    caseType?: string;
    memberNationality?: string;
    diagnosisCodes?: {
        diagnosisCode?: string;
        diagnosisDescription?: string;
        diagnosisType?: string;
    }[];
    drugList?: {
        roaSuggested?: string;
        dosageForm?: string;
        strengthUnit?: string;
        strength?: string;
        scientificCode?: string;
        scientificName?: string;
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
        drugListId?: string;
    }[];

}


export class NewPrescriptionDiagnosisCode implements ListItem {

    @Header("customization.icdCode", 1)
    diagnosisCode?: string;
    @Header("prescription.codeDescription", 2)
    diagnosisDescription?: string;
    @Header("prescription.type", 3)
    diagnosisType?: string;

    get id() {
        return this.diagnosisCode ?? '';
    }

}