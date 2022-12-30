package com.dh.marin.service;


import com.dh.marin.dto.TurnoDTO;
import com.dh.marin.entity.Domicilio;
import com.dh.marin.entity.Odontologo;
import com.dh.marin.entity.Paciente;
import com.dh.marin.entity.Turno;
import com.dh.marin.exceptions.DuplicateConflictException;
import com.dh.marin.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;

    public void cargarDatos() throws DuplicateConflictException {

        Paciente p = pacienteService.guardarPaciente(new Paciente("Luis",
                "Marin","5154", LocalDate.of(2022,11,28),
                "luifeee@gmail.com",new Domicilio("Calle a",454,"Medellin",
                "Antioquia")));
        this.odontologoService.guardarOdontologo(new Odontologo("984#d9s", "Tiago", "Lopez"));

    }

    @Test
    @Order(1)
    public void altaTurnoTest() throws DuplicateConflictException, ResourceNotFoundException {

        this.cargarDatos();
        turnoService.guardarTurno(new TurnoDTO(LocalDate.now(),pacienteService.buscarPaciente(1L).getId(),odontologoService.buscarOdontologo(1L).getId()));
        assertNotNull(turnoService.buscarTurno(1L));
    }


    @Test
    @Order(2)
    public void buscarTurnoTest() throws ResourceNotFoundException {
        assertNotNull(turnoService.buscarTurno(1L));
    }


    @Test
    @Order(3)
    public void eliminarTurnoTest() throws ResourceNotFoundException {
        turnoService.eliminarTurno(1L);
        assertThrows(ResourceNotFoundException.class, () -> turnoService.buscarTurno(1L), "Turno con id 1 no encontrado");
    }

    @Test
    @Order(4)
    public void listarTurnoTest()   {
        List<TurnoDTO> lista = turnoService.listarTurnos();
        assertEquals(0,lista.size());
    }

}
