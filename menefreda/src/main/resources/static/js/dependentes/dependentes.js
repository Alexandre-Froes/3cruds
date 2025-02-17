window.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    const cpf = document.getElementById("txtCpf");
    const sexo = document.getElementById("txtGenero");
    const telefone = document.getElementById("txtTel");
    const paciente = document.getElementById("txtPaciente");
    
    form.addEventListener("submit", function(e) { 
        e.preventDefault();

        if (cpf.value == "" || sexo.value == "" || telefone.value == "") {
            alert("Preencha todos os campos!");
            return;
        }

        if(paciente.value == ""){
            alert("Selecione um paciente!"); 
            return;
        }

        if (!/^\d{11}$/.test(cpf.value)) {
            alert("CPF inválido! CPF precisa ter 11 números.");
            return;
        }

        if (!/^\d{11}$/.test(telefone.value)) {
            alert("Telefone inválido! Telefone precisa ter 11 números.");
            return;
        }
        this.submit();
    });
});