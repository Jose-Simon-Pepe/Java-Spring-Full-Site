window.addEventListener('load', function() {
    let form = document.querySelector('form');

    form.addEventListener('submit', (e) => {
        let errores = [];

        let nick = document.querySelector('input.nombre_usuario');

        if (nick.value == '') {
            errores.push('El campo de nombre de usuario tiene que estar completo');
        }else if (nick.value.length < 3) {
            errores.push('El campo de nombre debe tener al menos 3 caracteres');
        }

        let email = document.querySelector('input.email');

        if (email.value == '') {
            errores.push('El campo de email tiene que estar completo');
        }

        let pass = document.querySelector('input.contraseña');

        if (pass.value == '') {
            errores.push('El campo de contraseña tiene que estar completo');
        }

        let name = document.querySelector('input.nombre');

        if (name.value == '') {
            errores.push('El campo de nombre tiene que estar completo');
        }else if (name.value.length < 3) {
            errores.push('El campo de nombre debe tener al menos 3 caracteres');
        }

        let lastName = document.querySelector('input.apellido');

        if (lastName.value == '') {
            errores.push('El campo de apellido tiene que estar completo');
        }

        let dni = document.querySelector('input.dni');

        if (dni.value == '') {
            errores.push('El campo de DNI tiene que estar completo');
        }

        let born = document.querySelector('input.fechaNacimiento');

        if (born.value == '') {
            errores.push('El campo de fecha de nacimiento tiene que estar completo');
        }

        let phone = document.querySelector('input.telefono');

        if (phone.value == '') {
            errores.push('El campo de telefono tiene que estar completo');
        }

        let address = document.querySelector('input.direccion');

        if (address.value == '') {
            errores.push('El campo de direccion tiene que estar completo');
        }

        let sex = document.querySelector('input.sexo');

        if (sex.value == '') {
            errores.push('Debe elegir un sexo');
        }

        let type = document.querySelector('input.tipo_usuario');

        if (type.value == '') {
            errores.push('Debe elegir un tipo de usuario');
        }

        if (errores.length > 0) {
            e.preventDefault();

            let ulErrores = document.querySelector('div.errores ul');
/*             ulErrores.innerHTML = '';
 */
            errores.forEach(error => {
                ulErrores.innerHTML += `<li>${error}</li>`;
            });
        }
    });
});




