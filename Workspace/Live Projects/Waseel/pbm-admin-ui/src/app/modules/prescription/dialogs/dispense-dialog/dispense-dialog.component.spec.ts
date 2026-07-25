import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DispenseDialogComponent } from './dispense-dialog.component';

describe('DispenseDialogComponent', () => {
  let component: DispenseDialogComponent;
  let fixture: ComponentFixture<DispenseDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DispenseDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DispenseDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
