import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AnwenderComponent} from './anwender.component';

describe('AnwenderComponent', () => {
  let component: AnwenderComponent;
  let fixture: ComponentFixture<AnwenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AnwenderComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnwenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
