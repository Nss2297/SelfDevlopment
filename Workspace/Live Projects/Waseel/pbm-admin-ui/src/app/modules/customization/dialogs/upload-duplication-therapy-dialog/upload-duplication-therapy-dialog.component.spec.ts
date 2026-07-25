import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadDuplicationTherapyDialogComponent } from './upload-duplication-therapy-dialog.component';

describe('UploadDuplicationTherapyDialogComponent', () => {
  let component: UploadDuplicationTherapyDialogComponent;
  let fixture: ComponentFixture<UploadDuplicationTherapyDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadDuplicationTherapyDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadDuplicationTherapyDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
