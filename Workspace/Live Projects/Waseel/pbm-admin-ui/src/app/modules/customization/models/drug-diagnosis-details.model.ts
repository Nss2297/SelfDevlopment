import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";


export class DrugDiagnosisDetails implements ListItem {
    
    @Header('customization.serviceCode', 1)
    serviceCode?: string;
    @Header('Updated Date', 2)
    updateDateAndTime?:string
    @Header('customization.icdCode', 3)
    icdCode?: string;
    @Header('customization.payer', 4)
    payer?: string;
    @Header('customization.rejectionCategory', 5)
    rejectionCategory?: string;
    @Header('customization.module', 6)
    moduleName?: string;
    @Header('customization.serviceStatus', 7)
    serviceStatus?: string;

    categoryOfApproval?: string;
    rejectionReason?: string;

    get id() {
        return this.serviceCode ?? '';
    }

}