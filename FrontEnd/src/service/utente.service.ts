import { Injectable } from '@angular/core';
import { Utente } from '../app/models/utente';
import { Amministratore } from '../app/models/amministratore';

@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  

  private currentUser: Utente | null = null;

  constructor() {}

  setUser(user: Utente) {
    this.currentUser = user;
  }

  getUser(): Utente | null {
    return this.currentUser;
  }

  isAdministrator(): boolean {
    return this.currentUser?.role === 'USER';
  }
}