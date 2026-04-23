package net.htoomaungthait.buynowdotcom.service.image.implementation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.exception.custom.EntityNotFoundException;
import net.htoomaungthait.buynowdotcom.common.exception.custom.GeneralException;
import net.htoomaungthait.buynowdotcom.common.response.StatusCodesAndMessages;
import net.htoomaungthait.buynowdotcom.dto.resp.ImageDto;
import net.htoomaungthait.buynowdotcom.model.Image;
import net.htoomaungthait.buynowdotcom.model.Product;
import net.htoomaungthait.buynowdotcom.repository.ImageRepository;
import net.htoomaungthait.buynowdotcom.service.image.IImageService;
import net.htoomaungthait.buynowdotcom.service.product.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return this.findImageById(id);
    }

    @Override
    public ImageDto deleteImageById(Long id) {
        Image imgToDelete = this.findImageById(id);
        imageRepository.delete(imgToDelete);
    }

    @Override
    public ImageDto updateImage(MultipartFile file, Long imageId, Long productId) {
        Product product = productService.findProductById(productId);
        Image existingImage = this.findImageById(imageId);
        try {
            existingImage.setFileName(file.getOriginalFilename());
            existingImage.setFileType(file.getContentType());
            existingImage.setImage(new SerialBlob(file.getBytes()));
            existingImage.setProduct(product);


            imageRepository.save(existingImage);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productService.findProductById(productId);
        List<ImageDto> savedImages = new ArrayList<>();

        for(MultipartFile file: files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/download/" ;
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = ImageDto.of(
                        savedImage.getId(),
                        savedImage.getFileName(),
                        savedImage.getFileType(),
                        savedImage.getDownloadUrl()
                );

                savedImages.add(imageDto);


            } catch (IOException | SQLException e) {
                log.error(e.getMessage());

                throw new GeneralException(STR."\{StatusCodesAndMessages.getByStatusCode("IMG_002").getMessage()}Failed to save image: \{file.getOriginalFilename()}",
                        "IMG_002");
            }

        }
        return savedImages;
    }

    private Image findImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(STR."Image not found with id: \{id}", "IMG_004"));

    }
}
