import { Component, input } from '@angular/core';
import { Router } from '@angular/router';
import { BookingRequest } from '../../models/booking-request';
import { AuthService } from '../../../service/auth.service';
import { BookingService } from '../../../service/booking.service';
import { ModalComponent } from '../../modal/modal.component';
import { MatDialog } from '@angular/material/dialog';


@Component({
  selector: 'app-martedi',
  templateUrl: './martedi.component.html',
  styleUrl: './martedi.component.css'
})
export class MartediComponent {

  

  constructor(
    
    private router: Router,
    private authService: AuthService,
    private bookingService : BookingService,
    public dialog: MatDialog
    ) {

    
  }


  //controlla il giorno corrente
  checkIfMondayOrTuesday(): boolean {
    const currentDate = new Date();
    const dayOfWeek = currentDate.getDay();
    return dayOfWeek === 1 || dayOfWeek === 2; // 1 è Lunedì, 2 è Martedì
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

  home() {
    this.router.navigate(['/home']);
  }

    logOut(){
      this.authService.logout();
    }

    book(ora : string,indirizzo : string){
      const token = this.authService.getToken();
      if(token){
        const bookingData: BookingRequest = {
          fascia_oraria_prenotazione: ora,
          giorno_prenotazione: "MARTEDI",
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


}
