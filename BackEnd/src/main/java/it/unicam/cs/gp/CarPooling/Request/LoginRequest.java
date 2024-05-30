package it.unicam.cs.gp.CarPooling.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Questa classe serve per definire i dati per la Request del Login
 */
@Getter
@Setter
public class LoginRequest implements Serializable {

    @JsonProperty("email")
    public String email;
    @JsonProperty("password")
    public String password;

}
