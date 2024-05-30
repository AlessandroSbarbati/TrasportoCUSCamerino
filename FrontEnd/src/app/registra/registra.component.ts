import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router, UrlTree } from '@angular/router';

@Component({
  selector: 'app-registra',
  templateUrl: './registra.component.html',
  styleUrl: './registra.component.css'
})
export class RegistraComponent {

  prova: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, public dialog: MatDialog, private router: Router, private route: ActivatedRoute,) {
    this.prova = this.fb.group({
      nome: ['', [Validators.required,]],
      numero: ['', [Validators.required,]],
      cognome: ['', [Validators.required,]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      telefono: ['', [Validators.required,]],
      
    });
  }

  
  onSubmit() {
    if (this.prova.valid) {
      const userData = this.prova.value;
      this.http.post('http://localhost:8080/api/user/createUser', userData)
        .subscribe(
          (response) => {
            // Gestisci la risposta di successo
            console.log('Risposta del server:', response);
            alert("Registrazione avvenuta con successo!")
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
  home() {
    this.router.navigate(['/homeAmministratore']);
  }

  login() {
    this.router.navigate(['/loginUtente']);
  }

}
