import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Anwender} from './anwender.model';

@Injectable()
export class AnwenderserviceService {

  constructor(private http: HttpClient) {
  }

  getEigenschaften(id: string): Observable<Anwender> {
    return this.http.get<Anwender>(`/api/anwender/${id}`);
  }
}
