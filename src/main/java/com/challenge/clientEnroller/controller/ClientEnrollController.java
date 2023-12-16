package com.challenge.clientEnroller.controller;

import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.service.ClientEnrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/client/enroll")
public class ClientEnrollController {
    private final ClientEnrollService caregiverService;

    @Autowired
    public ClientEnrollController(ClientEnrollService caregiverService) {
        this.caregiverService = caregiverService;
    }

    @Operation(summary = "Gets the descending sorted list of all cryptos comparing the normalized range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got the descending ordered list",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientDTO.class))})})
    @GetMapping(value = "/checkClient")
    public ResponseEntity<String> checkClient() {
        return new ResponseEntity<>(caregiverService.checkClient(), HttpStatus.OK);
    }
}
