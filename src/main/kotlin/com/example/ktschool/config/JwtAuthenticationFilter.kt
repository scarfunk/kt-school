package com.example.ktschool.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService,
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        // http 헤더에서 토큰을 가져오고
        val token = resolveToken(request as HttpServletRequest)
        // jwt 검사를 진행하고.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication = jwtTokenProvider.getAuthentication(token)
            // 실시간으로 재조회 하고 싶다면 아래처럼. (이건 필요에 따라서)
//            val username = (authentication.principal as UserDetails).username
//            val userDetails = userDetailsService.loadUserByUsername(username)
//            val newAuthentication = UsernamePasswordAuthenticationToken(
//                userDetails,
//                authentication.credentials,
//                userDetails.authorities
//            )
//            SecurityContextHolder.getContext().authentication = newAuthentication
            // SecurityContextHolder에 인증정보를 넣어준다.
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        // header 형태는 {Authorization: Bearer xxxx} 같은 형태이다.
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}