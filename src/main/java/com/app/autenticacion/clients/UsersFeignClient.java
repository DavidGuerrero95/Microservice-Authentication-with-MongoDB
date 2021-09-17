package com.app.autenticacion.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.autenticacion.models.Usuario;

@FeignClient(name = "app-usuarios")
public interface UsersFeignClient {

	@GetMapping("/users/encontrarUsuario/{usuario}")
	public Usuario encontrarUsuario(@PathVariable String usuario);
	
	@GetMapping("/users/listar")
	public List<Usuario> getUsers();

}
