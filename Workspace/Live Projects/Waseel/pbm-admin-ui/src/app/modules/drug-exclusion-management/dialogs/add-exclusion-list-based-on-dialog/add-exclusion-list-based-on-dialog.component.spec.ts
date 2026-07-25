import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddExclusionListBasedOnDialogComponent } from './add-exclusion-list-based-on-dialog.component';

describe('AddExclusionListBasedOnDialogComponent', () => {
  let component: AddExclusionListBasedOnDialogComponent;
  let fixture: ComponentFixture<AddExclusionListBasedOnDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddExclusionListBasedOnDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddExclusionListBasedOnDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
