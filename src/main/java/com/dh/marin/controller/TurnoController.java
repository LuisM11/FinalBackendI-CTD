package com.dh.marin.controller;

import com.dh.marin.dto.TurnoDTO;
import com.dh.marin.exceptions.BadRequestException;
import com.dh.marin.exceptions.DuplicateConflictException;
import com.dh.marin.exceptions.ResourceNotFoundException;
import com.dh.marin.service.OdontologoService;
import com.dh.marin.service.PacienteService;
import com.dh.marin.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;


    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
    }
    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody TurnoDTO turno) throws ResourceNotFoundException, DuplicateConflictException {
        return new ResponseEntity<>(turnoService.guardarTurno(turno), HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        return  new ResponseEntity<>(turnoService.buscarTurno(id),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>("Se elimino el odontologo con id " + id, HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    public ResponseEntity<TurnoDTO> actualizarTurno(@RequestBody TurnoDTO turno) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.actualizarTurno(turno));
    }


}
