// login.js
document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();

    // Obtém os valores dos campos de input
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    // Cria o objeto DTO com os dados
    const usuarioDTO = {
        username: username,
        password: password
    };

    // Envia a requisição POST
    fetch("/auth/logar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(usuarioDTO)
    })
    .then(response => {
        if (response.status === 200) {
            // Se o login for bem-sucedido, redireciona para a home
            window.location.href = "/atendimentoshome";
        } else {
            // Exibe a mensagem de erro caso o login falhe
            document.getElementById('errorMessage').style.display = 'block';
        }
    })
    .catch(error => {
        console.error("Erro ao fazer login:", error);
        alert("Erro ao fazer login");
    });
});
