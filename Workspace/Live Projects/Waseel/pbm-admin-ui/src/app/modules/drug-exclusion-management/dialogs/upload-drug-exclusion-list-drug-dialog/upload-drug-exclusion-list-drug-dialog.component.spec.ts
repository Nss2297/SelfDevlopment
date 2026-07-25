import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadDrugExclusionListDrugDialogComponent } from './upload-drug-exclusion-list-drug-dialog.component';

describe('UploadDrugExclusionListDrugDialogComponent', () => {
  let component: UploadDrugExclusionListDrugDialogComponent;
  let fixture: ComponentFixture<UploadDrugExclusionListDrugDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadDrugExclusionListDrugDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadDrugExclusionListDrugDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
