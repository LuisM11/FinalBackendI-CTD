function deleteBy(id){
    const url='http://localhost:8080/pacientes/'+id;
    const settings={
        method: 'DELETE'
    }
    const f = async () =>{
        const a = await fetch(url,settings)
        let row_id = "#tr_" + id;
        document.querySelector(row_id).remove();
        location.reload()
  
      }
      f()
}