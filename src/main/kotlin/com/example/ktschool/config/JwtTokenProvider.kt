package com.example.ktschool.config

import com.example.ktschool.domain.dto.MyUserDetail
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}
const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 30

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String
    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    /**
     * Token 생성
     */
    fun createToken(authentication: Authentication): TokenInfo {
        val authorities: String = authentication
            .authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)
        logger.info { "authentication.principal: ${authentication.principal}" }
        // Access Token 발행
        val accessToken = Jwts
            .builder()
            .setSubject(authentication.name)
            .claim("auth", authorities) // 여기 ROLE 이 들어간다.
            .claim(
                "username",
                (authentication.principal as MyUserDetail).username
            ) // 유저 account 식별자
            .claim(
                "id",
                (authentication.principal as MyUserDetail).id
            ) // 유저 account 식별자
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return TokenInfo("Bearer", accessToken)
    }

    /**
     * Token 정보 추출
     */
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token)
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val username = claims["username"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val id = claims["id"] ?: throw RuntimeException("잘못된 토큰입니다.")
        // 권한 정보 추출 key auth 를 통해 ROLE 정보를 가져온다.
        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }
        val principal: UserDetails =
            MyUserDetail((id as Int).toLong(), claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    /**
     * Token 검증
     */
    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            logger.error { e.message }
            when (e) {
                is SecurityException, is MalformedJwtException -> {
                    throw JwtException(
                        "Invalid JWT signature"
                    )
                }  // Invalid JWT Token
                is ExpiredJwtException -> {
                    throw JwtException(
                        "Expired JWT Token"
                    )
                }    // Expired JWT Token
                else -> {
                    throw JwtException(
                        "Invalid JWT token"
                    )
                }  // else
            }
        }
    }

    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

    companion object {
        const val AUTHORIZATION_HEADER: String = "Authorization"
    }
}