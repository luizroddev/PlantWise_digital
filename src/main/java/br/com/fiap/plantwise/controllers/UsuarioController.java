package br.com.fiap.plantwise.controllers;

import br.com.fiap.plantwise.models.Usuario;
import br.com.fiap.plantwise.models.UsuarioLogin;
import br.com.fiap.plantwise.records.Credencial;
import br.com.fiap.plantwise.records.Token;
import br.com.fiap.plantwise.repositories.UsuarioRepository;
import br.com.fiap.plantwise.service.TokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/usuarios/")
@Slf4j
public class UsuarioController {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca, @PageableDefault(size = 5) Pageable pageable){
        log.info("Buscando usuários");

        Page<Usuario> usuarios = (busca == null) ?
                repository.findAll(pageable):
                repository.findByNomeContaining(busca, pageable);

        return assembler.toModel(usuarios.map(Usuario::toEntityModel));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody @Valid Usuario usuario){
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioLogin> login(@RequestBody Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        Token token = tokenService.generateToken(credencial);
        Usuario usuario = repository.findByEmail(credencial.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        UsuarioLogin usuarioLogin = new UsuarioLogin(usuario.getId(), usuario.getEmail(), token.token());
        return ResponseEntity.ok(usuarioLogin);
    }

    @GetMapping("{id}")
    public EntityModel<Usuario> show(@PathVariable Long id) {
        log.info("Buscar usuário com ID " + id);
        Usuario usuario = getUserByIdOrThrow(id);
        return usuario.toEntityModel();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Deletando usuário");

        repository.delete(getUserByIdOrThrow(id));
        return ResponseEntity.noContent().build();
    }

    private Usuario getUserByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

}
