package com.digitalhouse.clinica.service;

import com.digitalhouse.clinica.entity.Turno;
import com.digitalhouse.clinica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    public Turno registrarTurno(Turno turno){
        return turnoRepository.save(turno);
    }

    public void actualizarTurno(Turno turno){
        turnoRepository.save(turno);
    }

    public Optional<Turno> buscarPorId(Long id){
        return turnoRepository.findById(id);
    }

    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }

    public List<Turno> buscarTodos(){
        return turnoRepository.findAll();
    }
}
