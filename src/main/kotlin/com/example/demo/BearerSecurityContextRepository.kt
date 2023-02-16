package com.example.demo

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Repository
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Repository
class BearerSecurityContextRepository: ServerSecurityContextRepository {


    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw NotImplementedError("Not support this function.")
    }


    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        return Mono.just(SecurityContextImpl(AuthToken(listOf(SimpleGrantedAuthority("ROLE_USER")))))
    }


    class AuthToken (
        authorities: List<GrantedAuthority>?
    ): AbstractAuthenticationToken(authorities) {

        override fun getCredentials(): Any {
            return "OK"
        }

        override fun getPrincipal(): Any {
            return "OK"
        }
    }
}