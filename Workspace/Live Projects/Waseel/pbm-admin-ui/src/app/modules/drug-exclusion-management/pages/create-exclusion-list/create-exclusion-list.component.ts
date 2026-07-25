import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ExclusionTypeDrugRequestModel, ExclusionTypeRequestModel, exclusionDetailsModel } from '../../drug-exclusion-models/exclusion-details.model';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { BehaviorSubject } from 'rxjs';
import { DialogService } from 'src/app/modules/shared/services/dialog-service/dialog.service';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/modules/authentication/services/auth-service/auth.service';
import { exclusionDrugDetailsModel } from '../../drug-exclusion-models/drugExclusionListDetails.model';
import { ExclusionServiceTsService } from '../../services/exclusion-service.ts.service';

@Component({
  selector: 'app-create-exclusion-list',
  templateUrl: './create-exclusion-list.component.html',
  styles: [
  ]
})
export class CreateExclusionListComponent {

  exclusionTypeList$: BehaviorSubject<ListViewModel<exclusionDetailsModel>> = new BehaviorSubject(new ListViewModel<exclusionDetailsModel>());
  exclusionTypeList: ListViewModel<exclusionDetailsModel> = new ListViewModel<exclusionDetailsModel>();
  localExclusionTypeList: ListViewModel<exclusionDetailsModel> = new ListViewModel<exclusionDetailsModel>();
  exclusionTypeListDataControl: exclusionDetailsModel = new exclusionDetailsModel();
  exclusionTypeListPrototype = exclusionDetailsModel.prototype;

  exclusionDrugList$: BehaviorSubject<ListViewModel<exclusionDrugDetailsModel>> = new BehaviorSubject(new ListViewModel<exclusionDrugDetailsModel>());
  exclusionDrugList: ListViewModel<exclusionDrugDetailsModel> = new ListViewModel<exclusionDrugDetailsModel>();
  localExclusionDrugList: ListViewModel<exclusionDrugDetailsModel> = new ListViewModel<exclusionDrugDetailsModel>();
  exclusionDrugListPrototype = exclusionDrugDetailsModel.prototype;
  drugDataControl: exclusionDrugDetailsModel = new exclusionDrugDetailsModel();

  addDrugDialogOpen = false;
  uploadDrugDialogOpen = false;
  addBasedOnDialogOpen = false;
  drugDuplicateErrorMsg = "";

  @ViewChild('exclusionListName') exclusionListName!: any;

  constructor(
    private router: Router,
    private translate: TranslateService,
    private dialogService: DialogService,
    private exclusionservice:ExclusionServiceTsService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.exclusionTypeListDataControl.recordSize = 5;
    this.drugDataControl.recordSize = 5;

    this.populateExclusionTypeList();
    this.populateDrugList();
  }

  populateExclusionTypeList() {
    this.exclusionTypeList$.subscribe(data => {
      if (data.content.length > 0) {
        const list: exclusionDetailsModel[] = data.content ?? []
        this.exclusionTypeList.totalElements = list.length;
        while (this.exclusionTypeList.number != 0 && (this.exclusionTypeList.number * this.exclusionTypeList.size) > this.exclusionTypeList.totalElements!) {
          this.exclusionTypeList.number--;
        }

        this.exclusionTypeList.size = Number(this.exclusionTypeListDataControl.recordSize);
        this.exclusionTypeList.content = list.slice(this.exclusionTypeList.number * this.exclusionTypeList.size, this.exclusionTypeList.size + (this.exclusionTypeList.number * this.exclusionTypeList.size));
        this.exclusionTypeList.first = this.exclusionTypeList.number == 0;
        this.exclusionTypeList.last = (list.length - 1) <= (this.exclusionTypeList.size + (this.exclusionTypeList.number * this.exclusionTypeList.size));
        this.exclusionTypeList.totalElements = list.length;
        this.exclusionTypeList.totalPages = Math.ceil(list.length / this.exclusionTypeList.size);
      } else {
        this.exclusionTypeList = new ListViewModel();
      }
      this.authService.hideSystemLoader();
    })
  }

