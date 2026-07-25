import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDrugDrugInteractionDialogComponent } from './view-drug-drug-interaction-dialog.component';

describe('ViewDrugDrugInteractionDialogComponent', () => {
  let component: ViewDrugDrugInteractionDialogComponent;
  let fixture: ComponentFixture<ViewDrugDrugInteractionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewDrugDrugInteractionDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDrugDrugInteractionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
