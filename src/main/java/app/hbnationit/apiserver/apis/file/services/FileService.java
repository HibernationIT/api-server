package app.hbnationit.apiserver.apis.file.services;

import app.hbnationit.apiserver.apis.file.models.dto.FileRequest;
import app.hbnationit.apiserver.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileUtil fileUtil;

    public String uploadFile(FileRequest fileRequest) {
        if (fileRequest.getFile() == null) {
            throw new IllegalArgumentException("File upload fail. check request file");
        }
        String folder = fileRequest.getFolder();
        if (folder == null || folder.equals("/")) {
            folder = "";
        } else {
            folder += "/";
        }

        try {
            return fileUtil.uploadFile(folder, fileRequest.getFile());
        } catch (IOException e) {
            throw new IllegalArgumentException("File upload fail. check request file");
        }
    }
}
