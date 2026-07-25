
export class DispenseDetail {

    memberPolicyConsumption!: { [category: string]: { maxPatientShare: number, patientShare: number, currentConsumption: number } };
    prescriptionDrugs?: DispensableDrug[];

}


export class DispensableDrug {

    scientificName?: string;
    scientificCode?: string;
    quantity?: number;
    isBrand?: boolean;
    unitPrice?: number;
    totalPrice?: number;
    patientShare?: number;
    benefitCase?: string;
    net?: number;
    sfdaCode?: string;
    sfdaDescription?: string;
    dosageForm?: string;
    strengthUnit?: string;
    strength?: string;
    roaSuggested?: string;
    isApproved?: boolean;
    suggestedDrugs: BrandDrug[] = [];
    needsApproval?: boolean = false;
    patientShareVatAmount?: string;
    patientShareVatCurrency?: string;
    isApprovalRequired?: boolean;
}


export class BrandDrug {
    unitPrice?: number;
    totalPrice?: number;
    benefitCase?: string;
    sfdaCode?: string;
    sfdaDescription?: string;
    dosageForm?: string;
    strengthUnit?: string;
    strength?: string;
    roaSuggested?: string;
    inExclusionList?: boolean;
    drugFormulary?: boolean;
    isApproved?: boolean;
    patientShare?: number;
    maxPatientShare?: number;
    maxPatientShareCurrency?: string;
    patientShareCurrency?: string;
    patientShareVatAmount?: string;
    patientShareVatCurrency?: string;
    isApprovalRequired?: boolean;
}