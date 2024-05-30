import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../service/auth.service';
import { ActivatedRoute, Router, UrlTree } from '@angular/router';
import { UtenteService } from '../../../service/utente.service';
import { Utente } from '../../models/utente';


@Component({
  selector: 'app-login-utente',
  templateUrl: './login-utente.component.html',
  styleUrl: './login-utente.component.css'
})
export class LoginUtenteComponent {
  prova: FormGroup;
  private tokenKey = 'authToken';
  loginMessage: string | null;
  giornoCorrente: number;



  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private utenteService :UtenteService
    ) {
    const dataCorrente = new Date();
    this.giornoCorrente = dataCorrente.getDay();
    this.loginMessage = this.route.snapshot.queryParams['message'] || null;

    this.prova = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      
    });
    
  }

  login() {
    this.router.navigate(['/loginUtente']);
  }

  registrazione() {
    this.router.navigate(['/registraUtente']);
  }

  home() {
    this.router.navigate(['/home']);
  }


  
  onSubmit() {
    if (this.prova.valid) {
      const userData = this.prova.value;
      this.http.post('http://localhost:8080/api/user/login', userData).subscribe(
        (response: any) => {

          const userLogged = new Utente(response.utente.id, response.utente.nome, response.utente.cognome, response.utente.email, response.utente.email, response.utente.email, response.utente.telefono);
          // this.utenteService.setUser(userLogged);

          // console.log("nome " + userLogged.getNome());
          // Gestisci la risposta di successo
          console.log('Risposta del server:', response);


          // Salva il token nella memoria locale di Chrome
          this.authService.setToken(response.token);

          this.router.navigate(['home']);

          // Opzionale: reindirizza l'utente a una rotta protetta
        },
        (error) => {
          // Gestisci l'errore
          console.error('Errore durante la chiamata:', error);
        }
      );
    } else {
      // Il form non è valido, gestisci l'errore di convalida
      console.error('Il form non è valido');
    }
  }

  loginAdmin(){
    this.router.navigate(['/loginAmministratore']);


  }


}
