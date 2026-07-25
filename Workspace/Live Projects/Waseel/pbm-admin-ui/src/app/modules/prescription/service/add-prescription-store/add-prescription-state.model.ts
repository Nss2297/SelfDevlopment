// import { customizationNewRequestModel } from "../../models/customization-new-request.model";
import { customizationNewRequestModel } from "../../models/customization-new-request.model";
// import { customizationResponsemodel } from "../../models/customiztion-request-model";
import { PrescriptionNewRequestModel } from "../../models/prescription-new-request.model";


export class AddPrescriptionState {

    activeStep: 1 | 2 | 3 | 4 = 1;
    mode: 'new' | 'followUp' = 'new';
    step1IsValid: boolean = false;
    step2IsValid: boolean = false;
    step3IsValid: boolean = false;
    step4IsValid: boolean = false;

    prescriptionData: PrescriptionNewRequestModel = new PrescriptionNewRequestModel();
customizationData:customizationNewRequestModel = new customizationNewRequestModel();
// customizationData:PrescriptionCustomizationRequest = new PrescriptionCustomizationRequest();
}