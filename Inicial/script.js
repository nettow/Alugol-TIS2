function checkLogin(form){
    if(form.email.value == "jow" && form.password.value == "123")
    window.open('/Inicial/bem-vindo.html')
    else
    alert("E-mail ou senha inválidos :'(");
}

function checkRegister(form){
    if ((form.name.value).length < 3)
    alert("Nome deve conter mais de 3 letras.");

    if ((form.password.value).length < 6)
    alert("Senha deve conter mais de 6 caracteres.");
    
}

const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});