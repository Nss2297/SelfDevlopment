import * as moment  from "moment";
// import { FormularyDetailsModel } from "./formulary-details.model";

export class formularyRequest{
    formularyId?:string
    formularyName?: string;
    createdDateFrom?: string;
    createdDateTo?: string;
    createdBy?: string;
    updatedDateFrom?: string;
    updatedDateTo?: string;
    pageNumber: number = 0;
    recordSize: number = 10;
    request!: formularyRequest;
    // formularyRequestDetail?:FormularyDetailsModel;
    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        if (this.formularyId!= null && this.formularyId.trim().length > 0) {
            queryParams = `formularyId=${this.formularyId}&`.concat(queryParams);
        }
        if (this.formularyName!= null && this.formularyName.trim().length > 0) {
            queryParams = `formularyName=${this.formularyName}&`.concat(queryParams);
        }
        if (this.createdDateFrom != "" || (this.createdDateFrom != null && this.createdDateFrom.trim().length > 0)) {
            if(this.createdDateFrom != "Invalid date"){
                queryParams = `createdDateFrom=${this.createdDateFrom}&`.concat(queryParams);
            }
        }
        if (this.createdDateTo != "" || (this.createdDateTo != null && this.createdDateTo.trim().length > 0)) {
            if(this.createdDateTo != "Invalid date"){
                queryParams = `createdDateTo=${this.createdDateTo}&`.concat(queryParams);
            }
        }
        if (this.updatedDateFrom != "" || (this.updatedDateFrom != null && this.updatedDateFrom.trim().length > 0)) {
            if(this.updatedDateFrom !="Invalid date"){
                queryParams = `updatedDateFrom=${this.updatedDateFrom}&`.concat(queryParams);
            }
        }
        if (this.updatedDateTo != "" || (this.updatedDateTo != null && this.updatedDateTo.trim().length > 0)) {
            if(this.updatedDateTo !="Invalid date"){
                queryParams =`updatedDateTo=${this.updatedDateTo}&`.concat(queryParams);
            }
            
        }
        
     
        return queryParams;

    }
}