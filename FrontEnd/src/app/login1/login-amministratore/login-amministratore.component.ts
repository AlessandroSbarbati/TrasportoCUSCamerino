import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../service/auth.service';
import { ActivatedRoute, Router, UrlTree } from '@angular/router';
import { UtenteService } from '../../../service/utente.service';
import { Utente } from '../../models/utente';


@Component({
  selector: 'app-login-amministratore',
  templateUrl: './login-amministratore.component.html',
  styleUrl: './login-amministratore.component.css'
})
export class LoginAmministratoreComponent {


  reg: FormGroup;
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

    this.reg = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      
    });
    
  }


  
  loginAdmin() {
    if (this.reg.valid) {
      const userData = this.reg.value;
      this.http.post('http://localhost:8080/api/admin/loginAdmin', userData).subscribe(
        (response: any) => {

          // Gestisci la risposta di successo
          console.log('Risposta del server:', response);


          // Salva il token nella memoria locale di Chrome
          this.authService.setToken(response.token);

          this.router.navigate(['homeAmministratore']);

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

  //rotte

  login() {
    this.router.navigate(['/loginUtente']);
  }

  registrazione() {
    this.router.navigate(['/registraUtente']);
  }

  home() {
    this.router.navigate(['/home']);
  }





}
