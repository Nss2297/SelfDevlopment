
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { PrescriptionDetails } from '../../models/prescription-details.model';
import { PrescriptionRequest } from '../../models/prescription-request.model';
import { PrescriptionProviderService } from '../../service/prescription-provider.service';
import * as moment from 'moment';
import { PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { Subscription } from 'rxjs';
import { CancelPrescriptionRequestModel } from '../../../../modules/prescription/models/cancel-prescription-request.model';
import { CancelPrescriptionResponseModel } from '../../../../modules/prescription/models/cancel-prescription-response.model';
import { PrescriptionDetailsDiagnosis } from '../../models/prescription-details-diagnosis.model';
import { PrescriptionValidationsModel } from '../../models/prescription-details-drugs-rejection.model';
import { PrescriptionDetailsDrugs } from '../../models/prescription-details-drugs.model';
import { PrescriptionDetailsPayerMemberPhysicianInfoModel } from '../../models/prescription-details-payer-member-physician-info.model';
import { PrescriptionService } from '../../service/prescription.service';
import { HttpException } from 'src/app/util/default-http-client';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';



@Component({
  selector: 'app-inquiry-listing',
  templateUrl: './inquiry-listing.component.html'

})
export class InquiryListingComponent implements OnInit, OnDestroy {

  data: ListViewModel<PrescriptionDetails> = new ListViewModel();
  dataControl: PrescriptionRequest = new PrescriptionRequest();
  drugData: ListViewModel<PrescriptionDetailsDrugs> = new ListViewModel();
  // drugListPrototype = PrescriptionDetailsDrugs.prototype;

  diagnosisData: ListViewModel<PrescriptionDetailsDiagnosis> = new ListViewModel();
  // diagnosisListPrototype = PrescriptionDetailsDiagnosis.prototype;
  payerMemberPhysicianInfoData: PrescriptionDetailsPayerMemberPhysicianInfoModel = new PrescriptionDetailsPayerMemberPhysicianInfoModel();

  drugRejectionData: PrescriptionValidationsModel[] = [new PrescriptionValidationsModel];
  listIsLoading: boolean = false;
  showEditButton: boolean = false;
  showDeleteButton: boolean = false;
  showMoreActionsMenu: boolean = true;
  moreActionsList: { action: string, displayCode: string, isVisible: (item: PrescriptionDetails) => boolean, allowedAuthorities?: string[] }[] = [
    { action: 'CANCEL', displayCode: 'CANCEL', isVisible: (item) => !['DISPENSED', 'PARTIAL_DISPENSED', 'CANCELLED', 'REJECTED', 'INVALID', 'FAILED', 'PENDING'].includes((item.status || '').toUpperCase()), allowedAuthorities: ['PBM_PRESCRIPTION', 'PRESCRIPTION_SUBMISSION', 'PRESCRIPTION_CANCELLATION'] },
    { action: 'EDIT', displayCode: 'EDIT', isVisible: (item) => ['REJECTED', 'APPROVED','PARTIAL_APPROVED',].includes((item.status || '')), allowedAuthorities: ['PBM_PRESCRIPTION', 'PRESCRIPTION_SUBMISSION', 'FOLLOW_UP_PRESCRIPTION'] },
    {
      action: 'FULL_DISPENSE', displayCode: 'FULL-DISPENSE', isVisible: (item) => ['APPROVED', 'PARTIAL_APPROVED', 'PARTIAL_DISPENSED'].includes((item.status || '')), allowedAuthorities: ['PRESCRIPTION_DISPENSE']
    },
    {
      action: 'PARTIAL_DISPENSE', displayCode: 'PARTIAL-DISPENSE', isVisible: (item) => ['APPROVED', 'PARTIAL_APPROVED', 'PARTIAL_DISPENSED'].includes((item.status || '')), allowedAuthorities: ['PRESCRIPTION_DISPENSE']
    },
  ]
  dispensePrescriptionDialogOpen = false;
  ePrescriptionReferenceNumber?: string;
  action?: string;
  //filer arrays
  referenceNoList: any[] = []
  values: any;
  statusValue: any[] = []
  membersList: any[] = []

  status: any[] = []
  // data:any[]=[]
  dateArrival1: any
  dateArrival2: any
  startdate = moment();
  enddate = moment();
  dateErrorMessage: string = ''
  cancelPrescriptionRequestModel: CancelPrescriptionRequestModel = new CancelPrescriptionRequestModel();
  cancelPrescriptionResponseModel: CancelPrescriptionResponseModel = new CancelPrescriptionResponseModel();
  errorCode: string = '';
  refNo!: number;
  memberN!: string;
   returnUrl?:string;
  noContentMessage?:string;
  noContentSubtitle?:string;
  noContentIsSearchAgain = false;
  isMemberPhysicianInfoAvailable?:boolean;
  currentUser:any;
  inquiryData:any;
  isMemberIdError:boolean = false;
  memberIdErrorMsg:string = "";


  // approvalStatus:string="APPROVAL"
  approvalStatus: any = [
    { key: "APPROVED", value: "APPROVED" },
    { key: "REJECTED", value: "REJECTED" },
    { key: "CANCELLED", value: "CANCELLED" },
    { key: "DISPENSED", value: "DISPENSED" },
    { key: "PARTIALLY DISPENSED", value: "PARTIALLY DISPENSED" }]
  insurance: any = [
    { key: "TAWUNIYA", value: "TAWUNIYA" }
  ]

  // insurance: string = "Tawuniya"
  subscription: Subscription[] = [];
  // insurance: string = "Tawuniya"
  startDate: any = new Date()
  endDate: any = new Date()

  isFilterDrawerOpen: boolean = false;
  filterForm: FormGroup = new FormGroup({
    referenceNo: new FormControl(),
    dateAndTime: new FormControl(),
    startdate: new FormControl(),
    enddate: new FormControl(),
    memberId: new FormControl(),
    memberName: new FormControl(),

    // policyNumber: new FormControl(),
    insurance: new FormControl(),
    status: new FormControl()
  })
  listPrototype = PrescriptionDetails.prototype;


  constructor(
    private translate: TranslateService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private prescriptionProviderService: PrescriptionProviderService,
    public prescriptionLovService: PrescriptionlovService,
    public prescriptionService: PrescriptionService,
    public dialogService: DialogService,
    public authService: AuthService 
  ) { 
    { 
      this.authService.user$.subscribe( data =>{
        this.currentUser = data;
      })
    }
  }


  

  ngOnInit(): void {

    this.prescriptionLovService.initializeAllLists();
    this.listIsLoading = true;
    this.prescriptionProviderService.prescription$.subscribe(data => {
      if(!data){
        this.noContentIsSearchAgain = true;
        this.data = new ListViewModel();
      }else{
        this.data = data;
        if(this.data.content.length == 0){
          this.noContentIsSearchAgain = true;
        }else{
          this.noContentIsSearchAgain = false;
        }
      }
      this.inquiryData = this.data.content.forEach(element=>{
        element.status

      })
      this.data.content.forEach(element => {
        this.listIsLoading = false;
        if (element.dateAndTime) {
          let date = new Date(element.dateAndTime);
          element.dateAndTime = moment(date).format("MM/DD/YYYY hh:mm a")
        }
      });
      // this.transformServiceStatusToTranslationCode();
      this.listIsLoading = false;
      this.showEditButton = false;
      this.showDeleteButton = false;
      this.showMoreActionsMenu = true;
      this.dateArrival1 = new Date();
      this.dateArrival2 = new Date();

      // this.subscriptions.push(this.prescriptionService.prescriptionDetailsPayerMemberPhysicianInfo$.subscribe(data => {
        
        // if(Object.keys(this.data).length > 0){
        //   this.noContentIsSearchAgain = false; 
        //   this.noContentSubtitle = "";
        //   if(this.currentUser.authorities.some((data:any)=>data.authority.includes("PRESCRIPTION_INQUIRY")) && (this.inquiryData === "DISPENSED" || this.inquiryData === "CANCELLED" || this.inquiryData === "REJECTED")){
        //     let message ="";
        //     this.translate.get('prescription.searchAgainMessage',{referencenumber:this.ePrescriptionReferenceNumber, status:this.inquiryData}).subscribe(text=> message = text);
        //     this.isMemberPhysicianInfoAvailable = false;
        //     this.noContentMessage = message;          
        //   }else{
        //     this.isMemberPhysicianInfoAvailable = true;
        //   }
        // }
        // else{
        //   let searchAgainMessage = "";
        //   this.translate.get('prescription.searchAgainSubtitle',{referencenumber:this.ePrescriptionReferenceNumber}).subscribe(text=> searchAgainMessage = text);
        //   this.noContentIsSearchAgain = true;
        //   this.noContentSubtitle = searchAgainMessage;
        // }
        // this.listIsLoading = false;
      


    });
    const params = this.activatedRoute.snapshot.queryParams;
    this.dataControl.referenceNo = params['referenceNo'] || '';
    this.dataControl.enddate = params['enddate'] || '';
    this.dataControl.startdate = params['startdate'] || '';
    this.dataControl.dateAndTime = params['dataAndTime'] || '';
    this.dataControl.memberId = params['memberId'] || '';
    this.dataControl.memberName = params['memberName'] || '';
    this.dataControl.policyNumber = params['policyNumber'] || '';
    this.dataControl.status = params['status'] || '';
    this.dataControl.idNumber = params['idNumber'] || '';
    this.dataControl.insurance = params['insurance'] || '';
    this.dataControl.pageNumber = Number.parseInt(params['pageNumber'] || 0);
    this.dataControl.recordSize = Number.parseInt(params['recordSize'] || 10);
    // this.listIsLoading = true;
    // this.fetchData();
    // console.log("fetched data",this.fetchData())
  }

  // transformServiceStatusToTranslationCode(){
  //   this.data.content = this.data.content
  //       .map(service => ({
  //         ...service,
  //         status: service.status?.toLowerCase() == 'approved' ?
  //           'prescription.approved' :
  //           (service.status?.toLowerCase() == 'rejected' ?
  //             'prescription.rejected' : service.status)
  //       }))


  // }


  fetchData() {
     this.listIsLoading = true;
    this.prescriptionProviderService.getPrescriptions(this.dataControl).subscribe();
    if (this.errorCode === '') {
      this.isFilterDrawerOpen = false;
    }
    else {
      this.isFilterDrawerOpen = true;
    }
    this.showEditButton = false;
    this.showDeleteButton = false;
    this.showMoreActionsMenu = true;
  //  this. updateQueryParams() 
  }
  // updateQueryParams() {
  //   const queryParams = new HttpParams({
  //     fromObject: {
  //       'referenceNo': this.dataControl.referenceNo || '',
  //       'dateAndTime': this.dataControl.dateAndTime || '',
  //       'memberId': this.dataControl.memberId || '',
  //       'memberName': this.dataControl.memberName || '',
  //       'policyNumber': this.dataControl.policyNumber || '',
  //       'status': this.dataControl.status || '',
  //       'idNumber': this.dataControl.idNumber || '',
  //       'insurance': this.dataControl.insurance || '',
  //       'providerId': this.dataControl.provider || '',
  //       'pageNumber': this.dataControl.pageNumber || '',
  //       'recordSize': this.dataControl.recordSize || '',
  //       'startdate': this.dataControl.startdate || '',
  //       'enddate': this.dataControl.enddate || ''
  //     }
  //   });
  //   const params = queryParams.keys()
  //     .filter(key => queryParams.get(key) != null && queryParams.get(key) != '')
  //     .filter(key => key != 'pageNumber' || queryParams.get(key) != '0')
  //     .filter(key => key != 'recordSize' || queryParams.get(key) != '10')
  //     .reduce((acc: { [k: string]: any }, key) => {
  //       acc[key] = queryParams.get(key);
  //       return acc;
  //     }, {});
  //   this.router.navigate([''], { replaceUrl: true, queryParams: params });
  // }

  
  getExtraClassesForListView() {
    return this.data.content.map((item, index) => {
      
      if (item.status?.toLowerCase().includes("approved")) {
        return { [index + ':4']: 'text-success dark:text-success-300 body-2-medium' }
      } else if (item.status?.toLowerCase().includes("rejected")) {
        return { [index + ':4']: 'text-error dark:text-error-400 body-2-medium' }
      } else if (item.status?.toLowerCase().includes("invalid")) {
        return { [index + ':4']: 'text-error dark:text-error-400 body-2-medium' }
      } else if (item.status?.toLowerCase().includes("dispensed")) {
        return { [index + ':4']: 'text-success dark:text-error-400 body-2-medium' }
      } else if (item.status?.toLowerCase().includes("cancelled")) {
        return { [index + ':4']: 'text-error dark:text-error-400 body-2-medium' }
      } else if (item.status?.toLowerCase().includes("pending")) {
        return { [index + ':4']: 'text-warning dark:text-error-400 body-2-medium' }
      } else {
        return { [index + ':4']: 'text-text' }
      }
    }).reduce((row1, row2) => ({ ...row1, ...row2 }), {});
  }

  onMoreActionsMenuItemClick(action: string, id: any) {
    this.ePrescriptionReferenceNumber = id;
    if (action == 'FULL_DISPENSE' || action == 'PARTIAL_DISPENSE') {
      this.action = action;
      this.openDispensePrescriptionDialog();
    } else if (action == 'CANCEL') {
      let canceltitle = "";
      let cancelSubtitle = "";
      this.translate.get('prescription.cancelPrescriptionTitle', { value: this.ePrescriptionReferenceNumber }).subscribe(text => canceltitle = text);
      this.translate.get('prescription.cancelPrescriptionSubTitle').subscribe(text => cancelSubtitle = text);

      this.dialogService.showConfirmDialog(canceltitle, cancelSubtitle, (data: any) => {
        if (data) {
          this.listIsLoading = true;
          this.setValueInCancelPrescriptionRequestModel(this.ePrescriptionReferenceNumber, this.payerMemberPhysicianInfoData.payerId);
          this.prescriptionService.cancelPrescriptionData(this.cancelPrescriptionRequestModel.ePrescriptionReferenceNumber).subscribe({
            next: (data) => {
              const prescriptionResponseModel = JSON.parse(JSON.stringify(data));
              let successMsg = '';
              let referenceNoMsg = '';
              let referenceNoText = "";
              this.translate.get('REFERENCENO').subscribe(text => referenceNoText = text);

              if (prescriptionResponseModel.status == "Invalid" || prescriptionResponseModel.status == "INVALID") {
                this.dialogService.showErrorDialog(referenceNoText + prescriptionResponseModel.ePrescriptionReferenceNumber + " " + prescriptionResponseModel.statusDescription, (event: any) => {
                  this.fetchData();
                })
              }
              else if (prescriptionResponseModel.status == "Ineligible" || prescriptionResponseModel.status == "INELIGIBLE") {
                this.dialogService.showErrorDialog(referenceNoText + prescriptionResponseModel.ePrescriptionReferenceNumber + " " + prescriptionResponseModel.statusDescription, (event: any) => {
                  this.fetchData();
                });
              }
              else if (prescriptionResponseModel.status == "Failed" || prescriptionResponseModel.status == "FAILED") {
                let referenceNoText = "";
                this.translate.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoText = text);
                this.dialogService.showErrorDialog(`${referenceNoText} ${prescriptionResponseModel.ePrescriptionReferenceNumber} ${prescriptionResponseModel.statusDescription}`, (event: any) => {
                  this.fetchData();
                })
              } else {

                this.translate.get('prescription.cancelSuccess').subscribe(text => successMsg = text);
                this.translate.get('prescription.prescriptionReferenceNo').subscribe(text => referenceNoMsg = text);
                this.setValueInCancelPrescriptionResponseModel(data);
                this.dialogService.showSuccessDialog(referenceNoMsg + ": " + this.cancelPrescriptionResponseModel.ePrescriptionReferenceNumber + " " + successMsg, (data: any) => { })
                this.fetchData();
                this.listIsLoading = false;
              }
            },
            error: (exception) => {
              if (exception instanceof HttpException) {
                console.log(exception);
                this.dialogService.showErrorDialog(exception.response.error.statusDescription, (data: any) => { })
              }
              this.listIsLoading = false;
            }
          });
        } else {
          return;
        }
      })

    } else if (action == 'EDIT') {
      this.router.navigate(["prescription", this.ePrescriptionReferenceNumber, 'followUp']);
    }
  }
  closeDispensePrescriptionDialog() {
    this.dispensePrescriptionDialogOpen = false;
    this.fetchData();
  }

  openDispensePrescriptionDialog() {
    this.dispensePrescriptionDialogOpen = true;
  }

  applyFilter() {
    this.isMemberIdError = false;
    this.memberIdErrorMsg = "";
    let referenceNoValue = this.filterForm.controls['referenceNo'].value;
    const trimmedReferenceNoValue = referenceNoValue?.replace(/^\s+|\s+$/g, '');
    this.refNo = trimmedReferenceNoValue;
    let memberName = this.filterForm.controls['memberName'].value;
    let trimmedMemberName = memberName?.replace(/^\s+|\s+$/g, '');
    this.memberN = trimmedMemberName;

    if(memberName){
      if(!isNaN(memberName)){
        this.dataControl.idNumber = memberName;
      }else{
        let memberNameWithoutSpace = memberName?.replace(/ /g, "");
        if(!isNaN(memberNameWithoutSpace)){
          this.isMemberIdError = true;
          this.translate.get("prescription.idNumberErrorInFilter").subscribe((msg:any)=>{
            this.memberIdErrorMsg = msg;
          });
          
          return;
        }else{
          const words = trimmedMemberName?.split(' ');
          this.dataControl.memberName = words.map((word:any) => { 
            return word[0]?.toUpperCase() + word.substring(1); 
          }).join(" ");
        }
      }
    }

    this.errorCode = '';
    this.dataControl.referenceNo = trimmedReferenceNoValue
    this.dataControl.enddate = moment(this.filterForm.controls['enddate'].value).format("DD-MM-yyyy");
    this.dataControl.startdate = moment(this.filterForm.controls['startdate'].value).format("DD-MM-yyyy");
    this.dataControl.insurance = this.filterForm.controls['insurance'].value;
    this.dataControl.status = this.filterForm.controls['status'].value;
    if ((this.dataControl.startdate > this.dataControl.enddate)) {
      this.translate.get('prescription.startDateGreaterThanEndDate').subscribe(text => this.errorCode = text);
    }
    if ((this.dataControl.startdate === "Invalid date" && this.dataControl.enddate !== "Invalid date") ||
      (this.dataControl.startdate !== "Invalid date" && this.dataControl.enddate === "Invalid date")) {
      this.translate.get('prescription.missigDatePairs').subscribe(text => this.errorCode = text);
    }
    this.dataControl.pageNumber = 0;
    if (this.errorCode === '') { this.fetchData(); }
  }

  resetFilter() {
    this.errorCode = '';
    this.dataControl.referenceNo = undefined;
    this.dataControl.startdate = undefined;
    this.dataControl.dateAndTime = undefined;
    this.dataControl.memberId = undefined;
    this.dataControl.memberName = undefined;
    this.dataControl.idNumber = undefined;

    this.dataControl.enddate = undefined;
    this.dataControl.status = undefined;
    this.dataControl.insurance = undefined;
    this.filterForm.setValue({
      'referenceNo': null,
      'startdate': null,
      'enddate': null,
      'memberName': null,
      'memberId': null,

      'status': null,
      'insurance': null,
      'dateAndTime': null
    });
  }

  ngOnDestroy() {
    this.prescriptionProviderService.prescription$.next(new ListViewModel<PrescriptionDetails>);
    this.subscription.forEach(input => input.unsubscribe());
}
  setValueInCancelPrescriptionRequestModel(ePrescriptionReferenceNumber: any, payerId: any) {
    this.cancelPrescriptionRequestModel!.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
    this.cancelPrescriptionRequestModel!.payerId = payerId;
  }

  setValueInCancelPrescriptionResponseModel(responseData: any) {
    let responseModel = <CancelPrescriptionResponseModel>responseData;
    this.cancelPrescriptionResponseModel!.canCancel = responseModel.canCancel;
    this.cancelPrescriptionResponseModel!.canFollowUp = responseModel.canFollowUp;
    this.cancelPrescriptionResponseModel!.ePrescriptionReferenceNumber = responseModel.ePrescriptionReferenceNumber;
    this.cancelPrescriptionResponseModel!.httpStatusCode = responseModel.httpStatusCode;
    this.cancelPrescriptionResponseModel!.status = responseModel.status;
    this.cancelPrescriptionResponseModel!.statusDescription = responseModel.statusDescription;
  }

  openViewDrugDiagnosisDialog(ePrescriptionReferenceNumber: string) {
    this.prescriptionService.setReturnUrl("prescription/inquiryList");
    this.router.navigate(["prescription/details", ePrescriptionReferenceNumber])
  }
  onBackClick = () => {
    this.router.navigateByUrl('/prescription/inquire');
  }
}



