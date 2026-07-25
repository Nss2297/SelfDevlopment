import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class DispensedDetails implements ListItem {

    @Header('prescription.pharmacy', 1)
    pharmacy?: string;
    @Header('prescription.dispenseDate', 2)
    dispenseDate?: string;

    get id() {
        return crypto.randomUUID();
    }
}
