import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'waseel-alert',
  templateUrl: './waseel-alert.component.html',
  styleUrls: []
})
export class WaseelAlertComponent implements OnInit {
  @Input('variant')
  variant?: 'danger' | 'success' | 'warning' | 'info';

  constructor() {

  }

  ngOnInit(): void {
    if (!this.variant) {
      this.variant = 'danger';
    }
  }

}