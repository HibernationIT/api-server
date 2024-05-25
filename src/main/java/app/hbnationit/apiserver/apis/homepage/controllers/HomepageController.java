package app.hbnationit.apiserver.apis.homepage.controllers;

import app.hbnationit.apiserver.apis.homepage.models.vo.ProjectsResponse;
import app.hbnationit.apiserver.apis.homepage.services.BlogService;
import app.hbnationit.apiserver.apis.homepage.services.DesignService;
import app.hbnationit.apiserver.apis.homepage.services.ProjectService;
import app.hbnationit.apiserver.apis.homepage.services.StackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/homepage")
public class HomepageController {
    private final StackService stackService;
    private final ProjectService projectService;
    private final BlogService blogService;
    private final DesignService designService;

    /*
    ========================================
    Stacks
    ========================================
    */
    @GetMapping("/stacks")
    public ResponseEntity<?> stackList(
            Pageable pageable,
            @RequestParam(required = false) String type
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stackService.findStacksVo(pageable, type));
    }

    /*
    ========================================
    Projects
    ========================================
    */
    @GetMapping("/projects/{id}")
    public ResponseEntity<?> projectDetails(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.findProjectVo(id));
    }

    @GetMapping("/projects")
    public ResponseEntity<Page<ProjectsResponse>> projectList(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String stacks,
            @RequestParam(required = false) String description
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.findProjectsVo(pageable, name, stacks, description));
    }

    /*
    ========================================
    Blogs
    ========================================
    */
    @GetMapping("/blogs/{id}")
    public ResponseEntity<?> blogDetails(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(blogService.findBlogVo(id));
    }

    @GetMapping("/blogs")
    public ResponseEntity<?> blogList(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String description
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(blogService.findBlogsVo(pageable, name, tags, description));
    }

    /*
    ========================================
    Designs
    ========================================
    */
    @GetMapping("/design/{id}")
    public ResponseEntity<?> designDetails(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(designService.findDesignVo(id));
    }

    @GetMapping("/design")
    public ResponseEntity<?> designList(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(designService.findDesignsVo(pageable, name, description));
    }
}
