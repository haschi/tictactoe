import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {flatMap, map} from 'rxjs/operators';
import {Subject} from 'rxjs';
import {Anwenderverzeichnis, Anwenderverzeichnisse} from './anwenderverzeichnisse.model';
import * as oboe from 'oboe';
import {isUndefined} from 'util';
import {Router} from "@angular/router";

@Component({
  selector: 'app-anwenderverzeichnisse',
  templateUrl: './anwenderverzeichnisse.component.html',
  styleUrls: ['./anwenderverzeichnisse.component.less']
})
export class AnwenderverzeichnisseComponent implements OnInit {

  verzeichnisse$: Subject<Anwenderverzeichnisse> = new Subject<Anwenderverzeichnisse>();

  constructor(private http: HttpClient, private router: Router) {
  }


  private static onStart(status) {
    console.info(`GET /api/anwenderverzeichnisse => ${status}`);
  }

  fallsAnwenderverzeichnisse(item: Anwenderverzeichnisse) {
    console.info('falls Anwenderverzeichnisse');
    if (isUndefined(this.verzeichnisse$)) {
      console.error('Subject ist undefiniert');
    } else {
      this.verzeichnisse$.next(item);
    }
  }

  ngOnInit() {
    oboe({url: '/api/anwenderverzeichnisse', headers: {Accept: 'application/stream+json'}})
      .start(AnwenderverzeichnisseComponent.onStart)
      .node('verzeichnisse', (item: Anwenderverzeichnisse) => {
        this.verzeichnisse$.next(item);
        console.info('Lesemodel aktualisiert');
      })
      .node('verzeichnisse', this.fallsAnwenderverzeichnisse);

    this.verzeichnisse$.subscribe(item => {
      console.info('Verzeichnisse: ' + JSON.stringify(item));
    });
  }

  anlegen() {
    console.info('Anwenderverzeichnis anlegen');
    this.http.post('/api/anwenderverzeichnisse', {}, {observe: 'response'})
      .pipe(
        map(response => response.headers.get('Location')),
        flatMap(location => this.http.get<Anwenderverzeichnis>(location))
        // TODO: flatMap GET LOCATION => Anwenderverzeichnis => ID => routing
      )
      .subscribe(value => {
        console.info(value);
        this.router.navigate(['/anwenderverzeichnis', value.id]);
      }, error => console.error(error));
  }
}
