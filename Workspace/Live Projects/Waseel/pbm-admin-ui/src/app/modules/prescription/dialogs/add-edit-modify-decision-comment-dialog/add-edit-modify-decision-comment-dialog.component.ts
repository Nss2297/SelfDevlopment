import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'add-edit-modify-decision-comment-dialog',
  templateUrl: './add-edit-modify-decision-comment-dialog.component.html',
  styles: [
  ]
})
export class AddEditModifyDecisionCommentDialogComponent {

  @Input('dialogMode')
  dialogMode: 'add' | 'edit' = 'add';
  
  @Input('drugCode')
  drugCode?: string;

  @Input('decisionDescription')
  decisionDescription?: string;

  @Input('onClose')
  onClose?: (data: any) => void;

  commentboxError = false;

  closeDialog(comment:any) {
    if (this.onClose) {
      this.onClose({drugCode: this.drugCode, comment: comment, decisionDescription: this.decisionDescription, button:"cancel"});
    }
  }

  saveComments(comment:any){
    if(comment){
      if (this.onClose) {
        this.onClose({drugCode: this.drugCode, comment: comment, decisionDescription: this.decisionDescription, button:"save"});
      }
    } else{
      this.commentboxError = true;
    }
  }
}
