package com.enterpriseapplications.springboot.controllers.images;


import com.enterpriseapplications.springboot.data.dto.input.PaginationRequest;
import com.enterpriseapplications.springboot.data.dto.input.create.images.CreateUserImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.ProductImageDto;
import com.enterpriseapplications.springboot.data.dto.output.images.UserImageDto;
import com.enterpriseapplications.springboot.services.interfaces.images.UserImageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/userImages")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UserImageController {

    private final UserImageService userImageService;


    @PreAuthorize("@permissionHandler.hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<PagedModel<UserImageDto>> getImages(@ParameterObject @Valid PaginationRequest paginationRequest) {
        PagedModel<UserImageDto> userImages = this.userImageService.getImages(PageRequest.of(paginationRequest.getPage(),paginationRequest.getPageSize()));
        return ResponseEntity.ok(userImages);
    }

    @GetMapping("{userID}/details")
    public ResponseEntity<UserImageDto> getUserImageDetails(@PathVariable("userID") UUID userID) {
        return ResponseEntity.ok(this.userImageService.getUserImageDetails(userID));
    }
    @GetMapping("{userID}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable("userID") UUID userID) {
        UserImageDto userImageDto = this.userImageService.getUserImage(userID);
        return ResponseEntity.ok().contentType(MediaType.valueOf(userImageDto.getType())).body(userImageDto.getImage());
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserImageDto> uploadImage(@ModelAttribute @Valid CreateUserImageDto createUserImageDto) {
        return ResponseEntity.ok(this.userImageService.uploadImage(createUserImageDto));
    }
}
