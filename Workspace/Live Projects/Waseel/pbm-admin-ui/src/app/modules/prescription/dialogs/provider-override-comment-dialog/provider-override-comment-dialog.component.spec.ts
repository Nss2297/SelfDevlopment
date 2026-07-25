import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderOverrideCommentDialogComponent } from './provider-override-comment-dialog.component';

describe('ProviderOverrideCommentDialogComponent', () => {
  let component: ProviderOverrideCommentDialogComponent;
  let fixture: ComponentFixture<ProviderOverrideCommentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProviderOverrideCommentDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProviderOverrideCommentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
