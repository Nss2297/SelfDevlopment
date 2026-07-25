export class PrescriptionNewResponseModel {
    
        requestId?: string;
        status?: string;
        statusDescription?: any;
        ePrescriptionReferenceNumber?: string;
        diagnosisCodes?:string [];
        results?:string [];
        canCancel?: boolean;
        canFollowUp?: boolean;
        httpStatusCode?: number;
       
        httpStatusDescription?:string;

      }
