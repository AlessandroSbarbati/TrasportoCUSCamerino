package it.unicam.cs.gp.CarPooling.Controller;

import it.unicam.cs.gp.CarPooling.Model.Utente;
import it.unicam.cs.gp.CarPooling.Request.ChangePasswordRequest;
import it.unicam.cs.gp.CarPooling.Request.LoginRequest;
import it.unicam.cs.gp.CarPooling.Request.UserRequest;
import it.unicam.cs.gp.CarPooling.Response.JwtAuthenticationResponse;
import it.unicam.cs.gp.CarPooling.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Questa classe gestisce le richieste relative agli utenti nel sistema CarPooling.
 */
@Controller
@RequestMapping(path = "/api/user")
@CrossOrigin(origins = "http://localhost:4200") // Sostituisci con il tuo dominio Angular
public class UtenteController {

    @Autowired
    private UtenteService service;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * Aggiunge un nuovo utente al sistema.
     *
     * @param request la richiesta di registrazione dell'utente
     * @return una ResponseEntity che indica l'esito dell'operazione
     */
    @PostMapping("/createUser")
    public ResponseEntity<String> addUtente(@RequestBody UserRequest request) {
        try {
            if (service.existsByEmail(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"Email già in uso\"}");
            }
            if (service.existsByTelefono(request.getTelefono())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"Telefono già in uso\"}");
            }

            String result = service.registerUtente(request);
            return ResponseEntity.ok().body("{\"message\": \"" + result + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'aggiunta dell'utente: " + e.getMessage());
        }
    }

    /**
     * Gestisce la richiesta di accesso di un utente al sistema.
     *
     * @param loginRequest la richiesta di accesso
     * @return una ResponseEntity contenente il token di autenticazione se l'accesso è avvenuto con successo, altrimenti un errore di autenticazione
     */
    @PostMapping(value = "/login")
    public ResponseEntity<JwtAuthenticationResponse> getLogin(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(service.signIn(loginRequest));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Entra sul controller ma non arriva al service");
            return ResponseEntity.badRequest().body(JwtAuthenticationResponse.builder().error("Authentication failed").build());
        }
    }

    /**
     * Restituisce tutti gli utenti presenti nel sistema.
     *
     * @return una ResponseEntity contenente tutti gli utenti
     */
    @GetMapping("/getUsers")
    public ResponseEntity<Iterable<Utente>> getAllUtenti() {
        Iterable<Utente> utenti = service.findAllUtenti();
        return ResponseEntity.ok(utenti);
    }

    /**
     * Restituisce i dati dell'utente autenticato.
     *
     * @param token il token di autenticazione dell'utente
     * @return una ResponseEntity contenente i dati dell'utente
     */
    @GetMapping("/getUserData")
    public ResponseEntity<Utente> getUser(@RequestHeader("Authorization") String token) {
        try {
            String cleanedToken = token.replace("Bearer ", "");
            Utente result = service.getData(cleanedToken);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Questo metodo serve per la modifica dei dati di un utente; nel caso della modifica della mail
     * essendo essa collegata alla generazione dei token, ne verrà generato uno nuovo che sostituirà quello precedente.
     * @param request dati vecchi e nuovi
     * @param token token identificativo dell'utente
     * @return messaggio di avvenuta modifica dei dati
     */
    @PutMapping("/updateUser")
    public ResponseEntity<JwtAuthenticationResponse> updateUser(@RequestBody UserRequest request, @RequestHeader("Authorization") String token) {
        try {
            // Ottieni l'utente corrispondente al token
            String cleanedToken = token.replace("Bearer ", "");
            Utente utente = service.getData(cleanedToken);

            // Verifica se la nuova email o telefono sono già in uso da un altro utente
            if (request.getEmail() != null && !request.getEmail().equals(utente.getEmail()) && service.existsByEmail(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(JwtAuthenticationResponse.builder().error("Email già in uso").build());
            }
            if (request.getTelefono() != null && !request.getTelefono().equals(utente.getTelefono()) && service.existsByTelefono(request.getTelefono())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(JwtAuthenticationResponse.builder().error("Telefono già in uso").build());
            }

            // Applica le modifiche ai campi dell'utente con quelli presenti nella richiesta
            if (request.getNome() != null) {
                utente.setNome(request.getNome());
            }
            if (request.getCognome() != null) {
                utente.setCognome(request.getCognome());
            }
            if (request.getEmail() != null) {
                utente.setEmail(request.getEmail());
            }
            if (request.getTelefono() != null) {
                utente.setTelefono(request.getTelefono());
            }

            // Salva le modifiche nell'utente
            service.updateUtente(utente);

            // Genera un nuovo token
            String newToken = service.generateTokenForUpdatedUser(utente);

            // Crea la risposta con il nuovo token
            JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                    .token(newToken)
                    .utente(utente)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JwtAuthenticationResponse.builder().error("Errore durante l'aggiornamento dei dati utente: " + e.getMessage()).build());
        }
    }

    /**
     * Questo metodo serve per la modifica della password di un utente
     * @param request password vecchia e nuova
     * @param token token identificativo dell'utente
     * @return messaggio di avvenuta modifica della password
     */
    @PutMapping("/changePassword")
    public ResponseEntity<JwtAuthenticationResponse> changePassword(@RequestBody ChangePasswordRequest request,
                                                                    @RequestHeader("Authorization") String token) {
        try {
            // Ottieni l'utente corrispondente al token
            String cleanedToken = token.replace("Bearer ", "");
            Utente utente = service.getData(cleanedToken);

            // Verifica la vecchia password
            if (!passwordEncoder.matches(request.getOldPassword(), utente.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(JwtAuthenticationResponse.builder().error("Vecchia password errata").build());
            }

            // Aggiorna la password
            utente.setPassword(passwordEncoder.encode(request.getNewPassword()));
            service.updateUtente(utente);

            // Genera un nuovo token
            String newToken = service.generateTokenForUpdatedUser(utente);

            // Crea la risposta con il nuovo token
            JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                    .token(newToken)
                    .utente(utente)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JwtAuthenticationResponse.builder().error("Errore durante la modifica della password: " + e.getMessage()).build());
        }
    }

    /**
     * Questo metodo serve per il reset della password tramite mail e telefono
     * @param request dati vecchi e nuova password da inserire
     * @return messaggio di avvenuto reset della password
     */
    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody UserRequest request) {
        try {
            service.resetPassword(request.getEmail(), request.getTelefono(), request.getPassword());
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during password reset: " + e.getMessage());
        }
    }

    /**
     * Questo metodo serve per l'eliminazione di un utente da parte dell'admin
     * @param request dati dell'utente da eliminare
     * @return messaggio di avvenuta eliminazione dell'utente
     */
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUtente(@RequestBody UserRequest request) {
        try {
            String result = service.deleteUtente(request.getNome(),
                    request.getCognome(),
                    request.getEmail());
            return ResponseEntity.ok().body("{\"message\": \"" + result + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la cancellazione dell'utente: " + e.getMessage());
        }
    }
}