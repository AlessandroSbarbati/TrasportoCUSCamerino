export class BookingRequest {
    fascia_oraria_prenotazione: string;
    giorno_prenotazione: string;
    indirizzo: string;


    constructor(fascia_oraria_prenotazione: string, giorno_prenotazione: string, indirizzo: string) {
        this.fascia_oraria_prenotazione = fascia_oraria_prenotazione;
        this.giorno_prenotazione = giorno_prenotazione;
        this.indirizzo = indirizzo;
      }
}
