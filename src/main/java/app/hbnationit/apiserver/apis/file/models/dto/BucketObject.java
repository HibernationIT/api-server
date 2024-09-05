package app.hbnationit.apiserver.apis.file.models.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BucketObject {
    private String name;
    private Long size;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
