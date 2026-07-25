import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { HttpException } from 'src/app/util/default-http-client';
import { DrugDiagnosisDetails } from '../../models/drug-diagnosis-details.model';
import { DrugToDiagnosisRequest } from '../../models/drug-diagnosis-request.model';
import { CustomizationService } from '../../services/customization-service/customization.service';

@Component({
  selector: 'add-edit-drug-diagnosis-customization-dialog',
  templateUrl: './add-edit-drug-diagnosis-customization-dialog.component.html',
  styles: [
  ]
})
export class AddEditDrugDiagnosisCustomizationDialogComponent implements OnInit {


  constructor(
    public lovService: LovService,
    public customizationService: CustomizationService,
    private authService: AuthService
  ) {
    this.authService.user$.subscribe( data =>{
      this.currentUser = data;
    })
  }
  @Input('dialogMode')
  dialogMode: 'add' | 'edit' = 'add';

  @Input()
  data?: DrugDiagnosisDetails;
  // @Input() data1: any;
  initialDataHash?: number;
  serviceCodeInitialOption?: { key: string, value: string };
  icdCodeInitialOption?: { key: string, value: string };
  payerIdInitialOption?: { key: string, value: string };
  dataControl: DrugToDiagnosisRequest = new DrugToDiagnosisRequest();
  selectedServiceCode: any = "";
  selectedICDCode: any = "";
  selectedRejectionReason: any = "";
  selectedPayersId: any = "";
  currentUser:any;
  isAdminUser:boolean = false;
  getDrugsSubscription?: Subscription;

  @Output('onClose')
  onCloseEmitter: EventEmitter<{ status: 'cancel' } | { status: 'saved', id: string }> = new EventEmitter();

  errorCode: string = '';

  customizationForm: FormGroup = new FormGroup({
    serviceCode: new FormControl('', { validators: Validators.required }),
    icdCode: new FormControl('', { validators: Validators.required }),
    payerId: new FormControl('', { validators: Validators.required }),
    categoryOfApproval: new FormControl('', { validators: Validators.required }),
    rejectionCategory: new FormControl('', { validators: Validators.required }),
    serviceStatus: new FormControl('', { validators: Validators.required }),
    additionalRejectionReason: new FormControl('', { validators: [Validators.minLength(0), Validators.maxLength(500)] }),
    moduleName: new FormControl('', { validators: Validators.required }),
    // overrideIfExists: new FormControl(false)
  });

  ngOnInit(): void {
    // this.lovService.initializeAllLists({ serviceCode: undefined, icdCode: undefined }).subscribe();
    this.isAdminUser = this.currentUser.authorities.findIndex((x:any) => x.authority.includes("PBM_ADMIN")) != -1;

    if(this.dialogMode == "add"){
      if(this.currentUser.authorities.some((data:any)=>data.authority.includes('VIEW_PRESCRIPTION')) && !this.isAdminUser){
        this.customizationForm.get('payerId')?.setValue(this.currentUser.accId);
        this.customizationForm.get('categoryOfApproval')?.setValue("Tawuniya");
        this.customizationForm.get('rejectionCategory')?.setValue("Diagnosis-Indication");
        this.customizationForm.get('moduleName')?.setValue("ALL");
      }else if(this.isAdminUser){
        this.customizationForm.get('payerId')?.setValue(this.currentUser.accId);
        this.customizationForm.get('rejectionCategory')?.setValue("Diagnosis-Indication");
        this.customizationForm.get('moduleName')?.setValue("ALL");
      }    
    }

    if (this.data != undefined) {
      this.customizationForm.setValue({
        serviceCode: this.data.serviceCode || '',
        icdCode: this.data.icdCode || '',
        payerId: this.data.payer || '',
        categoryOfApproval: this.data.categoryOfApproval || '',
        rejectionCategory: this.data.rejectionCategory || '',
        serviceStatus: (this.data.serviceStatus?.includes('Approved') ? 'APPROVED' : 'REJECTED') || '',
        additionalRejectionReason: this.data.rejectionReason || '',
        moduleName: this.data.moduleName || '',
      });

      if(!this.customizationForm.get('payerId')?.value){
        this.customizationForm.get('payerId')?.setValue(this.currentUser.accId);
      }

      this.selectedRejectionReason = this.data.rejectionReason || '';
      if (this.data.serviceCode != undefined) {
        this.serviceCodeInitialOption = { key: this.data.serviceCode, value: this.data.serviceCode };
        this.selectedServiceCode = this.data.serviceCode
      }
      if (this.data.icdCode != undefined) {
        this.icdCodeInitialOption = { key: this.data.icdCode, value: this.data.icdCode };
        this.selectedICDCode = this.data.icdCode;
      }
      if (this.data.payer != undefined) {
        this.payerIdInitialOption = { key: this.data.payer, value: this.data.payer };
        this.selectedPayersId = this.data.payer;
      }
      this.initialDataHash = this.hashCode(this.customizationForm.value);
      console.log("inial", this.initialDataHash)
    }
  
  }

  closeDialog(data: { status: 'cancel' } | { status: 'saved', id: string }) {
    this.onCloseEmitter.emit(data);
  }

 
//   filterDrugs(query: string) {
//     if (query) {
//       if (this.getDrugsSubscription != null) {
//         this.getDrugsSubscription.unsubscribe();
//         this.lovService.drugsList$.next([]);
//       }
//       this.getDrugsSubscription = this.lovService.getDrugsForCustomization({ value: query || '' })
//         .subscribe({
//           next: (value) => this.getDrugsSubscription = undefined,
//           error: (value) => this.getDrugsSubscription = undefined,
//         });
//     } else {
//       this.lovService.drugsList$.next([]);
//     }
//   }

filterDrugs(query: string) {
    this.lovService.getDrugs({ serviceCode: query }).subscribe();
}


  filterDiagnosis(query: string) {
    this.lovService.getDiagnosis({ icdCode: query }).subscribe();
  }
  filterPayers(query: string) {
    this.lovService.getPayers({ payerId: query }).subscribe((data) => {

    });
  }

  saveData() {
    if (this.authService.systemLoaderVisible || this.customizationForm.invalid) {
      return;
    }
    this.authService.showSystemLoader();
    let body = this.customizationForm.value;
    let subscription = this.dialogMode == 'add' ? this.customizationService.addDrugToDiagnosisData(body) : this.customizationService.editDrugToDiagnosisData(this.data?.id || 'null', body);
    subscription.subscribe({
      next: (data) => {
        this.closeDialog({ status: 'saved', id: (this.dialogMode == 'add' ? (data?.id || '') : (this.data?.id || '')) });
        this.authService.hideSystemLoader();
      },
      error: (exception) => {
        if (exception instanceof HttpException) {
          this.errorCode = exception.response.error.message || exception.response.error.errorDescriptions
          ;
        }
        this.authService.hideSystemLoader();
      }
    });
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
    return this.hashCode(this.customizationForm.value) != this.initialDataHash;
  }
}