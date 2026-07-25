export class DrugGenderRequest {

    serviceCode?: string;
    gender?: string;
    moduleName?: string;
    serviceStatus?: string;
    payerId?: string;
    pageNumber: number = 0;
    recordSize: number = 10;

    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;
        if (this.serviceCode != null && this.serviceCode.trim().length > 0) {
            queryParams = `serviceCode=${this.serviceCode}&`.concat(queryParams);
        }

        if (this.gender != null && this.gender.trim().length > 0) {
            queryParams = `gender=${this.gender}&`.concat(queryParams);
        }
        if (this.payerId != null && this.payerId.trim().length > 0) {
            queryParams = `payerId=${this.payerId}&`.concat(queryParams);
        }
        if (this.moduleName != null && this.moduleName.trim().length > 0) {
            queryParams = `moduleName=${this.moduleName}&`.concat(queryParams);
        }

        if (this.serviceStatus != null && this.serviceStatus.trim().length > 0) {
            queryParams = `serviceStatus=${this.serviceStatus}&`.concat(queryParams);
        }
        return queryParams;
    }
}