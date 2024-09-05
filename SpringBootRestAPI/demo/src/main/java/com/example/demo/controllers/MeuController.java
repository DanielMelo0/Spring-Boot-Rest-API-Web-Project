package com.example.demo.controllers;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeuController {

    @Autowired /* IC/CD ou CDI - Injeção de Dependência*/
    private UsuarioRepository usuarioRepository;


    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String saudacao(@PathVariable String name){
        return "Olá " + name +" , Bem vindo ao meu Aplicativo Spring";
    }

    // Metodo Que Intercepta a Requisição
    @RequestMapping(value = "/olamundo/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String name){

        Usuario usuario = new Usuario();
        usuario.setNome(name);

        usuarioRepository.save(usuario);
        return "Parabens, Você fez uma Requisição ! e Inseriu um Dado no Banco. Nome de Usuario : " + name;
    }

    @GetMapping(value = "listatodos") // Metodo de API
    @ResponseBody // Retorna os Dados para o Corpo da Resposta
    public ResponseEntity<List<Usuario>> listaUsuario(){ // End Point

        List<Usuario> usuarios = usuarioRepository.findAll(); // Executa a Consulta no Banco de Dados

        return new ResponseEntity<List<Usuario>>(usuarios,HttpStatus.OK); // Retorna Lista em Json

    }

    @PostMapping(value = "salvar") // Mapeia a Url
    @ResponseBody // Descrição da Resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ // Recebe Parametros para Salvar

        Usuario user = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);

    }

    @PutMapping(value = "atualizar") // Mapeia a Url
    @ResponseBody // Descrição da Resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {

        if(usuario.getId() == null){
            return new ResponseEntity<String>("Id não foi Informado", HttpStatus.OK);
        }

        Usuario user = usuarioRepository.saveAndFlush(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete") //Mapeia a Url
    @ResponseBody // Descrição da Resposta
    public ResponseEntity<String> delete(@RequestParam Long iduser){

        usuarioRepository.deleteById(iduser);

        return new ResponseEntity<String>("User Deletado Com Sucesso", HttpStatus.OK);

}

    @GetMapping(value = "buscaruserid") // Metodo de API
    @ResponseBody // Retorna os Dados para o Corpo da Resposta
    public ResponseEntity<Usuario> buscaruserid(@RequestParam (name =  "iduser")Long iduser){ // End Point

        Usuario usuario = usuarioRepository.findById(iduser).get(); // Executa a Consulta no Banco de Dados

        return new ResponseEntity<Usuario>(usuario,HttpStatus.OK); // Retorna Lista em Json
    }


    @GetMapping(value = "buscarpornome") // Metodo de API
    @ResponseBody // Retorna os Dados para o Corpo da Resposta
    public ResponseEntity<List<Usuario>> buscarpornome(@RequestParam (name =  "name") String name){ // End Point

        List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase()); // Executa a Consulta no Banco de Dados

        return new ResponseEntity<List<Usuario>>(usuario,HttpStatus.OK); // Retorna Lista em Json

    }









}
