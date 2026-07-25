import { Header } from "../../shared/components/list-view/models/decorators/header.decorator";
import { ListItem } from "../../shared/components/list-view/models/list-item.model";

export class customizationNewRequestModel {
  ePrescriptionReferenceNo?:string;
  drugCode?:string;
  drugName?:string;
    icdCode?:string;
    icdDescription?:string;
   
    unitType?:string;
    moduleName?:string;
    fromAgeInDays?:string;
    toAgeInDays?:string;
    maxValuePerDay?:string;
   productPackageSize?:string;
    rejectionReason?:string
}

   