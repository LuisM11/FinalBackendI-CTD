package com.dh.marin.security;


import com.dh.marin.entity.Usuario;
import com.dh.marin.entity.UsuarioRole;
import com.dh.marin.repository.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesUsuarios implements ApplicationRunner {

    UsuarioRepository usuarioRepository;

    public DatosInicialesUsuarios(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //crear un usuario como si fuera real
        //guardarlo en la base
        BCryptPasswordEncoder cifrador= new BCryptPasswordEncoder();
        String passSinCifrar="digital";
        String passCifrada=cifrador.encode(passSinCifrar);
        Usuario usuarioAInsertar=new Usuario("luis",
                "luis",
                "luisfeli1097@gmail.com",passCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuarioAInsertar);
        //crear un usuario tipo admin
        String passCifrada2=cifrador.encode("house");
        usuarioAInsertar= new Usuario("felipe","felipe","felipe@gmail.com",
                passCifrada2, UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuarioAInsertar);

    }
}
