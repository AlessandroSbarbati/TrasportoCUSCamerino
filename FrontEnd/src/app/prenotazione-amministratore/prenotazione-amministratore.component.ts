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
  selector: 'app-prenotazione-amministratore',
  templateUrl: './prenotazione-amministratore.component.html',
  styleUrl: './prenotazione-amministratore.component.css'
})
export class PrenotazioneAmministratoreComponent  implements OnInit{

  prenotazioni: any[] = [];  // Aggiungi questa riga per memorizzare gli prenotazioni
  prenotazioniLunedi: any[] = [];  // Aggiungi questa riga per memorizzare le prenotazioni
  prenotazioniMartedi: any[] = [];  // Aggiungi questa riga per memorizzare le prenotazioni
  prenotazioniMercoledi: any[] = [];  // Aggiungi questa riga per memorizzare le prenotazioni
  prenotazioniGiovedi: any[] = [];  // Aggiungi questa riga per memorizzare le prenotazioni
  prenotazioniVenerdi: any[] = [];  // Aggiungi questa riga per memorizzare le prenotazioni
  prenotazioniSabato: any[] = [];
  prenotazioniDomenica: any[] = [];
  tutti: boolean = true;  // Aggiungi questa riga per memorizzare gli utenti

  //CONDIZIONI BOOLEANE CHE SE TRUE FANNO VISUALIZZARE IL DIV SUL COMPONENT (*ngIf)
  lunedi : boolean = false;
  martedi : boolean = false;
  mercoledi : boolean = false;
  giovedi : boolean = false;
  venerdi : boolean = false;
  sabato : boolean = false;
  domenica : boolean = false;


  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private utenteService :UtenteService,
    private user :UserService,
    private bookingService : BookingService,
    private router: Router,    
    ) {

    }


    ngOnInit(): void {
        this.recuperaDiLunedi();
        this.recuperaDiMartedi();
        this.recuperaDiMercoledi();
        this.recuperaDiGiovedi();
        this.recuperaDiVenerdi();
        this.recuperaDiSabato();
        this.recuperaDiDomenica();
        this.recuperaTutti();
    }


    
    apriTutti(){
      this.tutti = true;
      this.martedi = false;
      this.mercoledi = false;
      this.lunedi=false;
      this.giovedi=false;
      this.venerdi=false;
      this.sabato=false;
      this.domenica=false;
    }

    apriLun(){
      this.lunedi = true;
      this.martedi = false;
      this.mercoledi = false;
      this.tutti=false;
      this.giovedi = false;
      this.venerdi = false;
      this.sabato=false;
      this.domenica=false;
    }

    apriMar(){
      this.martedi = true;
      this.lunedi = false;
      this.mercoledi = false;
      this.tutti = false;
      this.giovedi = false;
      this.venerdi = false;
      this.sabato=false;
      this.domenica=false;
    }

    apriMer(){
      this.mercoledi = true;
      this.lunedi = false;
      this.martedi = false;
      this.tutti = false;
      this.giovedi = false;
      this.venerdi = false;
      this.sabato=false;
      this.domenica=false;
    }

    apriGiov(){
      this.giovedi = true;
      this.mercoledi = false;
      this.lunedi = false;
      this.martedi = false;
      this.tutti = false;
      this.venerdi = false;
      this.sabato=false;
      this.domenica=false;
    }

    apriVen(){
      this.venerdi = true;
      this.mercoledi = false;
      this.lunedi = false;
      this.martedi = false;
      this.giovedi = false;
      this.tutti = false;
      this.sabato=false;
      this.domenica=false;
    }

    apriSab(){
      this.sabato = true;
      this.mercoledi = false;
      this.lunedi = false;
      this.martedi = false;
      this.giovedi = false;
      this.tutti = false;
      this.venerdi=false;
      this.domenica=false;
    }
 
    apriDom(){
      this.domenica = true;
      this.mercoledi = false;
      this.lunedi = false;
      this.martedi = false;
      this.giovedi = false;
      this.tutti = false;
      this.sabato=false;
      this.venerdi=false;
    }

  recuperaTutti(){
    const token = this.authService.getToken();
    if(token) {
     this.bookingService.getBooking(token).subscribe(
       book => {
         this.prenotazioni = book;
         console.log(book)
       },
       error => {
         console.error('Errore durante il recupero degli utenti:', error);
       }
     );
   } else {
     console.error('Token assente');
   }
  }

  
  recuperaDiLunedi(){
    const token = this.authService.getToken();
    const bookData = { giorno_prenotazione: "LUNEDI" }; // Se `bookingData` deve essere un oggetto
    if(token) {
      this.bookingService.getLunedi(token, bookData).subscribe(
        book => {
          this.prenotazioniLunedi = book;
          console.log(book);
        },
        error => {
          console.error('Errore durante il recupero delle prenotazioni:', error);
        }
      );
    } else {
      console.error('Token assente');
    }
  }

  recuperaDiMartedi(){
    const token = this.authService.getToken();
    const bookData = { giorno_prenotazione: "MARTEDI" }; // Se `bookingData` deve essere un oggetto
    if(token) {
      this.bookingService.getLunedi(token, bookData).subscribe(
        book => {
          this.prenotazioniMartedi = book;
          console.log(book);
        },
        error => {
          console.error('Errore durante il recupero delle prenotazioni:', error);
        }
      );
    } else {
      console.error('Token assente');
    }
  }

  recuperaDiMercoledi(){
    const token = this.authService.getToken();
    const bookData = { giorno_prenotazione: "MERCOLEDI" }; // Se `bookingData` deve essere un oggetto
    if(token) {
      this.bookingService.getLunedi(token, bookData).subscribe(
        book => {
          this.prenotazioniMercoledi = book;
          console.log(book);
        },
        error => {
          console.error('Errore durante il recupero delle prenotazioni:', error);
        }
      );
    } else {
      console.error('Token assente');
    }
  }

  recuperaDiGiovedi(){
    const token = this.authService.getToken();
    const bookData = { giorno_prenotazione: "GIOVEDI" }; // Se `bookingData` deve essere un oggetto
    if(token) {
      this.bookingService.getLunedi(token, bookData).subscribe(
        book => {
          this.prenotazioniGiovedi = book;
          console.log(book);
        },
        error => {
          console.error('Errore durante il recupero delle prenotazioni:', error);
        }
      );
    } else {
      console.error('Token assente');
    }
  }

  recuperaDiVenerdi(){
    const token = this.authService.getToken();
    const bookData = { giorno_prenotazione: "VENERDI" }; // Se `bookingData` deve essere un oggetto
    if(token) {
      this.bookingService.getLunedi(token, bookData).subscribe(
        book => {
          this.prenotazioniVenerdi = book;
          console.log(book);
        },
        error => {
          console.error('Errore durante il recupero delle prenotazioni:', error);
        }
      );
    } else {
      console.error('Token assente');
    }
  }
  recuperaDiSabato(){
    const token = this.authService.getToken();
    const bookData = { giorno_prenotazione: "SABATO" }; // Se `bookingData` deve essere un oggetto
    if(token) {
      this.bookingService.getLunedi(token, bookData).subscribe(
        book => {
          this.prenotazioniSabato = book;
          console.log(book);
        },
        error => {
          console.error('Errore durante il recupero delle prenotazioni:', error);
        }
      );
    } else {
      console.error('Token assente');
    }
  }
  recuperaDiDomenica(){
    const token = this.authService.getToken();
    const bookData = { giorno_prenotazione: "DOMENICA" }; // Se `bookingData` deve essere un oggetto
    if(token) {
      this.bookingService.getLunedi(token, bookData).subscribe(
        book => {
          this.prenotazioniDomenica = book;
          console.log(book);
        },
        error => {
          console.error('Errore durante il recupero delle prenotazioni:', error);
        }
      );
    } else {
      console.error('Token assente');
    }
  }

  logOut(){
    this.authService.logout();
    this.router.navigate(['/loginAmministratore']);
  }

  home() {
    this.router.navigate(['/homeAmministratore']);
  }
}
