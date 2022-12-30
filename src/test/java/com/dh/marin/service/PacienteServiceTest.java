package com.dh.marin.service;

import com.dh.marin.entity.Domicilio;
import com.dh.marin.entity.Paciente;
import com.dh.marin.exceptions.BadRequestException;
import com.dh.marin.exceptions.DuplicateConflictException;
import com.dh.marin.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPacienteTest(){
        Paciente pacienteAGuardar= new Paciente("Luis",
                "Marin","5154", LocalDate.of(2022,11,28),
                "luifeee@gmail.com",new Domicilio("Calle a",454,"Medellin",
                "Antioquia"));
        Paciente pacienteGuardado= null;
        try {
            pacienteGuardado = pacienteService.guardarPaciente(pacienteAGuardar);
        } catch (DuplicateConflictException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1L,pacienteGuardado.getId());
    }
    @Test
    @Order(2)
    public void buscarPacientePorIDTest(){
        Long idABuscar=1L;
        Paciente pacienteBuscado= null;
        try {
            pacienteBuscado = pacienteService.buscarPaciente(idABuscar);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(pacienteBuscado);
    }
    @Test
    @Order(3)
    public void buscarPacientesTest(){
        List<Paciente> pacientes=pacienteService.buscarPacientes();
        assertEquals(1,pacientes.size());
    }
    @Test
    @Order(4)
    public void actualizarPacienteTest() throws DuplicateConflictException, BadRequestException, ResourceNotFoundException {
        Paciente pacienteAActualizar= new Paciente(1L,"Ezequiel",
                "Baspineiro","5154", LocalDate.of(2022,11,28),
                "rodo@gmail.com",new Domicilio(1L,"Calle a",454,"Salta capital",
                "Salta"));
        pacienteService.actualizarPaciente(pacienteAActualizar);
        Paciente pacienteActualizado=pacienteService.buscarPaciente(1L);
        assertEquals("Ezequiel",pacienteActualizado.getNombre());
    }
    @Test
    @Order(5)
    public void eliminarPacienteTest(){
        Long idAEliminar=1L;
        try {
            pacienteService.eliminarPaciente(idAEliminar);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        Optional<Paciente> pacienteEliminado = Optional.empty();
        try {
            pacienteEliminado = Optional.of(pacienteService.buscarPaciente(idAEliminar));
        } catch (ResourceNotFoundException e) {
        }
        assertFalse(pacienteEliminado.isPresent());
    }
}