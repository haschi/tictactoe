import {async, TestBed} from '@angular/core/testing';

import {AnwenderComponent} from './anwender.component';
import {APP_BASE_HREF} from '@angular/common';
import {ActivatedRoute, convertToParamMap} from '@angular/router';
import {of} from 'rxjs';
import {AnwenderserviceService} from './anwenderservice.service';
import {NO_ERRORS_SCHEMA} from "@angular/core";

describe('AnwenderComponent', () => {

  const spy = jasmine.createSpyObj('AnwenderserviceService', ['getAnwender']);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AnwenderComponent],
      providers: [
        {provide: APP_BASE_HREF, useValue: '/'},
        {provide: ActivatedRoute, useValue: {paramMap: of(convertToParamMap({id: '4711'}))}}
      ],
      schemas: [NO_ERRORS_SCHEMA]
    })
      .overrideComponent(AnwenderComponent, {
        set: {
          providers: [{
            provide: AnwenderserviceService,
            useValue: spy
          }]
        }
      })
      .compileComponents().then(() => {
    });
  }));

  beforeEach(() => {
  });

  it('should create', (done: DoneFn) => {
    spy.getAnwender.and.returnValue(of({eigenschaften: {name: 'Matthias'}}));

    const fixture = TestBed.createComponent(AnwenderComponent);
    fixture.detectChanges();

    expect(fixture.componentInstance.anwender$).toBeTruthy();
    fixture.componentInstance.anwender$.subscribe(
      value => {
        expect(value).toEqual({eigenschaften: {name: 'Matthias'}});
        done();
      }
    );
  });
});
