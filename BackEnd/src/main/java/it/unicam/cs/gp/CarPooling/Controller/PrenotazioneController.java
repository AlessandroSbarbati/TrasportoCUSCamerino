package it.unicam.cs.gp.CarPooling.Controller;

import it.unicam.cs.gp.CarPooling.Model.GiornoSettimana;
import it.unicam.cs.gp.CarPooling.Model.FasciaOraria;
import it.unicam.cs.gp.CarPooling.Model.Prenotazione;
import it.unicam.cs.gp.CarPooling.Model.Utente;
import it.unicam.cs.gp.CarPooling.Request.BookingRequest;
import it.unicam.cs.gp.CarPooling.Service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/**
 * Questa classe gestisce le richieste relative alla prenotazione di viaggi nel sistema CarPooling.
 */
@Controller
@RequestMapping(path = "/api/booking")
@CrossOrigin(origins = "http://localhost:4200")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;
    /**
     * Effettua una prenotazione di viaggio.
     *
     * @param request la richiesta di prenotazione
     * @param token il token di autenticazione dell'utente
     * @return una ResponseEntity che indica l'esito dell'operazione
     */
    @PostMapping(path = "/book")
    public ResponseEntity<String> book(@RequestBody BookingRequest request,
                                       @RequestHeader("Authorization") String token) {
        try {
            String cleanedToken = token.replace("Bearer ", "");
            String result = prenotazioneService.prenota(request, cleanedToken);
            return ResponseEntity.ok().body("{\"message\": \"" + result + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'aggiunta della prenotazione: " + e.getMessage());
        }
    }
    /**
     * Restituisce tutte le prenotazioni presenti nel sistema.
     *
     * @return una ResponseEntity contenente tutte le prenotazioni
     */
    @GetMapping(path = "/allBookings")
    public ResponseEntity<Iterable<Prenotazione>> getAllPrenotazioni() {
             Iterable<Prenotazione> prenotazioni = prenotazioneService.findAllPrenotazioni();
             return ResponseEntity.ok(prenotazioni);
        }
    /**
     * Restituisce le prenotazioni effettuate per un determinato giorno.
     *
     * @param bookingRequest la richiesta contenente il giorno di interesse
     * @return una ResponseEntity contenente le prenotazioni per il giorno specificato
     */
    @PostMapping(path="/getDayBookings")
    public ResponseEntity<Iterable<Prenotazione>> getPrenotazioniDelGiorno(@RequestBody BookingRequest bookingRequest) {
        GiornoSettimana giornoSettimana = bookingRequest.getGiorno_prenotazione();
        Iterable<Prenotazione> prenotazioni = prenotazioneService.findPrenotazioniDelGiorno(giornoSettimana);
        return ResponseEntity.ok(prenotazioni);
    }
    /**
     * Restituisce le prenotazioni effettuate da un utente.
     *
     * @param token il token di autenticazione dell'utente
     * @return una ResponseEntity contenente le prenotazioni dell'utente
     */
    @GetMapping(path="/userBookings")
    public ResponseEntity<Iterable<Prenotazione>> getUserBookings(@RequestHeader("Authorization") String token){

            String cleanedToken = token.replace("Bearer ", "");
            Iterable<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioniUtente(cleanedToken);
            return ResponseEntity.ok(prenotazioni);

    }
    /**
     * Restituisce una prenotazione dato il suo identificativo.
     *
     * @param id l'identificativo della prenotazione
     * @return la prenotazione corrispondente all'identificativo specificato
     */
    @GetMapping(path = "/booking/{id}")
    public @ResponseBody Prenotazione getPrenotazioneById(@PathVariable Integer id) {
        return prenotazioneService.getPrenotazioneById(id);
    }
    /**
     * Elimina una prenotazione dato il suo identificativo.
     *
     * @param id l'identificativo della prenotazione da eliminare
     * @return un messaggio indicante l'esito dell'operazione
     */
    @DeleteMapping(path = "/booking/{id}")
    public @ResponseBody String deletePrenotazione(@PathVariable Integer id) {
        try {
            prenotazioneService.deletePrenotazione(id);
            return "Prenotazione eliminata con successo";
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore durante l'eliminazione della prenotazione: " + e.getMessage();
        }
    }
}
