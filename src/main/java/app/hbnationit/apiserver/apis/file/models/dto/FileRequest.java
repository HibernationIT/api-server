package app.hbnationit.apiserver.apis.file.models.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileRequest {
    private String folder;
    private MultipartFile file;
}
