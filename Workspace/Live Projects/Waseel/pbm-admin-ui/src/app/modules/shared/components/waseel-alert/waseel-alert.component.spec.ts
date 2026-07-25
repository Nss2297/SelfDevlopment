import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaseelAlertComponent } from './waseel-alert.component';

describe('WaseelAlertComponent', () => {
  let component: WaseelAlertComponent;
  let fixture: ComponentFixture<WaseelAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WaseelAlertComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(WaseelAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
