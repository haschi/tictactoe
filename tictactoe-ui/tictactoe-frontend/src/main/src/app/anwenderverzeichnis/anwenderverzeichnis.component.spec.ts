import {async, TestBed} from '@angular/core/testing';

import {AnwenderverzeichnisComponent} from './anwenderverzeichnis.component';
import {ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute, convertToParamMap} from '@angular/router';
import {of} from 'rxjs/internal/observable/of';
import {APP_BASE_HREF} from '@angular/common';
import {AnwenderverzeichnisService} from './anwenderverzeichnis.service';
import {NO_ERRORS_SCHEMA} from '@angular/core';

describe('AnwenderverzeichnisComponent', () => {

  const spy = jasmine.createSpyObj('AnwenderverzeichnisService', ['getAnwenderverzeichnis']);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AnwenderverzeichnisComponent],
      imports: [ReactiveFormsModule],
      providers: [
        {provide: AnwenderverzeichnisService, useValue: spy},
        {provide: APP_BASE_HREF, useValue: '/'},
        {provide: ActivatedRoute, useValue: {paramMap: of(convertToParamMap({id: 4711}))}}
      ],
      // TODO: Entfernen: RegistrierungComponent deklarieren. Aber dazu muss diese den Anwenderservice benutzen
      schemas: [NO_ERRORS_SCHEMA]
    })
      .compileComponents();
  }));

  it('should create', (done: DoneFn) => {

    spy.getAnwenderverzeichnis.and.returnValue(of({id: '4711'}));

    const fixture = TestBed.createComponent(AnwenderverzeichnisComponent);
    fixture.detectChanges();

    expect(fixture.componentInstance.anwenderverzeichnis$).toBeTruthy();
    fixture.componentInstance.anwenderverzeichnis$.subscribe(
      value => {
        expect(value).toEqual({id: '4711'});
        done();
      }
    );
  });


});
