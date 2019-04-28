import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {inject, TestBed} from '@angular/core/testing';
import {AnwenderverzeichnisService} from "./anwenderverzeichnis.service";

interface Data {
  name: string;
}

describe('AnwenderverzeichnisService', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AnwenderverzeichnisService]
    });
  });

  it('kann Anwederverzeichnis erzeugen', inject([AnwenderverzeichnisService], (service: AnwenderverzeichnisService) => {
    expect(service).not.toBeNull();
  }));

  it('Kann Anwenderverzeichnis lesen', inject([AnwenderverzeichnisService, HttpTestingController],
    (service: AnwenderverzeichnisService, http: HttpTestingController) => {
      service.getAnwenderverzeichnis('4712').subscribe(
        value => {
          expect(value).toEqual({id: '4712'});
        }
      );

      http.expectOne('/api/anwenderverzeichnisse/4712')
        .flush({id: '4712'});
    }));

  afterEach(inject([HttpTestingController], (http: HttpTestingController) => {
    http.verify();
  }));
});
