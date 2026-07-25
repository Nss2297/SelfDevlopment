import { Injectable } from '@angular/core';
import { Store } from 'src/app/util/store';
import { DrugsDetails } from '../../models/prescription-drug.model';
import { PrescriptionNewRequestModel } from '../../models/prescription-new-request.model';
import { AddPrescriptionState } from './add-prescription-state.model';

@Injectable({
    providedIn: 'root'
})
export class AddPrescriptionStoreService extends Store<AddPrescriptionState> {

    payers = [
        {
            key: '102',
            value: 'Tawuniya | التعاونية',
            selected: true
        }
    ];
    caseType = [{
        key: 'INPATIENT',
        value: 'Inpatient | مريض داخلي',
        selected: true
    },
    {
        key: 'OUTPATIENT',
        value: 'Outpatient | العيادات الخارجية',
    }
    ]

    selectedMemberInfo?: { memberName: string, age: string, gender: string, dob: string, idNumber: string, nationality: string };
    // selectedPhysicianInfo?: { registrationNumber: string, name: string, category: string };
    selectedPhysicianInfo?: { registrationNumber: string, name: string, category: string, physicianSpeciality: string };
    // selectedCaseType?:{ caseType: string};
    selectedCaseType?: { INPATIENT: string; OUTPATIENT: string };


    constructor() {
        super(new AddPrescriptionState());
    }

    changeMode(mode: 'new' | 'followUp') {
        this.setState({ ...this.state, mode: mode });
    }

    setStep(newStep: 1 | 2 | 3 | 4) {
        this.setState({ ...this.state, activeStep: newStep });
    }

    changeStepStatus(step: 1 | 2 | 3 | 4, isValid: boolean) {
        if (step == 1) {
            this.setState({ ...this.state, step1IsValid: isValid });
        } else if (step == 2) {
            this.setState({ ...this.state, step2IsValid: isValid });
        } else if (step == 3) {
            this.setState({ ...this.state, step3IsValid: isValid });
        } else if (step == 4) {
            this.setState({ ...this.state, step4IsValid: isValid });
        }
    }

