import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrugDiagnosisCustomizationPageComponent } from './drug-diagnosis-customization-page.component';

describe('DrugDiagnosisCustomizationPageComponent', () => {
  let component: DrugDiagnosisCustomizationPageComponent;
  let fixture: ComponentFixture<DrugDiagnosisCustomizationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrugDiagnosisCustomizationPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrugDiagnosisCustomizationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
