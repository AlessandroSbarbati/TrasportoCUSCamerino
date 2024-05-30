package it.unicam.cs.gp.CarPooling.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Questa classe serve per definire una struttura per l'Orario
 */
@Entity
@Getter
@Setter
public class Orario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Il campo giorno_settimana non può essere vuoto")
    private String giornoSettimana;

    @NotBlank(message = "Il campo fascia_oraria non può essere vuoto")
    private String fasciaOraria;

    // Costruttori, Getter e Setter

    public Orario() {
        // Costruttore vuoto richiesto da JPA
    }

    public Orario(String giornoSettimana, String fasciaOraria) {
        this.giornoSettimana = giornoSettimana;
        this.fasciaOraria = fasciaOraria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGiornoSettimana() {
        return giornoSettimana;
    }

    public void setGiornoSettimana(String giornoSettimana) {
        this.giornoSettimana = giornoSettimana;
    }

    public String getFasciaOraria() {
        return fasciaOraria;
    }

    public void setFasciaOraria(String fasciaOraria) {
        this.fasciaOraria = fasciaOraria;
    }
}
