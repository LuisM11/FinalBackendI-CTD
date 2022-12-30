window.addEventListener('load', function () {


    
    //Buscamos y obtenemos el formulario donde estan
    //los datos que el usuario pudo haber modificado de la pelicula
    const formulario = document.querySelector('#update_turnos_form');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault()
        
        //creamos un JSON que tendrá los datos de la película
        //a diferencia de una pelicula nueva en este caso enviamos el id
        //para poder identificarla y modificarla para no cargarla como nueva
        const formData = {
            id:document.querySelector('#turnos_id').value,
            fecha: document.querySelector('#fecha').value,
            pacienteId: document.querySelector('#pacienteId').value,
            odontologoId: document.querySelector('#odontologoId').value,
        };
        
        console.log(formData)
        //invocamos utilizando la función fetch la API peliculas con el método PUT que modificará
        //la película que enviaremos en formato JSON
        const url = 'http://localhost:8080/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
        /* const f = async () => {
            const data = await fetch(url,settings)
            const datajson = await data.json()
            location.reload()
            }
        f() */

        fetch(url, settings)
            .then(response => {
                if(!response.ok) {
                    return response.text()
                    .then( res => {
                        if(res.includes("Odontologo")){
                            alert("No se pudieron actualizar los datos, Odontologo no existente")
                        }else{
                            alert("No se pudieron actualizar los datos, Paciente no existente")
                        }
                        throw new Error("")
                    })
                   }else{ return response.json()}                   
                })
            .then(data => {
                location.reload()
            })
            .catch(e => console.log("Verifique los datos") )
    })
 })

    //Es la funcion que se invoca cuando se hace click sobre el id de una pelicula del listado
    //se encarga de llenar el formulario con los datos de la pelicula
    //que se desea modificar
    function findBy(id) {
          const url = 'http://localhost:8080/turnos/'+ id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let turnos = data;
              document.querySelector('#turnos_id').value=turnos.id;
              document.querySelector('#fecha').value=turnos.fecha;
              document.querySelector('#pacienteId').value=turnos.pacienteId;
              document.querySelector('#odontologoId').value=turnos.odontologoId;

              //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_turnos_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }