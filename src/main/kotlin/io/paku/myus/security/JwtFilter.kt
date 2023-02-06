package io.paku.myus.security

import io.paku.myus.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtUtils: JwtUtils
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
            ?: return filterChain.doFilter(request, response)

        val token = authorizationHeader.substring("Bearer ".length)

        if (jwtUtils.validateToken(token)) {
            val email = jwtUtils.getEmail(token)
            val authentication = jwtUtils.getAuthentication(email)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}