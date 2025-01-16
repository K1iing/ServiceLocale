package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.model.client.Cliente;
import com.mysql.model.email.EmailDTO;
import com.mysql.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JavaMailSender mailSender;

    public String sendToken(EmailDTO emailDTO) {

        Optional<Cliente> cliente = clienteRepository.findByEmail(emailDTO.email());

        if (cliente.isEmpty()) {
            throw new ExceptionPersonalizada("Cliente não encontrado");
        }

        Cliente clienteget = cliente.get();

        String token = UUID.randomUUID().toString();

        String subject = "Recuperação de Senha - Seu Token";
        String message = "Olá, " + clienteget.getNome() + ",\n\n" +
                "Aqui está o seu token de recuperação de senha:\n\n" +
                token + "\n\n" +
                "Este token é válido por 15 minutos.\n\n" +
                "Atenciosamente,\nEquipe de Suporte";


        sendEmail(emailDTO.email(), subject, message);

        return "Token enviado com sucesso para " + clienteget.getEmail();
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public String verifyToken(String email, String token) {

    }
}

