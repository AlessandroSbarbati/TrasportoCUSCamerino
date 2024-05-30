import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable, catchError, throwError } from 'rxjs';
import { Utente } from '../app/models/utente';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Amministratore } from '../app/models/amministratore';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }


  //restituisce l'elenco degli utenti
  getUsers(token: string): Observable<Utente[]> {
    const apiUrl = 'http://localhost:8080/api/user/getUsers';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    return this.http.get<Utente[]>(apiUrl, { headers }).pipe(
      catchError(error => {
        console.error('Errore durante la chiamata getUsers:', error);
        return throwError(() => new Error('Errore durante la chiamata getUsers'));
      })
    );
  }

//restituisce i dati dell'utente
  getUser(token: string): Observable<Utente> {
    const apiUrl = 'http://localhost:8080/api/user/getUserData';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    return this.http.get<Utente>(apiUrl, { headers }).pipe(
      catchError(error => {
        console.error('Errore durante la chiamata getUsers:', error);
        return throwError(() => new Error('Errore durante la chiamata getUsers'));
      })
    );
  }

  


  getAdmin(token: string): Observable<Amministratore> {
    const apiUrl = 'http://localhost:8080/api/admin/getAdmin';
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
    return this.http.get<Amministratore>(apiUrl, { headers }).pipe(
      catchError(error => {
        console.error('Errore durante la chiamata getUsers:', error);
        return throwError(() => new Error('Errore durante la chiamata getUsers'));
      })
    );
  }



}
