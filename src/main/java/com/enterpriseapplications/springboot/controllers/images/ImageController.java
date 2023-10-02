package com.enterpriseapplications.springboot.controllers.images;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateProductImageDto;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ImageDto;
import com.enterpriseapplications.springboot.data.entities.enums.ImageOwner;
import com.enterpriseapplications.springboot.services.interfaces.images.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController
{
    private final ImageService imageService;

    @GetMapping("/private")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<ImageDto>> getImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<ImageDto> images = this.imageService.getImages(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(images);
    }

    @GetMapping("/public/owners")
    public ResponseEntity<ImageOwner[]> getImageOwners() {
        return ResponseEntity.ok(this.imageService.getImageOwners());
    }

    @GetMapping("/public/{imageID}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageID") UUID imageID) {
        ImageDto imageDto = this.imageService.getImage(imageID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(imageDto.getType())).body(imageDto.getImage());
    }

    @GetMapping("/private/name/{name}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ImageDto>> getImagesByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(this.imageService.getImagesByName(name));
    }

    @GetMapping("/private/type/{type}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ImageDto>> getImagesByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(this.imageService.getImagesByType(type));
    }

    @DeleteMapping("/private/{id}")
    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") UUID imageID) {
        this.imageService.deleteImage(imageID);
        return ResponseEntity.noContent().build();
    }
}
