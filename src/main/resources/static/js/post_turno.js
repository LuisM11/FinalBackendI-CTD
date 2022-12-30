window.addEventListener('load', function () {

    
    const formulario = document.querySelector('#add_new_turno');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault()

        const formData = {
            fecha: document.querySelector('#fecha').value,
            pacienteId: document.querySelector('#pacienteId').value,
            odontologoId: document.querySelector('#odontologoId').value,
        };
        console.log(formData)
        //invocamos utilizando la función fetch la API peliculas con el método POST que guardará
        //la película que enviaremos en formato JSON
        const url = 'http://localhost:8080/turnos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => {
                if(!response.ok) {
                    return response.text()
                    .then( res => {
                        if(res.includes("Odontologo")){
                            alert("Odontologo no existente")
                        }else{
                            alert("Paciente no existente")
                        }
                        throw new Error(res)
                        
                    })
                   }
                  else {
                   return response.json();
                 }    
                })
            .then(data => {
                console.log(data)
                 //Si no hay ningun error se muestra un mensaje diciendo que la pelicula
                 //se agrego bien
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Odontologo agregado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                
                    /* console.log(error.status + "error") */
                    //Si hay algun error se muestra un mensaje diciendo que la pelicula
                    //no se pudo guardar y se intente nuevamente
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     //se dejan todos los campos vacíos por si se quiere ingresar otra pelicula
                     resetUploadForm();})
    });


    function resetUploadForm(){
        console.log("reset")
        document.querySelector('#fecha').value = "";
        document.querySelector('#pacienteId').value = "";
         document.querySelector('#odontologoId').value = "";

    }

    /* (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/post_turnos.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })(); */
});