import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class PrescriptionDetails implements ListItem {

    @Header('prescription.unitType', 1)
    unitTypes?: string;
    @Header('prescription.quantity', 2)
    quantity?: string;
    @Header('prescription.use', 3)
    use?: string;
    @Header('prescription.frequencyTypes', 4)
    frequencyTypes?: string;
    @Header('prescription.frequencyDes', 5)
    frequencyDes?: string;
    @Header('prescription.duration', 6)
    duration?: string;
    @Header('prescription.drug', 7)
    drug?: string;
    @Header('prescription.unitPrice', 8)
    unitPrice?: string;

    //Listing page
    @Header('prescription.referenceNo', 9)
    referenceNo?: string;
    @Header('prescription.dateAndTime', 10)
    dateAndTime?: string;
    // @Header('prescription.memberId')
    memberId?: string;
    @Header('prescription.member', 11)
    memberName?: string;
    // @Header('prescription.policyNumber')
    // policyNumber?: string;
    @Header('prescription.insurance', 12)
    insurance?: string;

    @Header('prescription.provider', 13)
    providerName?: string;

    @Header('prescription.status', 14)
    status?: string;
    //  member?:string;

    get id() {
        return this.referenceNo ?? '';
    }



    idNumber?: string;

    payerId?: string;
    serviceDate?: string;

}
