

export class MemberDetailsRequest {

    name?: string;
    idNumber?: string
    gender?: string;
    dateOfBirth?: string;
    nationality?: string;

    pageNumber: number = 0;
    recordSize: number = 10;

    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;

        if (this.name != null && this.name.trim().length > 0) {
            queryParams = `name=${this.name}&`.concat(queryParams);
        }

        if (this.idNumber != null && this.idNumber.trim().length > 0) {
            queryParams = `idNumber=${this.idNumber}&`.concat(queryParams);
        }

        if (this.gender != null && this.gender.trim().length > 0) {
            queryParams = `gender=${this.gender}&`.concat(queryParams);
        }

        if (this.nationality != null && this.nationality.trim().length > 0) {
            queryParams = `nationality=${this.nationality}&`.concat(queryParams);
        }


        return queryParams;
    }
}