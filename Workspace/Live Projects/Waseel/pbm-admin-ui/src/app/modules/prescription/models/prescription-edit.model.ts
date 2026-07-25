import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class EditPrescription {
    drugList?: {
        drugCode?: string;
        
        unitType?: string;
        quantity?: string;
        unitPrice?: number;
        useUnitValue?: string;
        frequency?: string;
        net?: string;
        patientShare?: string;
        totalOfNetAndPatientShare?: string;
        duration?: string;
         status?: string;
        decisionDescription?: string;
    }[];
   

}