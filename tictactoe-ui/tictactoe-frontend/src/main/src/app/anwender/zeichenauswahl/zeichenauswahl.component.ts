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
    console.info(`Zeichen ausgewählt: ${zeichen} für ${this.id} an ${url}`);
    this.http.patch(url, {zeichenauswahl: zeichen}, {}).subscribe(
      result => {
        console.info(`POST ${JSON.stringify(result)}`);
      },
      error => {
        console.error(JSON.stringify(error));
      }
    );
  }
}
