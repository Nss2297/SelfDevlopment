


export class drugExclusionRequest{
    exclusionId?:string;
    name?: string;
    createdDateFrom?: string;
    createdDateTo?: string;
    createdBy?: string;
    updatedDateFrom?: string;
    updatedDateTo?: string;
    pageNumber: number = 0;
    recordSize: number = 10;
    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        if (this.exclusionId!= null && this.exclusionId.trim().length > 0) {
            queryParams = `exclusionId=${this.exclusionId}&`.concat(queryParams);
        }
        if (this.name!= null && this.name.trim().length > 0) {
            queryParams = `name=${this.name}&`.concat(queryParams);
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