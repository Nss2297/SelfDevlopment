import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class FormularyListDetailsModel implements ListItem {
    
    @Header('ID', 1)
    formularyId?: string;
    @Header('Name', 2)
    formularyName?: string;
    @Header('Created Date', 3)
    createdDate?: string;
    @Header('Updated Date', 4)
    updatedDate?:string
   

    get id() {
        return this.formularyId ?? '';
    }
}
