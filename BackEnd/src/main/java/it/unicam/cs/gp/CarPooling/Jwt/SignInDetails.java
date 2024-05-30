package it.unicam.cs.gp.CarPooling.Jwt;
import it.unicam.cs.gp.CarPooling.Model.Utente;
import it.unicam.cs.gp.CarPooling.Repository.AdminRepository;
import it.unicam.cs.gp.CarPooling.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SignInDetails implements SignInService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return this::createUserDetails;

    }


    private UserDetails createUserDetails(String username) {
        Utente utente = utenteRepository.findByEmail(username).orElse(null);
        if (utente != null) {
            return utente;
        } else {
            return adminRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
    }
}