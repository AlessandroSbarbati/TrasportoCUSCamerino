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
    this.router.navigate(['/login']);
  }

  home() {
    this.router.navigate(['/home']);
  }
}
