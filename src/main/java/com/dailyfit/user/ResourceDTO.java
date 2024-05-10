package com.dailyfit.user;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;

public record ResourceDTO (MediaType mediaType, FileSystemResource fsr) {
}
