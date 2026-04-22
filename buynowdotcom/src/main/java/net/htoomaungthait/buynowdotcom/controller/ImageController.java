package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.resp.ImageDto;
import net.htoomaungthait.buynowdotcom.model.Image;
import net.htoomaungthait.buynowdotcom.service.image.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController extends BaseController {

    private final IImageService iImageService;

    /**
     * To upload images of product, we will handle for creating related images of given product Id.
     * @author Htoo Maung Thait
     * @since 2026-03-05
     * @return ResponseEntity with ApiResponse containing list of ImageDto
     * */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<List<ImageDto>>> uploadProductImages(
            @RequestParam("productId") @Min(value = 1, message = "Product ID must be a positive number.") Long productId,
            @RequestParam("images") List<MultipartFile> files
    ) {



        return makeResponse(
                HttpStatus.CREATED.value(),
                "IMG_001",
                "Images uploaded successfully.",
                getStatusMessageByCode("IMG_001"),
                iImageService.saveImages(productId, files)
        );


    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = iImageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, STR."attachment; filename=\"\{image.getFileName()}\"")
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .body(resource);
    }

}
