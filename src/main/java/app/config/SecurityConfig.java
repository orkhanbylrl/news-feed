package app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http builder configurations for authorize requests and form login (see below)


        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/user/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .usernameParameter("email")
//                .loginProcessingUrl("/user/handle_login")
                .defaultSuccessUrl("/news_feed")
                .and()
                .logout()
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionAuthenticationStrategy(new SessionFixationProtectionStrategy())
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web
                .ignoring()
                .requestMatchers("/templates/**", "/css/**", "/js/**", "/lib/**", "favicon.ico", "/resources/**", "/static/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(userDetailsService);
        return auth;
    }



}
