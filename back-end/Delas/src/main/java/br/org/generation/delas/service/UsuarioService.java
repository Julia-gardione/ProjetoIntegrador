package br.org.generation.delas.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.org.generation.delas.model.UserLogin;
import br.org.generation.delas.model.UsuarioModel;
import br.org.generation.delas.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public UsuarioModel CadastrarUsuario (UsuarioModel usuario) {
		
		if(repository.findByUser(usuario.getUser()).isPresent()) {
			return null; 
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
	String senhaEncoder = encoder.encode(usuario.getSenha());
	usuario.setSenha(senhaEncoder);
	
	return repository.save(usuario); //esclarecer 
	
	}
	
	public Optional<UserLogin> Logar(Optional<UserLogin> user){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<UsuarioModel> usuario = repository.findByUser(user.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);
				
				user.get().setToken(authHeader);
				user.get().setNome(usuario.get().getNome());
				user.get().setSenha(usuario.get().getSenha());
				return user;
				
				
				
									
			}
		}
		
		return null;
	}
}
