package com.dh.marin.service;


import com.dh.marin.entity.Paciente;
import com.dh.marin.exceptions.BadRequestException;
import com.dh.marin.exceptions.DuplicateConflictException;
import com.dh.marin.exceptions.ResourceNotFoundException;
import com.dh.marin.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente) throws DuplicateConflictException {
        Optional<Paciente> dniRepetido = pacienteRepository.findByCedula(paciente.getCedula());
        if(dniRepetido.isPresent()){
            throw new DuplicateConflictException(String.format("La cedula %s ya se encuentra asociada a otro paciente ", paciente.getCedula()));
        }

        if(paciente.getId()!= null && pacienteRepository.findById(paciente.getId()).isPresent() ){
            throw new DuplicateConflictException(String.format("El id %s ya se encuentra asociado a otro paciente ", paciente.getId()));
        }

        return pacienteRepository.save(paciente);
    }
    public Paciente buscarPaciente(Long id) throws ResourceNotFoundException {
        return pacienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format( "Paciente con id %d no encontrado",id)));
    }
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteAeliminar= pacienteRepository.findById(id);
        if(pacienteAeliminar.isPresent()){
            pacienteRepository.deleteById(id);
        }else{
            throw   new ResourceNotFoundException("No se puede eliminar el paciente con id "+ id +" no existe en la base de datos");
        }
    }
    public Paciente actualizarPaciente(Paciente paciente) throws BadRequestException, ResourceNotFoundException, DuplicateConflictException {
        if(paciente.getId() == null){
            throw  new BadRequestException("Incluya un id");
        }
        pacienteRepository.findById(paciente.getId()).orElseThrow( ()-> new ResourceNotFoundException(String.format( "No se puede actualizar paciente con id %d no encontrado",paciente.getId())));
        Optional<Paciente> cedulaRepetida = pacienteRepository.findByCedula(paciente.getCedula());
        if(cedulaRepetida.isPresent() && paciente.getId() != null && cedulaRepetida.get().getId() != paciente.getId() ){
            throw new DuplicateConflictException(String.format("La cedula  %s ya se encuentra asociada a otro paciente ", paciente.getCedula()));
        }
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> buscarPacientes(){
        return pacienteRepository.findAll();
    }
    public Optional<Paciente> buscarPacientePorCorreo(String correo){
        return pacienteRepository.findByEmail(correo);
    }
}
