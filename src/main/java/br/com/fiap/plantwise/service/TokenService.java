package br.com.fiap.plantwise.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import br.com.fiap.plantwise.models.Usuario;
import br.com.fiap.plantwise.records.Credencial;
import br.com.fiap.plantwise.records.Token;
import br.com.fiap.plantwise.repositories.UsuarioRepository;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Token generateToken(Credencial credencial) {
        Algorithm alg = Algorithm.HMAC256("meusecret");
        var jwt = JWT.create()
                .withSubject(credencial.email())
                .withIssuer("NaoFalindo")
                .withExpiresAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .sign(alg);
        return new Token(jwt, "JWT", "Bearer");
    }

    public Usuario validate(String token) {
        Algorithm alg = Algorithm.HMAC256("meusecret");
        var email = JWT.require(alg)
                .withIssuer("NaoFalindo")
                .build()
                .verify(token)
                .getSubject();

        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new JWTVerificationException("Usuário nao encontrado"));

    }

}
