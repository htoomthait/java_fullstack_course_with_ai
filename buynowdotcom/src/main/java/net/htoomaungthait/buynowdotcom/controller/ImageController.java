package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.resp.ImageDto;
import net.htoomaungthait.buynowdotcom.service.image.IImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     *
     * */
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


}
