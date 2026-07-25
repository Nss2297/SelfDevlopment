import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularyDetailsComponent } from './formulary-details.component';

describe('FormularyDetailsComponent', () => {
  let component: FormularyDetailsComponent;
  let fixture: ComponentFixture<FormularyDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormularyDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
