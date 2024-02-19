package com.example.ktschool.application.service

import com.example.ktschool.application.port.`in`.CreateTokenUseCase
import com.example.ktschool.config.JwtTokenProvider
import com.example.ktschool.logger
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
) : CreateTokenUseCase {
    override fun createToken(command: CreateTokenUseCase.Command): CreateTokenUseCase.Result {
        val authenticationToken = UsernamePasswordAuthenticationToken(
            command.username, ""
        )
        // 여기가 내부적으로 loadUserByUsername 을 호출.
        val authentication = authenticationManager.authenticate(authenticationToken)
        logger.info { "authentication: $authentication" }
        // jwt token 생성
        val token = jwtTokenProvider.createToken(authentication)
        return CreateTokenUseCase.Result(
            token.grantType,
            token.accessToken,
        )
    }
}