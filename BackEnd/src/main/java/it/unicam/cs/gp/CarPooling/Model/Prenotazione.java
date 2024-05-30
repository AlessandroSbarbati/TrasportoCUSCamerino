package it.unicam.cs.gp.CarPooling.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Questa classe serve per definire una struttura per le Prenotazioni
 */
@Entity
@Getter
@Setter
@Data
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "giornoSettimana")
    private GiornoSettimana giornoSettimana;

    @Enumerated(EnumType.STRING)
    @Column(name = "fasciaOrariaPrenotazione")
    private FasciaOraria fasciaOrariaPrenotazione;

    @Column(name = "indirizzo")
    private String indirizzo;

    @ManyToOne
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
    private Utente utente;


    // Costruttori, Getter e Setter

    public Prenotazione() {
        // Costruttore vuoto richiesto da JPA
    }

    public Prenotazione(Utente utente, GiornoSettimana giornoSettimana, FasciaOraria fasciaOrariaPrenotazione) {
        this.utente = utente;
        this.giornoSettimana = giornoSettimana;
        this.fasciaOrariaPrenotazione = fasciaOrariaPrenotazione;
    }

    // Metodi Getter e Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GiornoSettimana getGiornoSettimana() {
        return giornoSettimana;
    }

    public void setGiornoSettimana(GiornoSettimana giornoSettimana) {
        this.giornoSettimana = giornoSettimana;
    }

    public FasciaOraria getFasciaOrariaPrenotazione() {
        return fasciaOrariaPrenotazione;
    }

    public void setFasciaOrariaPrenotazione(FasciaOraria fasciaOrariaPrenotazione) {
        this.fasciaOrariaPrenotazione = fasciaOrariaPrenotazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
