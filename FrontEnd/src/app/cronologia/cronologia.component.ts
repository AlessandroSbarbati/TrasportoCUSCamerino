import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { BookingService } from '../../service/booking.service';
import { Router } from '@angular/router';
import { BookingRequest } from '../models/booking-request';

@Component({
  selector: 'app-cronologia',
  templateUrl: './cronologia.component.html',
  styleUrls: ['./cronologia.component.css']
})
export class CronologiaComponent implements OnInit {

  prenotazioni: any[] = []; 
 
  constructor(
    private authService: AuthService,
    private bookingService: BookingService,   
    private router: Router
  ) {}

  ngOnInit(): void {
    this.recuperaTutti();
  }

  recuperaTutti() {
    const token = this.authService.getToken();
    if (token) {
      this.bookingService.getUserBookings(token).subscribe(
        book => {
          this.prenotazioni = book;
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

  logOut() {
    this.authService.logout();
    this.router.navigate(['/loginUtente']);
  }

  home() {
    this.router.navigate(['/home']);
  }

  eliminaPrenotazione(prenotazione: BookingRequest) {
    const token = this.authService.getToken();

    if (token) {
        console.log('Eliminazione prenotazione con token:', token);
        console.log('Prenotazione da eliminare:', prenotazione);

        this.bookingService.deleteBooking(token, prenotazione).subscribe(
            response => {
                console.log('Prenotazione eliminata:', response);
                this.recuperaTutti();  // Ricarica le prenotazioni dopo l'eliminazione
            },
            error => {
                console.error('Errore durante l\'eliminazione della prenotazione:', error);
            }
        );
    } else {
        console.error('Token assente');
    }
}

  modificaPrenotazione(oldPrenotazione: BookingRequest, newPrenotazione: BookingRequest) {
    const token = this.authService.getToken();
    if (token) {
      this.bookingService.updateBooking(token).subscribe(
        response => {
          console.log('Prenotazione modificata:', response);
          this.recuperaTutti();  // Ricarica le prenotazioni dopo la modifica
        },
        error => {
          console.error('Errore durante la modifica della prenotazione:', error);
        }
      );
    } else {
      console.error('Token assente');
    }
  }
}
