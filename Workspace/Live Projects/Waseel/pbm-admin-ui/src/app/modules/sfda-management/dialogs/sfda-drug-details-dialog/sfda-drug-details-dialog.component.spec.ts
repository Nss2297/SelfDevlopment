import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SfdaDrugDetailsDialogComponent } from './sfda-drug-details-dialog.component';

describe('SfdaDrugDetailsDialogComponent', () => {
  let component: SfdaDrugDetailsDialogComponent;
  let fixture: ComponentFixture<SfdaDrugDetailsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SfdaDrugDetailsDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SfdaDrugDetailsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