  populateDrugList() {
    this.exclusionDrugList$.subscribe(data => {
      if (data.content.length > 0) {
        const list: exclusionDrugDetailsModel[] = data.content ?? [];
        this.exclusionDrugList.totalElements = list.length;
        while (this.exclusionDrugList.number != 0 && (this.exclusionDrugList.number * this.exclusionDrugList.size) > this.exclusionDrugList.totalElements!) {
          this.exclusionDrugList.number--;
        }

        this.exclusionDrugList.size = Number(this.drugDataControl.recordSize);
        this.exclusionDrugList.content = list.slice(this.exclusionDrugList.number * this.exclusionDrugList.size, this.exclusionDrugList.size + (this.exclusionDrugList.number * this.exclusionDrugList.size));
        this.exclusionDrugList.first = this.exclusionDrugList.number == 0;
        this.exclusionDrugList.last = (list.length - 1) <= (this.exclusionDrugList.size + (this.exclusionDrugList.number * this.exclusionDrugList.size));
        this.exclusionDrugList.totalElements = list.length;
        this.exclusionDrugList.totalPages = Math.ceil(list.length / this.exclusionDrugList.size);
      } else {
        this.exclusionDrugList = new ListViewModel();
      }
    })
  }

  fetchData(tableName?: string) {

  }

  deleteExclusionTypeListDetail(exclusionData:any){
    this.dialogService.showDeleteConfirmDialog((data: any) => {
      if (data) {
        this.authService.showSystemLoader();
        this.deleteDrugExclusionData(exclusionData)
      } else {
        return;
      }
    })
  }
  
  deleteDrugExclusionData(exclusionData: any) {
    setTimeout(() => {
      let drugExclusionDetailsData = this.localExclusionTypeList.content.findIndex(x=>x.id == exclusionData)
      if(drugExclusionDetailsData != -1){
        this.localExclusionTypeList.content.splice(drugExclusionDetailsData, 1);
        this.exclusionTypeList$.next(this.localExclusionTypeList);
        if(this.exclusionTypeList.content.length == 0){
          this.exclusionTypeList.number -= 1;
        }
        this.populateExclusionTypeList();
        let drugExclusionDetailDeleteMsg = '';
        this.translate.get('prescription.drugExclusionDetailDeleteMsg').subscribe(text => drugExclusionDetailDeleteMsg = text);
        this.dialogService.showSuccessDialog(drugExclusionDetailDeleteMsg, (data: any) => {
          if (data) {
            this.authService.hideSystemLoader();
          }
        })
      }
    }, 1000);

  }

  deleteDrugDetailData(drugCode:string){
    this.dialogService.showDeleteConfirmDialog((data: any) => {
      if (data) {
        this.authService.showSystemLoader();
        this.deleteDrugData(drugCode)
      } else {
        return;
      }
    })   
  }

  deleteDrugData(drugCode:string){
    setTimeout(() => {
      let rowId = this.localExclusionDrugList.content.findIndex(x=>x.sfdaCode == drugCode)
      if(rowId != -1){
      this.localExclusionDrugList.content.splice(rowId, 1);
        this.exclusionDrugList$.next(this.localExclusionDrugList);
        if(this.exclusionDrugList.content.length == 0){
          this.exclusionDrugList.number -= 1;
        }
        this.populateDrugList();
        let drugDetailDeleteMsg = '';
        this.translate.get('prescription.drugDetailDeleteMsg').subscribe(text => drugDetailDeleteMsg = text);
        this.dialogService.showSuccessDialog(drugDetailDeleteMsg, (data: any) => { if (data) { 
          this.authService.hideSystemLoader();
          } })
        }
    }, 1000);
  }


  

  showItemPerPageDropdown(totalRecords: number): boolean {
    return totalRecords >= 10;
  }

  onBackClick = () => {
    this.router.navigateByUrl('/drug-exclusion-management/list');
  }

