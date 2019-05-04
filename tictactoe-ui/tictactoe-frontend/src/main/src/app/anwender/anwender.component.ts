import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Anwender} from './anwender.model';
import {ActivatedRoute} from '@angular/router';
import {AnwenderserviceService} from './anwenderservice.service';
import {flatMap} from 'rxjs/operators';

@Component({
  selector: 'app-anwender',
  templateUrl: './anwender.component.html',
  styleUrls: ['./anwender.component.less'],
  providers: [AnwenderserviceService]
})
export class AnwenderComponent implements OnInit {

  anwender$: Observable<Anwender>;

  constructor(
    private activeRoute: ActivatedRoute,
    private service: AnwenderserviceService) {
  }

  ngOnInit() {
    this.anwender$ = this.activeRoute.paramMap.pipe(
      flatMap(params => this.service.getAnwender(params.get('id')))
    );
  }
}
