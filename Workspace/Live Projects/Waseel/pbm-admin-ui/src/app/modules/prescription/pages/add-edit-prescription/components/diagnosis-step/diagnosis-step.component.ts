import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { AddPrescriptionStoreService } from 'src/app/modules/prescription/service/add-prescription-store/add-prescription-store.service';
import { LovService } from 'src/app/modules/shared/services/lov-services/lov.service';
import { PrescriptionlovService } from 'src/app/modules/shared/services/prescriptionlov-service/prescriptionlov.service';
import { Subscription, map, withLatestFrom, Subject } from 'rxjs';
import { ListViewModel } from 'src/app/modules/shared/components/list-view/models/list-view.model';
import { NewPrescriptionDiagnosisCode } from 'src/app/modules/prescription/models/prescription-new-request.model';

@Component({
  selector: 'add-prescription-diagnosis-step',
  templateUrl: './diagnosis-step.component.html',
  styleUrls: []
})
export class DiagnosisStepComponent implements OnInit, OnDestroy {

  searchFormControl: FormControl = new FormControl();
  // selectedDiagnosis?: { key: string, value: string };
   selectedDiagnosis?: { key: string, value: string };

  getDiagnosisSubscription?: Subscription;

  updateData$ = new Subject();

  listViewModel = new ListViewModel<NewPrescriptionDiagnosisCode>();
  listPrototype = NewPrescriptionDiagnosisCode.prototype;

  showDuplicateError: boolean = false;


  constructor(public storeService: AddPrescriptionStoreService, public prescriptionLOVs: PrescriptionlovService, public lovService: LovService) {

  }

  ngOnInit(): void {
    this.listViewModel.number = 0;
    this.listViewModel.size = 5;
    this.updateData$.pipe(
      withLatestFrom(this.storeService.state$),
      map(data => ({ codes: data[1].prescriptionData.diagnosisCodes, isValid: data[1].step2IsValid }))
    ).subscribe(({ codes, isValid }) => {
      const list: NewPrescriptionDiagnosisCode[] = (codes || []).map(code => ({ ...code, id: code.diagnosisCode ?? "" }));
      this.listViewModel.totalElements = list.length;
      while (this.listViewModel.number != 0 && (this.listViewModel.number * this.listViewModel.size) > this.listViewModel.totalElements!) {
        this.listViewModel.number--;
      }
      //this.listViewModel.size = this.policyDataControl.recordSize;
      this.listViewModel.content = list.slice(this.listViewModel.number * this.listViewModel.size, this.listViewModel.size + (this.listViewModel.number * this.listViewModel.size));
      this.listViewModel.first = this.listViewModel.number == 0;
      this.listViewModel.last = (list.length - 1) <= (this.listViewModel.size + (this.listViewModel.number * this.listViewModel.size));

      this.listViewModel.totalPages = Math.ceil(list.length / this.listViewModel.size);
      if (!isValid && codes != null && codes.length > 0 && codes.filter(code => code.diagnosisType == 'PRIMARY').length == 1) {
        this.storeService.changeStepStatus(2, true);
      } else if (isValid && codes != null && (codes.length == 0 || codes.filter(code => code.diagnosisType == 'PRIMARY').length == 0)) {
        this.storeService.changeStepStatus(2, false);
      }
    });
    this.updateData$.next('');
  }

  ngOnDestroy(): void {
    this.updateData$.complete();
  }

  getDiagnosis(event: any) {
    if (this.searchFormControl.value != null) {
      if (this.getDiagnosisSubscription != null) {
        this.getDiagnosisSubscription.unsubscribe();
        this.lovService.diagnosisList$.next([]);
      }
      this.getDiagnosisSubscription = this.lovService.getDiagnosis({ icdCode: this.searchFormControl.value })
        .subscribe({
          next: (value) => this.getDiagnosisSubscription = undefined,
          error: (value) => this.getDiagnosisSubscription = undefined,
        });
    } else {
      this.lovService.diagnosisList$.next([]);
    }
  }


  selectItem(diagnosis: { key: string, value: string }) {
    this.selectedDiagnosis = diagnosis;
    this.searchFormControl.setValue(`${diagnosis.value}`);
    
    this.lovService.diagnosisList$.next([]);
  }
 
  addSelectedDiagnosis() {
    this.showDuplicateError = false;
    if (this.selectedDiagnosis != null) {
      const isAdded = this.storeService.addDiagnosis(this.selectedDiagnosis?.key, this.selectedDiagnosis?.value);
      this.searchFormControl.setValue('');
      this.selectedDiagnosis = undefined;
      if (isAdded) {
        this.updateData$.next('');
      } else {
        this.showDuplicateError = true;
      }
    }
  }

  onDiagnosisTypeChange(diagnosis: NewPrescriptionDiagnosisCode) {
    if (diagnosis != null && diagnosis.diagnosisCode != null && diagnosis.diagnosisType != null) {
      this.storeService.updateDiagnosisType(diagnosis.diagnosisCode, diagnosis.diagnosisType as "PRIMARY" | "SECONDARY");
      this.updateData$.next('');
    }
  }

  onDiagnosisDelete(event: { event: MouseEvent, id: string }) {
    this.storeService.removeDiagnosis(event.id);
    let diagnosisData = this.listViewModel.content.findIndex(x=>x.id == event.id)
      if(diagnosisData != -1){
        this.listViewModel.content.splice(diagnosisData, 1);
        if(this.listViewModel.content.length == 0 && this.listViewModel.number > 0){
          this.listViewModel.number -= 1;
        }
      }
    
    this.updateData$.next('');
  }

  onListViewPageChange(event: { event: MouseEvent, pageNumber: number }) {
    this.listViewModel.number = event.pageNumber;
    this.updateData$.next('');
  }
  onListViewPageSizeChange(event:any){
    this.listViewModel.size = Number(event.pageSize);
    this.updateData$.next('');
  }

  showItemPerPageDropdown(totalRecords: number): boolean {
    return totalRecords >= 5;
  }

}
