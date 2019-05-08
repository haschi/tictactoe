import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EigenschaftenComponent} from './eigenschaften.component';
import {Component} from '@angular/core';
import {Eigenschaften} from '../anwender.model';
import {By} from "@angular/platform-browser";

describe('EigenschaftenComponent', () => {

  // noinspection AngularMissingOrInvalidDeclarationInModule
  @Component({
    selector: 'app-test-host',
    template: '<app-eigenschaften [eigenschaften]="eigenschaften"></app-eigenschaften>'
  })
  class EigenschaftenTestHostComponent {
    eigenschaften: Eigenschaften = {name: 'Matthias'};
  }


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EigenschaftenComponent, EigenschaftenTestHostComponent]
    })
      .compileComponents().then(() => {
    });
  }));

  let fixture: ComponentFixture<EigenschaftenTestHostComponent>;
  beforeEach(() => {
    fixture = TestBed.createComponent(EigenschaftenTestHostComponent);
    fixture.detectChanges();
  });

  it('host should create', () => {
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('host should contain eigenschaften component', () => {
    const paragraph = fixture.debugElement.query(By.css('p'));
    const p: HTMLElement = paragraph.nativeElement;
    expect(p.textContent).toEqual('Matthias');
  });
});
