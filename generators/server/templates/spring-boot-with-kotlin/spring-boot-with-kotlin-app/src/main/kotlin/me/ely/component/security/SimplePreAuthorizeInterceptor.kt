package me.ely.component.security

import me.ely.extension.substringBetween
import me.ely.util.HttpServletRequestExtensions
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Aspect
@Component
@Order(100)
class SimplePreAuthorizeInterceptor {

    @Around("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    @Throws(Throwable::class)
    fun intercept(joinPoint: ProceedingJoinPoint): Any {
        val authority = (joinPoint.signature as MethodSignature).method.getAnnotation(PreAuthorize::class.java).value
        if (authority.isNotEmpty() && authority.contains("hasAuthority")) {
            HttpServletRequestExtensions.saveRequestAuthority(authority.substringBetween("hasAuthority('", "')"))
        }
        return joinPoint.proceed()
    }
}
