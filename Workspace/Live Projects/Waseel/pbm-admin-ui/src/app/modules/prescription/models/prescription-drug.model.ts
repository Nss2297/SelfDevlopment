import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";


export class DrugsDetails implements ListItem {


    @Header('prescription.drug', 1)
    drugCode?: string;
    scientificCode?:string;
    drugName?: string;

    @Header('prescription.unitType', 2)
    unitType?: string;
    @Header('prescription.quantity', 3)
    quantity?: string;
    @Header('prescription.use', 4)
    useUnitValue?: string;
    @Header('prescription.frequency', 5)
    frequency?: string;
    @Header('prescription.duration', 6)
    duration?: string;
    @Header('prescription.unitPrice', 7)
    unitPrice?: number;

    @Header('prescription.servicedate', 8)
    serviceStartDate?: string;

    @Header('prescription.dosageForm', 9)
    dosageForm?:string;
    status?:string;
    
    type?:string;
    frequencyOthersDescription?: string;
    scientificName?:string;
    strengthUnit?:string;
    // scientificCode?:string;
    // drugListId? :string;
    strength?:string;
    roaSuggested?:string;
    drugListId?:string;
    get id() {
        return this.drugCode ?? '';
    }

  
    // categoryOfApproval?: string;
    // rejectionReason?: string;
}

