package com.digitalhouse.clinica;

import com.digitalhouse.clinica.entity.Domicilio;
import com.digitalhouse.clinica.entity.Paciente;
import com.digitalhouse.clinica.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionPacientesTest {
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MockMvc mockMvc;

    public void cargarDatos(){
        Paciente pacienteGuardado=pacienteService.guardarPaciente(new Paciente("Francisco","Gavilan","9999", LocalDate.of(2024,6,7),new Domicilio("Arica",9,"Lima","Lima"),"fgavilan@gmail.com"));
    }

    @Test
    public void listarTodosLosPacientes() throws Exception{
        cargarDatos();
        MvcResult respuesta=mockMvc.perform(MockMvcRequestBuilders.get("/paciente").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }
}
