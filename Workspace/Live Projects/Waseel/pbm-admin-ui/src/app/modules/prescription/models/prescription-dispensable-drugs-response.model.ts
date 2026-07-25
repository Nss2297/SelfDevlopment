import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";
import Big from 'big.js';

export class DispensableDrugsResponseModel implements ListItem {
    drugCode?: string;
    drugDescription?: string;
    @Header('prescription.drug', 1)
    drug: string = '';
    @Header('prescription.quantity', 2)
    quantity?: Big;
    @Header('prescription.unitPrice', 3)
    unitPrice?: number;
    @Header('TOTAL-PRICE', 4)
    totalPrice?: Big;
    @Header('requestId', 5)
    requestId?: string;

    get id() {
        return this.requestId ?? '';
    }
}
