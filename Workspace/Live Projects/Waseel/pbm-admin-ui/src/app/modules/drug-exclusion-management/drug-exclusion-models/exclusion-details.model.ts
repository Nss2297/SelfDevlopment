import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class exclusionDetailsModel implements ListItem {

    @Header('TYPE', 1)
    type?: string;
    @Header('NETWORK', 2)
    networkName?: string;
    @Header('PROVIDER', 3)
    providerName?: string;
    @Header('Speciality', 4)
    speciality?: string;
    providerId?: number;

    exclusionType?: string;
    exclusionName?: string;
    networkId?: number;
    specialityId?: number;
    specialityName?: string;
    exclusionAsscId?: string;
    pageNumber?: number = 0;
    recordSize?: number = 5;

    get id() {
        return this.exclusionAsscId?.toString() ?? '';
    }
}


export class ExclusionDrugDetailsModel implements ListItem {

    @Header('DRUG', 1)
    drugName?: string;
    drugCode?: string;
    @Header('Scientific Name', 2)
    scientificName?: string;
    @Header('Scientific Code', 3)
    scientificCode?: string;
    @Header('LAST-UPDATED-DATE', 4)
    lastUpdateDate?: string;
    drugExclusionDetailsId?: number;

    pageNumber?: number = 0;
    recordSize?: number = 5;

    get id() {
        return this.drugExclusionDetailsId?.toString() ?? '';
    }
}
export class ExclusionTypeRequestModel {
    exclusionType?: string;
    networkId?: number;
    exclusionNetwork?: number;
    providerId?: number;
    specialityId?: number;
    exclusionProvider?: number;
    exclusionProviderName?: string;
    exclusionSpecialty?: number;


}

export class ExclusionTypeDrugRequestModel {
    drugCode?: string;
    drugName?: string;
    scientificName?: string;
    scientificCode?: string;
    price?: string;
    waseelDrugId?: number;
    lastUpdateDate?: string;
}

export class BasedOnAllExclusion {
    exclusionType?: string;
    specialityId?: string;
    specialityName?: string;
    networkName?: string;
    provider?: string;
    providerId?: string;
    speciality?: string;
    networkId?: number;
    exclusionName?: string;
    pageNumber?: number = 0;
    recordSize?: number = 5;

    toQueryParams() {
        let queryParams = `pageNumber=${this.pageNumber}&recordSize=${this.recordSize}`;


        if (this.exclusionType && typeof this.exclusionType != 'undefined') {
            queryParams = `exclusionType=${this.exclusionType}&`.concat(queryParams);
        }
        if (this.exclusionName && typeof this.exclusionName != 'undefined') {
            queryParams = `exclusionName=${this.exclusionName}&`.concat(queryParams);
        }


        return queryParams;
    }
}




