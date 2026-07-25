import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class DrugAgeDetails implements ListItem {

    @Header('customization.serviceCode', 1)
    serviceCode?: string;
    
    @Header('Updated Date', 2)
    updateDateAndTime?:string

    @Header('prescription.age', 3)
    fromAgeInDays?: number;
    toAgeInDays?:number;

    @Header('customization.payer', 4)
    payerId?: string;

    @Header('customization.module', 5)
    moduleName?: string;

    @Header('customization.serviceStatus', 6)
    serviceStatus?: string;

    ageGroup?:string
    rejectionReason?: string;
    get id() {
        return this.serviceCode ?? '';
    }

}