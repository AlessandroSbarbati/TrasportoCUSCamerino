import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { Utente } from '../models/utente';
import { UserService } from '../../service/user.service';
import { Amministratore } from '../models/amministratore';

@Component({
  selector: 'app-home-amministratore',
  templateUrl: './home-amministratore.component.html',
  styleUrl: './home-amministratore.component.css'
})
export class HomeAmministratoreComponent implements OnInit {


user!:Amministratore ;



  constructor(
    private router: Router,
    private authService : AuthService,
    private userService : UserService
    ) {}



  ngOnInit(): void {
    this.recuperadati();
  }


  
 //METODO PER AUTENTICARE 
 isUser(): boolean {
  if(this.user?.role == "ADMIN"){
    return true
  }else{
    return false
  }
}

    //RECUPERA I DATI DELLA PERSONA LOGGATA
    recuperadati(){
      const token = this.authService.getToken();
      if(token) {
        this.userService.getAdmin(token).subscribe(
          users => {
            console.log(users)
            this.user = users;
          },
          error => {
            console.error('Errore durante il recupero degli utenti:', error);
          }
        );
      } else {
        console.error('Token assente');
    }
      
    }

  //effettua il logout svuotando il token e riportandoti al login
  logOut(){
    this.authService.logout();
    this.router.navigate(['/loginAmministratore']);
  }
  
  //ROTTE
  home() {
    this.router.navigate(['/homeAmministratore']);
  }

  prenota() {
    this.router.navigate(['/prenotazioni']);
  }

  paginaUtenti() {
    this.router.navigate(['/utente']);
  }


}
