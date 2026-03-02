package net.htoomaungthait.buynowdotcom.service.image;

import net.htoomaungthait.buynowdotcom.dto.request.ImageDto;
import net.htoomaungthait.buynowdotcom.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    void deleteImageById(Long id);

    void updateImage(MultipartFile file,  Long imageId);

    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);




}
