import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { Subscription } from 'rxjs';
import { PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { PrescriptionDetails } from '../../models/prescription-details.model';
import { PrescriptionRequest } from '../../models/prescription-request.model';

@Component({
  selector: 'app-payer-list',
  templateUrl: './payer-list.component.html',
  styleUrls: ['./payer-list.component.css']
})
export class PayerListComponent implements OnInit {
  dataControl: any
  provider: any = [
    { key: "Dallah Hospital", value: "Dallah Hospital" }
  ]
  listIsLoading: boolean = false;
  showEditButton: boolean = false;
  showDeleteButton: boolean = false;
  showMoreActionsMenu: boolean = true;
  isFilterDrawerOpen: boolean = false;
  filterForm: FormGroup = new FormGroup({
    referenceNo: new FormControl(),
    dateAndTime: new FormControl(),
    startdate: new FormControl(),
    enddate: new FormControl(),
    memberId: new FormControl(),
    memberName: new FormControl(),

    // policyNumber: new FormControl(),
    provider: new FormControl(),
    status: new FormControl()
  })
  errorCode: string = '';
  memberN: any;
  refNo: any;
  providerList: any[] = []
  selectedproviders?: { providerId: string, providerName: string, code: string };
 

  getProvidersSubscription?: Subscription;
  constructor(private router: Router,public prescriptionLOVs: PrescriptionlovService,){}

  ngOnInit(): void {
    this.applyFilter
      // this.getProvidersData();
   this.getProviders(null)
   }
   goToviewpayerdetailsPage() {
    this.router.navigate(['/prescription/payerdetails']);
  }

  
  getProviders(event: any) {
    if (this.filterForm.value != null) {
      if (this.getProvidersSubscription != null) {
        this.getProvidersSubscription.unsubscribe();
        this.prescriptionLOVs.providersList$.next([]);
      }
      this.getProvidersSubscription = this.prescriptionLOVs.getProviders({ value: '8587' }).subscribe(providers => {
               console.log(providers);
        this.providerList=providers
            });
        // .subscribe({
        //   next: (value) => this.getProvidersSubscription = undefined,
        //   error: (value) => this.getProvidersSubscription = undefined,
        // });
    } else {
      this.prescriptionLOVs.providersList$.next([]);
    }
  }
  selectItem(provider: { providerId: string, providerName: string, code: string }) {
    console.log(provider)
    this. selectedproviders = provider;
    this.provider.setValue(`${provider.code}`);
    
    this.prescriptionLOVs.providersList$.next([]);
  }
//   getProvidersData() {
//     this.prescriptionLOVs.getProviders({ value: '8587' }).subscribe(providers => {
//       console.log(providers);
// this.providerList=providers
//     });
//    }

   fetchData() {
    this.listIsLoading = true;
    this.prescriptionLOVs.getProviders(this.dataControl).subscribe();
    if (this.errorCode === '') {
      this.isFilterDrawerOpen = false;
    }
    else {
      this.isFilterDrawerOpen = true;
    }
    this.showEditButton = false;
    this.showDeleteButton = false;
    this.showMoreActionsMenu = true;
    // this.updateQueryParams();

  }

   applyFilter() {
    let referenceNoValue = this.filterForm.controls['referenceNo'].value;
    const trimmedReferenceNoValue = referenceNoValue?.replace(/^\s+|\s+$/g, '');
    this.refNo = trimmedReferenceNoValue;
    let memberName = this.filterForm.controls['memberName'].value;
    let trimmedMemberName = memberName?.replace(/^\s+|\s+$/g, '');
    this.memberN = trimmedMemberName;
    if (!isNaN(Number(trimmedMemberName))) {
      this.dataControl.idNumber = trimmedMemberName;
    } else {
      //  this.dataControl.memberName = trimmedMemberName;
      
      
        const words = memberName?.split(' ');
        const capitalizedWords = words?.map((word:any) => {
          if (!word) {
            return word; 
          }
          const firstLetter = word.charAt(0).toUpperCase();
          const restOfWord = word.slice(1).toLowerCase();
          this.dataControl.memberName = firstLetter + restOfWord;
        });
        this.dataControl.referenceNo = trimmedReferenceNoValue
        this.dataControl.enddate = moment(this.filterForm.controls['enddate'].value).format("DD-MM-yyyy");
        this.dataControl.startdate = moment(this.filterForm.controls['startdate'].value).format("DD-MM-yyyy");
        this.dataControl.provider = this.filterForm.controls['provider'].value;
        this.dataControl.status = this.filterForm.controls['status'].value;
        
      }
}



  

  
  }

