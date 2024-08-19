package swe.poliwagexample.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import swe.poliwagexample.dto.UserDto;

@Slf4j
@Service
public class GitHubApiService {
    private final GitHubApiClient gitHubApiClient;

    @Autowired
    public GitHubApiService(GitHubApiClient gitHubApiClient) {
        this.gitHubApiClient = gitHubApiClient;
    }

    // Fetches a list of users from the GitHub API
    public List<UserDto> getUsers() {
        String url = "https://api.github.com/users";
        return getUserData(url, true);
    }

    // Fetches a list of followers for a given GitHub username
    private List<UserDto> getUsersFollowers(String username) {
        String url = String.format("https://api.github.com/users/%s/followers?per_page=5", username);
        return getUserData(url, false);
    }

    // Fetches user data from the GitHub API and optionally includes followers
    private List<UserDto> getUserData(String url, boolean includeFollowers) {
        try {
            // Sends a request to the GitHub API and retrieves the user data
            List<Map> users = gitHubApiClient.sendRequest(url);
            return users.stream()
                    .map(user -> new UserDto(
                            (int) user.get("id"),
                            (String) user.get("login"),
                            includeFollowers ? getUsersFollowers((String) user.get("login")) : new ArrayList<>() // Fetch followers if required
                    ))
                    .collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            log.error(e.toString());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return new ArrayList<>();
        }
    }

}
