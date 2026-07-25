import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDrugExclusionListDrugDialogComponent } from './add-drug-exclusion-list-drug-dialog.component';

describe('AddDrugExclusionListDrugDialogComponent', () => {
  let component: AddDrugExclusionListDrugDialogComponent;
  let fixture: ComponentFixture<AddDrugExclusionListDrugDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddDrugExclusionListDrugDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddDrugExclusionListDrugDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
