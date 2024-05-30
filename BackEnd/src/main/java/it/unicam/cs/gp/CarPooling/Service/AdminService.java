package it.unicam.cs.gp.CarPooling.Service;

import it.unicam.cs.gp.CarPooling.Jwt.JwtServiceInterface;
import it.unicam.cs.gp.CarPooling.Model.Admin;
import it.unicam.cs.gp.CarPooling.Model.Role;
import it.unicam.cs.gp.CarPooling.Model.Utente;
import it.unicam.cs.gp.CarPooling.Repository.AdminRepository;
import it.unicam.cs.gp.CarPooling.Request.LoginRequest;
import it.unicam.cs.gp.CarPooling.Request.SignUpRequest;
import it.unicam.cs.gp.CarPooling.Response.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Questa classe definisce i metodi necessari per eseguire i vari comandi dell'Admin
 */
@Service
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtServiceInterface jwtService;
    @Autowired
    private JwtServiceInterface jwtServiceInterface;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Questo metodo serve per la registrazione dell'Admin
     * @param registerRequest richiesta di registrazione
     * @return stringa di conferma
     */
    public String registerAdmin(SignUpRequest registerRequest) {

        Admin admin = new Admin();
        admin.setNome(registerRequest.getNome());
        admin.setCognome(registerRequest.getCognome());
        admin.setEmail(registerRequest.getEmail());
        admin.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Aggiungi le proprietà mancanti o modifica se necessario
        admin.setRole(Role.ADMIN);

        repository.save(admin);
        return "tutto ok ";
    }

    /**
     * Questo metodo serve per il login dell'Admin
     * @param request richiesta di login
     * @return token generato dell'Admin
     */
    public JwtAuthenticationResponse signIn(LoginRequest request) {
        System.out.println("Entra");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        System.out.println("Metà metodo del service");
        Admin admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtServiceInterface.generateToken(admin);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    /**
     * Questo metodo serve per eliminare un Admin
     * @param id Integer identificativo dell'Admin
     * @return stringa di conferma
     */
    public String deleteAdmin(Integer id) {
        repository.deleteById(id);
        return "Admin rimosso con successo";
    }

    /**
     * Questo metodo serve per cercare tutti gli Admin all'interno della Repository
     * @return Repository con tutti gli Admin
     */
    public Iterable<Admin> findAllAdmins() {
        return repository.findAll();
    }

    /**
     * Questo metodo serve per trovare un Admin a seconda del suo username
     * @param username identificativo dell'Admin
     * @return Admin cercato
     * @throws UsernameNotFoundException Eccezione per controllare se Username esiste
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin non trovato: " + username));

        return User.builder()
                .username(admin.getEmail())
                .password(admin.getPassword())
                .roles(admin.getRole().name())
                .build();
    }
    /**
     * Questo metodo serve per la ricerca dell'admin a seconda del token
     * @param token identificativo dell'admin
     * @return admin trovato
     */
    public Admin getData(String token) {

        String userEmail = jwtService.extractUserName(token);
        Admin admin = repository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));
        return admin;
    }
}