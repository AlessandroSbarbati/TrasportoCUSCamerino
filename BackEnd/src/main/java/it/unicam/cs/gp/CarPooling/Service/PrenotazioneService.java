package it.unicam.cs.gp.CarPooling.Service;

import com.azure.core.annotation.HeaderParam;
import it.unicam.cs.gp.CarPooling.Jwt.JwtServiceInterface;
import it.unicam.cs.gp.CarPooling.Model.*;
import it.unicam.cs.gp.CarPooling.Repository.PrenotazioneRepository;
import it.unicam.cs.gp.CarPooling.Repository.UtenteRepository;
import it.unicam.cs.gp.CarPooling.Request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Questa classe definisce i metodi necessari per eseguire le varie azioni sulle Prenotazioni
 */
@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private JwtServiceInterface jwtService;
    @Autowired
    private UtenteRepository utenteRepository;

    /**
     * Questo metodo serve per effettuare una prenotazione
     * @param bookingRequest richiesta della prenotazione
     * @param token identificativo dell'utente che vuole effettuare la prenotazione
     * @return stringhe di conferma o di errore
     */
    public String prenota(BookingRequest bookingRequest, String token) {
        if(prenotazioneRepository.
                countByGiornoSettimanaAndFasciaOrariaPrenotazione(bookingRequest.getGiorno_prenotazione(),
                                                                  bookingRequest.getFascia_oraria_prenotazione()) > 8) {
            return "Prenotazione non disponibile per mancanza di posti.";
        }
        String userEmail = jwtService.extractUserName(token);
        Utente utente = utenteRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        if(prenotazioneRepository.
                existsByUtenteAndGiornoSettimanaAndFasciaOrariaPrenotazione(
                        utente,
                        bookingRequest.getGiorno_prenotazione(),
                        bookingRequest.getFascia_oraria_prenotazione())) {
            return "Prenotazione gia' effettuata.";
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setFasciaOrariaPrenotazione(bookingRequest.getFascia_oraria_prenotazione());
        prenotazione.setGiornoSettimana(bookingRequest.getGiorno_prenotazione());
        prenotazione.setIndirizzo(bookingRequest.getIndirizzo());

        prenotazioneRepository.save(prenotazione);
        return "tutto ok ";
    }

    /**
     * Questo metodo serve per cercare tutte le prenotazioni effettuate da un utente
     * @param token identificativo dell'utente
     * @return repository delle prenotazioni dell'utente
     */
    public Iterable<Prenotazione> getPrenotazioniUtente(String token) {
        String userEmail = jwtService.extractUserName(token);
        Utente utente = utenteRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        return prenotazioneRepository.findByUtente(utente);
    }

    /**
     * Questo metodo serve per cercare tutte le prenotazioni di tutti gli utenti
     * @return repository di tutte le prenotazioni
     */
    public Iterable<Prenotazione> findAllPrenotazioni() {
        return prenotazioneRepository.findAll();
    }
    /**
     * Questo metodo serve per cercare tutte le prenotazioni effettuate da un utente
     * @param id identificativo dell'utente
     * @return repository delle prenotazioni dell'utente
     */
    public Prenotazione getPrenotazioneById(Integer id) {
        return prenotazioneRepository.findById(id).orElse(null);
    }

    /**
     * Questo metodo serve per eliminare una prenotazione di un utente
     * @param id identificativo dell'utente
     */
    public void deletePrenotazione(Integer id) {
        prenotazioneRepository.deleteById(id);
    }

    /**
     * Questo metodo serve per cercare le prenotazioni presenti in un giorno
     * @param giornoSettimana giorno in cui cercare le prenotazioni
     * @return repository delle prenotazioni in quel giorno
     */
    public Iterable<Prenotazione> findPrenotazioniDelGiorno(GiornoSettimana giornoSettimana){
        return prenotazioneRepository.selectDayBookings(giornoSettimana);
    }

    /**
     * Questo metodo serve per eliminare tutte le prenotazioni dalla Repository delle Prenotazioni
     */
    public void deleteAllPrenotazioni(){
        prenotazioneRepository.deleteAll();
    }
}
