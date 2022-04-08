package br.com.tech.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech.model.Usuario;
import br.com.tech.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired // IC/CD ou CDI - Injeçao de dependencia
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */

	// TESTE
//	@RequestMapping(value = "/mostarnome/{name}", method = RequestMethod.GET) // metodo de exemplo
//	@ResponseStatus(HttpStatus.OK)
//	public String greetingText(@PathVariable String name) {
//		return "Projeto SpringBoot " + name + "!";
//	}
//
//	//metodo salvar  http://localhost:8080//ola/{nome}
//	@RequestMapping(value = "/ola/{nome}", method = RequestMethod.GET) // metodo de exemplo
//	@ResponseStatus(HttpStatus.OK)
//	public String mostarOlar(@PathVariable String nome) {
//		
//		Usuario usuario = new Usuario();
//		usuario.setNome(nome);
//		usuarioRepository.save(usuario); // vai gravar no banco de dado		
//		return "Ola mundo " + nome + "!";
//	}

	// http://localhost:8080/listatodos
	// faz a consulta LISTA no banco de dados metodo de apai
	@GetMapping(value = "listatodos")
	@ResponseBody // retorna os dados para o corpo da resposta
	public ResponseEntity<List<Usuario>> listaUsuario() {
		List<Usuario> usuarios = usuarioRepository.findAll(); // faz a consulta no banco de dados

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	// SALVAR
	// http://localhost:8080/salvar USADO APENAS NO POSTMAN OU PAGINA HTML
	@PostMapping(value = "salvar") // mapeia a url
	@ResponseBody // executa a descricao da reposta
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { // recebe os dados para salavar

		Usuario user = usuarioRepository.save(usuario); 
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);// retorna na tela salvo/criado
	}

	
	// DELETA
	// http://localhost:8080/delete  USADO APENAS NO POSTMAN OU PAGINA HTML
	@DeleteMapping(value = "delete") // mapeia a url
	@ResponseBody // executa a descricao da reposta
	public ResponseEntity<String> delete(@RequestParam Long iduser ) { // recebe os dados para DELETAR

		usuarioRepository.deleteById(iduser);
		return new ResponseEntity<String>("Usuario Deletado Com Sucesso ( :", HttpStatus.OK);// deleta usuario
	}

	
	
	// buscauserid
	// http://localhost:8080/buscauserid  USADO APENAS NO POSTMAN OU PAGINA HTML
	@GetMapping(value = "buscauserid") // mapeia a url
	@ResponseBody // executa a descricao da reposta
	public ResponseEntity<Usuario> buscauserid(@RequestParam(name = "iduser") Long iduser) { // busca o usuario por id

		Usuario usuario = usuarioRepository.findById(iduser).get(); // pesquisa no banco
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);// retorna na tela
	}
	
	
	
	// buscarPorNome
	// http://localhost:8080/buscarPorNome  USADO APENAS NO POSTMAN OU PAGINA HTML
	@GetMapping(value = "buscarPorNome") // mapeia a url
	@ResponseBody // executa a descricao da reposta
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) { //buscarPorNome

		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase()); // pesquisa no banco parte do nome
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);// retorna na tela
	}
	

	// ATUALIZAR
	// http://localhost:8080/atualizar USADO APENAS NO POSTMAN OU PAGINA HTML
	@PutMapping(value = "atualizar") // mapeia a url
	@ResponseBody // executa a descricao da reposta
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { // recebe os dados para Atualizar
		
		if(usuario.getId() == null ) {
			return new ResponseEntity<String>("Id nao informado para a atualização", HttpStatus.OK); //validando a obrigacao do ID
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);//altualiza/salva
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);// retorna na tela
	}

}
