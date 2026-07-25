import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrintUcafComponent } from './print-ucaf.component';

describe('PrintUcafComponent', () => {
  let component: PrintUcafComponent;
  let fixture: ComponentFixture<PrintUcafComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrintUcafComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrintUcafComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
