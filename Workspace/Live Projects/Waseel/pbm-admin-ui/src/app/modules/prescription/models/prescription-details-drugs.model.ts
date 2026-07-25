import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class PrescriptionDetailsDrugs implements ListItem {
  

    @Header('prescription.drug', 1, ['drugCode'])
    drugName?: string;
    @Header('prescription.servicedate', 2)
    serviceStartDate?: string;
    @Header('prescription.unitType', 3)
    unitType?: string;
    @Header('prescription.quantity', 4)
    quantity?: string;
    @Header('prescription.use', 5)
    useUnitType?: string
    @Header('prescription.frequency', 6)
    frequency?: string;
    frequencyOthersDescription?: string;
    @Header('prescription.duration', 8)
    duration?: string;
    @Header('prescription.unitPrice', 9)
    unitPrice?: string;
     @Header('prescription.total', 10)
    totalOfNetAndPatientShare?: string;
    @Header('prescription.net', 11)
    net?: string;
    @Header('prescription.patientShare', 12)
    patientShare?: string;

    @Header('prescription.patientSharevatvalue&currency', 13)
    patientShareVatAmount?:string;
    patientShareVatCurrency?:string

    @Header('prescription.status', 14)
    status?: string;
    
    useUnitValue?: string;
    drugCode? : number ;
    decisionDescription?: string;
    overridingReason?:string   
    showCommentIcon?:boolean;   
    scientificCode?: number;
    scientificName?:string;
    isOverridableByProvider?:boolean;
    drugListId?:string;
    get id() {
        //return crypto.randomUUID();
        return this.drugCode!.toString();
    }
    
}
