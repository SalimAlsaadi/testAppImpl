package com.app.implementation.Controller;

import com.app.implementation.Entity.Photos;
import com.app.implementation.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/photo")
public class PhotosController {

    private final ImageService imageService;

    @Autowired
    public PhotosController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Photos savedPhoto = imageService.saveImage(file);
            return ResponseEntity.ok("Image uploaded successfully with ID: " + savedPhoto.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<byte[]> getImageByID(@PathVariable Long id) {
        return imageService.getImage(id);
    }



    @GetMapping()
    public ResponseEntity<List<byte[]>> getAllImages() {
        List<Photos> photos=imageService.getAllImages();
        if(photos!=null){

            List<byte[]> getContentImages = photos.stream()
                    .map(Photos::getContent)  // Extract the content (byte[])
                    .collect(Collectors.toList());

            // Set headers for binary response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);  // Set to octet-stream since multiple files are returned

            return new ResponseEntity<>(getContentImages, headers, HttpStatus.OK);
        }

             else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

    }

    @GetMapping("/all")
    public List<String> getAllImageUrls() {
        List<Photos> photos = imageService.getAllImages();

        return photos.stream()
                .map(photo -> "/api/photo/id/" + photo.getId()) // Return a list of URLs
                .collect(Collectors.toList());
    }


}
