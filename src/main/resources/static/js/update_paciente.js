window.addEventListener('load', function () {


    //Buscamos y obtenemos el formulario donde estan
    //los datos que el usuario pudo haber modificado de la pelicula
    const formulario = document.querySelector('#update_paciente_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault()

        //creamos un JSON que tendrá los datos de la película
        //a diferencia de una pelicula nueva en este caso enviamos el id
        //para poder identificarla y modificarla para no cargarla como nueva
        const formData = {
            id: document.querySelector('#paciente_id').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            cedula: document.querySelector('#cedula').value,
            fechaIngreso: document.querySelector('#fechaIngreso').value,
            email: document.querySelector('#email').value,
            domicilio:{
                id:document.querySelector('#domicilio_id').value,
                calle: document.querySelector('#calle').value,
                numero: document.querySelector('#numero').value,
                localidad: document.querySelector('#localidad').value,
                provincia: document.querySelector('#provincia').value,
                        }

        };

        //invocamos utilizando la función fetch la API peliculas con el método PUT que modificará
        //la película que enviaremos en formato JSON
        const url = 'http://localhost:8080/pacientes';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        const f = async () => {
            const data = await fetch(url,settings)
            location.reload()
            }
        f()
        

    })
 })

    //Es la funcion que se invoca cuando se hace click sobre el id de una pelicula del listado
    //se encarga de llenar el formulario con los datos de la pelicula
    //que se desea modificar
    function findBy(id) {
        console.log(id)
          const url = 'http://localhost:8080/pacientes'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => {
            /* console.log(response) */
            return response.json()
          })
        
          .then(data => {
            /* console.log(data) */
            let paciente = data;
            document.querySelector('#paciente_id').value=paciente.id;
            document.querySelector('#nombre').value=paciente.nombre;
            document.querySelector('#apellido').value=paciente.apellido;
            document.querySelector('#cedula').value=paciente.cedula;
            document.querySelector('#fechaIngreso').value=paciente.fechaIngreso;
            document.querySelector('#email').value=paciente.email;
            document.querySelector('#domicilio_id').value=paciente.domicilio.id;
            document.querySelector('#calle').value=paciente.domicilio.calle;
            document.querySelector('#numero').value=paciente.domicilio.numero;
            document.querySelector('#localidad').value=paciente.domicilio.localidad;
            document.querySelector('#provincia').value=paciente.domicilio.provincia;

              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_paciente_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }