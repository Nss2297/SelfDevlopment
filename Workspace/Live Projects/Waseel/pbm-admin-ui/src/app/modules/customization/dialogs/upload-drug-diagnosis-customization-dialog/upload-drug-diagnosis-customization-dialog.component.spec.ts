import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadDrugDiagnosisCustomizationDialogComponent } from './upload-drug-diagnosis-customization-dialog.component';

describe('UploadDrugDiagnosisCustomizationDialogComponent', () => {
  let component: UploadDrugDiagnosisCustomizationDialogComponent;
  let fixture: ComponentFixture<UploadDrugDiagnosisCustomizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadDrugDiagnosisCustomizationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadDrugDiagnosisCustomizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
