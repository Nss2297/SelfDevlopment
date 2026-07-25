import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditExclusionListDialogComponent } from './edit-exclusion-list-dialog.component';

describe('EditExclusionListDialogComponent', () => {
  let component: EditExclusionListDialogComponent;
  let fixture: ComponentFixture<EditExclusionListDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditExclusionListDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditExclusionListDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
