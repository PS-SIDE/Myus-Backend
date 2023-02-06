package io.paku.myus.config

import io.paku.myus.security.CustomUserDetailService
import io.paku.myus.security.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val jwtFilter: JwtFilter,
    private val customUserDetailService: CustomUserDetailService
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) }
            .oauth2Login {
                it.userInfoEndpoint().userService(customUserDetailService)
                it.defaultSuccessUrl("/api/auth/login")
                it.failureUrl("/fail")
            }

//        http
//            .authorizeHttpRequests()
//            .requestMatchers("/", "/login/**", "/oauth2/**").permitAll()
//            .anyRequest().authenticated()
//            .and()
//
//            .oauth2Login()
//            .defaultSuccessUrl("/login/complete")
//            .and()
//
//            .headers().frameOptions().disable()
//            .and()
//
//            .exceptionHandling()
//            .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
//            .and()
//
//            .formLogin()
//            .successForwardUrl("/welcome")
//            .and()
//
//            .logout()
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/login")
//            .deleteCookies("JSESSIONID")
//            .invalidateHttpSession(true)
//            .and()
//
//            .addFilterAt(CharacterEncodingFilter(), CsrfFilter::class.java)
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .csrf().disable()

//        http.csrf().disable()
//
//        http.authorizeHttpRequests()
//            .requestMatchers("/api/auth/register", "/api/auth").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

//    @Bean
//    fun clientRegistrationRepository(oauthClientProperties: OAuth2ClientProperties, customOAuthClientProperties: CustomOAuthClientProperties): InMemoryClientRegistrationRepository {
//        val registrations = oauthClientProperties.registration.keys.mapNotNull {
//            getRegistration(
//                oauthClientProperties,
//                it
//            )
//        }.toMutableList()
//
//        val customRegistrations = customOAuthClientProperties.registaration
//
//        for (customRegistration in customRegistrations) {
//            when(customRegistration.key) {
//                "kakao" -> registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
//                    .clientId(customRegistration.value.clientId)
//                    .clientSecret(customRegistration.value.clientSecret)
//                    .jwkSetUri(customRegistration.value.jwkSetUri)
//                    .build()
//                )
//                "naver" -> registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
//                    .clientId(customRegistration.value.clientId)
//                    .clientSecret(customRegistration.value.clientSecret)
//                    .jwkSetUri(customRegistration.value.jwkSetUri)
//                    .build()
//                )
//            }
//        }
//
//        return InMemoryClientRegistrationRepository(registrations)
//    }
//
//    private fun getRegistration(clientProperties: OAuth2ClientProperties, client: String): ClientRegistration? {
//        val registration = clientProperties.registration[client]
//        return when(client) {
//            "google" -> CommonOAuth2Provider.GOOGLE.getBuilder(client)
//                .clientId(registration?.clientId)
//                .clientSecret(registration?.clientSecret)
//                .scope("email")
//                .build()
//
//            else -> null
//        }
//    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager = authenticationConfiguration.authenticationManager
}