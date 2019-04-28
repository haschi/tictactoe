import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';


import {Anwenderverzeichnis} from '../anwenderverzeichnisse/anwenderverzeichnisse.model';
import {BehaviorSubject, Observable} from 'rxjs';
import {AnwenderverzeichnisService} from './anwenderverzeichnis.service';

@Component({
  selector: 'app-anwenderverzeichnis',
  templateUrl: './anwenderverzeichnis.component.html',
  styleUrls: ['./anwenderverzeichnis.component.less']
})
export class AnwenderverzeichnisComponent implements OnInit {

  anwenderverzeichnis$: Observable<Anwenderverzeichnis> = new BehaviorSubject({id: '34'});

  constructor(private route: ActivatedRoute, private service: AnwenderverzeichnisService) {
  }

  ngOnInit() {
    console.info('ngOnInit');
    this.anwenderverzeichnis$ = this.service.getAnwenderverzeichnis('4711');

    console.info('ngOnInit: subscribed');
    //   .pipe(
    //   tap(result => console.info("GOT " + JSON.stringify(result))),
    //   catchError(err => {console.info(err); return throwError("Fehler"); })
    // );

    // this.anwenderverzeichnis$ = this.route.paramMap.pipe(
    //   tap(params => console.info('AnwenderverzeichnisComponent#ngOnInit(): params  = ' + JSON.stringify(params))),
    //   tap(params => console.info('HTTP Client: ' + JSON.stringify(this.http !== undefined))),
    //   flatMap(params => {
    //     const id = params.get('id');
    //     console.info('HTTP GET ' + id);
    //     console.info('HTTP Client: ' + JSON.stringify(this.http !== undefined));
    //     // return of({id: '4712'});
    //     return this.http.get<Anwenderverzeichnis>('http://api/anwenderverzeichnisse/' + id, {observe: 'response'}).pipe(
    //       catchError(error => {
    //         console.error(JSON.stringify(error));
    //         return throwError('Fehler beim Laden des Anwenderverzeichnisses');
    //       }),
    //       tap(result => console.info('HTTP Request durchgeführt' + JSON.stringify(result)))
    //     );
    //   }),
    //   tap(verzeichnis => console.info('AnwenderverzeichnisComponent#ngOnInit(): http.get() = ' + JSON.stringify(verzeichnis)))
    // );
  }
}
