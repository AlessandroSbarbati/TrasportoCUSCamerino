package it.unicam.cs.gp.CarPooling.Service;

import it.unicam.cs.gp.CarPooling.Model.Role;
import it.unicam.cs.gp.CarPooling.Request.UserRequest;
import it.unicam.cs.gp.CarPooling.Jwt.JwtServiceInterface;
import it.unicam.cs.gp.CarPooling.Model.Utente;
import it.unicam.cs.gp.CarPooling.Repository.UtenteRepository;
import it.unicam.cs.gp.CarPooling.Request.LoginRequest;
import it.unicam.cs.gp.CarPooling.Response.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Questa classe definisce i metodi necessari per eseguire i vari comandi dell'Utente
 */
@Service
public class UtenteService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtenteRepository repository;

    @Autowired
    private JwtServiceInterface jwtService;
    @Autowired
    private JwtServiceInterface jwtServiceInterface;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Questo metodo serve per la registrazione dell'Utente
     * @param registerRequest richiesta della registrazione
     * @return stringa di conferma
     */
    public String registerUtente(UserRequest registerRequest) {

        Utente utente = new Utente();
        utente.setNome(registerRequest.getNome());
        utente.setCognome(registerRequest.getCognome());
        utente.setEmail(registerRequest.getEmail());
        utente.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        utente.setTelefono(registerRequest.getTelefono());
        utente.setCodiceCUS(registerRequest.getCodiceCUS());
        utente.setRole(Role.USER);

        repository.save(utente);
        return "tutto ok ";
    }

    /**
     * Questo metodo serve per il login dell'Utente
     * @param request richiesta di login
     * @return token generato dell'Utente
     */
    public JwtAuthenticationResponse signIn(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Utente user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtServiceInterface.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .utente(user)
                .build();
    }


    /**
     * Questo metodo serve per aggiungere un Utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @return stringa di conferma
     */
    public String addUtente(String nome, String cognome) {
        Utente utente = new Utente();
        utente.setNome(nome);
        utente.setCognome(cognome);
        repository.save(utente);
        return "Utente salvato con successo";
    }
    /**
     * Questo metodo serve per trovare tutti gli utenti dalla repository
     * @return repository con tutti gli utenti
     */
    public Iterable<Utente> findAllUtenti() {
        return repository.findAll();
    }

    /**
     * Questo metodo serve per la registrazione dell'utente nella repository
     * @param utente repository con l'utente salvato
     */
    public void registerUtente(Utente utente) {
        String encodedPassword = passwordEncoder().encode(utente.getPassword());
        utente.setPassword(encodedPassword);
        repository.save(utente);
    }

    /**
     * Questo metodo serve per la ricerca dell'utente a seconda dell'username
     * @param username identificativo dell'utente
     * @return utente trovato
     * @throws UsernameNotFoundException Eccezione dell'utente non trovato
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = repository.findByUsername(username);
        if (utente == null) {
            throw new UsernameNotFoundException("Utente non trovato: " + username);
        }

        return User.builder()
                .username(utente.getUsername())
                .password(utente.getPassword())
                .roles("USER")
                .build();
    }

    /**
     * Questo metodo serve per criptare la password
     * @return password criptata
     */
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Questo metodo serve per la ricerca dell'utente a seconda del token
     * @param token identificativo dell'utente
     * @return utente trovato
     */
    public Utente getData(String token) {

        String userEmail = jwtService.extractUserName(token);
        Utente utente = repository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        return utente;
    }

    /**
     * Questo metodo serve per aggiornare i dati di un utente
     * @param utente utente da modificare
     */
    public void updateUtente(Utente utente) {
        repository.save(utente);
    }

    /**
     * Questo metodo serve per controllare se una mail è gia presente nel sistema
     * @param email email da controllare
     * @return messaggio di conferma o meno
     */
    public boolean existsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }
    /**
     * Questo metodo serve per controllare se un telefono è gia presente nel sistema
     * @param telefono telefono da controllare
     * @return messaggio di conferma o meno
     */
    public boolean existsByTelefono(String telefono) {
        return repository.findByTelefono(telefono).isPresent();
    }

    /**
     * Questo metodo serve per rigenerare un token per un utente che ha modificato la sua mail
     * @param utente utente che ha modificato i dati
     * @return messaggio di conferma o meno
     */
    public String generateTokenForUpdatedUser(Utente utente) {
        return jwtServiceInterface.generateToken(utente);
    }

    /**
     * Questo metodo serve per il reset della password
     * @param email email dell'utente
     * @param phoneNumber telefono dell'utente
     * @param newPassword nuova password da impostare
     */
    public void resetPassword(String email, String phoneNumber, String newPassword) {
        Utente utente = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (!utente.getTelefono().equals(phoneNumber)) {
            throw new IllegalArgumentException("Phone number does not match");
        }

        utente.setPassword(passwordEncoder.encode(newPassword));
        repository.save(utente);
    }

    /**
     * Questo metodo serve per l'eliminazione di un utente da parte dell'admin
     * @param nome nome dell'utente da eliminare
     * @param cognome cognome dell'utente da eliminare
     * @param email email dell'utente da eliminare
     * @return messaggio di conferma o meno
     */
    public String deleteUtente(String nome, String cognome, String email) {
        Utente utente = repository.findByNomeAndCognomeAndEmail(nome, cognome, email)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        repository.delete(utente);
        return "Utente rimosso con successo";
    }
}