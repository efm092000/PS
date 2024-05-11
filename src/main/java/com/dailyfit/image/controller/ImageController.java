package com.dailyfit.image.controller;

import com.dailyfit.image.service.ImageService;
import com.dailyfit.user.ResourceDTO;
import com.dailyfit.user.exception.FileNotFoundException;
import com.dailyfit.user.exception.FileNotSupportedException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageController{

    protected ImageService imageService;

    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @GetMapping(value = "/{file}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String file) {
        try {
            ResourceDTO resourceDTO = imageService.getImage(file);
            return ResponseEntity.ok().contentType(resourceDTO.mediaType()).body(resourceDTO.fsr());
        } catch ( FileNotFoundException | FileNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
