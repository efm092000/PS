package com.dailyfit.image.service;

import com.dailyfit.user.ResourceDTO;
import com.dailyfit.user.exception.FileNotFoundException;
import com.dailyfit.user.exception.FileNotSupportedException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public ResourceDTO getImage(String fileName) throws FileNotSupportedException, FileNotFoundException {
        Path path = new File("src/main/resources/profile-pictures/" + fileName).toPath();
        FileSystemResource resource = new FileSystemResource(path);
        if (resource.exists() && resource.isReadable()) {
            MediaType mediaType;
            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (fileName.endsWith(".png")) {
                mediaType = MediaType.IMAGE_PNG;
            } else {
                throw new FileNotSupportedException();
            }
            return new ResourceDTO(mediaType, resource);
        } else {
            throw new FileNotFoundException();
        }

    }

    @Override
    public String saveImage(MultipartFile file) {

        try {
            String fileName = UUID.randomUUID().toString();
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();
            long fileSize = file.getSize();
            long maxFileSize = 10 * 1024 * 1024;

            if (fileSize > maxFileSize) {
                return ("File size must be less then or equal 10MB");
            }

            assert fileOriginalName != null;
            if (!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") && !fileOriginalName.endsWith(".png")) {
                return ("Only JPG, JPEG, PNG files are allowed");
            }

            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = fileName + fileExtension;

            File folder = new File("src/main/resources/profile-pictures");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Path path = Paths.get("src/main/resources/profile-pictures/" + newFileName);
            Files.write(path, bytes);
            return "http://localhost:8080/images/"+newFileName;

        } catch (Exception e) {
            return ("Error uploading file");
        }

    }

    @Override
    public void deleteImage(String imageUrl) {
        File file = new File(imageUrl);
        String fileName = file.getName();
        if(fileName.equals("icon.png")){
            return;
        }
        Path path = Paths.get("src/main/resources/profile-pictures/"+ fileName);

        try {
            Files.delete(path);
            System.out.println("File was deleted successfully.");
        } catch (IOException e) {
            System.out.println("File couldn't be deleted: " + e.getMessage());
        }
    }
}
