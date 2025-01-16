package com.mysql.controller;

import com.mysql.model.email.ConfirmationEmailDTO;
import com.mysql.model.email.EmailDTO;
import com.mysql.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping()
    @Operation(summary = "Token Recuperação de senha")
    public ResponseEntity<String> enviarEmail(@RequestBody @Valid EmailDTO emailDTO) {
        return ResponseEntity.ok(emailService.sendToken(emailDTO));
    }

    @PostMapping("/getToken")
    @Operation(summary = "Receber token")
    public ResponseEntity<String> verificaToken(@RequestBody @Valid ConfirmationEmailDTO confirmationEmailDTO) {
        return ResponseEntity.ok(emailService.verifyToken(confirmationEmailDTO.email(), confirmationEmailDTO.email()));
    }
}
