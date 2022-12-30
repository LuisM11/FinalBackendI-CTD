package com.dh.marin.service;

import com.dh.marin.dto.TurnoDTO;
import com.dh.marin.entity.Odontologo;
import com.dh.marin.entity.Paciente;
import com.dh.marin.entity.Turno;
import com.dh.marin.exceptions.BadRequestException;
import com.dh.marin.exceptions.DuplicateConflictException;
import com.dh.marin.exceptions.ResourceNotFoundException;
import com.dh.marin.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    private TurnoRepository turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    public TurnoDTO guardarTurno(TurnoDTO turnodto) throws ResourceNotFoundException, DuplicateConflictException {
        odontologoService.buscarOdontologo(turnodto.getOdontologoId());
        pacienteService.buscarPaciente(turnodto.getPacienteId());
        if(turnodto.getId()!= null && turnoRepository.findById(turnodto.getId()).isPresent() ){
            throw new DuplicateConflictException(String.format("El id %s ya se encuentra asociado a otro turno ", turnodto.getId()));
        }
        Turno turnoGuardado=turnoRepository.save(turnoDTOATurno(turnodto));
        return turnoATurnoDTO(turnoGuardado);
    }
    public TurnoDTO buscarTurno(Long id) throws ResourceNotFoundException {
        return turnoATurnoDTO(
                turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format( "Turno con id %d no encontrado",id)))
        );

    }
    public List<TurnoDTO> listarTurnos(){
        List<Turno> turnosEncontrados=turnoRepository.findAll();
        List<TurnoDTO> respuesta= new ArrayList<>();
        for (Turno turno:turnosEncontrados) {
            respuesta.add(turnoATurnoDTO(turno));
        }
        return respuesta;
    }
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoAEliminar= turnoRepository.findById(id);
        if(turnoAEliminar.isPresent()){
            turnoRepository.deleteById(id);
        }else{
            throw   new ResourceNotFoundException("No se puede eliminar el Turno con id "+ id +" no existe en la base de datos");
        }
    }
    public TurnoDTO actualizarTurno(TurnoDTO turnodto) throws BadRequestException, ResourceNotFoundException {
        if(turnodto.getId() == null){
            throw  new BadRequestException("Incluya un id");
        }
        odontologoService.buscarOdontologo(turnodto.getOdontologoId());
        pacienteService.buscarPaciente(turnodto.getPacienteId());
        turnoRepository.findById(turnodto.getId()).orElseThrow( ()-> new ResourceNotFoundException(String.format( "No se puede actualizar el turno con id %d, no encontrado",turnodto.getId())));
        Turno turno = turnoRepository.save(turnoDTOATurno(turnodto));
        return turnoATurnoDTO(turno);
    }
    private Turno turnoDTOATurno(TurnoDTO turnodto){
        Turno respuesta= new Turno();
        //cargar la información de turno DTO al turno
        Odontologo odontologo= new Odontologo();
        Paciente paciente= new Paciente();
        odontologo.setId(turnodto.getOdontologoId());
        paciente.setId(turnodto.getPacienteId());
        respuesta.setFecha(turnodto.getFecha());
        respuesta.setId(turnodto.getId());
        //no debemos olvidarnos de agregar ambos objetos
        respuesta.setOdontologo(odontologo);
        respuesta.setPaciente(paciente);
        //salida
        return respuesta;
    }
    private TurnoDTO turnoATurnoDTO(Turno turno){
        //convertir el turno a un turnoDTO
        TurnoDTO respuesta= new TurnoDTO();
        //cargar la información de turno al turno DTO
        respuesta.setId(turno.getId());
        respuesta.setPacienteId(turno.getPaciente().getId());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setFecha(turno.getFecha());
        //devolución
        return respuesta;
    }
}
