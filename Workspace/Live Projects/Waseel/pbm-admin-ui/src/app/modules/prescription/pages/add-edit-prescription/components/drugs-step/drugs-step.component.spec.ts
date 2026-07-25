import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrugsStepComponent } from './drugs-step.component';

describe('DrugsStepComponent', () => {
  let component: DrugsStepComponent;
  let fixture: ComponentFixture<DrugsStepComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrugsStepComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrugsStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
