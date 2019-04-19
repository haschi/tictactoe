import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegistrierungComponent} from './registrierung/registrierung.component';
import {AnwenderverzeichnisseComponent} from './anwenderverzeichnisse/anwenderverzeichnisse.component';

const routes: Routes = [
  {path: 'registrierung', component: RegistrierungComponent},
  {path: 'anwenderverzeichnisse', component: AnwenderverzeichnisseComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
