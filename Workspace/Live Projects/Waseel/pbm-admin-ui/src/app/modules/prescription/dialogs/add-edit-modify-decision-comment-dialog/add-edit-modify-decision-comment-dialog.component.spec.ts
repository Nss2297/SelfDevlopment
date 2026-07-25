import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditModifyDecisionCommentDialogComponent } from './add-edit-modify-decision-comment-dialog.component';

describe('AddEditModifyDecisionCommentDialogComponent', () => {
  let component: AddEditModifyDecisionCommentDialogComponent;
  let fixture: ComponentFixture<AddEditModifyDecisionCommentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddEditModifyDecisionCommentDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditModifyDecisionCommentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
