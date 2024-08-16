package swe.poliwagexample.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import swe.poliwagexample.dto.UserDto;

@Slf4j
@Service
public class GitHubUserService {
    List<UserDto> getUsers() {
        List<UserDto> list = new ArrayList<>();
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
            List<Map> users = objectMapper.readValue(httpResponse.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            list = users.stream()
                    .map(user -> new UserDto((int) user.get("id"), (String) user.get("login")))
                    .collect(Collectors.toList());

            log.info("Total users: " + users.size());
        } catch (IOException e) {
            log.error(e.toString());
            e.printStackTrace();
        } catch (InterruptedException e){
            log.error(e.toString());
            Thread.currentThread().interrupt();
        }
        return list;
    }
}
