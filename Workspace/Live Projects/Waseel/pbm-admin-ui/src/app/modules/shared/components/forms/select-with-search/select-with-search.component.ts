import { AfterViewInit, Component, EventEmitter, forwardRef, Host, Input, OnInit, Optional, Output } from '@angular/core';
import { ControlContainer, ControlValueAccessor, FormControl, FormGroupDirective, NG_VALUE_ACCESSOR, Validators } from '@angular/forms';

@Component({
  selector: 'waseel-select-with-search',
  templateUrl: './select-with-search.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectWithSearchComponent),
      multi: true
    }
  ]
})
export class SelectWithSearchComponent implements ControlValueAccessor, OnInit, AfterViewInit {

  @Input("id")
  id?: string = (Math.floor(Math.random() * 100000)).toString();

  @Input("name")
  name: string = '';

  @Input('label')
  label?: string = '';

  @Input('error')
  error?: string = '';

  @Input('options')
  options?: {
    key: string;
    value: string;
    selected?: boolean;
  }[];

  @Input()
  initialOption?: {
    key: string;
    value: string;
    selected?: boolean;
  };

  @Input("placeholder")
  placeholder?: string = '';

  @Input("wrapperCssClass")
  wrapperCssClass?: string = '';

  @Input("cssClass")
  cssClass?: string = '';

  @Output()
  onSearch: EventEmitter<string> = new EventEmitter();

  @Input("disabled")
  disabled?: boolean = false;

  searchFieldControl: FormControl = new FormControl();

  private _value = '';
  valuePreview: string = '';

  public onChange: any = Function.prototype;

  public onTouched: any = Function.prototype;

  isRequired: boolean = false;

  constructor(@Optional() @Host() private parent: ControlContainer) {

  }

  ngOnInit(): void {
    if (this.initialOption != undefined && this.options?.find(option => option.key == this.initialOption?.key) == undefined) {
      this.options?.push(this.initialOption);
    }
    const selectedItem = this.options?.find(opt => opt.selected);
    if (selectedItem) {
      this.value = selectedItem.value;
    }
  }

  ngAfterViewInit(): void {
    this.isRequired = (this.parent && (this.parent as FormGroupDirective).form.get(this.name)?.hasValidator(Validators.required)) || false;
    this.options?.forEach(option => {
      if (option.selected) {
        this.selectItem(option);
      }
    })
  }

  onSearchInputFieldKeyUp(event: KeyboardEvent) {
    this.onSearch.emit(this.searchFieldControl.value);
  }

  selectItem(option: {
    key: string;
    value: string;
    selected?: boolean;
  }) {
    this.value = option.key;
    this.valuePreview = option.value;
    this.options = this.options?.map(op => ({ ...op, selected: op.key == option.key }));
  }



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
    this._value = value || '';
    const selectedOption = this.options?.find(option => option.key == value);
    this.valuePreview = selectedOption?.value || '';

  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

}
