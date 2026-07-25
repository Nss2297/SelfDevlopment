import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { PrescriptionDetails } from '../../models/prescription-details.model';
import { PrescriptionRequest } from '../../models/prescription-request.model';

import * as moment from 'moment';
import { BehaviorSubject } from 'rxjs';
import { DrugsDetails } from '../../models/prescription-drug.model';
@Component({
  selector: 'add-edit-prescription-drug-dialog',
  templateUrl: './add-edit-prescription-drug-dialog.component.html',
  styles: [
  ]
})

export class AddEditPrescriptionDrugDialogComponent implements OnInit {
  qtyRec: any;
  addDrugRec: any;
  drugLocal: any;
  dataRecForSearch: any;
  dataforunit: any;
  dataRecDrugComp: any;
  frequencyDes: any;
 



  constructor(public prescriptionLovService: PrescriptionlovService, private translateService: TranslateService) { }

  @Input()
  data?: DrugsDetails;
  @Input('onClose')
  onClose?: (data: any) => void;

  @Input('dialogMode')
  dialogMode: 'add' | 'edit' = 'add';

  dataDrug?: DrugsDetails;
  useInitialOption?: { key: string, value: string };
  frequencyInitialOption?: { key: string, value: string };
  durationInitialOption?: { key: string, value: string };
  selectedUse: any = "";
  selectedDuration: any = "";
  selectedFrequency: any = "";
  drugsList: any[] = [];
  useData: any;
  durationData: any;
  serviceDate = moment();
  drugValue: string = '';
  drugErrorMessages: string = '';
  drugDdValue: string = '';
  drugErrorCode: string = '';
  prescriptionRequest: PrescriptionRequest = new PrescriptionRequest();
  unitType: string = '';
  unitPrice: any;
  totalPrice: number = 0;
  quantity!: number;
  isFormReset: boolean = false;
  savedDrugsList: any = [];
  frequencyTypesError: boolean = false;
  unitTypesError: boolean = false;
  quantityError: boolean = false;
  useUnitsError: boolean = false;
  durationError: boolean = false;
  drugError: boolean = false;
  quantityZeroNumError: boolean = false;
  frequencyDescError: boolean = false;
  frequencyData: string = '';
  isFrequencyDescVisible: boolean = false;
  frequencyDesc: string = '';
  initialDataHash?: number;
  savedDrugDataList: any = [];

  drugsList$: BehaviorSubject<{ key: string, value: string }[]> = new BehaviorSubject([{ key: '', value: 'Select' }]);
  serviceCodeInitialOption?: any;


  closeDialog() {
    if (this.onClose) {
      this.onClose(null);
      this.dataRecDrugComp = ''
      this.prescriptionLovService.setData('');
    }


  }
  isLoading: boolean = false;
  prescriptionForm: FormGroup = new FormGroup({
    unitTypes: new FormControl('', { validators: Validators.required }),
    quantity: new FormControl('', { validators: [Validators.required, Validators.pattern('^(?:999|[0-9]{1,3})([.][0-9]{1,250})?$'), Validators.minLength(0), Validators.maxLength(250)] }),
    use: new FormControl('', { validators: [Validators.required, Validators.pattern('^(?:99|[0-9]{1,2})([.][0-9]{1,2})?$'), Validators.minLength(0), Validators.maxLength(5)] }),
    frequencyTypes: new FormControl('', { validators: Validators.required }),
      frequencyDes: new FormControl(),
    duration: new FormControl('', { validators: [Validators.required, Validators.minLength(0), Validators.maxLength(10), Validators.pattern('^[0-9]*$')] }),
    drug: new FormControl('', { validators: Validators.required }),
    serviceDate: new FormControl('', { validators: Validators.required }),
  });



  ngOnInit(): void {
    this.prescriptionLovService.initializeAllLists();
    this.dataRecForSearch = this.prescriptionLovService.getData();
    this.dataRecDrugComp = this.dataRecForSearch;

    if (this.data != undefined) {
      this.prescriptionForm.setValue({
        unitTypes: this.data.unitType || '',
        quantity: this.data.quantity || '',
        use: this.data.useUnitValue || '',
        frequencyTypes: this.data.frequency || '',
        duration: this.data.duration || '',
        drug: this.data.drugCode || '',
        serviceDate: this.data.serviceStartDate || '',
      });


    }

    if (this.dialogMode == "edit") {
      this.drugValue = this.prescriptionForm.value.drug;
      var dataforunit = this.data?.unitPrice;
      this.unitPrice = dataforunit;
      this.qtyRec = this.data?.quantity;
      this.totalPrice = this.unitPrice * this.qtyRec;

    }
    if (this.dataRecForSearch.actualValue) {
      this.drugValue = this.dataRecForSearch.value;
      this.dataforunit = this.dataRecForSearch.actualValue.split("|");
      this.unitPrice = this.dataforunit[0];
      this.qtyRec = this.data?.quantity;
      this.totalPrice = this.unitPrice * this.qtyRec;
    }


    this.useData = this.prescriptionForm.value.use;
    this.frequencyData = this.prescriptionForm.value.frequencyTypes;
    // this.frequencyDesc = this.prescriptionForm.value.frequencyDesc;
      this.frequencyDes = this.prescriptionForm.value.frequencyDes;
    this.durationData = this.prescriptionForm.value.duration;
    this.initialDataHash = this.hashCode(this.prescriptionForm.value);

  }

