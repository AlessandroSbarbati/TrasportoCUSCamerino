import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistraComponent } from './registra/registra.component';
import { UtenteComponent } from './utente/utente.component';
import { AuthGuard } from './auth.guard';
import { HomeComponent } from './home/home.component';
import { LunediComponent } from './prenotazione/lunedi/lunedi.component';
import { MartediComponent } from './prenotazione/martedi/martedi.component';
import { MercolediComponent } from './prenotazione/mercoledi/mercoledi.component';
import { LoginUtenteComponent } from './login1/login-utente/login-utente.component';
import { RegistrazioneUtenteComponent } from './registrazione/registrazione-utente/registrazione-utente.component';
import { HomeAmministratoreComponent } from './home-amministratore/home-amministratore.component';
import { LoginAmministratoreComponent } from './login1/login-amministratore/login-amministratore.component';
import { PrenotazioneAmministratoreComponent } from './prenotazione-amministratore/prenotazione-amministratore.component';
import { GiovediComponent } from './prenotazione/giovedi/giovedi.component';
import { VenerdiComponent } from './prenotazione/venerdi/venerdi.component';
import { SabatoComponent } from './prenotazione/sabato/sabato.component';
import { DomenicaComponent } from './prenotazione/domenica/domenica.component';

const routes: Routes = [

  {  path: 'registra',  component: RegistraComponent },
  {  path: 'registraUtente',  component: RegistrazioneUtenteComponent },
  {  path: 'loginUtente',  component: LoginUtenteComponent },
  {  path: 'loginAmministratore',  component: LoginAmministratoreComponent },
  {  path: 'prenotazioni',  component: PrenotazioneAmministratoreComponent },
  {  path: 'utente',  component: UtenteComponent, canActivate: [AuthGuard]  },
  {  path: 'lunedi',  component: LunediComponent, canActivate: [AuthGuard]  },
  {  path: 'martedi',  component: MartediComponent, canActivate: [AuthGuard]  },
  {  path: 'mercoledi',  component: MercolediComponent, canActivate: [AuthGuard]  },
  {  path: 'giovedi',  component: GiovediComponent, canActivate: [AuthGuard]  },
  {  path: 'venerdi',  component: VenerdiComponent, canActivate: [AuthGuard]  },
  {  path: 'sabato',  component: SabatoComponent, canActivate: [AuthGuard]  },
  {  path: 'domenica',  component: DomenicaComponent, canActivate: [AuthGuard]  },
  {  path: 'home',  component: HomeComponent, canActivate: [AuthGuard] },
  {  path: 'homeAmministratore',  component: HomeAmministratoreComponent },
  {  path: '',  component: LoginUtenteComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
