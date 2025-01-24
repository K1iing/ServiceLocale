package com.mysql.controller;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.model.email.EmailDTO;
import com.mysql.model.email.ResetPasswordDTO;
import com.mysql.model.email.TokenDTO;
import com.mysql.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/postToken")
    @Operation(summary = "Receber token")
    public ResponseEntity<String> enviarToken(@RequestBody @Valid TokenDTO token) {
        try {
            String result = emailService.verifyToken(token);
            return ResponseEntity.ok(result);
        }catch (ExceptionPersonalizada e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resetPassword")
    @Operation(summary = "Alterar senha após confirmar o token")
    public ResponseEntity<String> alterarSenha(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResponseEntity.ok(emailService.resetPassword(resetPasswordDTO.token(), resetPasswordDTO.email(), resetPasswordDTO.newPassword()));
    }

    @PostMapping("/postConfirmationAtendimento")
    @Operation(summary = "Envia email de atendimento confirmado com sucesso")
    public ResponseEntity<String> enviarEmailAtendimentoComSucesso(@RequestBody @Valid EmailDTO emailDTO) {
        return ResponseEntity.ok(emailService.sendConfirmationAtendimento(emailDTO));
    }
}
