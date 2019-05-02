import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Anwenderverzeichnis} from '../anwenderverzeichnisse/anwenderverzeichnisse.model';

@Injectable()
export class AnwenderverzeichnisService {
  constructor(private http: HttpClient) {
  }

  getAnwenderverzeichnis(id: string): Observable<Anwenderverzeichnis> {
    console.info(`Anwenderverzeichnis Service: Get Anwenderverzeichnis: ${id}`);
    return this.http.get<Anwenderverzeichnis>(`/api/anwenderverzeichnisse/${id}`);
  }
}
