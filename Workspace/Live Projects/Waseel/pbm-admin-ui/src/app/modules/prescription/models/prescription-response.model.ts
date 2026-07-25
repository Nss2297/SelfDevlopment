export class PrescriptionResponseModel {
    requestId?:string;
    status?:string;
    statusDescription?:string;
    ePrescriptionReferenceNumber?:string;
    diagnosisCodes?:any;
    results?:any;
    canCancel?:boolean;
    canFollowUp?:boolean;
    httpStatusCode?:number;
    httpStatusDescription?:string;
}
