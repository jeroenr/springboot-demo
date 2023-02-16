package com.example.demo


import jakarta.validation.ConstraintViolationException
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/")
@Validated
class Controller {

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleException(e: ConstraintViolationException): ResponseEntity<String> {
        return ResponseEntity<String>(e.constraintViolations.joinToString(";") { it.message }, HttpStatus.BAD_REQUEST)
    }

    //-----------------------------------------------------
    // With PreAuthorize
    //-----------------------------------------------------
    @GetMapping("withPreAuthorize")
    @PreAuthorize("hasRole('USER')")
    suspend fun withPreAuthorize(
        @RequestParam(
            defaultValue = "100",
            required = true
        ) @Min(1L) @Max(100) limit: Int,
    ): String {
        return "OK $limit"
    }


    //-----------------------------------------------------
    // Without PreAuthorize
    //-----------------------------------------------------
    @GetMapping("withoutPreAuthorize")
    suspend fun withoutPreAuthorize(
        @RequestParam(
            defaultValue = "100",
            required = true
        ) @Min(1) @Max(100) limit: Int,
    ): String {
        return "OK $limit"
    }
}