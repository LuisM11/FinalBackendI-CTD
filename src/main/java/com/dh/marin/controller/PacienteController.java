package com.dh.marin.controller;

import com.dh.marin.entity.Paciente;
import com.dh.marin.exceptions.BadRequestException;
import com.dh.marin.exceptions.DuplicateConflictException;
import com.dh.marin.exceptions.ResourceNotFoundException;
import com.dh.marin.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        return  new ResponseEntity<>(pacienteService.buscarPaciente(id),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes(){
        return ResponseEntity.ok(pacienteService.buscarPacientes());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return new ResponseEntity<>("Se elimino el paciente con id " + id, HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException, DuplicateConflictException {
        if(paciente.getNombre() == null || paciente.getApellido() == null || paciente.getEmail() == null  || paciente.getCedula() == null || paciente.getFechaIngreso() == null || paciente.getDomicilio() == null) {
            throw new BadRequestException("No se han especificado correctamente los datos del paciente o son nulos");
        }
        return new ResponseEntity<>(pacienteService.guardarPaciente(paciente),HttpStatus.CREATED);

    }
    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) throws DuplicateConflictException, BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.actualizarPaciente(paciente));
    }

    @GetMapping("/buscar/correo/{email}")
    public ResponseEntity<Paciente> buscarPacientePorCorreo(@PathVariable String email){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPacientePorCorreo(email);
        return pacienteBuscado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
