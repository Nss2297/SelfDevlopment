import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class SfdaListModel implements ListItem {

    @Header('ID', 1)
    drugListId?: string;
    @Header('FILE-NAME', 2)
    fileName?: string;
    @Header('EFFECTIVE-DATE', 3)
    effectiveDate?: string
    @Header('UPLOAD-DATE', 4)
    uploadDate?: string;


    get id() {
        return this.drugListId ?? '';
    }

}