  getDrugs(value: any) {
    this.drugError = false;
    this.drugsList = [];
    this.unitPrice = '';
    this.totalPrice = 0;
    this.isFormReset = false;
    this.drugErrorMessages = '';
    if (this.drugDdValue != value && this.prescriptionForm.get('drug')?.valid) {
      this.drugValue = value;
      this.drugErrorMessages = this.translateService.instant('drugError');
    } else {
      this.drugErrorMessages = '';
    }
    if (value.length >= 3) {
      // this.prescriptionLovService.getDrugs({ drugValue: value }).subscribe(
      //   data => {
      //     this.drugsList = data;
      //   });
    }
  }

  getValueOfDrug(drug: any) {
    this.drugDdValue = drug
    var data = drug.actualValue.split("|");
    this.drugValue = drug.value;
    this.unitPrice = data[0];
    if (this.quantity !== undefined) {
      this.setTotalPrice(this.quantity);
    }
    this.drugsList = [];
    this.drugErrorMessages = '';
  }
  getFrequencyTypes() {
    this.isFrequencyDescVisible = false;
    this.frequencyTypesError = false;
    this.frequencyDescError = false;
    
    if (this.frequencyData == 'others') {
      this.isFrequencyDescVisible = true;
    }
  }

  getFrequencyDesc(value: string) {
    // this.frequencyDesc = value;
     this.frequencyDes = value;
   
    this.frequencyDescError = false;
    if (value == '') {
      this.frequencyDescError = true;
    }
  }

  getUnitTypes() {
    this.unitTypesError = false;
  }

  getQuantity(value: any) {
    this.quantityZeroNumError = false
    if (!Boolean(Number(value))) {
      // This returns true if value does not contain only zeros and false if value contains only zeros
      this.quantityZeroNumError = true
    }
    this.quantityError = false;
    this.quantity = value;
    this.setTotalPrice(this.quantity);
  }
  getUseUnits() {
    this.useUnitsError = false;
  }

  getDuration() {
    this.durationError = false;
  }

  setTotalPrice(qty: number) {
    this.totalPrice = this.unitPrice * qty;
  }

  saveAddNewDrugButton() {
    this.validationErrorMessages()
    if (this.checkAllFieldsCorrectOrNot()) {
      this.saveDrugData();
      this.isFrequencyDescVisible = false
      this.prescriptionForm.reset();
      this.serviceDate = moment();
      this.isFormReset = true;
    }
  }

  saveDrugButton() {
    this.validationErrorMessages();
    if (this.checkAllFieldsCorrectOrNot()) {
      this.saveDrugData();
        this.frequencyDes = '';
        // this.frequencyDesc = '';
      this.closeDialog();
      this.dataRecForSearch = ''
    }
  }
  saveDrugData() {


    let saveDrugList = this.prescriptionForm.value
    saveDrugList["unitPrice"] = this.unitPrice;
    saveDrugList["serviceDate"] = moment(this.serviceDate).format("DD/MM/yyyy");
      // saveDrugList["frequencyOthersDescription"] = this.frequencyDesc;
      //  saveDrugList["frequencyOthersDescription"] = this.frequencyDes;
    //  saveDrugList["frequencyData"] = this.frequencyData 
    this.savedDrugsList = [saveDrugList];
    let subscription = this.dialogMode == 'add' ? this.prescriptionLovService.addSaveDrug(saveDrugList) : this.prescriptionLovService.editSaveDrug(saveDrugList);
  }

  validationErrorMessages() {
    if (this.prescriptionForm.get('frequencyTypes')?.invalid) {
      this.frequencyTypesError = true;
    }
    if (this.prescriptionForm.get('unitTypes')?.invalid) {
      this.unitTypesError = true;
    }
    if (this.prescriptionForm.controls['quantity'].errors?.['required']) {
      this.quantityError = true;
    }
    if (this.prescriptionForm.controls['use'].errors?.['required']) {
      this.useUnitsError = true;
    }
    if (this.prescriptionForm.controls['duration'].errors?.['required']) {
      this.durationError = true;
    }
    if (this.prescriptionForm.controls['drug'].errors?.['required']) {
      this.drugError = true;
    }

    // if (this.isFrequencyDescVisible && this.frequencyDesc == '') {
    //   this.frequencyDescError = true;
    // }
    if (this.isFrequencyDescVisible && this.frequencyDes == '') {
      this.frequencyDescError = true;
    }
  }

  hashCode(data: any) {
    let dataString = JSON.stringify(data);
    let hash = 0;
    for (let i = 0; i < dataString.length; i++) {
      let code = dataString.charCodeAt(i);
      hash = ((hash << 5) - hash) + code;
      hash = hash & hash; // Convert to 32bit integer
    }
    return hash;
  }

  dataHasChanged() {
    return this.hashCode(this.prescriptionForm.value) != this.initialDataHash;
  }

  fieldErrorMessages(fieldName: any) {
    if (this.prescriptionForm.controls[fieldName].invalid &&
      (this.prescriptionForm.controls[fieldName].dirty)) {
      if (this.prescriptionForm.controls[fieldName].errors?.['required']) {
        return 0;
      } else if (this.prescriptionForm.controls[fieldName].errors?.['pattern']) {
        return 1;
      }
    }
    return -1;
  }

  checkAllFieldsCorrectOrNot() {
    return this.prescriptionForm.valid && this.drugErrorMessages.length == 0 &&
      !this.quantityZeroNumError &&
      (((this.isFrequencyDescVisible && !this.frequencyDescError)) || (!this.isFrequencyDescVisible && !this.frequencyTypesError))
  }
}


