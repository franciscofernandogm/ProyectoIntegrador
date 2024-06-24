package com.digitalhouse.clinica.controller;

import com.digitalhouse.clinica.entity.Odontologo;
import com.digitalhouse.clinica.entity.Paciente;
import com.digitalhouse.clinica.entity.Turno;
import com.digitalhouse.clinica.exception.ResourceNotFoundException;
import com.digitalhouse.clinica.service.OdontologoService;
import com.digitalhouse.clinica.service.PacienteService;
import com.digitalhouse.clinica.service.TurnoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarPorId(turno.getOdontologo().getId());
        if(pacienteBuscado.isPresent() && odontologoBuscado.isPresent()){
            turno.setPaciente(pacienteBuscado.get());
            turno.setOdontologo(odontologoBuscado.get());
            return ResponseEntity.ok(turnoService.registrarTurno(turno));
        }else if(pacienteBuscado.isPresent()){
            throw new BadRequestException("No existe odontologo");
        }else{
            throw new BadRequestException("No existe paciente");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoBuscado=turnoService.buscarPorId(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }else {
            throw new ResourceNotFoundException("No se encontro turno con id "+id);
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) throws BadRequestException{
        Optional<Turno> turnoBuscado=turnoService.buscarPorId(turno.getId());
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarPorId(turno.getOdontologo().getId());
        if(turnoBuscado.isPresent() && pacienteBuscado.isPresent() && odontologoBuscado.isPresent()){
            turno.setPaciente(pacienteBuscado.get());
            turno.setOdontologo(odontologoBuscado.get());
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("turno actualizado");
        }else if(turnoBuscado.isEmpty()){
            throw  new BadRequestException("No se encontro turno");
        } else if (pacienteBuscado.isEmpty()) {
            throw  new BadRequestException("No se encontro paciente");
        }else{
            throw new BadRequestException("No se encontro odontologo");
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado=turnoService.buscarPorId(id);
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("turno eliminado con exito");
        }else{
            throw new ResourceNotFoundException("No existe ese id "+id);
        }
    }
}
