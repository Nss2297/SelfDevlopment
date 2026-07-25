import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditDrugDrugInteractionDialogComponent } from './add-edit-drug-drug-interaction-dialog.component';

describe('AddEditDrugDrugInteractionDialogComponent', () => {
  let component: AddEditDrugDrugInteractionDialogComponent;
  let fixture: ComponentFixture<AddEditDrugDrugInteractionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditDrugDrugInteractionDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditDrugDrugInteractionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