    updatePrescriptionData(newData: PrescriptionNewRequestModel) {
        this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, ...newData } });
    }

    addDiagnosis(diagnosisCode: string, diagnosisDescription: string) {
        let codes = this.state.prescriptionData.diagnosisCodes;
        if (codes == null) {
            codes = [];
        }
        if (!codes.some(code => code.diagnosisCode == diagnosisCode)) {
            codes.unshift({ diagnosisCode: diagnosisCode, diagnosisDescription: diagnosisDescription, diagnosisType: codes.length == 0 ? 'PRIMARY' : 'SECONDARY' });
            this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, diagnosisCodes: codes } });
            return true;
        } else {
            return false;
        }
    }

    updateDiagnosisType(diagnosisCode: string, diagnosisType: 'PRIMARY' | 'SECONDARY') {
        let codes = this.state.prescriptionData.diagnosisCodes;
        if (codes != null) {
            let index = codes.findIndex(diagnosis => diagnosis.diagnosisCode == diagnosisCode);
            if (index != -1) {
                codes[index].diagnosisType = diagnosisType;
                this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, diagnosisCodes: codes } });
            }
        }
    }


    removeDiagnosis(diagnosisCode: string) {
        let codes = this.state.prescriptionData.diagnosisCodes;
        if (codes != null) {
            let index = codes.findIndex(diagnosis => diagnosis.diagnosisCode == diagnosisCode);
            if (index != -1) {
                codes.splice(index, 1);
                this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, diagnosisCodes: codes } });
            }
        }
    }
    // removeCuatomizationRequest(customizationRequestId: string) {
    //   let codes = this.state.customizationData.customizationRequestId;
    //   if (codes != null) {
    //     let index = codes.findIndex(diagnosis => diagnosis.diagnosisCode == customizationRequestId);
    //     if (index != -1) {
    //       codes.splice(index, 1);
    //       this.setState({ ...this.state, customizationData: { ...this.state.customizationData, customizationRequestId: codes } });
    //     }
    //   }
    // }

    addDrug(drug: DrugsDetails, selectedBasedOnRadio: string) {
        let codes = this.state.prescriptionData.drugList;
        if (codes == null) {
            codes = [];
        }
        if (selectedBasedOnRadio == 'tradeName') {
            if (!codes.some(code => code.drugCode == drug.drugCode)) {
                codes.unshift({
                    drugCode: drug.drugCode,
                    drugName: drug.drugName,
                    useUnitValue: drug.useUnitValue,
                    frequency: drug.frequency,
                    frequencyOthersDescription: drug.frequencyOthersDescription,
                    duration: drug.duration,
                    quantity: drug.quantity,
                    serviceStartDate: drug.serviceStartDate,
                    unitPrice: drug.unitPrice,
                    unitType: drug.unitType,
                    drugListId: drug.drugListId
                    // 
                    // scientificCode:drug.scientificCode,
                    // scientificName:drug.scientificName,
                    // dosageForm:drug.dosageForm,
                    // strengthUnit:drug.strengthUnit,
                    // strength:drug.strength,
                });
                this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, drugList: codes } });
                return true;
            } else {
                return false;
            }
        } else {
            if (!codes.some(code => code.scientificCode == drug.scientificCode)) {
                codes.unshift({
                    roaSuggested: drug.roaSuggested,
                    scientificCode: drug.scientificCode,
                    drugName: drug.scientificName,
                    useUnitValue: drug.useUnitValue,
                    frequency: drug.frequency,
                    frequencyOthersDescription: drug.frequencyOthersDescription,
                    duration: drug.duration,
                    quantity: drug.quantity,
                    serviceStartDate: drug.serviceStartDate,
                    unitPrice: Number(''),
                    unitType: drug.unitType,
                    scientificName: drug.scientificName,
                    dosageForm: drug.dosageForm,
                    strengthUnit: drug.strengthUnit,
                    strength: drug.strength,
                    drugListId: drug.drugListId
                });
                this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, drugList: codes } });
                return true;
            } else {
                return false;
            }
        }

    }


    editDrug(newDrug: DrugsDetails, selectedBasedOnRadio: string) {
        let codes = this.state.prescriptionData.drugList;
        if (codes != null) {

            let index
            if (selectedBasedOnRadio == 'tradeName') {
                index = codes.findIndex(drug => drug.drugCode == newDrug.drugCode || drug.scientificCode == newDrug.drugCode);
            }
            else {
                index = codes.findIndex(drug => drug.scientificCode == newDrug.scientificCode || drug.drugCode == newDrug.scientificCode);
            }
            if (index != -1) {
                codes[index] = newDrug;
                
console.log('New Drug:', newDrug);
console.log('Index:', index);
                this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, drugList: codes } });
                return true;
            }
        }
        return false;
    }
    // editDrug(newDrug: DrugsDetails, selectedBasedOnRadio: string) {
    //   let codes = this.state.prescriptionData.drugList;
    //   if (codes == null) {
    //     codes = [];
    //   }
    //   if(selectedBasedOnRadio == 'tradeName'){
    //   if (!codes.some(code => code.drugCode == newDrug.drugCode)) {
    //     codes.unshift({
    //       drugCode: newDrug.drugCode,
    //       drugName: newDrug.drugName,
    //       useUnitValue: newDrug.useUnitValue,
    //       frequency: newDrug.frequency,
    //       frequencyOthersDescription: newDrug.frequencyOthersDescription,
    //       duration: newDrug.duration,
    //       quantity: newDrug.quantity,
    //       serviceStartDate: newDrug.serviceStartDate,
    //       unitPrice: newDrug.unitPrice,
    //       unitType: newDrug.unitType,

    //     });
    //     this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, drugList: codes } });
    //     return true;
    //   } else {
    //     return false;
    //   }
    //   }else{
    //   if (!codes.some(code => code.scientificCode == newDrug.scientificCode)) {
    //     codes.unshift({
    //       roaSuggested:newDrug.roaSuggested,
    //       scientificCode: newDrug.scientificCode,
    //       drugName: newDrug.scientificName,
    //       useUnitValue: newDrug.useUnitValue,
    //       frequency: newDrug.frequency,
    //       frequencyOthersDescription: newDrug.frequencyOthersDescription,
    //       duration: newDrug.duration,
    //       quantity: newDrug.quantity,
    //       serviceStartDate: newDrug.serviceStartDate,
    //       unitPrice: Number(''),
    //       unitType: newDrug.unitType,
    //       scientificName:newDrug.scientificName,
    //       dosageForm:newDrug.dosageForm,
    //       strengthUnit:newDrug.strengthUnit,
    //       strength:newDrug.strength,
    //       // drugListId : drug.drugListId
    //     });
    //     this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, drugList: codes } });
    //     return true;
    //   } else {
    //     return false;
    //   }
    //   }} 


    removeDrug(drugCode: string) {
        let codes = this.state.prescriptionData.drugList;
        if (codes != null) {
            let index = codes.findIndex(drug => drug.drugCode == drugCode || drug.scientificCode == drugCode);
            if (index != -1) {
                codes.splice(index, 1);
                this.setState({ ...this.state, prescriptionData: { ...this.state.prescriptionData, drugList: codes } });
            }
        }
    }


    reset() {
        this.selectedMemberInfo = undefined;
        this.selectedPhysicianInfo = undefined;
        this.selectedCaseType = undefined;

        this.setState({
            activeStep: 1,
            mode: 'new',
            step1IsValid: false,
            step2IsValid: false,
            step3IsValid: false,
            step4IsValid: false,
            prescriptionData: {},
            customizationData: {}
        });
    }


}



