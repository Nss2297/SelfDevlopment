import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SfdaListComponent } from './sfda-list.component';

describe('SfdaListComponent', () => {
  let component: SfdaListComponent;
  let fixture: ComponentFixture<SfdaListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SfdaListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SfdaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
