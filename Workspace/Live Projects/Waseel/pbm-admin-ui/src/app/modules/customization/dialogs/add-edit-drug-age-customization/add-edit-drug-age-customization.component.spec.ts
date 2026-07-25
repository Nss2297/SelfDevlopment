import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditDrugAgeCustomizationComponent } from './add-edit-drug-age-customization.component';

describe('AddEditDrugAgeCustomizationComponent', () => {
  let component: AddEditDrugAgeCustomizationComponent;
  let fixture: ComponentFixture<AddEditDrugAgeCustomizationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditDrugAgeCustomizationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditDrugAgeCustomizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
