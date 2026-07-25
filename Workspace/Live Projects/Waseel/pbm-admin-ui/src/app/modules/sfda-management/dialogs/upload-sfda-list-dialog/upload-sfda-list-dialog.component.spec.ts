import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadSfdaListDialogComponent } from './upload-sfda-list-dialog.component';

describe('UploadSfdaListDialogComponent', () => {
  let component: UploadSfdaListDialogComponent;
  let fixture: ComponentFixture<UploadSfdaListDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadSfdaListDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadSfdaListDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
