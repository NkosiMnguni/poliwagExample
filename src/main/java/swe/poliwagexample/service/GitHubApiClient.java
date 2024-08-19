package swe.poliwagexample.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GitHubApiClient {

    @Value("${github.token}")
    private String githubToken;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public GitHubApiClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public List<Map> sendRequest(String url) throws IOException, InterruptedException {
        HttpRequest httpRequest = buildGetRequest(url);
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(httpResponse.body(), objectMapper.getTypeFactory().constructCollectionType(java.util.List.class, java.util.Map.class));
    }

    public HttpRequest buildGetRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + githubToken)
                .GET()
                .build();
    }

}
