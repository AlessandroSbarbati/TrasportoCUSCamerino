import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BookingRequest } from '../../models/booking-request';
import { AuthService } from '../../../service/auth.service';
import { BookingService } from '../../../service/booking.service';
import { ModalComponent } from '../../modal/modal.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-sabato',
  templateUrl: './sabato.component.html',
  styleUrl: './sabato.component.css'
})
export class SabatoComponent {

  constructor(
    
    private router: Router,
    private authService: AuthService,
    private bookingService : BookingService,
    public dialog: MatDialog
    ) {

    
  }
  
  openModal( content: string): void {
    const dialogRef = this.dialog.open(ModalComponent, {
      width: '400px',
      data: { title: "attenzione", content: content } // Passa i dati dinamici al modal
    });
  }
  
  lunedi() {
    this.router.navigate(['/lunedi']);
  }

  martedi() {
    this.router.navigate(['/martedi']);
  }

  mercoledi() {
    this.router.navigate(['/mercoledi']);
  }

  giovedi() {
    this.router.navigate(['/giovedi']);
  }

  venerdi() {
    this.router.navigate(['/venerdi']);
  }
  sabato() {
    this.router.navigate(['/sabato']);
  }
  domenica() {
    this.router.navigate(['/domenica']);
  }
  home() {
    this.router.navigate(['/home']);
  }

    logOut(){
      this.authService.logout();
      this.router.navigate(['/loginUtente']);
    }


    book(ora : string,indirizzo : string){
      const token = this.authService.getToken();
      if(token){
        const bookingData: BookingRequest = {
          fascia_oraria_prenotazione: ora,
          giorno_prenotazione: "SABATO",
          indirizzo: indirizzo,
        };
      
        this.bookingService.bookBooking(token, bookingData).subscribe(
          response => {
            this.openModal(response.message);
            console.log('Prenotazione effettuata con successo:', response);
          },
          error => {
            console.error('Errore durante la prenotazione:', error);
          }
        );
      }
    }

    checkIfDay(): boolean{
      const currentDate = new Date();
      const dayOfWeek = currentDate.getDay();
        return dayOfWeek === 0 ;
    }
}
