import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ModalComponent } from '../../modal/modal.component';
import { ActivatedRoute, Router, UrlTree } from '@angular/router';



@Component({
  selector: 'app-registrazione-utente',
  templateUrl: './registrazione-utente.component.html',
  styleUrl: './registrazione-utente.component.css'
})
export class RegistrazioneUtenteComponent {

  reg: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, public dialog: MatDialog, private router: Router, private route: ActivatedRoute,) {
    this.reg = this.fb.group({
      nome: ['', [Validators.required,]],
      cognome: ['', [Validators.required,]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      telefono: ['', [Validators.required,]],
      codiceCUS: ['', [Validators.required,]]
    });
  }

  home() {
    this.router.navigate(['/home']);
  }

  login() {
    this.router.navigate(['/loginUtente']);
  }

  openModal(): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      width: '400px', // Specifica la larghezza del modal
      data: { } // Dati da passare al modal, se necessario
    });
  }
  
  registra() {
    if (this.reg.valid) {
      const userData = this.reg.value;
      this.http.post('http://localhost:8080/api/user/createUser', userData)
        .subscribe(
          (response) => {
            // Gestisci la risposta di successo
            alert("registrazione avvenuta con successo")
            console.log('Risposta del server:', response);
            this.reg.reset();
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

}
