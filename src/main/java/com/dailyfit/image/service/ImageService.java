package com.dailyfit.image.service;

import com.dailyfit.user.ResourceDTO;
import com.dailyfit.user.exception.FileNotFoundException;
import com.dailyfit.user.exception.FileNotSupportedException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResourceDTO getImage(String fileName) throws FileNotSupportedException, FileNotFoundException;

    String saveImage(MultipartFile file);

    void deleteImage(String s);
}