/* 

function qs(element){
    return document.querySelector(element);
}

window.addEventListener('load',function(){
    let $form =qs ('#form'),
    $inputName = qs('#nombre_usuario'),
    $nameErrors = qs('#nombre_usuarioErrors'),
    $email = qs('#email'),
    $emailErrors = qs('#emailErrors'),
    $pass = qs('#contraseña'),
    $passErrors = qs('#contraseñaErrors'),
    $file = qs('#formFile'),
    $fileErrors = qs('#fileErrors'),
    regExAlpha = /^[a-zA-Z\sñáéíóúü ]*$/,
    regExDNI = /^[0-9]{7,8}$/,
    regExEmail = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i,
    regExPass = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$/;

    //Nombre
    $inputName.addEventListener('blur', function(){
        console.log($inputName.value.trim())
        switch (true) {
            case !$inputName.value.trim():
                $nameErrors.innerHTML = 'El campo nombre es obligatorio';
                $inputName.classList.add('is-invalid');
            break;
            case !regExAlpha.test($inputName.value):
                $nameErrors.innerHTML = 'Ingresa un nombre valido';
                $inputName.classList.add('is-invalid');
                break;
            case $inputName.value.trim().length <= 2:
                    $nameErrors.innerHTML = 'El nombre debe tener más de 2 caracteres';
                    $inputName.classList.add('is-invalid');
                break;
            default:
                $inputName.classList.remove('is-invalid');
                $inputName.classList.add('is-valid');
                $nameErrors.innerHTML = ""
            break;
        }
    });

    //Apellido
    $inputLastname.addEventListener('blur', function(){
        console.log($inputLastname.value.trim())
        switch (true) {
            case !$inputLastname.value.trim():
                $lastnameErrors.innerHTML = 'El campo Apellido es obligatorio';
                $inputLastname.classList.add('is-invalid');
            break;
            case !regExAlpha.test($inputLastname.value):
                $lastnameErrors.innerHTML = 'Ingresa un Apellido valido';
                $inputLastname.classList.add('is-invalid');
                break;
            case $inputLastname.value.trim().length <= 1:
                    $lastnameErrors.innerHTML = 'El Apellido debe tener más de un caracter';
                    $inputLastname.classList.add('is-invalid');
                break;
            default:
                $inputLastname.classList.remove('is-invalid');
                $inputLastname.classList.add('is-valid');
                $lastnameErrors.innerHTML = "";
            break;
        }
    });

    //Email
    $email.addEventListener('blur', function(){
        switch (true) {
            case !$email.value.trim():
                $emailErrors.innerHTML = 'El campo email es obligatorio';
                $email.classList.add('is-invalid');
                break;
            case !regExEmail.test($email.value):
                $emailErrors.innerHTML = 'Debe ingresar un email válido';
                $email.classList.add('is-invalid');
                break;    
            default:
                $email.classList.remove("is-invalid");
                $email.classList.add('is-valid');
                $emailErrors.innerHTML = "";
                break;
        }
    });

    //password
    $pass.addEventListener('blur', function(){
        switch (true) {
            case !$pass.value.trim():
                $passErrors.innerHTML = 'El campo contraseña es obligatorio';
                $pass.classList.add('is-invalid');
                break;
            case !regExPass.test($pass.value):
                $passErrors.innerHTML = 'La contraseña debe tener: entre 6 o 12 caracteres, al menos una mayúscula, una minúscula y un número';
                $pass.classList.add('is-invalid');
                break;    
            default:
                $pass.classList.remove("is-invalid");
                $pass.classList.add('is-valid');
                $passErrors.innerHTML = "";
                break;
        }
    });

    //password 2
    $pass2.addEventListener('blur', function(){
        switch (true) {
            case !$pass2.value.trim():
                $pass2Errors.innerHTML = 'Reingresa la contraseña';
                $pass2.classList.add('is-invalid');
                break;
            case $pass2.value !== $pass.value:
                $pass2Errors.innerHTML = 'las contraseñas no coinciden';
                $pass2.classList.add('is-invalid');
                break;             
            default:
                $pass2.classList.remove('is-invalid');
                $pass2.classList.add('is-valid');
                $pass2Errors.innerHTML = "";
                break;
        }
    });

    //Terminos y Condiciones
    $terms.addEventListener('click',()=>{
        $terms.value = "on";
        $terms.classList.toggle('is-valid');
        $terms.classList.remove('is-invalid');
        $termsErrors.innerHTML = "";
    });

    //Img
    $file.addEventListener('change', function fileValidation() {
        let filePath = $file.value, //Capturo el valor del input
            allowefExtensions = /(.jpg|.jpeg|.png|.gif|.web)$/i //Extensiones permitidas

        if (!allowefExtensions.exec(filePath)) { //El método exec() ejecuta una busqueda sobre las coincidencias de una expresión regular en una cadena especifica. Devuelve el resultado como array, o null.
            $fileErrors.innerHTML = 'Carga un archivo de imagen válido, con las extensiones (.jpg - .jpeg - .png - .gif)';
            $file.value = '';
            $imgPreview.innerHTML = '';
            return false;
        } else {
            // Image preview
            console.log($file.files);
            if ($file.files && $file.files[0]) {
                let reader = new FileReader();
                reader.onload = function (e) {
                    $imgPreview.innerHTML = '<img src="' + e.target.result + '"/>';
                };
                reader.readAsDataURL($file.files[0]);
                $fileErrors.innerHTML = '';
                $file.classList.remove('is-invalid');
            }
        }     
    });

    $form.addEventListener("submit", function (e) {
        e.preventDefault();
        let error = false;
        let formElements = this.elements;
        console.log(formElements)

        for (let index = 0; index < formElements.length - 1; index++) {
            if (
                formElements[index].value === "" &&
                formElements[index].name !== "avatar" &&
                formElements[index].name !== "phone" ||
                formElements[index].classList.contains('is-invalid')
            ) {
                formElements[index].classList.add('is-invalid');
                submitErrors.innerHTML = "Los campos señalados son obligatorios";
                error = true;
            }
        }

        if (!error) {
            console.log('Todo bien');
            $form.submit();
        }
    });
}); */