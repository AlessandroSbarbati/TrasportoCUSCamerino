import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private http: HttpClient) { }

   // Metodo per effettuare la richiesta POST
   bookBooking(token: string, bookingData: any): Observable<any> {
    const apiUrl = 'http://localhost:8080/api/booking/book';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.post(apiUrl, bookingData, { headers }).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Errore durante la chiamata bookBooking:', error);
        return throwError('Errore durante la chiamata bookBooking');
      })
    );
  }

  getBooking(token: string): Observable<any[]> {
    const apiUrl = 'http://localhost:8080/api/booking/allBookings';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    return this.http.get<any[]>(apiUrl, { headers }).pipe(
      catchError(error => {
        console.error('Errore durante la chiamata getUsers:', error);
        return throwError(() => new Error('Errore durante la chiamata getUsers'));
      })
    );
  }

  getLunedi(token: string, bookingData: any): Observable<any[]> {
    const apiUrl = 'http://localhost:8080/api/booking/getDayBookings';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    return this.http.post<any[]>(apiUrl, bookingData, { headers }).pipe(
      catchError(error => {
        console.error('Errore durante la chiamata getBook:', error);
        return throwError(() => new Error('Errore durante la chiamata getBook'));
      })
    );
  }


  
}
