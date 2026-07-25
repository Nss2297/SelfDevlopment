import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class SfdaDrugDetailsModel {
    id?: string;
    effectiveDate?: string;
    uploadDate?: string;
}

export class SfdaDrugListDetailsModel implements ListItem {
    @Header('SFDA-CODE', 1)
    sfdaCode?: string;
    @Header('GTIN-CODE', 2)
    gtinCode?: string
    @Header('TRADE-NAME', 3)
    tradeName?: string;
    @Header('SCIENTIFIC-NAME', 4)
    scientificName?: string;
    @Header('SCIENTIFIC-CODE', 5)
    scientificCode?: string;
    @Header('PRICE', 6)
    price?: string;

    pageNumber?: number;
    recordSize?: number;
    waseelDrugId?: string;
    dosageForm?: string;
    unitType?: string;
    administrationRoute?: string;
    packageSize?: string;
    packageType?: string;
    granularUnit?: string;
    strength?: string;
    strengthUnit?: string;
    drugListId?: string;

    get id() {
        return this.waseelDrugId ?? '';
    }


}

export class SfdaDrugListRequestModel {
    pageNumber?: number;
    recordSize?: number;
    searchValue?: string;

    toQueryParams() {
        let queryParams = `page=${this.pageNumber}&pageSize=${this.recordSize}`;

        if (this.searchValue != null && this.searchValue.trim().length > 0) {
            queryParams = `searchValue=${this.searchValue}&`.concat(queryParams);
        }
        return queryParams;
    }
}
