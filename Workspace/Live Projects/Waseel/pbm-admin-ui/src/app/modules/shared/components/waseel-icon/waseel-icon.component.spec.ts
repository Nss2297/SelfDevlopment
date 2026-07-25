import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaseelIconComponent } from './waseel-icon.component';

describe('WaseelIconComponent', () => {
  let component: WaseelIconComponent;
  let fixture: ComponentFixture<WaseelIconComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaseelIconComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WaseelIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
