import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditPrescriptionDrugDialogComponent } from './add-edit-prescription-drug-dialog.component';

describe('AddEditPrescriptionDrugDialogComponent', () => {
  let component: AddEditPrescriptionDrugDialogComponent;
  let fixture: ComponentFixture<AddEditPrescriptionDrugDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditPrescriptionDrugDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditPrescriptionDrugDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
