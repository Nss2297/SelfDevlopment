import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IdfManagementPageComponent } from './idf-management-page.component';

describe('IdfManagementPageComponent', () => {
  let component: IdfManagementPageComponent;
  let fixture: ComponentFixture<IdfManagementPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IdfManagementPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IdfManagementPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
