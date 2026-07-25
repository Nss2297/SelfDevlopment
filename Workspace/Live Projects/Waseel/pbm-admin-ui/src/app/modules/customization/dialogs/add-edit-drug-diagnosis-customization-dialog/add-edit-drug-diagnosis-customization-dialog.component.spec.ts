import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditDrugDiagnosisCustomizationDialogComponent } from './add-edit-drug-diagnosis-customization-dialog.component';

describe('AddEditDrugDiagnosisCustomizationDialogComponent', () => {
  let component: AddEditDrugDiagnosisCustomizationDialogComponent;
  let fixture: ComponentFixture<AddEditDrugDiagnosisCustomizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditDrugDiagnosisCustomizationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditDrugDiagnosisCustomizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
