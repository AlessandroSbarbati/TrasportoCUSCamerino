import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private tokenKey = 'authToken';

  constructor() {}

  // Metodo per salvare il token nella memoria locale
  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Metodo per recuperare il token dalla memoria locale
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Metodo per verificare se l'utente è autenticato
  isAuthenticated(): boolean {
    const token = this.getToken();
    return token !== null && token !== undefined;
  }

  // Metodo per effettuare il logout
  logout(): void {
    // Rimuovi il token dalla memoria locale
    localStorage.removeItem(this.tokenKey);
  }
}
