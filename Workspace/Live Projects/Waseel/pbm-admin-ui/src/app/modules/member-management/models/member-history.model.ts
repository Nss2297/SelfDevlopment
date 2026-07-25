import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";


export class MemberHistory implements ListItem {

    @Header('prescription.prescriptionreferencenumber', 1)
    ePrescriptionReferenceNumber?: string;

    @Header('prescription.dateAndTime', 2)
    lastUpdateDateTime?:string;
    
    @Header('prescription.provider', 3)
    providerName?:string;
    
    @Header('prescription.status', 4)
    ePrescriptionStatus?: string;
   
    idNumber?: string
   
    get id() {
        return this.idNumber ?? '';
    }

}

export class memberHistroyRequest{
    ePrescriptionReferenceNumber?: string;
    providerName?:string;
    ePrescriptionStatus?: string;
    lastUpdateDateTime?:string;
    pageNumber: number = 0;
    recordSize: number = 10;
}