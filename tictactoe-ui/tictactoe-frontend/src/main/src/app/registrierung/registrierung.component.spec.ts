import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrierungComponent} from './registrierung.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('RegistrierungComponent', () => {
  let component: RegistrierungComponent;
  let fixture: ComponentFixture<RegistrierungComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegistrierungComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrierungComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
