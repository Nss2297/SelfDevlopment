import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadDrugDrugInteractionDialogComponent } from './upload-drug-drug-interaction-dialog.component';

describe('UploadDrugDrugInteractionDialogComponent', () => {
  let component: UploadDrugDrugInteractionDialogComponent;
  let fixture: ComponentFixture<UploadDrugDrugInteractionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadDrugDrugInteractionDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadDrugDrugInteractionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
