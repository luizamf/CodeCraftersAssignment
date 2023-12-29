package com.challenge.clientEnroller.controller;

import com.challenge.clientEnroller.dto.ClientDTO;
import com.challenge.clientEnroller.service.ClientEnrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/client/enroll")
public class ClientEnrollController {
    private final ClientEnrollService clientEnrollService;

    @Autowired
    public ClientEnrollController(ClientEnrollService clientEnrollService) {
        this.clientEnrollService = clientEnrollService;
    }

    @Operation(summary = "Checks the client's document ID validity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document ID valid", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "The client data is invalid", content = {@Content(mediaType = "application/json")})})
    @PostMapping(value = "/checkClient")
    public ResponseEntity<String> checkClient(@Valid @RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientEnrollService.checkClient(clientDTO), HttpStatus.OK);
    }

    @Operation(summary = "Generates an enrollment or denial document based on the given client data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generated document", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "The client data is invalid", content = {@Content(mediaType = "application/json")})})
    @PostMapping(value = "/generateDocument")
    public ResponseEntity<String> generateDocument(@Pattern(regexp = "[A-Z]{2}[0-9]{6}", message = "Invalid document number.") @RequestBody String documentId) {
        return new ResponseEntity<>(clientEnrollService.generateDocument(documentId), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
