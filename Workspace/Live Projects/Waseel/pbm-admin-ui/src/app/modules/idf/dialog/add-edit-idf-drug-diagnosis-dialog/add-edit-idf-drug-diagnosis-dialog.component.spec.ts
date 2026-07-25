import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditIdfDrugDiagnosisDialogComponent } from './add-edit-idf-drug-diagnosis-dialog.component';

describe('AddEditIdfDrugDiagnosisDialogComponent', () => {
  let component: AddEditIdfDrugDiagnosisDialogComponent;
  let fixture: ComponentFixture<AddEditIdfDrugDiagnosisDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditIdfDrugDiagnosisDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditIdfDrugDiagnosisDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
