// URL base do backend
const BASE_URL = 'http://localhost:8080';

// Função para fazer login
async function login(username, password) {
    try {
        const response = await fetch(`${BASE_URL}/auth/logar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Erro ao fazer login. Verifique suas credenciais.');
        }

        const data = await response.json();
        localStorage.setItem('jwtToken', data.token);
        alert('Login realizado com sucesso!');
        window.location.href = '/home';
    } catch (error) {
        console.error('Erro ao fazer login:', error);
        alert('Erro ao fazer login: ' + error.message);
    }
}

// Função para verificar se o usuário está autenticado
function isAuthenticated() {
    const token = localStorage.getItem('jwtToken');
    return !!token && token.trim() !== '';
}

// Função para realizar logout
function logout() {
    localStorage.removeItem('jwtToken');
    alert('Você saiu da aplicação.');
    window.location.href = '/login';
}

// Função para carregar profissionais
// Função para carregar profissionais sem Bearer Token
function carregarProfissionais() {
    fetch(`${BASE_URL}/profissional`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao carregar os profissionais: ' + response.statusText);
        }
        return response.json();
    })
    .then(profissionais => {
        const selectElement = document.getElementById('idProfissional');
        selectElement.innerHTML = '';

        // Adiciona a opção padrão
        const option = document.createElement('option');
        option.value = '';
        option.textContent = 'Selecione um profissional';
        selectElement.appendChild(option);

        // Adiciona os profissionais ao dropdown
        profissionais.forEach(profissional => {
            const option = document.createElement('option');
            option.value = profissional.id;
            option.textContent = `${profissional.nome} - ${profissional.especialidade}`;
            selectElement.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Erro ao carregar os profissionais:', error);
        const messageElement = document.getElementById('message');
        if (messageElement) {
            messageElement.innerText = `Erro ao carregar profissionais: ${error.message}`;
            messageElement.classList.add('error');
        }
    });
}

// Função para agendar um atendimento
function agendarAtendimento(idProfissional, dataAgendada, descricao) {
    const token = localStorage.getItem('jwtToken');

    const atendimentoData = {
        idProfissional: parseInt(idProfissional),
        dataAgendada: dataAgendada,
        descricao: descricao
    };

    fetch(`${BASE_URL}/atendimentos`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify(atendimentoData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => {
                throw new Error(err.message || 'Erro ao agendar atendimento');
            });
        }

        // Verifica se há conteúdo no corpo da resposta
        return response.text().then(text => {
            return text ? JSON.parse(text) : null;
        });
    })
    .then(data => {
        alert('Atendimento agendado com sucesso!');
        document.getElementById('atendimentoForm').reset();
    })
    .catch(error => {
        console.error('Erro ao agendar atendimento:', error);
        alert('Erro ao agendar atendimento: ' + error.message);
    });
}


// Adiciona eventos ao DOM
document.addEventListener('DOMContentLoaded', function() {
    const profissionalSelect = document.getElementById('idProfissional');
    if (profissionalSelect) {
        carregarProfissionais();
    }

    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            login(username, password);
        });
    }

    const atendimentoForm = document.getElementById('atendimentoForm');
    if (atendimentoForm) {
        atendimentoForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const idProfissional = document.getElementById('idProfissional').value;
            const dataAgendada = document.getElementById('dataAgendada').value;
            const descricao = document.getElementById('descricao').value;
            agendarAtendimento(idProfissional, dataAgendada, descricao);
        });
    }

    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            logout();
        });
    }
});