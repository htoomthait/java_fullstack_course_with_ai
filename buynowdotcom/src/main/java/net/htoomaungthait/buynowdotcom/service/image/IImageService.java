package net.htoomaungthait.buynowdotcom.service.image;

import net.htoomaungthait.buynowdotcom.dto.resp.ImageDto;
import net.htoomaungthait.buynowdotcom.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    ImageDto deleteImageById(Long id);

    ImageDto updateImage(MultipartFile file,  Long imageId, Long productId);

    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);




}
