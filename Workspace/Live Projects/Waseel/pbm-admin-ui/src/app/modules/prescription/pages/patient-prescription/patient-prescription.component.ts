import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { CancelPrescriptionRequestModel } from '../../models/cancel-prescription-request.model';
import { CancelPrescriptionResponseModel } from '../../models/cancel-prescription-response.model';
import { PrescriptionDetailsDiagnosis } from '../../models/prescription-details-diagnosis.model';
import { PrescriptionValidationsModel } from '../../models/prescription-details-drugs-rejection.model';
import { PrescriptionDetailsDrugs } from '../../models/prescription-details-drugs.model';
import { PrescriptionDetailsPayerMemberPhysicianInfoModel } from '../../models/prescription-details-payer-member-physician-info.model';
import { PrescriptionDetails } from '../../models/prescription-details.model';
import { PrescriptionRequest } from '../../models/prescription-request.model';
import { PrescriptionService } from '../../service/prescription.service';
import { DispensedDetails } from '../../models/dispensed-details.model';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import * as moment from 'moment';
import { HttpException } from 'src/app/util/default-http-client';
import { Credentials } from 'src/app/modules/authentication/models/credentials.model';
import { User } from 'src/app/modules/authentication/models/user.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { DefaultHttpClient } from 'src/app/util/default-http-client';

@Component({
  selector: 'app-patient-prescription',
  templateUrl: './patient-prescription.component.html',
  styles: [
  ]
})
export class PatientPrescriptionComponent implements  OnInit {
  // errorCode: string;
  isLoading$: any;


  getYear() {
    return new Date().getFullYear();
  }
  constructor(
    private prescriptionService: PrescriptionService,
    private activeRouter: ActivatedRoute, private router: Router,
    private translate: TranslateService,
    private dialogService: DialogService,
    private authService: AuthService,
    protected httpClient: HttpClient
  ) { }

  data: ListViewModel<PrescriptionDetails> = new ListViewModel();
  drugData: ListViewModel<PrescriptionDetailsDrugs> = new ListViewModel();
 
  updatedDrugData: ListViewModel<PrescriptionDetailsDrugs> = new ListViewModel();
  drugListPrototype = PrescriptionDetailsDrugs.prototype;
  drugsDataControl: PrescriptionRequest = new PrescriptionRequest();
  diagnosisDataControl: PrescriptionRequest = new PrescriptionRequest();
  dispenseDataControl: PrescriptionRequest = new PrescriptionRequest();

  diagnosisData: ListViewModel<PrescriptionDetailsDiagnosis> = new ListViewModel();
  diagnosisListPrototype = PrescriptionDetailsDiagnosis.prototype;
  payerMemberPhysicianInfoData: PrescriptionDetailsPayerMemberPhysicianInfoModel = new PrescriptionDetailsPayerMemberPhysicianInfoModel();
  ePrescriptionReferenceNumber: any;
  drugRejectionData: PrescriptionValidationsModel[] = [new PrescriptionValidationsModel];
  action?: string;
  patientAccess_token:any
  // dispensedPatientDetails:DispensedDetails = new DispensedDetails();
  dispensedDetails: ListViewModel<DispensedDetails> = new ListViewModel();
  dispensedListPrototype= DispensedDetails.prototype;
  // patientCredentials: Credentials = new Credentials()
  // patientUser: User = new User()
  currentUser: any


