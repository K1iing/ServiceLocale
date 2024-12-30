package com.mysql.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String carregaPaginaListagem() {
        return "autenticacao/login";
    }

    @GetMapping("/home")
    public String carregarPaginaHome() {
        return "home/home";
    }

    @GetMapping("/")
    public String redirecionaHome(RedirectAttributes redirectAttributes) {
        return "redirect:/home";
    }

    @GetMapping("/atendimentoshome")
    public String redirecionarAtendimentos() {
        return "home/atendimentos";
    }

    @GetMapping("/cadastrar")
    public String redirecionarCadastro() {
        return "autenticacao/cadastro";
    }

    @GetMapping("/docker")
    public String redirecionarChat() {
        return "home/docker";
    }
}
