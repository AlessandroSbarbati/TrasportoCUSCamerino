package it.unicam.cs.gp.CarPooling.Controller;

import it.unicam.cs.gp.CarPooling.Model.Admin;
import it.unicam.cs.gp.CarPooling.Request.LoginRequest;
import it.unicam.cs.gp.CarPooling.Request.UserRequest;
import it.unicam.cs.gp.CarPooling.Response.JwtAuthenticationResponse;
import it.unicam.cs.gp.CarPooling.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/**
 * Questa classe gestisce le richieste relative alla gestione degli amministratori nel sistema CarPooling.
 */
@Controller
@RequestMapping(path = "/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminService service;
    /**
     * Aggiunge un nuovo amministratore al sistema.
     *
     * @param request la richiesta di registrazione dell'amministratore
     * @return una ResponseEntity che indica l'esito dell'operazione
     */
    @PostMapping("/createAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody UserRequest request) {
        try {
            String result = service.registerAdmin(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'aggiunta dell'utente: " + e.getMessage());
        }
    }
    /**
     * Gestisce la richiesta di accesso di un amministratore al sistema.
     *
     * @param loginRequest la richiesta di accesso
     * @return una ResponseEntity contenente il token di autenticazione se l'accesso è avvenuto con successo, altrimenti un errore di autenticazione
     */
    @PostMapping(value ="/loginAdmin")
    public ResponseEntity<JwtAuthenticationResponse> getLogin(@RequestBody LoginRequest loginRequest){
        try{
            return ResponseEntity.ok(service.signIn(loginRequest));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(JwtAuthenticationResponse.builder().error("Authentication failed").build());
        }
    }
    /**
     * Restituisce i dati dell'admin autenticato.
     *
     * @param token il token di autenticazione dell'admin
     * @return una ResponseEntity contenente i dati dell'admin
     */
    @GetMapping("/getAdmin")
    public ResponseEntity<Admin> getAdmin(@RequestHeader("Authorization") String token){
        try {
            String cleanedToken = token.replace("Bearer ", "");
            Admin result = service.getData( cleanedToken);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
