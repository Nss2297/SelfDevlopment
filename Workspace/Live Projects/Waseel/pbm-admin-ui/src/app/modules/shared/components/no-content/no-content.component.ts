import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'no-content',
  templateUrl: './no-content.component.html',
  styles: []
})
export class NoContentComponent implements OnInit {

  @Input("message")
  message?: string;

  @Input("subtitle")
  subtitle?: string = '';
    
  @Input("recordssubtitle")
  recordssubtitle?: string = '';

  @Input("formularysubtitle")
  formularysubtitle?: string = '';

  @Input("isSearchAgain")
  isSearchAgain?: boolean;

  @Input ("returnUrl")
  returnUrl: string = '';

  emptyStateMessage = "No prescriptions found!";

  constructor(private route:Router){

  }


  ngOnChanges(changes: SimpleChanges) {
    if(changes["message"]?.firstChange == false){
      this.emptyStateMessage = changes["message"].currentValue;
    } 
    
    if(changes["isSearchAgain"].firstChange == false){
      this.isSearchAgain = changes["isSearchAgain"].currentValue;
    }
  }

  ngOnInit(): void {
    if (this.message && this.message?.trim()) {
      this.emptyStateMessage = this.message;
    }
  }

  navigateToReturnUrl(){
    this.route.navigate([this.returnUrl]);
  }
}