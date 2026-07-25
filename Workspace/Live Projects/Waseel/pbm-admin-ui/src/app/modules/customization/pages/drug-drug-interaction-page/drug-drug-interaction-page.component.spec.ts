import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrugDrugInteractionPageComponent } from './drug-drug-interaction-page.component';

describe('DrugDrugInteractionPageComponent', () => {
  let component: DrugDrugInteractionPageComponent;
  let fixture: ComponentFixture<DrugDrugInteractionPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrugDrugInteractionPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrugDrugInteractionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
