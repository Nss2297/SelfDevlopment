export class DrugAgeRequest {

    serviceCode?: string;
    updateDateAndTime?:string;
    age?: string;
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

        // if (this.age != null && this.age.trim().length > 0) {
        //     queryParams = `age=${this.age}&`.concat(queryParams);
        // }
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