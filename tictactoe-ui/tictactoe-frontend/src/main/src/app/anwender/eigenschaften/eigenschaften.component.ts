import {Component, Input, OnInit} from '@angular/core';
import {Anwender} from "../anwender.model";

@Component({
  selector: 'app-eigenschaften',
  templateUrl: './eigenschaften.component.html',
  styleUrls: ['./eigenschaften.component.less']
})
export class EigenschaftenComponent implements OnInit {

  @Input() anwender: Anwender;

  constructor() {
  }

  ngOnInit() {
  }

}
