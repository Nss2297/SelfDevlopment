import { PrescriptionDetailsMemberInfoModel } from "./prescription-details-member-info.model";
import { PrescriptionDetailsPhysicianInfoModel } from "./prescription-details-physician-info.model";

export class PrescriptionDetailsPayerMemberPhysicianInfoModel {
    status?: string;
    statusDescription?: string;
    memberInfoModel?: PrescriptionDetailsMemberInfoModel;
    physicianModel?: PrescriptionDetailsPhysicianInfoModel;
    payerName?: string;
    payerId?: string;
    totalNet?: number;
    totalPatientShare?: number;
    caseType?:string;
    providerName?:string;
    providerId?:string;
    totalPatientShareVatAmountValue?:number;
    totalPatientShareVatAmountCurrency?:string
}
