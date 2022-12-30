package com.dh.marin.service;

import com.dh.marin.entity.Domicilio;
import com.dh.marin.entity.Odontologo;
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
public class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarOdontologoTest(){
        Odontologo odontologo= new Odontologo("102#df33","Carlos","Zambrano");
        Odontologo odontologoGuardado= null;
        try {
            odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        } catch (DuplicateConflictException e) {
        }
        assertEquals(1L,odontologoGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarOdontologoTest(){
        Long idABuscar=1L;
        Odontologo odontologoBuscado= null;
        try {
            odontologoBuscado = odontologoService.buscarOdontologo(idABuscar);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(odontologoBuscado);
    }

    @Test
    @Order(3)
    public void buscarOdontologosTest(){
        List<Odontologo> odontologos=odontologoService.buscarTodosOdontologos();
        assertEquals(1,odontologos.size());
    }

    @Test
    @Order(4)
    public void actualizarOdontologoTest() throws DuplicateConflictException, BadRequestException, ResourceNotFoundException {
        Odontologo odontologoActualizar= new Odontologo(1L,"2321#df33","Mariana","Arias");
        odontologoService.actualizarOdontologo(odontologoActualizar);
        Odontologo odontologoActualizado=odontologoService.buscarOdontologo(1L);
        assertEquals("Mariana",odontologoActualizado.getNombre());
    }

    @Test
    @Order(5)
    public void eliminarOdontologoTest(){
        Long idAEliminar=1L;
        try {
            odontologoService.eliminarOdontologo(idAEliminar);
        } catch (ResourceNotFoundException e) {
        }
        Optional<Odontologo> odontologoEliminado = Optional.empty();
        try {
            odontologoEliminado = Optional.of(odontologoService.buscarOdontologo(idAEliminar));
        } catch (ResourceNotFoundException e) {
        }
        assertFalse(odontologoEliminado.isPresent());
    }






}
