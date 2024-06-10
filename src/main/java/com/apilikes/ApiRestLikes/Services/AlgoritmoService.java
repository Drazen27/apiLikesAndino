package com.apilikes.ApiRestLikes.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlgoritmoService {

    private boolean marcadorRecibido = false;
    private List<String> mensajesEnTransito = new ArrayList<>();
    private Set<String> canalesMarcador = new HashSet<>();
    private String estadoLocal;
    private final RestTemplate restTemplate = new RestTemplate();

    public void iniciarAlgoritmo() {
        grabarEstadoLocal();
        enviarMarcadorATodos();
    }

    public void recibirMarcador(String from) {
        if (!marcadorRecibido) {
            grabarEstadoLocal();
            marcadorRecibido = true;
            enviarMarcadorATodos();
        }
        canalesMarcador.add(from);
        if (todosMarcadoresRecibidos()) {
            completarEstadoGlobal();
        }
    }

    public void recibirMensaje(String mensaje, String from) {
        if (marcadorRecibido && !canalesMarcador.contains(from)) {
            mensajesEnTransito.add(mensaje);
        }
    }

    private void grabarEstadoLocal() {
        estadoLocal = "Estado del proceso en Spring Boot";
        System.out.println("Estado local grabado: " + estadoLocal);
    }

    private void enviarMarcadorATodos() {
        List<String> otrosMicroservicios = List.of("https://apil-istas.vercel.app/api/algoritmo/recibirMarcador", "https://www.andsoundapi.somee.com/api/algoritmo/recibirMarcador");

        for (String url : otrosMicroservicios) {
            restTemplate.postForEntity(url + "?from=springboot", null, String.class);

        }
    }

    private boolean todosMarcadoresRecibidos() {
        // Asumimos que sabemos cuántos microservicios hay.
        return canalesMarcador.size() == 2;
    }

    private void completarEstadoGlobal() {
        System.out.println("Estado global completado con mensajes en tránsito: " + mensajesEnTransito);
    }
}