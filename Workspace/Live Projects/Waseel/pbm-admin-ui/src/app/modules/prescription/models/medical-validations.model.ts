import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class MedicalValidations implements ListItem {
    

    @Header('customization.icdCode', 1)
    diagnosisCode?: string;
    @Header('prescription.codeDescription', 2)
    diagnosisCodeDescription?: string;
    @Header('prescription.type', 3)
    diagnosisType?: string;

    get id() {
        return this.diagnosisCode ?? '';
    }
}