  closeAddDrugDialog = (data: any) => {
    this.translate.get('prescription.drugDuplicateErrorMsg').subscribe(text => this.drugDuplicateErrorMsg = text);
    if(!data){
    this.addDrugDialogOpen = false;
    }
    else{
      if(this.localExclusionDrugList.content.length > 0){
        const isDuplicateDrug = this.localExclusionDrugList.content.some((element: any) => {
          return element.sfdaCode == data.sfdaCode && element.sfdaDescription == data.sfdaDescription;
        });
        if (isDuplicateDrug) {
          this.dialogService.showErrorDialog(this.drugDuplicateErrorMsg, (data: any) => { });
          this.addDrugDialogOpen = false;
          this.authService.hideSystemLoader();
          return;
        }else{
          this.localExclusionDrugList.content.push(data);
        }
      }else{
        this.localExclusionDrugList.content.push(data);
      }
    }

    this.localExclusionDrugList.content.forEach((element: any) => { 
      element.id = element.sfdaCode;
    });
    this.exclusionDrugList$.next(this.localExclusionDrugList);
    this.addDrugDialogOpen = false;
  }

  closeUploadDrugListDialog = (data: any) => {
    if(!data){
      this.uploadDrugDialogOpen = false;
      }
    data.exclusionListDrugDetailsRequestModel.forEach((element: any) => { 
      element.id = element.drugCode;
      element.sfdaCode = element.drugCode;
      element.sfdaDescription = element.drugName;
      element.unitPrice = element.price;
      element.genericName = element.scientificName;
      element.waseelDrugId = Number(element.waseelDrugId);
      element.lastUpdatedDate = element.lastUpdateDate;

      
    });
    this.localExclusionDrugList.content.push(...data.exclusionListDrugDetailsRequestModel);

    this.exclusionDrugList$.next(this.localExclusionDrugList);
    this.uploadDrugDialogOpen = false;
    // this.fetchData();
    this.populateDrugList();
    
  }

  closeAddBasedOnDialog = (data:any) => {
    let duplicatenetworkExclusionMsg = '';
    let duplicateHighCostMsg = '';
    let duplicateproviderExclusionMsg = '';
    let duplicateSpecialityExclusionMsg = '';
    this.translate.get('prescription.duplicatenetworkExclusionMsg').subscribe(text => duplicatenetworkExclusionMsg = text);
    this.translate.get('prescription.duplicateHighCostMsg').subscribe(text => duplicateHighCostMsg = text);
    this.translate.get('prescription.duplicateproviderExclusionMsg').subscribe(text => duplicateproviderExclusionMsg = text);
    this.translate.get('prescription.duplicateSpecialityExclusionMsg').subscribe(text => duplicateSpecialityExclusionMsg = text);
    if(!data){
       this.addBasedOnDialogOpen = false;
      return;
    } else {
        if(this.localExclusionTypeList.content.length > 0){
          const isDuplicateExclusion = this.localExclusionTypeList.content.some((element: any) => {
            return element.networkId === data.formData.networkId && element.type === "Network Exclusion";
          });

          const isDuplicateHighCost = this.localExclusionTypeList.content.some((element: any) => {
            return data.basedOn == "high-cost" && element.type === "High Cost Medicine";
          });

          const isDuplicateProviderExclusion = this.localExclusionTypeList.content.some((element: any) => {
            return element.providerId === data.formData.providerId && element.type === "Provider Exclusion";
          });

          const isDuplicateSpecialityExclusion = this.localExclusionTypeList.content.some((element: any) => {
            return element.specialityId === data.formData.specialityId && element.type === "Speciality Exclusion";
          });
          
          //isDuplicateExclusion = false;
          if(isDuplicateExclusion){
            this.dialogService.showErrorDialog(duplicatenetworkExclusionMsg, (data: any) => { });
            this.addBasedOnDialogOpen = false;
            this.authService.hideSystemLoader();
            return;
          }
          if(isDuplicateHighCost){
            this.dialogService.showErrorDialog(duplicateHighCostMsg, (data: any) => { });
            this.addBasedOnDialogOpen = false;
            this.authService.hideSystemLoader();
            return;
          }
          if(isDuplicateProviderExclusion ){
            this.dialogService.showErrorDialog(duplicateproviderExclusionMsg, (data: any) => { });
            this.addBasedOnDialogOpen = false;
            this.authService.hideSystemLoader();
            return;
          }
          if(isDuplicateSpecialityExclusion ){
            this.dialogService.showErrorDialog(duplicateSpecialityExclusionMsg, (data: any) => { });
            this.addBasedOnDialogOpen = false;
            this.authService.hideSystemLoader();
            return;
          }
          else{
            this.localExclusionTypeList.content.push(data.formData);
          }
        }else{
          this.localExclusionTypeList.content.push(data.formData);
        }
    }
    this.exclusionTypeList$.next(this.localExclusionTypeList);
    this.addBasedOnDialogOpen = false;
  }

