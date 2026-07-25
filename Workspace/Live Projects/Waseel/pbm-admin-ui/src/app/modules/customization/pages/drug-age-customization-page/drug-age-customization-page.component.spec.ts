import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrugAgeCustomizationPageComponent } from './drug-age-customization-page.component';

describe('DrugAgeCustomizationPageComponent', () => {
  let component: DrugAgeCustomizationPageComponent;
  let fixture: ComponentFixture<DrugAgeCustomizationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrugAgeCustomizationPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrugAgeCustomizationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
