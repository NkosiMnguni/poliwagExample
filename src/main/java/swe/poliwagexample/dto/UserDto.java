package swe.poliwagexample.dto;

import java.util.List;

public record UserDto(int githubId, String userName, List<UserDto> followers) {
}
