import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DispensableDrugsResponseModel } from 'src/app/modules/prescription/models/prescription-dispensable-drugs-response.model';
import { PrescriptionService } from '../../service/prescription.service';
import Big, { BigSource } from 'big.js';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { PrescriptionDispenseRequestModel } from '../../models/prescription-dispense-request.model';
import { HttpException } from 'src/app/util/default-http-client';
import { PrescriptionDispenseResponseModel } from '../../models/prescription-dispense-response.model';
import { DispensableDrugsSummaryModel } from '../../../../modules/prescription/models/prescription-dispensable-drugs-summary-response.model';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'dispense-dialog',
  templateUrl: './dispense-dialog.component.html',
  styles: [
  ]
})
export class DispenseDialogComponent implements OnInit {
  dispensableDrugs: ListViewModel<DispensableDrugsResponseModel> = new ListViewModel();
  dispenseRequestModel: PrescriptionDispenseRequestModel = new PrescriptionDispenseRequestModel();
  dispenseResponseModel: PrescriptionDispenseResponseModel = new PrescriptionDispenseResponseModel();
  showEditButton: boolean = false;
  showDeleteButton: boolean = false;
  showCheckBox: boolean = false;
  pageNumber: number = 0;
  recordSize: number = 10;
  grandTotal: Big = new Big(0);
  totalDrugs: number = 0;
  totalQty: Big = new Big(0);
  isLoading: boolean = false;
  drugList: string[] = [];
  partiallyDispenseStr: string = 'PARTIAL_DISPENSE';
  dispensableDrugsSummaryModel: DispensableDrugsSummaryModel = new DispensableDrugsSummaryModel();
  isAllCheckBoxChecked: boolean = false;
  totalQtyMap = new Map();
  grandTotalMap = new Map();

  @Input()
  ePrescriptionReferenceNumber?: any;

  @Input()
  action?: any;

  @Input('onClose')
  onClose?: (data: any) => void;

  @Output('onClose')
  onCloseEmitter: EventEmitter<{ data?: any }> = new EventEmitter();

  listPrototype = DispensableDrugsResponseModel.prototype;

  constructor(
    private prescriptionService: PrescriptionService,
    private dialogService: DialogService,
    private translateService: TranslateService,
    public authService: AuthService,
    private route: Router

  ) {

  }

  ngOnInit(): void {
    if (this.action == this.partiallyDispenseStr) {
      this.showCheckBox = true;
    }
    this.isLoading = true;
    this.fetchTotalQuantityDrugsAndAmount();
    this.fetchData();
  }

  closeDialog(data: any) {
    this.drugList = [];
    this.onCloseEmitter.emit(data);
  }

  fetchData() {
    this.isLoading = true;
    this.prescriptionService.getDrugsToDispense(this.ePrescriptionReferenceNumber, { pageNumber: this.pageNumber, recordSize: this.recordSize }).subscribe(data => {
      this.dispensableDrugs = data;
      this.dispensableDrugs.content.forEach(function (value) {
        const code = value.drugCode;
        const name = value.drugDescription;
        if (undefined != code && undefined != name) {
          value.drug = code + '\n' + name;
        }
      });
      this.isLoading = false;
    });
    this.showEditButton = false;
    this.showDeleteButton = false;
  }

