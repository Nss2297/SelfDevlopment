import { OnInit,Component } from '@angular/core';
// import { Component } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { PrescriptionDetails } from '../../models/prescription-details.model';
import { PrescriptionRequest } from '../../models/prescription-request.model';
import { PrescriptionService } from '../../service/prescription.service';
import { PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { PrescriptionProviderService } from '../../service/prescription-provider.service';
import { DatePipe } from '@angular/common';
import * as moment from 'moment';

@Component({
  selector: 'app-inquire',
  templateUrl: './inquire-prescription.component.html',
  styles: [
  ]
})
export class InquirePrescriptionComponent implements OnInit {
 disabled:boolean = false
  inquireBy = 'ref-no';
//  inquireBy:any;  
 data: ListViewModel<PrescriptionDetails> = new ListViewModel();
 refRequiredMsg:string=''
 refErrorMessages:string=''
 refErrorCode:string=''
 memberRequiredMsg:string=''
 memberErrorMessages:string=''
 memberErrorCode:string=''
policyNoRequiredMsg:string=''
 policyNoErrorMessages:string=''
 policyNoErrorCode:string=''
 idRequiredMsg:string=''
 idErrorMessages:string=''
 idErrorCode:string=''
 
  
 dataControl: PrescriptionRequest = new PrescriptionRequest();
  listIsLoading: boolean = false;
  showEditButton: boolean = false;
  showDeleteButton: boolean = false;
  isFilterDrawerOpen: boolean = false;
  referenceNoList: any[] = []
  values:any;
  statusValue:any[]=[]
  membersList:any
  selectedOption:any;
  refNo!: number;
  dateArrival:any
  startdate = moment(); 
  enddate = moment();

  searchForm: FormGroup = new FormGroup({
    referenceNo: new FormControl(),
    dateAndTime: new FormControl(),
    memberId: new FormControl(),
    policyNumber: new FormControl(),
    idNumber: new FormControl()

  })
  translateService: any;
  

  constructor(
    private dialogService: DialogService,
    private translate: TranslateService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    public prescriptionService: PrescriptionService,
    public prescriptionLovService: PrescriptionlovService,
    public prescriptionProviderService: PrescriptionProviderService,
   
  ) { }

  ngOnInit(): void {

    const params = this.activatedRoute.snapshot.queryParams;
    this.dataControl.referenceNo = params['referenceNo'] || '';
    this.dataControl.enddate = params['enddate'] || '';
    this.dataControl.startdate = params['startdate'] || '';
    this.dataControl.dateAndTime = params['dataAndTime']
    this.dataControl.memberId = params['memberId'] || '';
    this.dataControl.memberName = params['memberName'] || '';
    this.dataControl.policyNumber = params['policyNumber'] || '';
    this.dataControl.status = params['status'] || '';
    this.dataControl.idNumber = params['idNumber'] || '';
    this.dataControl.insurance = params['insurance'] || '';
    this.dataControl.pageNumber = Number.parseInt(params['pageNumber'] || 0);
    this.dataControl.recordSize = Number.parseInt(params['recordSize'] || 10);
    // this.fetchData()
  }
  fetchData() {
    // this.listIsLoading = true;
    this.prescriptionProviderService.getPrescriptions(this.dataControl).subscribe(data => {
      this.values = data
    })
    this.isFilterDrawerOpen = false;
    this.listIsLoading = false;
    this.showEditButton = false;
    this.showDeleteButton = false;
   
 }
  
  applyFilter() {
    this.prescriptionService.setReturnUrl("prescription/inquire")
    let referenceNoValue = this.searchForm.controls['referenceNo'].value;
    const trimmedReferenceNoValue = referenceNoValue?.replace(/^\s+|\s+$/g, '');
    this.refNo = trimmedReferenceNoValue;
    this.dataControl.referenceNo = trimmedReferenceNoValue
  this.dataControl.dateAndTime = this.searchForm.controls['dateAndTime'].value; 
    this.dataControl.memberId = this.searchForm.controls['memberId'].value;
    this.dataControl.policyNumber = this.searchForm.controls['policyNumber'].value;
    this.dataControl.idNumber = this.searchForm.controls['idNumber'].value;
    
     this.fetchData();
     
    if(this.inquireBy=='ref-no'){
      this.refErrorCode='';
      this.refErrorMessages='';
      this.refRequiredMsg='';
      if(this.dataControl.referenceNo===null){      
          this.refRequiredMsg = this.translate.instant('prescription.referenceRequried');     
        return ;
      }
    
      else{
      // this.router.navigate(["prescription/inquiryList"])
      this.router.navigate(["prescription/details", this.dataControl.referenceNo]);
     }      
     }
     if(this.inquireBy=='member-id'){
      this.memberErrorCode='';
      this.memberErrorMessages='';
      this.memberRequiredMsg='';
      this.policyNoRequiredMsg='';
      this.policyNoErrorMessages='';
      this.policyNoErrorCode='';

      // if((this.dataControl.memberId===null && this.dataControl.policyNumber===null ) ||(this.dataControl.memberId===null || this.dataControl.policyNumber===null ) ){
        
      //     this.memberRequiredMsg = this.translate.instant('prescription.memberRequried');
        
      //   return ;
      //  } 
      if((this.dataControl.memberId===null )){
        
          this.memberRequiredMsg = this.translate.instant('prescription.memberIdRequired');
        
        return ;
       } 
      if((this.dataControl.policyNumber===null ) ){
        
          this.policyNoRequiredMsg = this.translate.instant('prescription.policyNoRequired');
        
        return ;
       } 
       
        else{
      this.router.navigate(["prescription/inquiryList"])
      }     
     }
     if(this.inquireBy=='national-id'){
      this.idErrorCode='';
      this.idErrorMessages='';
      this.idRequiredMsg='';
      // const nationalIdNo= this.dataControl.idNumber='';
     if (this.dataControl.idNumber === null || this.dataControl.idNumber?.length! == 0){  
          this.idRequiredMsg = this.translate.instant('prescription.idRequried');     
        return ;
      } else if(this.dataControl.idNumber?.length! < 10){
        this.idErrorCode =this.translate.instant('prescription.idError');
        return;
      } else if(!Number(this.dataControl.idNumber)){
        this.idErrorCode =this.translate.instant('prescription.idError');
        return;
      }
      
    //  if (nationalIdNo.length>10){  
    //       this.idRequiredMsg = this.translate.instant('prescription.idRequried');     
    //     return ;
    //   }
      
    else{
    
      this.router.navigate(["prescription/inquiryList"])
      }  

     }
    }
   
      

    openViewDrugDiagnosisDialog(ePrescriptionReferenceNumber: string) {
      this.router.navigate(["prescription/details", ePrescriptionReferenceNumber])        
  }
  
}
