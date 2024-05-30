import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Observable, catchError, throwError, of } from 'rxjs';
import { Utente } from '../models/utente';
import { Amministratore } from '../models/amministratore';
import { UtenteService } from '../../service/utente.service';
import { UserService } from '../../service/user.service';
import { BookingService } from '../../service/booking.service';
import { BookingRequest } from '../models/booking-request';
import { Router } from '@angular/router';


@Component({
  selector: 'app-utente',
  templateUrl: './utente.component.html',
  styleUrl: './utente.component.css'
})
export class UtenteComponent implements OnInit{


  utente!:Amministratore;
 
  users: Utente[] = [];  // Aggiungi questa riga per memorizzare gli utenti

  
  constructor(
    private router: Router,
    private authService: AuthService,
    private user :UserService,
    
    ) {

    }



  ngOnInit() {
    this.recuperaTutti();
    this.recuperadati();
    this.printCurrentDay();
    

  }





  //METODO CHE RECUPERA TUTTI GLI UTENTI
  recuperaTutti(){
    const token = this.authService.getToken();
    if(token) {
     this.user.getUsers(token).subscribe(
       users => {
         this.users = users;
       },
       error => {
         console.error('Errore durante il recupero degli utenti:', error);
       }
     );
   } else {
     console.error('Token assente');
   }
  }


  //METODO CHE RECUPERA I DATI DELLA PERSONA LOGGATA E LI SALVA SU THIS.UTENTE
  recuperadati(){
    const token = this.authService.getToken();
    if(token) {
      this.user.getAdmin(token).subscribe(
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
    if(this.utente?.role == "ADMIN"){
      return true
    }else{
      return false
    }
  }



  reloadUsers() {
    const token = this.authService.getToken();
    if(token){
    this.user.getUsers(token).subscribe(
      (users) => {
        // Assegna gli utenti alla proprietà users del componente
        this.users = users;
      },
      (error) => {
        // Gestisci l'errore, ad esempio mostrando un messaggio all'utente
        console.error('Errore durante il recupero degli utenti:', error);
      }
    );
  }
}

printCurrentDay(): void {
  const currentDate = new Date();
  const currentDay = currentDate.toLocaleDateString('it-IT', { weekday: 'long' });
  console.log('Oggi è:', currentDay);
}



logOut(){
  this.authService.logout();
  this.router.navigate(['/loginAmministratore']);
}

//ROTTE
prenota() {
  this.router.navigate(['/lunedi']);
}


home() {
  this.router.navigate(['/homeAmministratore']);
}



}
