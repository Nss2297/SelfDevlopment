import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";


export class CustomizationRequestModel implements ListItem {

    @Header('Request Date & Time', 1)
    lastUpdatedDate?: string;
    @Header('Drug', 2)
    drugCode?: string;
    drugName?: string;
    @Header('Type', 3)
    moduleName?: string;
    @Header('Details', 4)
    customizationDetails?: any[];
    //  customizationDetails?: {
    // value?:string;
    // lable?: string;
    //  }[];

    @Header('Status', 5)
    status?: string;
    customizationRequestId?: string;
    ePrescriptionReferenceNo?: string;


    get id() {
        return this.customizationRequestId ?? '';
    }
}






