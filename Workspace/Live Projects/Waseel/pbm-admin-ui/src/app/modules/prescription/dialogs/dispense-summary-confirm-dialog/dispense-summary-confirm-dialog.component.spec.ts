import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DispenseSummaryConfirmDialogComponent } from './dispense-summary-confirm-dialog.component';

describe('DispenseSummaryConfirmDialogComponent', () => {
  let component: DispenseSummaryConfirmDialogComponent;
  let fixture: ComponentFixture<DispenseSummaryConfirmDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DispenseSummaryConfirmDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DispenseSummaryConfirmDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
