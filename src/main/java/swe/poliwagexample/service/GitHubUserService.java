package swe.poliwagexample.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import swe.poliwagexample.entity.User;

@Slf4j
@Service
public class GitHubUserService {
    List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            // Step 1: Create an HttpClient instance
            HttpClient httpClient = HttpClient.newHttpClient();

            // Step 2: Build an HttpRequest
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/users"))
                    .GET() // You can change this to POST, PUT, etc.
                    .build();

            // Step 3: Send the request and receive the response
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Step 4: Handle the response
            ObjectMapper objectMapper = new ObjectMapper();
            users = objectMapper.readValue(httpResponse.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            users.forEach(user -> log.info(user.getLogin()));
            log.info("Total users: " + users.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
