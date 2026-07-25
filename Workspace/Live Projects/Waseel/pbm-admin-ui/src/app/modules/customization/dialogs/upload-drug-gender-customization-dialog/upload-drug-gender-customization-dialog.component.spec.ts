import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadDrugGenderCustomizationDialogComponent } from './upload-drug-gender-customization-dialog.component';

describe('UploadDrugGenderCustomizationDialogComponent', () => {
  let component: UploadDrugGenderCustomizationDialogComponent;
  let fixture: ComponentFixture<UploadDrugGenderCustomizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadDrugGenderCustomizationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadDrugGenderCustomizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
