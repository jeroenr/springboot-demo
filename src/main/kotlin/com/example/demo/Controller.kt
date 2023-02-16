package com.example.demo


import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min


@RestController
@RequestMapping("/")
@Validated
class Controller {

    //-----------------------------------------------------
    // With PreAuthorize
    //-----------------------------------------------------
    @GetMapping("withPreAuthorize")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    suspend fun withPreAuthorize(
        @RequestParam(defaultValue = "100", required = true) @Min(1) @Max(100) limit: Int,
    ): String {
        return "OK"
    }


    //-----------------------------------------------------
    // Without PreAuthorize
    //-----------------------------------------------------
    @GetMapping("withoutPreAuthorize")
    @ResponseStatus(HttpStatus.OK)
    suspend fun withoutPreAuthorize(
        @RequestParam(defaultValue = "100", required = true) @Min(1) @Max(100) limit: Int,
    ): String {
        return "OK"
    }
}