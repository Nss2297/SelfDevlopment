

export class DrugToDiagnosisRequest {

    serviceCode?: string;
    updateDateAndTime?:string
    icdCode?: string;
    payerId?: string;

    moduleName?: string;
    categoryOfApproval?: string;

    rejectionCategory?: string;
    serviceStatus?: string;

    pageNumber: number = 0;
    recordSize: number = 10;

    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        if (this.serviceStatus != null && this.serviceStatus.trim().length > 0) {
            queryParams = `serviceStatus=${this.serviceStatus}&`.concat(queryParams);
        }

        if (this.rejectionCategory != null && this.rejectionCategory.trim().length > 0) {
            queryParams = `rejectionCategory=${this.rejectionCategory}&`.concat(queryParams);
        }

        if (this.categoryOfApproval != null && this.categoryOfApproval.trim().length > 0) {
            queryParams = `categoryOfApproval=${this.categoryOfApproval}&`.concat(queryParams);
        }

        if (this.moduleName != null && this.moduleName.trim().length > 0) {
            queryParams = `moduleName=${this.moduleName}&`.concat(queryParams);
        }

        if (this.payerId != null && this.payerId.trim().length > 0) {
            queryParams = `payerId=${this.payerId}&`.concat(queryParams);
        }

        if (this.icdCode != null && this.icdCode.trim().length > 0) {
            queryParams = `icdCode=${this.icdCode}&`.concat(queryParams);
        }

        if (this.serviceCode != null && this.serviceCode.trim().length > 0) {
            queryParams = `serviceCode=${this.serviceCode}&`.concat(queryParams);
        }

        return queryParams;
    }
}