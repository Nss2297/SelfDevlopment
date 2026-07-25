import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditDuplicationTherapyDialogComponent } from './add-edit-duplication-therapy-dialog.component';

describe('AddEditDuplicationTherapyDialogComponent', () => {
  let component: AddEditDuplicationTherapyDialogComponent;
  let fixture: ComponentFixture<AddEditDuplicationTherapyDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditDuplicationTherapyDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditDuplicationTherapyDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
