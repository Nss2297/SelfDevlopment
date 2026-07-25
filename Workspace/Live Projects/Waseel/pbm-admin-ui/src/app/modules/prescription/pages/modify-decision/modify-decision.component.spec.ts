import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyDecisionComponent } from './modify-decision.component';

describe('ModifyDecisionComponent', () => {
  let component: ModifyDecisionComponent;
  let fixture: ComponentFixture<ModifyDecisionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyDecisionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyDecisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
