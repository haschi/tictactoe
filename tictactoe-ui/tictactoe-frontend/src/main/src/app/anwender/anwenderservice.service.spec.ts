import {inject, TestBed} from '@angular/core/testing';

import {AnwenderserviceService} from './anwenderservice.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

describe('AnwenderserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [AnwenderserviceService]
  }));

  it('should be created', () => {
    const service: AnwenderserviceService = TestBed.get(AnwenderserviceService);
    expect(service).toBeTruthy();
  });

  it('Kann Eigenschaften des Anwenders lesen', inject([AnwenderserviceService, HttpTestingController],
    (service: AnwenderserviceService, http: HttpTestingController) => {
      service.getEigenschaften('4711').subscribe(value => {
        expect(value).toEqual({eigenschaften: {name: 'Matthias'}});
      });
    }));
});
