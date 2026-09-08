package com.example.demo

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    private val log = LoggerFactory.getLogger(HelloController::class.java)

    @GetMapping("/hello")
    fun hello(@RequestParam(defaultValue = "world") name: String): Map<String, String> {
        log.info("[HELLO] Request received for name: {}", name)
        return mapOf("message" to "Hello, $name!")
    }

    @GetMapping("/order")
    fun createOrder(@RequestParam orderId: String, @RequestParam amount: Long): Map<String, Any> {
        log.info("[ORDER] Creating order with ID: {}, amount: {}", orderId, amount)
        Thread.sleep(50) // 비즈니스 로직 지연 시뮬레이션
        log.info("[ORDER] Payment processed successfully for order: {}", orderId)
        return mapOf("orderId" to orderId, "status" to "COMPLETED", "amount" to amount)
    }

    @GetMapping("/error-test")
    fun triggerError(@RequestParam(defaultValue = "UNKNOWN") code: String): Map<String, String> {
        log.error("[ERROR-TEST] Simulated failure occurred with error code: {}", code)
        throw IllegalStateException("Simulated business error with code: $code")
    }

    @GetMapping("/mdc-test")
    fun testMdc(
        @RequestParam(defaultValue = "user-999") userId: String,
        @RequestParam(defaultValue = "ROLE_ADMIN") role: String
    ): Map<String, String> {
        MDC.put("userId", userId)
        MDC.put("userRole", role)
        MDC.put("customTrackingId", "TRK-${System.currentTimeMillis()}")
        try {
            log.info("[MDC-TEST] Processing request with custom MDC context")
            return mapOf(
                "status" to "OK",
                "userId" to userId,
                "role" to role
            )
        } finally {
            MDC.clear()
        }
    }
}
