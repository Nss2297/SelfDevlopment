import { AfterViewInit, Component, ElementRef, EventEmitter, forwardRef, Host, Input, OnInit, Optional, Output, Renderer2 } from '@angular/core';
import { ControlContainer, FormGroupDirective, NG_VALUE_ACCESSOR, SelectControlValueAccessor, Validators } from '@angular/forms';

@Component({
  selector: 'waseel-select',
  templateUrl: './select.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectComponent),
      multi: true

    }
  ]
})
export class SelectComponent extends SelectControlValueAccessor implements OnInit, AfterViewInit {

  @Input("id")
  id?: string = crypto.randomUUID();

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
  disabled: boolean = false;

  @Input("placeholder")
  placeholder?: string = '';

  @Input("wrapperCssClass")
  wrapperCssClass?: string = '';

  @Input("cssClass")
  cssClass?: string = '';

  @Output()
  onSelectionChange: EventEmitter<string> = new EventEmitter();

  isRequired: boolean = false;

  constructor(_renderer: Renderer2, _elementRef: ElementRef,
    @Optional() @Host() private parent: ControlContainer) {
    super(_renderer, _elementRef);
  }

  ngOnInit() {
    if (!this.placeholder) {
      this.placeholder = '';
    }
    if (!this.wrapperCssClass) {
      this.wrapperCssClass = '';
    }
    if (!this.cssClass) {
      this.cssClass = '';
    }
    this.options?.forEach(option => {
      if (option.selected) {
        this.value = option.key;
      }
    })
  }

  ngAfterViewInit(): void {
    this.isRequired = (this.parent && (this.parent as FormGroupDirective).form.get(this.name)?.hasValidator(Validators.required)) || false;
  }

  onSelectionChangeFn() {
    this.onSelectionChange.emit(this.value);
    this.options = this.options?.map(option => ({ ...option, selected: option.key == this.value }));
  }

  override value: any;
  override onTouched: () => void = () => { };
  override onChange: (_: any) => void = () => { };
  override set compareWith(fn: (o1: any, o2: any) => boolean) {
    throw new Error('Method not implemented.');
  }
  compareFn(o1: any, o2: any) { return o1 && o2 ? o1.key === o2.key : o1 === o2 };
  override writeValue(value: any): void {
    if (value !== this.value) {
      this.value = value;
      this.onChange(value);
    }
  }
  override registerOnChange(fn: (value: any) => any): void {
    this.onChange = fn;
  }
  override registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

}
