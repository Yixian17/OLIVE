package com.olive_backend.Recipe.Vault.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Timer;
import java.util.TimerTask;

@Configuration
@Profile("prod")
public class RenderKeepAliveConfig {

    @Bean
    public CommandLineRunner preventRenderSleep() {
        return args -> {
            System.out.println("Keep-alive task started ✅");
            HttpClient client = HttpClient.newHttpClient();
            Timer timer = new Timer(true);

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://back-end-oo5f.onrender.com/api/config/ping"))
                                .GET()
                                .build();

                        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenAccept(response -> System.out
                                        .println("Self-ping response code: " + response.statusCode()))
                                .exceptionally(e -> {
                                    System.err.println("Self-ping failed: " + e.getMessage());
                                    return null;
                                });

                    } catch (Exception e) {
                        System.err.println("Self-ping setup error: " + e.getMessage());
                    }
                }
            }, 0, 14 * 60 * 1000); // every 14 minutes
        };
    }
}