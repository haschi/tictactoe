import {Component, Input} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {Observable, of} from 'rxjs';

@Component({
  selector: 'app-registrierung',
  templateUrl: './registrierung.component.html',
  styleUrls: ['./registrierung.component.less']
})
export class RegistrierungComponent {

  @Input() anwenderverzeichnisId: string;

  registrierungForm = this.fb.group({
    name: ['', Validators.required]
  });

  constructor(private fb: FormBuilder, private http: HttpClient) {
  }

  private static handleError(error: HttpErrorResponse): Observable<any> {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
      return of(error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        'body was: ' + JSON.stringify(error.error));
      return of(error.status);
    }
  }

  onSubmit() {
    this.benutzerRegistrieren()
      .subscribe(response => {
        console.info('Ergebnis: ' + JSON.stringify(response));
      });
  }

  benutzerRegistrieren(): Observable<any> {

    return this.http.post('/api/anwenderverzeichnisse/' + this.anwenderverzeichnisId,
      {eigenschaften: this.registrierungForm.value}, {observe: 'response'})
      .pipe(
        catchError(RegistrierungComponent.handleError)
      );
  }
}
