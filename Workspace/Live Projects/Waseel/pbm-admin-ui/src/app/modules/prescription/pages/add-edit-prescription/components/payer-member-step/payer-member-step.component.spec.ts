import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayerMemberStepComponent } from './payer-member-step.component';

describe('PayerMemberStepComponent', () => {
  let component: PayerMemberStepComponent;
  let fixture: ComponentFixture<PayerMemberStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PayerMemberStepComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PayerMemberStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
