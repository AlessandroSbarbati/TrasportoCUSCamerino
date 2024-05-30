package it.unicam.cs.gp.CarPooling.Repository;

import it.unicam.cs.gp.CarPooling.Model.Prenotazione;
import it.unicam.cs.gp.CarPooling.Model.Utente;
import it.unicam.cs.gp.CarPooling.Model.GiornoSettimana;
import it.unicam.cs.gp.CarPooling.Model.FasciaOraria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Quest'interfaccia serve per definire una struttura per la Repository delle Prenotazioni
 */
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Integer> {


    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Prenotazione p " +
            "WHERE p.utente = :utente " +
            "AND p.giornoSettimana = :giornoSettimana " +
            "AND p.fasciaOrariaPrenotazione = :fasciaOrariaPrenotazione")
    boolean existsByUtenteAndGiornoSettimanaAndFasciaOrariaPrenotazione(
            @Param("utente") Utente utente,
            @Param("giornoSettimana") GiornoSettimana giornoSettimana,
            @Param("fasciaOrariaPrenotazione") FasciaOraria fasciaOrariaPrenotazione);

    @Query("SELECT COUNT(p) FROM Prenotazione p " +
            "WHERE p.giornoSettimana = :giornoSettimana " +
            "AND p.fasciaOrariaPrenotazione = :fasciaOrariaPrenotazione ")
    long countByGiornoSettimanaAndFasciaOrariaPrenotazione(
            @Param("giornoSettimana") GiornoSettimana giornoSettimana,
            @Param("fasciaOrariaPrenotazione") FasciaOraria fasciaOrariaPrenotazione);

    @Query("SELECT p FROM Prenotazione p WHERE p.giornoSettimana = :giornoSettimana")
    Iterable<Prenotazione> selectDayBookings(@Param("giornoSettimana") GiornoSettimana giornoSettimana);

    @Query("SELECT p FROM Prenotazione p WHERE p.utente = :utente")
    Iterable<Prenotazione> findByUtente(@Param("utente") Utente utente);
}
