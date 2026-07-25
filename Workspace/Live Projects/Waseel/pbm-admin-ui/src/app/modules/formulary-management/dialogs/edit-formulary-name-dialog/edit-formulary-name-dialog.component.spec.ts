import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditFormularyNameDialogComponent } from './edit-formulary-name-dialog.component';

describe('EditFormularyNameDialogComponent', () => {
  let component: EditFormularyNameDialogComponent;
  let fixture: ComponentFixture<EditFormularyNameDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditFormularyNameDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditFormularyNameDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
