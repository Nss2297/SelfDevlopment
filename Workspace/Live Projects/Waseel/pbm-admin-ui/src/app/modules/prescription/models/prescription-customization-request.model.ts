import * as moment  from "moment";
import { CustomizationDetailsModel } from "./customization-details.request.model";
export class PrescriptionCustomizationRequest{
   
    requestDateAndTime?: string;
   
    drugCode?: string;
    drugName?: string;
    fromDate?:string;
    endDate?:string;
    moduleName?: string;
    customizationRequestDetail?:CustomizationDetailsModel;
    customizationDetails?:any[];
    // customizationDetails?: string;

    // customizationDetails?: {
    //     value?:string;
    //     lable?: string;
    //      }[];
    
    status?: string;
    pageNumber: number = 0;
    recordSize: number = 10;
    customizationRequestId?:number;
    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        if (this.drugCode != null && this.drugCode.trim().length > 0) {
            queryParams = `drugCode=${this.drugCode}&`.concat(queryParams);
        }
        if (this.fromDate != "" || (this.fromDate != null && this.fromDate.trim().length > 0)) {
            if(this.fromDate != "Invalid date"){
                queryParams = `fromDate=${this.fromDate}&`.concat(queryParams);
            }
        }
        if (this.endDate != "" || (this.endDate != null && this.endDate.trim().length > 0)) {
            if(this.endDate != "Invalid date"){
                queryParams = `endDate=${this.endDate}&`.concat(queryParams);
            }
        }
        if (this.drugName != null && this.drugName.trim().length > 0) {
            queryParams = `drugName=${this.drugName}&`.concat(queryParams);
        }
        if (this.requestDateAndTime != null && this.requestDateAndTime.trim().length > 0) {
            // queryParams = `dateAndTime=${this.dateAndTime}&`.concat(queryParams);
                queryParams = moment(this.requestDateAndTime).format('MM-DD-YYYY HH:mm');
        }

        if (this.moduleName != null && this.moduleName.trim().length > 0) {
            queryParams = `moduleName=${this.moduleName}&`.concat(queryParams);
        }
        // if (this.customizationRequestId != null && this.customizationRequestId.trim().length > 0) {
        //     queryParams = `customizationRequestId=${this.customizationRequestId}&`.concat(queryParams);
        // }

        // if (this.customizationDetails != null && this.customizationDetails.trim().length > 0) {
        //     queryParams = `customizationDetails=${this.customizationDetails}&`.concat(queryParams);
        // }
        if (this.customizationRequestDetail?.value != null && this.customizationRequestDetail?.value.trim().length > 0) {
            queryParams = `value=${this.customizationRequestDetail?.value}&`.concat(queryParams);
        }
       
        if (this.customizationRequestDetail?.lable != null && this.customizationRequestDetail?.lable.trim().length > 0) {
            queryParams = `lable=${this.customizationRequestDetail?.lable}&`.concat(queryParams);
        }

        if (this.status != null && this.status.trim().length > 0) {
            queryParams = `status=${this.status}&`.concat(queryParams);
        }      

        return queryParams;
    }
}