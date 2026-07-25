export class CancelPrescriptionResponseModel {
    ePrescriptionReferenceNumber?: string;
    status?: string;
    statusDescription?: string;
    canCancel?: boolean;
    canFollowUp?: boolean;
    httpStatusCode?: number;
}
