import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrugDispenseComponent } from './drug-dispense.component';

describe('DrugDispenseComponent', () => {
  let component: DrugDispenseComponent;
  let fixture: ComponentFixture<DrugDispenseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrugDispenseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrugDispenseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
