package hackathon.busan.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import hackathon.busan.dto.request.UploadProfileRequest;
import hackathon.busan.dto.response.UploadProfileResponse;
import hackathon.busan.dto.s3Dto.OldKeyRequest;
import hackathon.busan.dto.s3Dto.S3UploadRequest;
import hackathon.busan.dto.s3Dto.UploadAchievementRequest;
import hackathon.busan.dto.s3Dto.UploadAchievementResponse;
import hackathon.busan.entity.Account;
import hackathon.busan.entity.Image;
import hackathon.busan.entity.Mission;
import hackathon.busan.global.config.AmazonConfig;
import hackathon.busan.repository.AccountRepository;
import hackathon.busan.repository.ImageRepository;
import hackathon.busan.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final AmazonS3 amazonS3;
    private final AmazonConfig amazonConfig;
    private final AccountRepository accountRepository;
    private final MissionRepository missionRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public UploadProfileResponse uploadProfile(@Validated final UploadProfileRequest request) {
        // 업로드 파일이 null 이라면 기본 프로필로 초기화
        if (initDefaultIfFileIsNull(request)) return new UploadProfileResponse(null);
        Optional<String> userProfile = accountRepository.findProfileImageByAccountId(request.userId());
        userProfile.ifPresent(oldKey -> remove(new OldKeyRequest(oldKey)));

        String key = generateProfileName(request);
        String path = uploadToS3(new S3UploadRequest(request.multipartFile(), key));
        accountRepository.updateProfileImageByAccountId(key, request.userId());     // S3 업로드 이후 사용자 테이블 프로필 값 업데이트

        return new UploadProfileResponse(path);
    }

    // 요청 파일이 없다면 이미지 삭제(null)
    private boolean initDefaultIfFileIsNull(final UploadProfileRequest request) {
        if (request.multipartFile() == null || request.multipartFile().isEmpty()) {
            accountRepository.updateProfileImageByAccountId(null, request.userId());
            String oldKey = "user-uploads/" + request.userId() + "/profile.png";
            remove(new OldKeyRequest(oldKey));
            return true;
        }
        return false;
    }

    private String generateProfileName(final UploadProfileRequest request) {
        String contentType = request.multipartFile().getContentType();
        String fileExtension = contentType != null && contentType.contains("/")
                ? "." + contentType.split("/")[1]
                : ".png";
        return String.format("user-uploads/%d/profileImage%s", request.userId(), fileExtension);
    }

    private String uploadToS3(final S3UploadRequest s3UploadRequest) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(s3UploadRequest.multipartFile().getSize());
            metadata.setContentType(s3UploadRequest.multipartFile().getContentType());

            amazonS3.putObject(
                    new PutObjectRequest(
                            amazonConfig.getBucket(),
                            s3UploadRequest.key(),
                            s3UploadRequest.multipartFile().getInputStream(),
                            metadata
                    )
            );

            return amazonS3.getUrl(amazonConfig.getBucket(), s3UploadRequest.key()).toString();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void remove(final OldKeyRequest request) {
        if (!amazonS3.doesObjectExist(amazonConfig.getBucket(), request.oldKey())) {
            throw new AmazonS3Exception("Object " + request.oldKey() + " does not exist!");
        }
        amazonS3.deleteObject(amazonConfig.getBucket(), request.oldKey());
    }

    public String getImage(final OldKeyRequest request){
        return amazonS3.getUrl(amazonConfig.getBucket(), request.oldKey()).toString();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<String> uploadAchievement(UploadAchievementRequest request) {
        Account user = accountRepository.findById(request.userId()).orElseThrow();
        Mission mission = missionRepository.findById(request.missionId()).orElseThrow();
        List<S3UploadRequest> uploadList = request.images().stream().map(image -> generateAchievementName(new UploadProfileRequest(request.userId(), image)))
                .collect(Collectors.toList());

        List<Image> images = uploadList.stream().map(upload -> new Image(user, mission, uploadToS3(upload))).toList();
        List<Image> savedImages = imageRepository.saveAll(images);

        return savedImages.stream().map(Image::getUrl).collect(Collectors.toList());
    }

    private S3UploadRequest generateAchievementName(final UploadProfileRequest request) {
        String contentType = request.multipartFile().getContentType();
        String fileExtension = contentType != null && contentType.contains("/")
                ? "." + contentType.split("/")[1]
                : ".png";
        String url = String.format("user-uploads/%d/achievement%s", request.userId(), fileExtension);
        return new S3UploadRequest(request.multipartFile(), url);
    }
}
