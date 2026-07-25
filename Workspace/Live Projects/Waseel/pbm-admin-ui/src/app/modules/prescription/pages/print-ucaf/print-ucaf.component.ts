import { AfterContentInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { PrescriptionDetailsDiagnosis } from '../../models/prescription-details-diagnosis.model';
import { PrescriptionDetailsDrugs } from '../../models/prescription-details-drugs.model';
import { PrescriptionDetailsPayerMemberPhysicianInfoModel } from '../../models/prescription-details-payer-member-physician-info.model';

@Component({
  selector: 'app-print-ucaf',
  templateUrl: './print-ucaf.component.html',
  styles: [
  ]
})
export class PrintUcafComponent implements AfterContentInit {

  @Input()
  ePrescriptionReferenceNumber!: string;

  @Input()
  payerMemberPhysicianInfoData!: PrescriptionDetailsPayerMemberPhysicianInfoModel;

  @Input()
  diagnosisData!: PrescriptionDetailsDiagnosis[];

  principleCode?: string;
  nonPrimaryCodes: (string | undefined)[] = [];
  nonPrimaryCodesExtra: (string | undefined)[] = [];

  @Input()
  drugData!: PrescriptionDetailsDrugs[];


  @Output()
  onBackClick: EventEmitter<any> = new EventEmitter();


  constructor() { }

  ngAfterContentInit(): void {
    this.principleCode = this.diagnosisData.find(diagnosis => diagnosis.diagnosisType == 'PRIMARY')?.diagnosisCode;
    this.nonPrimaryCodes = this.diagnosisData
      .filter(diagnosis => diagnosis.diagnosisType != 'PRIMARY' && diagnosis != undefined && diagnosis.diagnosisCode != undefined)
      .map(diagnosis => diagnosis.diagnosisCode);
    this.nonPrimaryCodesExtra = this.nonPrimaryCodes.splice(7, 2);
    this.nonPrimaryCodes = this.nonPrimaryCodes.splice(0, 7);

    while (this.nonPrimaryCodes.length < 3) { 
      this.nonPrimaryCodes.push(" ")
    }
  }

  print() {
    document.body.classList.add('print-enable');
    setTimeout(() => {
      window.print();
      document.body.classList.remove('print-enable');
    }, 200);
  }

  goBack() {
    this.onBackClick.emit();
  }

  getPrimaryDiagnosis() {
    return this.diagnosisData.find(diagnosis => diagnosis.diagnosisType == 'PRIMARY')
  }

  getNonPrimaryDiagnosis() {

  }
}