  createDrugExclusion() {
    this.authService.showSystemLoader();
    let drugExclusionSuccessMessage = "";
    this.translate.get('prescription.drugExclusionSuccessMessage').subscribe(text => drugExclusionSuccessMessage = text);
    
    let exclusionTypeList: ExclusionTypeRequestModel[] = [];
    this.localExclusionTypeList.content.forEach(exclusionData => {
      console.log(exclusionData);

      switch (exclusionData.type) {
        case "High Cost Medicine":
          let highCostExclusion: ExclusionTypeRequestModel = {
            exclusionType: exclusionData.type,
          }
          exclusionTypeList.push(highCostExclusion);
          break;
        case "Network Exclusion":
          let networkExclusion: ExclusionTypeRequestModel = {
            exclusionType: exclusionData.type,
            exclusionNetwork: exclusionData.networkId
          }
          exclusionTypeList.push(networkExclusion);
          break;
          case "Provider Exclusion":
            let providerExclusion: ExclusionTypeRequestModel = {
              exclusionType: exclusionData.type,
              exclusionProviderName:exclusionData.providerName,
              exclusionProvider: exclusionData.providerId
            }
            exclusionTypeList.push(providerExclusion);
            break;
            case "Speciality Exclusion":
            let specialityExclusion: ExclusionTypeRequestModel = {
              exclusionType: exclusionData.type,
              exclusionSpecialty:exclusionData.specialityId
            }
            exclusionTypeList.push(specialityExclusion);
            break;
      
        default:
          break;
      }
     
    });

    let drugDetailsList: ExclusionTypeDrugRequestModel[] = [];
    this.localExclusionDrugList.content.forEach(drug => {
      let newDrug: ExclusionTypeDrugRequestModel = {
        drugCode: drug.sfdaCode,
        drugName: drug.sfdaDescription,
        scientificName: drug.scientificName,
        scientificCode: drug.scientificCode,
        lastUpdateDate:drug.lastUpdatedDate,
        price:drug.unitPrice,
        waseelDrugId:drug.waseelDrugId

      }
      drugDetailsList.push(newDrug);
    });

    let payload = {
      exclusionListName: this.exclusionListName.value,
      exclusionTypeDetails:exclusionTypeList,
      exclusionDrugDetails: drugDetailsList
    }
    this.exclusionservice.createDrugExclusion(payload).subscribe(data => {
      this.dialogService.showSuccessDialog(drugExclusionSuccessMessage, (data: any) => {
        if (data) {
          this.authService.hideSystemLoader();
          this.router.navigateByUrl("drug-exclusion-management/list");
        }
      })
    }, (error) => {
      if (error.response.error.errors) {
        this.dialogService.showErrorDialog(error.response.error.errors, (data: any) => {
          this.authService.hideSystemLoader();
        })
      } else {
        this.dialogService.showErrorDialog(error.response.error.errorMessage[0].errorDescription, (data: any) => { })
      }
    }
    )
  }

  onExclusionTypeListViewPageChange(event: { event: MouseEvent, pageNumber: number }) {
    this.exclusionTypeList.number = event.pageNumber;
    this.populateExclusionTypeList();
  }
  onExclusionTypeListViewPageSizeChange(event: any) {
    this.exclusionTypeList.size = Number(event.pageSize);
    this.exclusionTypeListDataControl.recordSize = Number(event.pageSize);
    this.populateExclusionTypeList();
  }
  onDrugListViewPageChange(event: { event: MouseEvent, pageNumber: number }) {
    this.exclusionDrugList.number = event.pageNumber;
    this.populateDrugList();
  }
  ondrugListViewPageSizeChange(event: any) {
    this.exclusionDrugList.size = Number(event.pageSize);
    this.drugDataControl.recordSize = Number(event.pageSize);
    this.populateDrugList();
  }
}
