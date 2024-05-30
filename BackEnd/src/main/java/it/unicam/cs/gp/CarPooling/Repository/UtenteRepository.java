package it.unicam.cs.gp.CarPooling.Repository;

import it.unicam.cs.gp.CarPooling.Model.Utente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Quest'interfaccia serve per definire una struttura per la Repository dell'Utente
 */
public interface UtenteRepository extends CrudRepository<Utente, Integer> {
    Utente findByUsername(String username);
    Optional<Utente> findByEmail(String email);

    Optional<Utente> findByNome(String nome);
}

