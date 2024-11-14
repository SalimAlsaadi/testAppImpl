package com.app.implementation.Service;

import com.app.implementation.Entity.Photos;
import com.app.implementation.Repos.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final PhotoRepo photoRepo;

    @Autowired
    public ImageService(PhotoRepo photoRepo) {
        this.photoRepo = photoRepo;
    }

    public Photos saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Cannot save empty file.");
        }

        Photos photo = new Photos();
        photo.setPhotoName(file.getOriginalFilename());
        photo.setContent(file.getBytes());
        return photoRepo.save(photo);
    }

    public ResponseEntity<byte[]> getImage(Long id) {
        Optional<Photos> image = photoRepo.findById(id);
        if (image.isPresent()) {
            Photos photos = image.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_GIF);  // Set the content type based on the image type
            headers.setContentDispositionFormData("attachment", photos.getPhotoName());

            return new ResponseEntity<>(photos.getContent(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    public List<Photos> getAllImages() {
        List<Photos> getAllImages = photoRepo.findAll();
        return getAllImages;
    }

}
