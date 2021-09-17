package com.app.autenticacion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.app.autenticacion.models.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	@RestResource(path = "buscar")
	public Usuario findByUsernameOrEmailOrPhone(@Param("username") String username, @Param("username") String email,
			@Param("username") String phone);

}