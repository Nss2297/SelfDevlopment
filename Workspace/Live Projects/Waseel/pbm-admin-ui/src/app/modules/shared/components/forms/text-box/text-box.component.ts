import { AfterContentChecked, AfterViewInit, Component, EventEmitter, forwardRef, Host, Input, OnInit, Optional, Output, ViewChild } from '@angular/core';
import { ControlContainer, ControlValueAccessor, FormControl, FormGroupDirective, NgModel, NG_VALUE_ACCESSOR, Validators } from '@angular/forms';

@Component({
  selector: 'waseel-textbox',
  templateUrl: './text-box.component.html',
  styleUrls: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TextBoxComponent),
      multi: true
    }
  ]
})
export class TextBoxComponent implements ControlValueAccessor, OnInit, AfterContentChecked {

  @ViewChild('inputModel')
  inputModel?: NgModel;

  @Input("id")
  id?: string = (Math.floor(Math.random() * 100000)).toString();

  @Input("name")
  name: string = '';

  @Input("disabled")
  disabled: boolean | string = false;

  @Input("multiline")
  multiline: boolean = false;

  @Input("rows")
  rows?: number;

  @Input("type")
  type?: 'text' | 'email' | 'password' | 'hidden' | 'date' | 'number' = 'text';

  @Input("placeholder")
  placeholder?: string = '';

  @Input("autocomplete")
  autocomplete?: string = 'off';

  @Input("endAdornment")
  endAdornment?: string = '';

  @Input("label")
  label?: string = '';

  @Input("error")
  error?: string = '';

  @Input("maxLength")
  maxLength: number = 1000000000;

  @Input("minLength")
  minLength: number = 0;

  @Output()
  onSearch: EventEmitter<string> = new EventEmitter();

  isRequired: boolean = false;
  isDatepicker = false;

  showPassword = false;

  constructor(@Optional() @Host() private parent: ControlContainer) {

  }
  private _value = '';

  public onChange: any = Function.prototype;

  public onTouched: any = Function.prototype;

  get value(): any {
    return this._value;
  }

  set value(v: any) {
    if (v !== this._value) {
      this._value = v;
      this.onChange(v);
    }
  }

  writeValue(value: any): void {
    if (value !== undefined)
      this._value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  ngOnInit(): void {
    if (this.type == 'date') {
      this.type = 'text';
      this.isDatepicker = true;
    }
  }

  ngAfterContentChecked(): void {
    this.isRequired = (this.parent && (this.parent as FormGroupDirective).form.get(this.name)?.hasValidator(Validators.required)) || false;
  }

  onSearchInputFieldKeyUp() {
    this.onSearch.emit(this.value);
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
    this.type = this.showPassword ? 'text' : 'password';
  }
}