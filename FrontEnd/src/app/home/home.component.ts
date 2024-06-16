import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { Utente } from '../models/utente';
import { UserService } from '../../service/user.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {


  utente!:Utente;
  autorizzato: boolean = true;

  
  constructor(
    private router: Router,
    private authService : AuthService,
    private user :UserService,
    ) {}


  ngOnInit(): void {
      this.recuperadati();
      this.isUser();
  }  



  //Recupero i dati dell'utente e li salvo nella variabile this.utente 
  recuperadati(){
    const token = this.authService.getToken();
    if(token) {
      this.user.getUser(token).subscribe(
        users => {
          this.utente = users;
        },
        error => {
          console.error('Errore durante il recupero degli utenti:', error);
        }
      );
    } else {
      console.error('Token assente');
  }
}


 //METODO PER AUTENTICARE 
 isUser(): boolean {
  if(this.utente?.role == "USER"){
    this.autorizzato = true;
    return true
  }else{
    this.autorizzato = false;
    return false
  }
}


 


  logOut(){
    this.authService.logout();
    this.router.navigate(['/loginUtente']);
  }

  //ROTTE
  prenota(): void {
    const currentDay = new Date().getDay(); // Ottieni il giorno corrente (0 = Domenica, 1 = Lunedì, ..., 6 = Sabato)
    
    switch (currentDay) {
      case 0:
        this.router.navigate(['/domenica']);
        break;
      case 1:
        this.router.navigate(['/lunedi']);
        break;
      case 2:
        this.router.navigate(['/martedi']);
        break;
      case 3:
        this.router.navigate(['/mercoledi']);
        break;
      case 4:
        this.router.navigate(['/giovedi']);
        break;
      case 5:
        this.router.navigate(['/venerdi']);
        break;
        case 6:
        this.router.navigate(['/sabato']);
        break;
      default:
        // Altri giorni della settimana possono essere gestiti qui
        // Ad esempio, puoi navigare a una pagina predefinita per i giorni non lavorativi
        break;
    }
  }

  
  login() {
    this.router.navigate(['/loginUtente']);
  }

  home() {
    this.router.navigate(['/home']);
  }

  cronologia() {
    this.router.navigate(['/cronologia']);
  }

}
