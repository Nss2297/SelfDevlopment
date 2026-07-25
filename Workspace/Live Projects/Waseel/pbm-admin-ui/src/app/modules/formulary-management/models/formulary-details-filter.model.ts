export class FormularyDetailsFilter{
    policyName?:string;
    policyNumber?:string;
    policyClassName?:string;
    policyMemberId?:string;
    drugFormularyDetailsId?:number;
    drugName?:string;
    drugCode?:string;
    genericName?:string;
    pageNumber: number = 0;
    recordSize: number = 10;

    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        if (this.policyName && typeof this.policyName!='undefined') {
            queryParams = `policyName=${this.policyName}&`.concat(queryParams);
        }
        if (this.policyNumber && typeof this.policyNumber!='undefined') {
            queryParams = `policyNumber=${this.policyNumber}&`.concat(queryParams);
        }
        if (this.policyClassName && typeof this.policyClassName!='undefined') {
            queryParams = `policyClassName=${this.policyClassName}&`.concat(queryParams);
        }
        if (this.policyMemberId && typeof this.policyMemberId!='undefined') {
            queryParams = `idNumber=${this.policyMemberId}&`.concat(queryParams);
        }
        if (this.drugName && typeof this.drugName!='undefined') {
            queryParams = `drugName=${this.drugName}&`.concat(queryParams);
        }
        if (this.drugCode && typeof this.drugCode!='undefined') {
            queryParams = `drugCode=${this.drugCode}&`.concat(queryParams);
        }
        if (this.genericName && typeof this.genericName!='undefined') {
            queryParams = `genericName=${this.genericName}&`.concat(queryParams);
        }
       
        
        return queryParams;
    }
}