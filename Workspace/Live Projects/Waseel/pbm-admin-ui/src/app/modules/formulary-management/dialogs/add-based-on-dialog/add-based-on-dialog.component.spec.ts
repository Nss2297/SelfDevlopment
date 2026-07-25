import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBasedOnDialogComponent } from './add-based-on-dialog.component';

describe('AddBasedOnDialogComponent', () => {
  let component: AddBasedOnDialogComponent;
  let fixture: ComponentFixture<AddBasedOnDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddBasedOnDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddBasedOnDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
