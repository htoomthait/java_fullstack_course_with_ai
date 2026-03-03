package net.htoomaungthait.buynowdotcom.service.image.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    public void deleteImageById(Long id) {
        Image imgToDelete = this.findImageById(id);
        imageRepository.delete(imgToDelete);
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image existingImage = this.findImageById(imageId);
        try {
            existingImage.setFileName(file.getOriginalFilename());
            existingImage.setFileType(file.getContentType());
            existingImage.setImage(new SerialBlob(file.getBytes()));


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

                String buildDownloadUrl = "/api/images/image/download/" ;
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
                throw new RuntimeException(e.getMessage());
            }

        }
        return savedImages;
    }

    private Image findImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + id));
    }
}
