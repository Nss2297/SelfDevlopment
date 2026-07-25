import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSfdaDrugDialogComponent } from './add-sfda-drug-dialog.component';

describe('AddSfdaDrugDialogComponent', () => {
  let component: AddSfdaDrugDialogComponent;
  let fixture: ComponentFixture<AddSfdaDrugDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSfdaDrugDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSfdaDrugDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
