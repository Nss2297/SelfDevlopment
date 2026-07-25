import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDrugDiagnosisCustomizationDialogComponent } from './view-drug-diagnosis-customization-dialog.component';

describe('ViewDrugDiagnosisCustomizationDialogComponent', () => {
  let component: ViewDrugDiagnosisCustomizationDialogComponent;
  let fixture: ComponentFixture<ViewDrugDiagnosisCustomizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewDrugDiagnosisCustomizationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDrugDiagnosisCustomizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
