import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class drugExclusionListDetailsModel implements ListItem {


    @Header('ID', 1)
    exclusionId?: string;
    @Header('Name', 2)
    name?: string;
    @Header('Created Date', 3)
    createdDate?: string;
    @Header('Updated Date', 4)
    updatedDate?: string

    get id() {
        return this.exclusionId ?? '';
    }

}

export class exclusionDrugDetailsModel implements ListItem {


    @Header('DRUG', 1)
    sfdaDescription?: string;
    @Header('SCIENTIFIC-NAME', 2)
    scientificName?: string;
    @Header('SCIENTIFIC-CODE', 3)
    scientificCode?: string;
    @Header('LAST-UPDATED-DATE', 4)
    lastUpdatedDate?: string;

    waseelDrugId?: number;
    unitPrice?: string;
    sfdaCode?: string;
    drugFormulary?: boolean;
    dosageForm?: string;
    drugCode?: string
    drugName?: string
    pageNumber?: number = 0;
    recordSize?: number = 5;

    get id() {
        return this.scientificCode ?? '';
    }
}
