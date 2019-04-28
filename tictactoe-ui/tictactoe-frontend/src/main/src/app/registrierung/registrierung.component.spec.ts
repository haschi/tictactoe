import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrierungComponent} from './registrierung.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Component} from '@angular/core';

@Component({
  selector: 'test-host',
  template: '<app-registrierung [anwenderverzeichnisId]="anwenderverzeichnisId"></app-registrierung>'
})
class RegistrierungTestHostComponent {
  anwenderverzeichnisid = '4711';
}

describe('RegistrierungComponent', () => {
  let component: RegistrierungTestHostComponent;
  let fixture: ComponentFixture<RegistrierungTestHostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegistrierungTestHostComponent, RegistrierungComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrierungTestHostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
