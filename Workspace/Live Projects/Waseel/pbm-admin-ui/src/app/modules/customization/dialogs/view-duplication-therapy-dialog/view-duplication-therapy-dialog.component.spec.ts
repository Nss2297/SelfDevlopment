import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDuplicationTherapyDialogComponent } from './view-duplication-therapy-dialog.component';

describe('ViewDuplicationTherapyDialogComponent', () => {
  let component: ViewDuplicationTherapyDialogComponent;
  let fixture: ComponentFixture<ViewDuplicationTherapyDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewDuplicationTherapyDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDuplicationTherapyDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
