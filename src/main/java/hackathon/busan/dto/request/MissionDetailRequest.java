package hackathon.busan.dto.request;

import hackathon.busan.entity.Category;
import hackathon.busan.entity.Location;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MissionDetailRequest {
    private Long userId;
    private String title;
    private String content;
    private List<Category> categories; // multipartFormData 받을 수 있음
    private List<MultipartFile> images; // multipartFormData 받을 수 있음
    private Location location;

    public MissionDetailRequest() {}

    public MissionDetailRequest(Long userId, String title, String content, List<Category> categories, List<MultipartFile> images, Location location) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.categories = categories;
        this.images = images;
        this.location = location;
    }
}