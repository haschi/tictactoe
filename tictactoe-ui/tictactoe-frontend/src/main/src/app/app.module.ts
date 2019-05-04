import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RegistrierungComponent} from './registrierung/registrierung.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AnwenderverzeichnisseComponent} from './anwenderverzeichnisse/anwenderverzeichnisse.component';
import {AnwenderverzeichnisComponent} from './anwenderverzeichnis/anwenderverzeichnis.component';
import {AnwenderComponent} from './anwender/anwender.component';
import {EigenschaftenComponent} from './anwender/eigenschaften/eigenschaften.component';
import {ZeichenauswahlComponent} from './anwender/zeichenauswahl/zeichenauswahl.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrierungComponent,
    AnwenderverzeichnisseComponent,
    AnwenderverzeichnisComponent,
    AnwenderComponent,
    EigenschaftenComponent,
    ZeichenauswahlComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
