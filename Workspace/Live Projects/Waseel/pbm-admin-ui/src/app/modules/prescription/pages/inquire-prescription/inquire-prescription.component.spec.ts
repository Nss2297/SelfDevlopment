import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InquirePrescriptionComponent } from './inquire-prescription.component';

describe('InquirePrescriptionComponent', () => {
  let component: InquirePrescriptionComponent;
  let fixture: ComponentFixture<InquirePrescriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InquirePrescriptionComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(InquirePrescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
