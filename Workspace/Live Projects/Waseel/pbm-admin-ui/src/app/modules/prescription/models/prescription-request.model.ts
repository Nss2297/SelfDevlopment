import * as moment from "moment";

export class PrescriptionRequest {
    unitTypes?: string;   
    quantity?: string;    
    use?: string;    
    frequencyTypes?: string;   
    frequencyDes?:string;
    // unitTypes?: string;   
    duration?: string;   
    drug?:string
    sfdaCode?:string;
    sfdaDescription?:string;
    scientificName?:string;
    unitPrice?:string
    
   referenceNo?: string;   
    dateAndTime?:string;
    startdate?:string;
    enddate?:string;
    memberId?: string;     
    memberName?: string;   
    policyNumber?:string;
    status?:string;
    idNumber?:string;
    insurance?:string;  
    provider?:string;  
    pageNumber: number = 0;
    recordSize: number = 10;

    

    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        if (this.referenceNo != null && this.referenceNo.trim().length > 0) {
            queryParams = `referenceNo=${this.referenceNo}&`.concat(queryParams);
        }
        if (this.dateAndTime != null && this.dateAndTime.trim().length > 0) {
            // queryParams = `dateAndTime=${this.dateAndTime}&`.concat(queryParams);
                queryParams = moment(this.dateAndTime).format('MM-DD-YYYY HH:mm');
        }
        if (this.startdate != "" || (this.startdate != null && this.startdate.trim().length > 0)) {
            if(this.startdate != "Invalid date"){
                queryParams = `fromDate=${this.startdate}&`.concat(queryParams);
            }
        }
        if (this.enddate != "" || (this.enddate != null && this.enddate.trim().length > 0)) {
            if(this.enddate != "Invalid date"){
                queryParams = `endDate=${this.enddate}&`.concat(queryParams);
            }
        }

        if (this.memberId != null && this.memberId.trim().length > 0) {
            queryParams = `memberId=${this.memberId}&`.concat(queryParams);
        }

        if (this.memberName != null && this.memberName.trim().length > 0) {
            queryParams = `memberName=${this.memberName}&`.concat(queryParams);
        }

        if (this.policyNumber != null && this.policyNumber.trim().length > 0) {
            queryParams = `policyNumber=${this.policyNumber}&`.concat(queryParams);
        }

        if (this.status != null && this.status.trim().length > 0) {
            queryParams = `status=${this.status}&`.concat(queryParams);
        }
        if (this.idNumber != null && this.idNumber.trim().length > 0) {
            queryParams = `idNumber=${this.idNumber}&`.concat(queryParams);
        }
        if (this.insurance != null && this.insurance.trim().length > 0) {
            queryParams = `insurance=${this.insurance}&`.concat(queryParams);
        }
        if (typeof this.provider!='undefined' && this.provider) {
            queryParams = `providerId=${this.provider}&`.concat(queryParams);
        }

       

        return queryParams;
    }
}
