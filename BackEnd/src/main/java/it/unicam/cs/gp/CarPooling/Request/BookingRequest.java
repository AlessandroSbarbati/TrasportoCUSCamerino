package it.unicam.cs.gp.CarPooling.Request;


import com.fasterxml.jackson.annotation.JsonProperty;
import it.unicam.cs.gp.CarPooling.Model.FasciaOraria;
import it.unicam.cs.gp.CarPooling.Model.GiornoSettimana;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * Questa classe serve per definire i dati per la Request della Prenotazione
 */
@Getter
@Setter
public class BookingRequest {

    @JsonProperty("fascia_oraria_prenotazione")
    public FasciaOraria fascia_oraria_prenotazione;
    @JsonProperty("giorno_prenotazione")
    public GiornoSettimana giorno_prenotazione;
    @JsonProperty("indirizzo")
    private String indirizzo;

}
