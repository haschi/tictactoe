import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RegistrierungComponent} from './registrierung.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Component} from '@angular/core';
import {RouterTestingModule} from '@angular/router/testing';

describe('RegistrierungComponent', () => {
  // noinspection AngularMissingOrInvalidDeclarationInModule
  @Component({
    selector: 'app-test-host',
    template: '<app-registrierung [anwenderverzeichnisId]="anwenderverzeichnisId"></app-registrierung>'
  })
  class RegistrierungTestHostComponent {
    anwenderverzeichnisId = '4711';
  }

  let component: RegistrierungTestHostComponent;
  let fixture: ComponentFixture<RegistrierungTestHostComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegistrierungTestHostComponent, RegistrierungComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule]
    })
      .compileComponents().then(() => {
    });
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
