package com.digitalhouse.clinica.controller;

import com.digitalhouse.clinica.entity.Paciente;
import com.digitalhouse.clinica.exception.ResourceNotFoundException;
import com.digitalhouse.clinica.service.PacienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException{
        if(paciente.getEmail()==null){
            throw new BadRequestException("Debes registrar tu email");
        }else{
            Optional<Paciente> pacienteBuscado=pacienteService.buscarPorEmail(paciente.getEmail());
            if(pacienteBuscado.isPresent()){
                throw new BadRequestException("El email "+paciente.getEmail()+" ya existe, debes registrar otro email");
            }else{
                return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPorId(id);
        if (pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else {
            throw new ResourceNotFoundException("No se encontro paciente con id "+id);
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPorId(paciente.getId());
        if(pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("paciente actualizado");
        }else{
            throw new BadRequestException("No se encontro paciente");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable String email) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPorEmail(email);
        if (pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else {
            throw new ResourceNotFoundException("No se encontro pacient con email "+email);
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPorId(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("paciente eliminado con éxito");
        }else{
            throw new ResourceNotFoundException("No existe ese id "+id);
        }
    }
}
