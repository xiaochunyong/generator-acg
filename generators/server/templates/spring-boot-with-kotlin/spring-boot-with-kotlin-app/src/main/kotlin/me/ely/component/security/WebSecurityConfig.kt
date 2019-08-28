package me.ely.component.security

import me.ely.component.security.jdbc.LoginFailureHandler
import me.ely.component.security.jdbc.LoginSuccessHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun configure(http: HttpSecurity) {
        // 禁用csrf
        http.csrf().disable()

        http
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/**/*.html","/**/*.js","/**/*.css", "/**/*.ico").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/v2/cons/gen").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources", "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .anyRequest().authenticated()
        .and()
            .formLogin() // 默认的 successHandler 是 SavedRequestAwareAuthenticationSuccessHandler, 会自动重定向到上次访问被拒的请求URL
             // .successHandler(LoginSuccessHandler("/"))
            .failureHandler(LoginFailureHandler())

        .and()
            .exceptionHandling()
                .accessDeniedHandler(SimpleAccessDeniedHandler())
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("1111")).authorities("USER_MANAGER").and()
                .withUser("ely").password(passwordEncoder().encode("1111")).authorities("NONE")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return MD5PasswordEncoder() // BCryptPasswordEncoder()
    }

}
