package app.hbnationit.apiserver.utils;

import app.hbnationit.apiserver.apis.file.models.dto.BucketObject;
import app.hbnationit.apiserver.config.OciConfig;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.*;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileUtil implements InitializingBean, DisposableBean {

    private ObjectStorage storage;
    private final AuthenticationDetailsProvider provider;

    @Override
    public void afterPropertiesSet() throws Exception {
        storage = ObjectStorageClient.builder().build(provider);
    }

    @Override
    public void destroy() throws Exception {
        storage.close();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        return uploadFile("", file);
    }

    public String uploadFile(String folder, MultipartFile file) throws IOException {
        String fileName = folder + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .namespaceName(OciConfig.NAMESPACE_NAME)
                .bucketName(OciConfig.BUCKET_NAME)
                .objectName(fileName)
                .contentLength(file.getSize())
                .putObjectBody(inputStream)
                .build();
        storage.putObject(putObjectRequest);

        return String.format("%s/n/%s/b/%s/o/%s", storage.getEndpoint(), OciConfig.NAMESPACE_NAME, OciConfig.BUCKET_NAME, fileName);
    }

    public String getFile(String fileName) {
        return String.format("%s/n/%s/b/%s/o/%s", storage.getEndpoint(), OciConfig.NAMESPACE_NAME, OciConfig.BUCKET_NAME, fileName);
    }

    public List<BucketObject> fileList() {
        ListObjectsRequest request = ListObjectsRequest.builder()
                .namespaceName(OciConfig.NAMESPACE_NAME)
                .bucketName(OciConfig.BUCKET_NAME)
                .fields("size, timeCreated, timeModified")
                .build();

        ListObjectsResponse response = storage.listObjects(request);

        return response.getListObjects().getObjects()
                .stream().map(res -> BucketObject.builder()
                        .name(res.getName())
                        .size(res.getSize())
                        .createdDate(LocalDateTime.ofInstant(res.getTimeCreated().toInstant(), ZoneId.of("Asia/Seoul")))
                        .updatedDate(LocalDateTime.ofInstant(res.getTimeModified().toInstant(), ZoneId.of("Asia/Seoul")))
                        .build()
                ).toList();
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                        .bucketName(OciConfig.BUCKET_NAME)
                        .namespaceName(OciConfig.NAMESPACE_NAME)
                        .objectName(fileName)
                        .build();
        storage.deleteObject(request);
    }
}
