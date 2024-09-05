package app.hbnationit.apiserver.apis.file.controllers;

import app.hbnationit.apiserver.apis.file.models.dto.FileRequest;
import app.hbnationit.apiserver.apis.file.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/console/file")
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<?> fileUpload(@ModelAttribute FileRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileService.uploadFile(request));
    }
}
