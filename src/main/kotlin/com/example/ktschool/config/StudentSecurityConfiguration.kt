//package com.example.ktschool.config
//
//import com.example.ktschool.application.StudentUserDetailService
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.Ordered
//import org.springframework.core.annotation.Order
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//
//
//@Configuration
//@EnableWebSecurity
//@Order(Ordered.HIGHEST_PRECEDENCE)
//class StudentSecurityConfiguration(
//    private val jwtTokenProvider: JwtTokenProvider,
//    private val userDetailsService: StudentUserDetailService
//) {
//    @Bean
//    fun filterChainSecond(http: HttpSecurity): SecurityFilterChain {
//        http
//            // userDetailsService 주입
//            .userDetailsService(userDetailsService)
//            .httpBasic { it.disable() }
//            .csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .authorizeHttpRequests {
//                it.anyRequest().permitAll()
//            }
//            .addFilterBefore(
//                JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
//                UsernamePasswordAuthenticationFilter::class.java
//            ).authenticationManager()
//
//        return http.build()
//    }
//}