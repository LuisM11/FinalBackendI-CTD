package com.dh.marin.controller;

import com.dh.marin.entity.Odontologo;
import com.dh.marin.exceptions.BadRequestException;
import com.dh.marin.exceptions.DuplicateConflictException;
import com.dh.marin.exceptions.ResourceNotFoundException;
import com.dh.marin.service.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos(){
        return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException, DuplicateConflictException {
        if(odontologo.getNombre() == null || odontologo.getMatricula() == null || odontologo.getApellido() == null ){
            throw new BadRequestException("No se han especificado correctamente los datos del odontologo o son nulos");
        }
        return new ResponseEntity<>(odontologoService.guardarOdontologo(odontologo),HttpStatus.CREATED);
    }
    @PutMapping()
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) throws ResourceNotFoundException, DuplicateConflictException, BadRequestException {
        return ResponseEntity.ok(odontologoService.actualizarOdontologo(odontologo));
    }
    @GetMapping("{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        return  new ResponseEntity<>(odontologoService.buscarOdontologo(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Se elimino el odontologo con id " + id, HttpStatus.NO_CONTENT);
    }








}
