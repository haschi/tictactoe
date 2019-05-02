import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';


import {Anwenderverzeichnis} from '../anwenderverzeichnisse/anwenderverzeichnisse.model';
import {BehaviorSubject, Observable} from 'rxjs';
import {AnwenderverzeichnisService} from './anwenderverzeichnis.service';
import {flatMap} from 'rxjs/operators';

@Component({
  selector: 'app-anwenderverzeichnis',
  templateUrl: './anwenderverzeichnis.component.html',
  styleUrls: ['./anwenderverzeichnis.component.less'],
  providers: [AnwenderverzeichnisService]
})
export class AnwenderverzeichnisComponent implements OnInit {

  anwenderverzeichnis$: Observable<Anwenderverzeichnis> = new BehaviorSubject({id: '34'});

  constructor(private route: ActivatedRoute, private service: AnwenderverzeichnisService) {
  }

  ngOnInit() {
    this.anwenderverzeichnis$ = this.route.paramMap.pipe(
      flatMap(params => this.service.getAnwenderverzeichnis(params.get('id')))
    );
  }
}
