import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, map, withLatestFrom } from 'rxjs';
import { DrugsDetails } from 'src/app/modules/prescription/models/prescription-drug.model';
import { NewPrescriptionDiagnosisCode } from 'src/app/modules/prescription/models/prescription-new-request.model';
import { AddPrescriptionStoreService } from 'src/app/modules/prescription/service/add-prescription-store/add-prescription-store.service';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'add-prescription-confirmation-step',
  templateUrl: './confirmation-step.component.html',
  styleUrls: ['./confirmation-step.component.css']
})
export class ConfirmationStepComponent implements OnInit, OnDestroy {

  diagnosisViewList = new ListViewModel<NewPrescriptionDiagnosisCode>();
  drugsViewList: ListViewModel<DrugsDetails> = new ListViewModel();

  diagnosisListPrototype = NewPrescriptionDiagnosisCode.prototype;
  drugListPrototype = DrugsDetails.prototype;
  basedOn = 'trade-name';
  updateData$ = new Subject();
  prescribeGenericOrBrandFlow: boolean = environment.featureToggle['prescribeGenericOrBrandFlow'];
  
  constructor(public storeService: AddPrescriptionStoreService) {

  }

  ngOnInit(): void {
    this.diagnosisViewList.number = 0;
    this.diagnosisViewList.size = 5;
    this.drugsViewList.number = 0;
    this.drugsViewList.size = 5;
    this.updateData$.pipe(
      withLatestFrom(this.storeService.state$),
      map(data => ({ drugList: data[1].prescriptionData.drugList, diagnosisList: data[1].prescriptionData.diagnosisCodes }))
    ).subscribe(({ drugList, diagnosisList }) => {
      const diagnosisListDetail: NewPrescriptionDiagnosisCode[] = (diagnosisList || []).map(code => ({ ...code, id: code.diagnosisCode ?? "" }));
      while (this.diagnosisViewList.number != 0 && (this.diagnosisViewList.number * this.diagnosisViewList.size) > this.diagnosisViewList.totalElements!) {
        this.diagnosisViewList.number--;
      }
      this.diagnosisViewList.content = diagnosisListDetail.slice(this.diagnosisViewList.number * this.diagnosisViewList.size, this.diagnosisViewList.size + (this.diagnosisViewList.number * this.diagnosisViewList.size));
      this.diagnosisViewList.first = this.diagnosisViewList.number == 0;
      this.diagnosisViewList.last = (diagnosisListDetail.length - 1) <= (this.diagnosisViewList.size + (this.diagnosisViewList.number * this.diagnosisViewList.size));
      this.diagnosisViewList.totalElements = diagnosisListDetail.length;
      this.diagnosisViewList.totalPages = Math.ceil(diagnosisListDetail.length / this.diagnosisViewList.size);

      const drugListDetails: DrugsDetails[] = (drugList || []).map(code => ({ ...code, id: code.drugCode ?? "", type: code.drugCode? "tradeName" : "scientificName", drugCode: code.drugCode? code.drugCode : code.scientificCode }));
      while (this.drugsViewList.number != 0 && (this.drugsViewList.number * this.drugsViewList.size) > this.drugsViewList.totalElements!) {
        this.drugsViewList.number--;
      }
      this.drugsViewList.content = drugListDetails.slice(this.drugsViewList.number * this.drugsViewList.size, this.drugsViewList.size + (this.drugsViewList.number * this.drugsViewList.size));
      this.drugsViewList.first = this.drugsViewList.number == 0;
      this.drugsViewList.last = (drugListDetails.length - 1) <= (this.drugsViewList.size + (this.drugsViewList.number * this.drugsViewList.size));
      this.drugsViewList.totalElements = drugListDetails.length;
      this.drugsViewList.totalPages = Math.ceil(drugListDetails.length / this.drugsViewList.size);
    });
    this.updateData$.next('');
  }

  ngOnDestroy(): void {
    this.updateData$.complete();
  }


  onDiagnosisListViewPageChange(event: { event: MouseEvent, pageNumber: number }) {
    this.diagnosisViewList.number = event.pageNumber;
    this.updateData$.next('');
  }


  onDrugListViewPageChange(event: { event: MouseEvent, pageNumber: number }) {
    this.drugsViewList.number = event.pageNumber;
    this.updateData$.next('');
  }
  onListViewPageSizeChange(event: any) {
    this.diagnosisViewList.size = Number(event.pageSize);
    this.updateData$.next('');
  }

  onDrugListViewPageSizeChange(event: any) {
    this.drugsViewList.size = Number(event.pageSize);
    this.updateData$.next('');
  }

  showItemPerPageDropdown(totalRecords: number): boolean {
    return totalRecords >= 5;
  }

}
