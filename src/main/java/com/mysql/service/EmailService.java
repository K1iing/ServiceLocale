package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.model.client.Cliente;
import com.mysql.model.email.EmailDTO;
import com.mysql.model.email.PasswordResetToken;
import com.mysql.model.email.TokenDTO;
import com.mysql.model.usuario.UsuarioEntity;
import com.mysql.repository.ClienteRepository;
import com.mysql.repository.PasswordResetTokenRepository;
import com.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

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
                "Atenciosamente,\nEquipe de Suporte, ClubService";

        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setCliente(clienteget);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpirationTime(expirationTime);

        passwordResetTokenRepository.save(passwordResetToken);

        sendEmail(emailDTO.email(), subject, message);

        return "Token enviado com sucesso para " + clienteget.getEmail();
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public String sendConfirmationAtendimento(EmailDTO emailDTO) {

        Optional<Cliente> cliente = clienteRepository.findByEmail(emailDTO.email());

        if (cliente.isEmpty()) {
            throw new ExceptionPersonalizada("Cliente não encontrado");
        }

        Cliente clienteget = cliente.get();

        String subject = "Atendimento Confirmado com Sucesso!";
        String message = "Olá, " + clienteget.getNome() + ",\n\n" +
                "Seu atendimento foi Cadastrado com Sucesso!\n\n" +
                "Atenciosamente,\nEquipe de Suporte, ClubService!";


        sendEmail(emailDTO.email(), subject, message);

        return "Email enviado com sucesso para " + clienteget.getEmail();
    }

    public String verifyToken(TokenDTO tokenDTO) {

        String token = tokenDTO.token();

        Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            throw new ExceptionPersonalizada("Token invalido ou não encontrado");
        }

        PasswordResetToken passwordResetToken = tokenOptional.get();

        if (passwordResetToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new ExceptionPersonalizada("O token expirou");
        }

        return "Token validado com sucesso";
    }

    public String resetPassword(TokenDTO tokenDTO, String email, String newPassword) {

        String verificationResult = verifyToken(tokenDTO);

        if ("Token validado com sucesso".equals(verificationResult)) {
            Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);

            Optional<UsuarioEntity> usuarioEntityOptional = userRepository.searchByUsername(email);

            if (clienteOptional.isEmpty() && usuarioEntityOptional.isEmpty()) {
                throw new ExceptionPersonalizada("Cliente não encontrado ou token expirado");
            }
            UsuarioEntity usuarioEntity = usuarioEntityOptional.get();
            Cliente cliente = clienteOptional.get();

            cliente.setSenha(bCryptPasswordEncoder.encode(newPassword));
            clienteRepository.save(cliente);

            usuarioEntity.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(usuarioEntity);



            return "Senha alterada com sucesso! " + cliente.getEmail();
        }

        return "Falha na verificação do token";
    }


}

