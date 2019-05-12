import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegistrierungComponent} from './registrierung/registrierung.component';
import {AnwenderverzeichnisseComponent} from './anwenderverzeichnisse/anwenderverzeichnisse.component';
import {AnwenderverzeichnisComponent} from './anwenderverzeichnis/anwenderverzeichnis.component';
import {AnwenderComponent} from './anwender/anwender.component';

const routes: Routes = [
  {path: 'registrierung', component: RegistrierungComponent},
  {path: 'anwenderverzeichnisse', component: AnwenderverzeichnisseComponent},
  {path: 'anwenderverzeichnis/:id', component: AnwenderverzeichnisComponent},
  {path: 'anwender/:id', component: AnwenderComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
