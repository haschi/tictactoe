import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-zeichenauswahl',
  templateUrl: './zeichenauswahl.component.html',
  styleUrls: ['./zeichenauswahl.component.less']
})
export class ZeichenauswahlComponent implements OnInit {

  @Input() id: string;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
  }

  zeichenAusgewaehlt(zeichen: string) {
    const url = `/api/anwender/${this.id}`;
    this.http.patch(url, {zeichenauswahl: zeichen}, {}).subscribe(
      result => {
        console.error('Die Weiterleitung zum Spiel ist noch nicht implementiert');
      },
      error => {
        console.error(JSON.stringify(error));
      }
    );
  }
}
