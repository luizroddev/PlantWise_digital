package br.com.fiap.plantwise.service;

import br.com.fiap.plantwise.records.PlantImageRequest;
import br.com.fiap.plantwise.records.PlantImageResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlantImageService {

    public PlantImageResponse analyzePlantImage(PlantImageRequest plantImageRequest) {
        // Faz uma nova requisição POST para outra API
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PlantImageRequest> requestEntity = new HttpEntity<>(plantImageRequest, headers);
        ResponseEntity<PlantImageResponse> responseEntity = restTemplate.exchange(
                "http://localhost:5000/analyze",
                HttpMethod.POST,
                requestEntity,
                PlantImageResponse.class
        );

        return responseEntity.getBody();
    }
}
