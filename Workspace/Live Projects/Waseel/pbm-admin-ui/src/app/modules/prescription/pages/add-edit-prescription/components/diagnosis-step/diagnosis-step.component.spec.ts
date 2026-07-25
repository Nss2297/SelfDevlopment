import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiagnosisStepComponent } from './diagnosis-step.component';

describe('DiagnosisStepComponent', () => {
  let component: DiagnosisStepComponent;
  let fixture: ComponentFixture<DiagnosisStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiagnosisStepComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DiagnosisStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
