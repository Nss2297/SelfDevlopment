export class DrugExclusionDetailsFilter{
   
    drugExclusionDetailsId?:number;
    drugName?:string;
    drugCode?:string;
    scientificName?:string;
    scientificCode?:string;
    price?:string;
    lastUpdatedDate?:string;
    updatedDateFrom?: string;
    updatedDateTo?: string;
    pageNumber: number = 0;
    recordSize: number = 10;

    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        
        if (this.drugName && typeof this.drugName!='undefined') {
            queryParams = `drugName=${this.drugName}&`.concat(queryParams);
        }
        if (this.drugCode && typeof this.drugCode!='undefined') {
            queryParams = `drugCode=${this.drugCode}&`.concat(queryParams);
        }
        if (this.scientificName && typeof this.scientificName!='undefined') {
            queryParams = `scientificName=${this.scientificName}&`.concat(queryParams);
        }
        if (this.scientificCode && typeof this.scientificCode!='undefined') {
            queryParams = `scientificCode=${this.scientificCode}&`.concat(queryParams);
        }
        if (this.price && typeof this.price!='undefined') {
            queryParams = `price=${this.price}&`.concat(queryParams);
        }
        
        if (this.drugExclusionDetailsId && typeof this.drugExclusionDetailsId!='undefined') {
            queryParams = `drugExclusionDetailsId=${this.drugExclusionDetailsId}&`.concat(queryParams);
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

