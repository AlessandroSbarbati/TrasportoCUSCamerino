package it.unicam.cs.gp.CarPooling.Config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import it.unicam.cs.gp.CarPooling.Jwt.SignInService;
import it.unicam.cs.gp.CarPooling.Model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

/**
 * Questa classe serve per gestire e controllare la sicurezza sugli accessi per fare i vari comandi
 * sia da parte dell'admin che da parte dell'utente.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SignInService signInService;

    /**
     * Questo metodo serve per dare le autorizzazioni ai vari comandi che si possono fare
     * sia da parte dell'admin che da parte dell'utente.
     * @param http connessione
     * @return http.build() creazione della connessione
     * @throws Exception eccezioni gestite dalla classe HttpSecurity
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(
                        "api/user/createUser", "api/admin/createAdmin", "api/user/login", "api/admin/loginAdmin",
                                "api/booking/allBookings","api/user/getUsers").permitAll()
                        .requestMatchers("api/user/getUserData","api/admin/delete",
                                "/api/booking/userBookings","api/booking/book").hasAuthority(Role.USER.name())
                        .requestMatchers("api/booking/getDayBookings").hasAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors(Customizer.withDefaults());
        return http.build();
    }

    /**
     * Questo metodo serve per criptare le password.
     * @return password criptata
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(signInService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
