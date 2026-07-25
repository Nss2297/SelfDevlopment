import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class DuplicationDetails implements ListItem {

    @Header('customization.serviceCode', 1)
    serviceCode?: string;
    @Header('customization.interactedServiceCode', 2)
    interactedServiceCode?: string;
    @Header('customization.updatedDate', 3)
    lastUpdateDateAndTime?: string;
    @Header('customization.payer', 4)
    payerId?: string;
    @Header('customization.module', 5)
    moduleName?: string;
    @Header('customization.serviceStatus', 6)
    serviceStatus?: string;



    additionalRejectionReason?: string;
    get id() {
        return this.serviceCode ?? '';
    }

}