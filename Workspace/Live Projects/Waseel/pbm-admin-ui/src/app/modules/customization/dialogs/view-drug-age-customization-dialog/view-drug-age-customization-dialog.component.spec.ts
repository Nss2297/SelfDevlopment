import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDrugAgeCustomizationDialogComponent } from './view-drug-age-customization-dialog.component';

describe('ViewDrugAgeCustomizationDialogComponent', () => {
  let component: ViewDrugAgeCustomizationDialogComponent;
  let fixture: ComponentFixture<ViewDrugAgeCustomizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewDrugAgeCustomizationDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDrugAgeCustomizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
