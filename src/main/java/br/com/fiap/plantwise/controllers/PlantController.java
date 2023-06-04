package br.com.fiap.plantwise.controllers;

import br.com.fiap.plantwise.models.Analise;
import br.com.fiap.plantwise.models.Plant;
import br.com.fiap.plantwise.models.Usuario;
import br.com.fiap.plantwise.repositories.AnaliseRepository;
import br.com.fiap.plantwise.repositories.UsuarioRepository;
import br.com.fiap.plantwise.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PlantController {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    AnaliseRepository analiseRepository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @PostMapping("api/plant")
    public ResponseEntity<Plant> receiveEncodedImage(@RequestBody PlantRequest plantRequest) {
        // Faz uma nova requisição POST para outra API
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PlantRequest> requestEntity = new HttpEntity<>(plantRequest, headers);
        ResponseEntity<Plant> responseEntity = restTemplate.exchange(
                "http://localhost:5000/download",
                HttpMethod.POST,
                requestEntity,
                Plant.class
        );

        // Processa a resposta da outra API
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        Plant plant = responseEntity.getBody();

        String[] parts = plant.disease().split("___");
        String plantName = parts[0];
        String diseaseName = parts[1];

        Usuario usuario = repository.findById(plantRequest.userId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Analise analise = new Analise();
        analise.setUsuario(usuario);
        analise.setPlanta(plantName);
        analise.setDoenca(diseaseName);
        analise.setDoencaEncontrada(!diseaseName.equalsIgnoreCase("healthy"));
        analise.setId(1L);

        Analise added = analiseRepository.save(analise);

        usuario.getAnalises().add(added);
        repository.save(usuario);
        // Responde à requisição recebida inicialmente
        return ResponseEntity.status(statusCode).body(plant);
    }
}

record PlantRequest(Long userId, String image) {
}

record AnalysisRequest(String planta, String doenca, boolean doencaEncontrada) {
}
