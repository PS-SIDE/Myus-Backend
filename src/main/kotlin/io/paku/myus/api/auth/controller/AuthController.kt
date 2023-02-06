package io.paku.myus.api.auth.controller

import io.paku.myus.api.auth.dto.request.AuthRequestDto
import io.paku.myus.api.auth.dto.response.AuthDto
import io.paku.myus.api.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/auth")
@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody authRequestDto: AuthRequestDto
    ): ResponseEntity<AuthDto> {
        return ResponseEntity
            .status(201)
            .body(authService.register(authRequestDto))
    }

    @PostMapping
    fun login(
        @RequestBody authRequestDto: AuthRequestDto
    ): ResponseEntity<AuthDto> {
        return ResponseEntity
            .ok()
            .body(authService.login(authRequestDto))
    }

    @GetMapping("/login")
    fun socialLogin(@AuthenticationPrincipal oAuth2User: OAuth2User): ResponseEntity<AuthDto> {
        return ResponseEntity
            .ok()
            .body(authService.socialLogin(oAuth2User))
    }

    @GetMapping
    fun refresh(
        @RequestBody authDto: AuthDto
    ): ResponseEntity<String> {
        return ResponseEntity
            .ok()
            .body(authService.refreshAuth(authDto))
    }
}