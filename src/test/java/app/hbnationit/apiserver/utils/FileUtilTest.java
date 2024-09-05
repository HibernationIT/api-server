package app.hbnationit.apiserver.utils;

import app.hbnationit.apiserver.apis.file.models.dto.BucketObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("local")
@SpringBootTest
class FileUtilTest {
    private @Autowired FileUtil fileUtil;

    @Test
    void uploadFile() throws Exception {
        byte[] content = Files.readAllBytes(Paths.get("test.png"));

        MultipartFile mFile = new MockMultipartFile(
                "profile.png",
                "profile.png",
                "text/plain",
                content
        );

        System.out.println(fileUtil.uploadFile(mFile));
    }

    @Test
    void fileList() {
        List<BucketObject> objects = fileUtil.fileList();
        objects.forEach(System.out::println);
    }

    @Test
    void getFile() {
        System.out.println(fileUtil.getFile("profile.png"));
    }

    @Test
    void deleteFile() {
        fileUtil.deleteFile("profile.png");
    }

}