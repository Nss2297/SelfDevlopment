export class networkExclusionModel{
    networkId?:string;
    networkName?:string;
    pageNumber?: number = 0;
    recordSize?: number = 10;

    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;
        if (this.networkId!= null && this.networkId.trim().length > 0) {
            queryParams = `formularyId=${this.networkId}&`.concat(queryParams);
        }
        if (this.networkName!= null && this.networkName.trim().length > 0) {
            queryParams = `formularyName=${this.networkName}&`.concat(queryParams);
        }

        return queryParams;
    }

}