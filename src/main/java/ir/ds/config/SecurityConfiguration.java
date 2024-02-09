package ir.ds.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
//@EnableWebSecurity
public class SecurityConfiguration {
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers(PathRequest.toH2Console()).permitAll()
////                        .requestMatchers(antMatcher("/api")).hasRole("ADMIN")
////                        .requestMatchers(antMatcher("/user")).hasRole("USER")
//                        .anyRequest().permitAll()
//                )
////                .formLogin(formLogin ->
////                        formLogin.defaultSuccessUrl("/")
////                )
//                .headers(headers ->
//                        headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
//                );
////                .csrf(csrf -> csrf
////                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2/**"))
////                );
//    }
}