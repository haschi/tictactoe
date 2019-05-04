import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ZeichenauswahlComponent} from './zeichenauswahl.component';

describe('ZeichenauswahlComponent', () => {
  let component: ZeichenauswahlComponent;
  let fixture: ComponentFixture<ZeichenauswahlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ZeichenauswahlComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ZeichenauswahlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
