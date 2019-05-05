import {Component, Input, OnInit} from '@angular/core';
import {Eigenschaften} from "../anwender.model";

@Component({
  selector: 'app-eigenschaften',
  templateUrl: './eigenschaften.component.html',
  styleUrls: ['./eigenschaften.component.less']
})
export class EigenschaftenComponent implements OnInit {

  @Input() eigenschaften: Eigenschaften;

  constructor() {
  }

  ngOnInit() {
  }

}
