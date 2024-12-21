// cadastro.js
document.getElementById("cadastroForm").addEventListener("submit", function(event) {
    event.preventDefault();

    // Obtém os valores dos campos
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Cria o objeto DTO
    const usuarioDTO = {
        username: username,
        password: password
    };

    // Realiza a requisição POST para o endpoint de cadastro
    fetch("/auth/cadastrar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(usuarioDTO)
    })
    .then(response => {
        const messageElement = document.getElementById("message");
        if (response.ok) {
            // Exibe mensagem de sucesso
            messageElement.style.display = "block";
            messageElement.querySelector("p").textContent = "Cadastro realizado com sucesso!";
            messageElement.style.color = "green";

            // Opcional: Redireciona para a página de login após alguns segundos
            setTimeout(() => {
                window.location.href = "/login";
            }, 2000);
        } else {
            // Exibe mensagem de erro
            messageElement.style.display = "block";
            messageElement.querySelector("p").textContent = "Erro ao realizar cadastro. Verifique os dados.";
            messageElement.style.color = "red";
        }
    })
    .catch(error => {
        console.error("Erro ao cadastrar usuário:", error);
        alert("Ocorreu um erro ao tentar cadastrar. Tente novamente mais tarde.");
    });
});
