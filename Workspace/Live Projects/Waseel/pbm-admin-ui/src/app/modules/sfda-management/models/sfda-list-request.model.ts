export class SfdaRequestModel {
    drugListId?: string
    effectiveDateFrom?: string;
    effectiveDateTo?: string;
    uploadDateFrom?: string;
    uploadDateTo?: string;
    fileName?: string;

    pageNumber: number = 0;
    recordSize: number = 10;
    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        if (this.drugListId != null && this.drugListId.trim().length > 0) {
            queryParams = `drugListId=${this.drugListId}&`.concat(queryParams);
        }
        if (this.fileName != null && this.fileName.trim().length > 0) {
            queryParams = `fileName=${this.fileName}&`.concat(queryParams);
        }
        if (this.effectiveDateFrom != "" || (this.effectiveDateFrom != null && this.effectiveDateFrom.trim().length > 0)) {
            if (this.effectiveDateFrom != "Invalid date") {
                queryParams = `effectiveDateFrom=${this.effectiveDateFrom}&`.concat(queryParams);
            }
        }
        if (this.effectiveDateTo != "" || (this.effectiveDateTo != null && this.effectiveDateTo.trim().length > 0)) {
            if (this.effectiveDateTo != "Invalid date") {
                queryParams = `effectiveDateTo=${this.effectiveDateTo}&`.concat(queryParams);
            }
        }
        if (this.uploadDateFrom != "" || (this.uploadDateFrom != null && this.uploadDateFrom.trim().length > 0)) {
            if (this.uploadDateFrom != "Invalid date") {
                queryParams = `uploadDateFrom=${this.uploadDateFrom}&`.concat(queryParams);
            }
        }
        if (this.uploadDateTo != "" || (this.uploadDateTo != null && this.uploadDateTo.trim().length > 0)) {
            if (this.uploadDateTo != "Invalid date") {
                queryParams = `uploadDateTo=${this.uploadDateTo}&`.concat(queryParams);
            }

        }


        return queryParams;

    }
}