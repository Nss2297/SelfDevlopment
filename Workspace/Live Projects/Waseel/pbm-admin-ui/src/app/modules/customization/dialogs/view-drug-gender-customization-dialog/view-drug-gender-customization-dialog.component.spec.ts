import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDrugGenderCustomizationDialogComponent } from './view-drug-gender-customization-dialog.component';

describe('ViewDrugGenderCustomizationDialogComponent', () => {
  let component: ViewDrugGenderCustomizationDialogComponent;
  let fixture: ComponentFixture<ViewDrugGenderCustomizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewDrugGenderCustomizationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDrugGenderCustomizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