  onDispenseBtnClick(drugListlength:number) {
    let dispenseTitle= "";
    let dispenseSubtitle="";
    this.translateService.get('prescription.dispensePrescriptionSubTitle').subscribe(text=> dispenseSubtitle = text);

    if(drugListlength>0){
      this.translateService.get('prescription.partialDispensePrescriptionTitle',{value:this.ePrescriptionReferenceNumber}).subscribe(text=> dispenseTitle = text);
    } else {
      this.translateService.get('prescription.fullDispensePrescriptionTitle',{value:this.ePrescriptionReferenceNumber}).subscribe(text=> dispenseTitle = text);
    }

    this.dialogService.showConfirmDialog(dispenseTitle,dispenseSubtitle,(data: any) => {
    if (data){ 
        this.isLoading = true;
        this.dispenseRequestModel.ePrescriptionReferenceNumber = this.ePrescriptionReferenceNumber;
        if (this.action == this.partiallyDispenseStr) {
          this.dispenseRequestModel.drugList = this.drugList;
        }
        this.dispenseApicall();
     } else{
        return;
      }
   });

  }
  dispenseApicall() {
    let response = this.prescriptionService.dispensePrescriptions(this.dispenseRequestModel);
    response.subscribe({
      next: (data) => {
        this.dispenseResponseModel = JSON.parse(JSON.stringify(data));
        this.isLoading = false;
        let successMsg = '';
        let referenceNoMsg = '';
        this.translateService.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoMsg = text);

        if (this.dispenseResponseModel.status == "Invalid" || this.dispenseResponseModel.status == "INVALID"){
          console.log("invalid")
          this.dialogService.showErrorDialog(referenceNoMsg + ": " + this.dispenseResponseModel.ePrescriptionReferenceNumber + " " + this.dispenseResponseModel.statusDescription  , (event: any) => { 
            if (event) { this.closeDialog(null) }
           })
        }
       
        else if (this.dispenseResponseModel.status =="Ineligible" || this.dispenseResponseModel.status == "INELIGIBLE"){
          this.translateService.get('prescription.rejectedResponseForIneligible',{value:this.dispenseResponseModel.ePrescriptionReferenceNumber, status:this.dispenseResponseModel.status}).subscribe(text => successMsg = text);
          this.dialogService.showErrorDialog(successMsg, (event: any) => {  
            if (event) { this.route.navigate(["prescription/list"]) } });

        }
        else if (this.dispenseResponseModel.status == "Failed" || this.dispenseResponseModel.status == "FAILED"){
          this.dialogService.showErrorDialog(referenceNoMsg + ": " + this.dispenseResponseModel.ePrescriptionReferenceNumber + " " + this.dispenseResponseModel.statusDescription  , (event: any) => { 
            if (event) { this.closeDialog(null) }
           })

        }
        else if (this.dispenseResponseModel.status == "Rejected" || this.dispenseResponseModel.status == "REJECTED"){
          if(this.dispenseResponseModel.statusDescription == "NO REMAINING LIMIT"){
            this.translateService.get('prescription.rejectedResponseForNoLimit',{value:this.dispenseResponseModel.ePrescriptionReferenceNumber, status:this.dispenseResponseModel.status}).subscribe(text => successMsg = text);
            this.dialogService.showErrorDialog(successMsg, (event: any) => { 
              if (event) { this.closeDialog(null) }
             })
          } 
          else if(this.dispenseResponseModel.statusDescription == "This Provider is not appointed to this Member/Policy"){
            this.translateService.get('prescription.rejectedResponseForIneligible',{value:this.dispenseResponseModel.ePrescriptionReferenceNumber, status:this.dispenseResponseModel.status}).subscribe(text => successMsg = text);
            this.dialogService.showErrorDialog(successMsg, (event: any) => { 
              if (event) { this.closeDialog(null) }
             })
          }
         

        }
        else{
        this.translateService.get('prescription.dispenseSuccess').subscribe(text => successMsg = text);
        this.closeDialog(null);
        this.dialogService.showSuccessDialog(referenceNoMsg + ": " + this.dispenseResponseModel.ePrescriptionReferenceNumber + " " + successMsg, (data: any) => { })
      }
    },
      error: (exception) => {
        if (exception instanceof HttpException) {
          var errorCode = exception.response.error.code || exception.messageCode;
          this.closeDialog(null);
          this.dialogService.showErrorDialog( exception.response.error.statusDescription, (data: any) => { })
        }
        this.isLoading = false;
      }
    });
  }

  onCheckboxChange(event: any, item: any) {
    if (event.isChecked) {
      this.checkBoxCheckedImplementation(item, event);
    } else {
      this.checkBoxNotCheckedImplementation(item, event);
    }
    this.setTotalDetailsForPartiallyDispense();
  }

  checkBoxCheckedImplementation(item: any, event: any) {
    if (event.isHeaderCheckBox) {
      this.setDataForAllCheckBoxBtnChecked();
    } else {
      this.addDrugInDrugList(item.drugCode, item.quantity, item.totalPrice);
    }
  }

  setTotalDetailsForPartiallyDispense() {
    if (this.drugList.length > 0) {
      let qty = new Big(0);
      let totalPrice = new Big(0);
      for (let value of this.totalQtyMap.values()) {
        qty = qty.plus(value);
      }
      for (let value of this.grandTotalMap.values()) {
        totalPrice = totalPrice.plus(value);
      }
      this.totalQty = qty.round(2, Big.roundHalfUp)
      this.grandTotal = totalPrice.round(2, Big.roundHalfUp);
      this.totalDrugs = this.drugList.length;
    } else {
      this.fetchTotalQuantityDrugsAndAmount();
    }
  }

  checkBoxNotCheckedImplementation(item: any, event: any) {
    if (event.isHeaderCheckBox) {
      this.isAllCheckBoxChecked = false;
      this.dispensableDrugs.content.forEach(drugDetail => {
        this.removeDrugInDrugList(drugDetail.drugCode!);
      });
    } else {
      this.removeDrugInDrugList(item.drugCode);
    }
  }

  setDataForAllCheckBoxBtnChecked() {
    this.isAllCheckBoxChecked = true;
    this.authService.hideSystemLoader();
    this.dispensableDrugs.content.forEach(drugDetail => {
      this.addDrugInDrugList(drugDetail.drugCode!, drugDetail.quantity!, drugDetail.totalPrice!);
      this.authService.hideSystemLoader();
    });
  }

  removeDrugInDrugList(drugCode: string) {
    if (this.drugList.includes(drugCode)) {
      this.drugList.splice(this.drugList.indexOf(drugCode), 1);
      this.totalQtyMap.delete(drugCode);
      this.grandTotalMap.delete(drugCode);
    }
  }

  addDrugInDrugList(drugCode: string, qty: Big, totalPrice: Big) {
    if (!this.drugList.includes(drugCode)) {
      this.drugList.push(drugCode);
      this.totalQtyMap.set(drugCode, qty);
      this.grandTotalMap.set(drugCode, totalPrice);
    }
  }

  fetchTotalQuantityDrugsAndAmount() {
    this.prescriptionService.fetchDrugsToDispenseSummary(this.ePrescriptionReferenceNumber).subscribe(data => {
      this.dispensableDrugsSummaryModel = data;
      this.totalDrugs = this.dispensableDrugsSummaryModel.totalDrugs == undefined ? 0 : this.dispensableDrugsSummaryModel.totalDrugs;
      this.grandTotal = this.dispensableDrugsSummaryModel.grandTotal == undefined ? new Big(0) : this.dispensableDrugsSummaryModel.grandTotal;
      this.totalQty = this.dispensableDrugsSummaryModel.totalQuantity == undefined ? new Big(0) : this.dispensableDrugsSummaryModel.totalQuantity;
    });
  }
}
