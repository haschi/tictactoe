import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegistrierungComponent} from "./registrierung/registrierung.component";

const routes: Routes = [
  {path: 'registrierung', component: RegistrierungComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
