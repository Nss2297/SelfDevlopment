export class DrugModifyDecisionModel {
    drugCode?: string;
    drugName?: string;
    duration?: string;
    frequency?: string;
    frequencyOthersDescription?: string;
    net!: number;
    orderingClinician!: string;
    patientShare!: number;
    quantity!: number;
    requestId!: string;
    serviceEndDate!: string;
    serviceStartDate!: string;
    status!: string;
    totalOfNetAndPatientShare!: number;
    unitPrice!: number;
    unitType!: string;
    useUnitType!: string;
    useUnitValue!: string;
    decisionDescription!: string;
    scientificCode?: string;
    scientificName?: string;
    overridingReason?:string;
    drugListId?:string;
}