  ngOnInit(): void {
    this.drugsDataControl.recordSize = 5;
    this.diagnosisDataControl.recordSize = 5;
    
    const access_token = this.activeRouter.snapshot.queryParamMap.get('access_token')
    this.patientAccess_token = access_token
   this.authService.credentials$.next({access_token:this.patientAccess_token,token_type:"Bearer"})
  
    this.authService.user$.subscribe( data =>{
      this.currentUser = data
      this.ePrescriptionReferenceNumber = this.currentUser.authorities[0].authority.split('|')[1]
      
      console.log("ePrescriptionNumber",this.ePrescriptionReferenceNumber)
      this.prescriptionService.getPrescriptionDetailsDrugs(this.ePrescriptionReferenceNumber,this.drugsDataControl.pageNumber, this.drugsDataControl.recordSize).subscribe();
      this.prescriptionService.getPrescriptionDetailsDiagnosis(this.ePrescriptionReferenceNumber,this.diagnosisDataControl.pageNumber, this.diagnosisDataControl.recordSize).subscribe();
      this.prescriptionService.getPayerMemberPhysicianInfo(this.ePrescriptionReferenceNumber).subscribe();
      this.prescriptionService.fetchDispenseDetails(this.ePrescriptionReferenceNumber).subscribe();
     
      
     })
    
    
    this.prescriptionService.prescriptionDetailsDrugs$.subscribe(data => {
      this.drugData = data;
      console.log("drug data", this.drugData)
      

    });
    this.prescriptionService.prescriptionDetailsDiagnosis$.subscribe(data => {
      this.diagnosisData = data;

      console.log("diagnosedData",this.diagnosisData)
      
    });
    this.prescriptionService.prescriptionDetailsPayerMemberPhysicianInfo$.subscribe(data => {
      this.payerMemberPhysicianInfoData = data;
      console.log("payer member",this.payerMemberPhysicianInfoData)
      
    });
    
    this.prescriptionService.dispensedDetails$.subscribe(data => {
      this.dispensedDetails = data;  
      this.dispensedDetails.content.forEach(element => {
          // this.listIsLoading = false;
          if (element.dispenseDate) {
            let date = new Date(element.dispenseDate);
            element.dispenseDate = moment(date).format("MM/DD/YYYY hh:mm a")
          }
        });
     
      
      console.log("dispensedData",this.dispensedDetails)
      
      this.listIsLoading = false;
      
    });
    // this.fetchData();
    // console.log("feteched data", this.fetchData)
    
  }
  listIsLoading: boolean = false;

  
  fetchData() {
    // this.listIsLoading = true;
    // this.prescriptionService.getPrescriptionDetailsDrugs(ePrescriptionReferenceNumber).subscribe();
    // this.prescriptionService.getPrescriptionDetailsDiagnosis(ePrescriptionReferenceNumber).subscribe();
    // this.prescriptionService.getPayerMemberPhysicianInfo(ePrescriptionReferenceNumber).subscribe();
    // this.prescriptionService.fetchDispenseDetails(ePrescriptionReferenceNumber).subscribe();
  }
  showItemPerPageDropdown(totalRecords:number):boolean{
    return totalRecords >= 5;
  }

  drugToAgeList: PrescriptionValidationsModel[] = [];
  drugToDiseaseContraIndicationsList: PrescriptionValidationsModel[] = [];
  drugToDiseaseIndicationsList: PrescriptionValidationsModel[] = [];
  drugToGenderList: PrescriptionValidationsModel[] = [];
  duplicateTherapyList: PrescriptionValidationsModel[] = [];
  quantityLimitCheckList: PrescriptionValidationsModel[] = [];
  refillToSoonList: PrescriptionValidationsModel[] = [];
  drugToDrugInteractionList: PrescriptionValidationsModel[] = [];
  
  getExtraClassesForListView() {
    return this.drugData.content.map((item, ind) => {
      if (item.status?.toLowerCase().includes("approved")
        || item.status?.toLowerCase().includes("dispensed")) {
        return { [ind + ':7']: 'text-success dark:text-success-300 body-2-medium' }
      } else if (item.status?.toLowerCase().includes("rejected")
        || item.status?.toLowerCase().includes("cancelled")) {
        return { [ind + ':7']: 'text-error dark:text-error-400 body-2-medium' }
      } else if (item.status?.toLowerCase().includes("pending")) {
        return { [ind + ':7']: 'text-warning dark:text-error-400 body-2-medium' }
      } else {
        return { [ind + ':7']: 'text-text' }
      }
    }).reduce((row1, row2) => ({ ...row1, ...row2 }), {});
  }
 
}
