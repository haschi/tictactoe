import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EigenschaftenComponent} from './eigenschaften.component';

describe('EigenschaftenComponent', () => {
  let component: EigenschaftenComponent;
  let fixture: ComponentFixture<EigenschaftenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EigenschaftenComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EigenschaftenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
