package com.app.autenticacion.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.autenticacion.clients.UsersFeignClient;
import com.app.autenticacion.models.Usuario;
import com.app.autenticacion.repository.UsuarioRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class AutenticacionController {

	@Autowired
	UsuarioRepository usuariosRepository;

	@Autowired
	UsersFeignClient usuariosCliente;

	@Autowired
	private UsersFeignClient client;

	@PostMapping("/autenticacion/arreglar")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Usuario> arreglar() {

		List<Usuario> usuario = usuariosCliente.getUsers();
		for (int i = 0; i < usuario.size(); i++) {
			usuariosRepository.save(usuario.get(i));
		}

		return usuario;
	}

	@DeleteMapping("/autenticacion/eliminarUsuario/{username}")
	public void eliminarUsuario(@PathVariable String username) {
		Usuario person = usuariosRepository.findByUsernameOrEmailOrPhone(username, username, username);
		String id = person.getId();
		usuariosRepository.deleteById(id);
	}

	@PostMapping("/autenticacion/crearUsuario")
	public void crearUsuario(@RequestBody Usuario usuario) {
		usuariosRepository.save(usuario);
	}

	@PutMapping("/autenticacion/editarUsuario")
	public void editarUsuario(@RequestBody Usuario usuario) {
		Usuario person = usuariosRepository.findByUsernameOrEmailOrPhone(usuario.getUsername(), usuario.getUsername(),
				usuario.getUsername());
		person.setUsername(usuario.getUsername());
		person.setPhone(usuario.getPhone());
		person.setEmail(usuario.getEmail());
		person.setPassword(usuario.getPassword());
		person.setEnabled(usuario.getEnabled());
		person.setNombre(usuario.getNombre());
		person.setApellido(usuario.getApellido());
		person.setCedula(usuario.getCedula());
		person.setUbicacion(usuario.getUbicacion());
		person.setIntereses(usuario.getIntereses());
		person.setIntentos(usuario.getIntentos());
		person.setActividadEconomica(usuario.getActividadEconomica());
		person.setDatosEconomica(usuario.getDatosEconomica());
		person.setRoles(usuario.getRoles());
		person.setGenero(usuario.getGenero());
		person.setCabezaFamilia(usuario.getCabezaFamilia());
		person.setTelefono(usuario.getTelefono());
		person.setStakeholders(usuario.getStakeholders());
		person.setEdad(usuario.getEdad());
		usuariosRepository.save(person);

	}

	@HystrixCommand(fallbackMethod = "findByUsernameAlternativo")
	public Usuario findByUsername(String username) {
		return client.encontrarUsuario(username);
	}

	public Usuario findByUsernameAlternativo(String username) {
		return usuariosRepository.findByUsernameOrEmailOrPhone(username, username, username);
	}
}
