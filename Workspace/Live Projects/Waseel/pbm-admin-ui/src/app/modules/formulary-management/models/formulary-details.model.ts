import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class FormularyDetailsModel {
    formularyId!: number;
    formularyName!: string;
    createdDate!: string;
    createdBy!: string;
    updatedDate!: string;
    payerId!: string;
}

export class FormularyPolicyDetailsModel implements ListItem {

    @Header('POLICY', 1)
    policyName?: string;
    @Header('POLICY-NUMBER', 2)
    policyNumber?: string;
    @Header('POLICY-CLASS', 3)
    policyClassName?: string;
    @Header('MEMBER-ID', 4)
    idNumber?: string;

    drugFormularyAssociationId?: string;
    formularyId?: string;
    policyType?: string;
    issueDate?: string;
    startDate?: string;
    endDate?: string;
    policyClasses?: [];
    basedOn?: string;
    pageNumber: number = 0;
    recordSize: number = 5;

    get id() {
        return crypto.randomUUID();
    }
}

export class FormularyMemberDetailsModel {
    idNumber?: string;
    memberName?: string;
    gender?: string;
    dateOfBirth?: string;
    maritalStatus?: string;
    nationality?: string;
    mobileNumber?: string;
    email?: string;
}

export class FormularyDrugDetailsModel implements ListItem {

    @Header('DRUG', 1)
    drugName?: string;
    @Header('GENERIC-NAME', 2)
    genericName?: string;
    @Header('PRICE', 3)
    price?: string;
    @Header('prescription.patientShare', 4)
    patientShare?: string;

    drugCode?: string;
    drugFormularyDetailsId?: number;
    formularyId?: string;
    pageNumber: number = 0;
    recordSize: number = 5;

    get id() {
        return this.drugFormularyDetailsId?.toString() ?? '';
    }
}