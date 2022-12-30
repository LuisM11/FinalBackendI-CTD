package com.dh.marin.service;
import com.dh.marin.entity.Odontologo;
import com.dh.marin.exceptions.BadRequestException;
import com.dh.marin.exceptions.DuplicateConflictException;
import com.dh.marin.exceptions.ResourceNotFoundException;
import com.dh.marin.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    private OdontologoRepository odontologoRepository;
    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }


    public Odontologo guardarOdontologo(Odontologo odontologo) throws DuplicateConflictException {
        Optional<Odontologo> matriculaRepetida = odontologoRepository.findByMatricula(odontologo.getMatricula());
        if(matriculaRepetida.isPresent()){
            throw new DuplicateConflictException(String.format("La matricula %s ya se encuentra asociada a otro odontologo ", odontologo.getMatricula()));
        }

        if(odontologo.getId()!= null && odontologoRepository.findById(odontologo.getId()).isPresent() ){
            throw new DuplicateConflictException(String.format("El id %s ya se encuentra asociado a otro odontologo ", odontologo.getId()));
        }

        return odontologoRepository.save(odontologo);
    }

    public Odontologo buscarOdontologo(Long id) throws ResourceNotFoundException {
        return odontologoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format( "Odontologo con id %d no encontrado",id)));
    }
    public Odontologo actualizarOdontologo(Odontologo odontologo) throws DuplicateConflictException, ResourceNotFoundException, BadRequestException {
        if(odontologo.getId() == null){
            throw  new BadRequestException("Incluya un id");
        }
        odontologoRepository.findById(odontologo.getId()).orElseThrow( ()-> new ResourceNotFoundException(String.format( "No se puede actualizar odontologo con id %d no encontrado",odontologo.getId())));
        Optional<Odontologo> matriculaRepetida = odontologoRepository.findByMatricula(odontologo.getMatricula());
        if(matriculaRepetida.isPresent() && odontologo.getId() != null && matriculaRepetida.get().getId() != odontologo.getId() ){
            throw new DuplicateConflictException(String.format("La matricula %s ya se encuentra asociada a otro odontologo ", odontologo.getMatricula()));
        }
        return odontologoRepository.save(odontologo);

    }
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoAEliminar= odontologoRepository.findById(id);
        if(odontologoAEliminar.isPresent()){
            odontologoRepository.deleteById(id);
        }else{
            throw   new ResourceNotFoundException("No se puede eliminar el Odontologo con id "+ id +" no existe en la base de datos");
        }
    }
    public List<Odontologo> buscarTodosOdontologos(){
        return odontologoRepository.findAll();
    }

}
