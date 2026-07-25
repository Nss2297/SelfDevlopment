import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFormularyDrugDialogComponent } from './add-formulary-drug-dialog.component';

describe('AddFormularyDrugDialogComponent', () => {
  let component: AddFormularyDrugDialogComponent;
  let fixture: ComponentFixture<AddFormularyDrugDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddFormularyDrugDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddFormularyDrugDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
