package com.dailyfit.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

@JsonAutoDetect
public record UserDTO(@JsonGetter("email") String email,
                      @JsonGetter("name") String name,
                      @JsonGetter("isPremium") boolean premium,
                      @JsonGetter("isAdmin") boolean admin,
                      @JsonGetter("profilePicture") String profilePicture) {
}
