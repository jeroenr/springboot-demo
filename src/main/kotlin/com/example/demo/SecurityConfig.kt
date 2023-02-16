package com.example.demo

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono


@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        applicationContext: ApplicationContext,
        bearerSecurityContextRepo: BearerSecurityContextRepository
    ): SecurityWebFilterChain {

        return http
            .exceptionHandling()
            .authenticationEntryPoint { exchange, _ ->
                val messageAsBytes = "Error".toByteArray()
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                exchange.response.headers.contentType = org.springframework.http.MediaType.APPLICATION_JSON
                exchange.response.writeWith(Mono.just(exchange.response.bufferFactory().wrap(messageAsBytes)))
            }
            .and()
            .securityContextRepository(bearerSecurityContextRepo)
            .authorizeExchange { exchange ->
                exchange
                    .pathMatchers(HttpMethod.GET).permitAll()
                    .anyExchange().authenticated()
            }
            .build()
    }
}