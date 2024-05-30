package it.unicam.cs.gp.CarPooling.Controller;

import it.unicam.cs.gp.CarPooling.Model.Utente;
import it.unicam.cs.gp.CarPooling.Request.LoginRequest;
import it.unicam.cs.gp.CarPooling.Request.SignUpRequest;
import it.unicam.cs.gp.CarPooling.Response.JwtAuthenticationResponse;
import it.unicam.cs.gp.CarPooling.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    /**
     * Aggiunge un nuovo utente al sistema.
     *
     * @param request la richiesta di registrazione dell'utente
     * @return una ResponseEntity che indica l'esito dell'operazione
     */
    @PostMapping("/createUser")
    public ResponseEntity<String> addUtente(@RequestBody SignUpRequest request) {
        try {
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
    @PostMapping(value ="/login")
    public ResponseEntity<JwtAuthenticationResponse> getLogin(@RequestBody LoginRequest loginRequest){
        try{
            return ResponseEntity.ok(service.signIn(loginRequest));
        }catch(Exception e){
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
     * Rimuove un utente dal sistema dato il suo identificativo.
     *
     * @param id l'identificativo dell'utente da rimuovere
     * @return una ResponseEntity indicante l'esito dell'operazione di rimozione
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeUtente(@PathVariable Integer id) {
        try {
            service.deleteUtente(id);
            return ResponseEntity.ok("Utente rimosso con successo");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la rimozione dell'utente: " + e.getMessage());
        }
    }
    /**
     * Restituisce i dati dell'utente autenticato.
     *
     * @param token il token di autenticazione dell'utente
     * @return una ResponseEntity contenente i dati dell'utente
     */
    @GetMapping("/getUserData")
    public ResponseEntity<Utente> getUser(@RequestHeader("Authorization") String token){
        try {
            String cleanedToken = token.replace("Bearer ", "");
            Utente result = service.getData( cleanedToken);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
