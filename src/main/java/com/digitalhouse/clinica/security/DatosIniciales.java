package com.digitalhouse.clinica.security;

import com.digitalhouse.clinica.entity.Usuario;
import com.digitalhouse.clinica.entity.UsuarioRole;
import com.digitalhouse.clinica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passSinCifrar="admin";
        String passSinCifrar2="user";
        String passCifrado=passwordEncoder.encode(passSinCifrar);
        String passCifrado2=passwordEncoder.encode(passSinCifrar2);

        Usuario usuario=new Usuario("Francisco","user","user@user.com",passCifrado2,UsuarioRole.ROLE_USER);
        Usuario usuario2=new Usuario("Francisco","admin","admin@admin.com",passCifrado,UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);
    }
}
