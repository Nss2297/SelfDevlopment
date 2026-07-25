import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { AddPrescriptionStoreService } from 'src/app/modules/prescription/service/add-prescription-store/add-prescription-store.service';
import { DrugListModel, PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { exclusionDrugDetailsModel } from '../../drug-exclusion-models/drugExclusionListDetails.model';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';

@Component({
  selector: 'app-add-drug-exclusion-list-drug-dialog',
  templateUrl: './add-drug-exclusion-list-drug-dialog.component.html',
  styles: [
  ]
})
export class AddDrugExclusionListDrugDialogComponent {
  searchFormControl: FormControl = new FormControl();
  getDrugsSubscription?: Subscription;
  selectedDrug?: exclusionDrugDetailsModel;
  selectedScientificName?: string
  selectedSfdaCode?: string;
  selectedLastUpdatedDate?: string;
  selectedScientificCode?: string;
  selectedsfdaDescription?:string;
  selectedDrugName?: string
  selectedDrugCode?: string;
  selectedgenericName?: string;
  selectedprice?: string;
  drugValidationMsg!:string;
  selectedUnitPrice!:string;
  selectedWaseelDrugId!:number;

  searchFormTouched: boolean = false;
  @Input('onClose')
  onClose?: any;

  @Input('fromPage')
  fromPage?: string;
  @Input() exclusionId: any;
  constructor(
    public storeService: AddPrescriptionStoreService, 
    public prescriptionLOVs: PrescriptionlovService,
    private translate: TranslateService,
    private authService: AuthService
  ) {  }
  
   
  ngOnInit():void{
    this.prescriptionLOVs.initializeAllLists();
  }

  getDrugsForExclusion(event: any) {
    if (this.searchFormControl.value != null) {
      if (this.getDrugsSubscription != null) {
        this.getDrugsSubscription.unsubscribe();
        this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
      }
      this.getDrugsSubscription = this.prescriptionLOVs.getDrugs({ drugValue: this.searchFormControl.value, idNumber: this.storeService.selectedMemberInfo?.idNumber || '' })
        .subscribe({
          next: (value) => this.getDrugsSubscription = undefined,
          error: (value) => this.getDrugsSubscription = undefined,
        });
    } else {
      this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
    }
}

selectDrug(drug:any) {
  this.selectedDrug = drug;
  this.searchFormControl.setValue(`${drug.sfdaCode} | ${drug.sfdaDescription} `);
  this.searchFormControl.get('sfdaCode')?.setValue(drug.sfdaCode);
  this.searchFormControl.get('sfdaDescription')?.setValue(drug.sfdaDescription);
  this.selectedScientificName = drug.scientificName;
  this.selectedSfdaCode = drug.sfdaCode;
  this.selectedLastUpdatedDate = drug.lastUpdatedDate;
  this.selectedScientificCode = drug.scientificCode;
  this.selectedsfdaDescription = drug.sfdaDescription;
  this.selectedUnitPrice = drug.unitPrice!;
  this.selectedWaseelDrugId = Number(drug.waseelDrugId);
  this.selectedDrugName = drug.sfdaDescription
  this.selectedDrugCode = drug.sfdaCode
  this.selectedgenericName = drug.scientificName
  this.selectedprice = drug.unitPrice
  this.prescriptionLOVs.drugsList$.next(new ListViewModel<DrugListModel>());
  this.drugValidationMsg = "";
}

saveDrugData() {
  this.drugValidationMsg = "";
  if (this.searchFormControl.value && this.selectedDrug){
    let drug = {
      sfdaCode:this.selectedSfdaCode,
      sfdaDescription:this.selectedsfdaDescription,
      scientificName:this.selectedScientificName,
      scientificCode:this.selectedScientificCode,
      lastUpdatedDate:this.selectedLastUpdatedDate,
      waseelDrugId:this.selectedWaseelDrugId,
      unitPrice:this.selectedUnitPrice,
      drugCode:this.selectedDrugCode,
        drugName:this.selectedDrugName,
        genericName:this.selectedgenericName,
        price:this.selectedprice
    }
    if(this.fromPage == "create"){
      this.authService.showSystemLoader();
      setTimeout(()=>{
      this.onClose(drug);
      this.authService.hideSystemLoader();
      },1000)
      
    } else {      
      this.onClose(drug);
    }
  } else {
    this.translate.get('prescription.drugValidationMsg').subscribe(text => this.drugValidationMsg = text);
  }
}

  closeDialog() {
    if (this.onClose) {
      this.onClose(null);
    }
  }
}
