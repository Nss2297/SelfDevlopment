import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadDrugAgeCustomizationDialogComponent } from './upload-drug-age-customization-dialog.component';

describe('UploadDrugAgeCustomizationDialogComponent', () => {
  let component: UploadDrugAgeCustomizationDialogComponent;
  let fixture: ComponentFixture<UploadDrugAgeCustomizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadDrugAgeCustomizationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadDrugAgeCustomizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
