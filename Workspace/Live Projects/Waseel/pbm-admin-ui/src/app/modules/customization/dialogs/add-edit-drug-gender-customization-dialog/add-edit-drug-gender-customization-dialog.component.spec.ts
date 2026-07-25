import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditDrugGenderCustomizationDialogComponent } from './add-edit-drug-gender-customization-dialog.component';

describe('AddEditDrugGenderCustomizationDialogComponent', () => {
  let component: AddEditDrugGenderCustomizationDialogComponent;
  let fixture: ComponentFixture<AddEditDrugGenderCustomizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditDrugGenderCustomizationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditDrugGenderCustomizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
