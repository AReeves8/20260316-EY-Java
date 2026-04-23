import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteConfirmationModal } from './delete-confirmation-modal';

describe('DeleteConfirmationModal', () => {
  let component: DeleteConfirmationModal;
  let fixture: ComponentFixture<DeleteConfirmationModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteConfirmationModal],
    }).compileComponents();

    fixture = TestBed.createComponent(DeleteConfirmationModal);
    component = fixture.componentInstance;
    // `visible` and `recordName` are required inputs — set them before
    // running change detection or the template throws NG0950.
    fixture.componentRef.setInput('visible', false);
    fixture.componentRef.setInput('recordName', 'Test');
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
