package app.hbnationit.apiserver.apis.homepage.controllers;

import app.hbnationit.apiserver.apis.homepage.models.dto.*;
import app.hbnationit.apiserver.apis.homepage.models.vo.ProjectResponse;
import app.hbnationit.apiserver.apis.homepage.models.vo.ProjectsResponse;
import app.hbnationit.apiserver.apis.homepage.models.vo.StackResponse;
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
@RequestMapping("/v1/console/homepage")
public class ConsoleHomepageController {
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
                .body(stackService.findStacks(pageable, type));
    }
    @PostMapping("/stacks")
    public ResponseEntity<?> stackAdd(@RequestBody AddStackRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stackService.addStack(body));
    }

    @PutMapping("/stacks/{name}")
    public ResponseEntity<?> stackModify(
            @PathVariable(name = "name") String name,
            @RequestBody ModifyStackRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stackService.modifyStack(name, body));
    }

    @DeleteMapping("/stacks/{name}")
    public ResponseEntity<?> stackRemove(@PathVariable(name = "name") String name) {
        stackService.removeStack(name);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
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
                .body(projectService.findProject(id));
    }

    @GetMapping("/projects")
    public ResponseEntity<?> projectList(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String stacks,
            @RequestParam(required = false) String description
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.findProjects(pageable, name, stacks, description));
    }

    @PostMapping("/projects")
    public ResponseEntity<?> projectAdd(@RequestBody AddProjectRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.addProject(body));
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<?> projectModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyProjectRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.modifyProject(id, body));
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<?> projectRemove(@PathVariable(name = "id") Long id) {
        projectService.removeProject(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
                .body(blogService.findBlog(id));
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
                .body(blogService.findBlogs(pageable, name, tags, description));
    }
    @PostMapping("/blogs")
    public ResponseEntity<?> blogAdd(@RequestBody AddBlogRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(blogService.addBlog(body));
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<?> blogModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyBlogRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(blogService.modifyBlog(id, body));
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<?> blogRemove(@PathVariable(name = "id") Long id) {
        blogService.removeBlog(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
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
                .body(designService.findDesign(id));
    }

    @GetMapping("/design")
    public ResponseEntity<?> designList(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(designService.findDesigns(pageable, name, description));
    }
    @PostMapping("/design")
    public ResponseEntity<?> designAdd(@RequestBody AddDesignRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(designService.addDesign(body));
    }

    @PutMapping("/design/{id}")
    public ResponseEntity<?> designModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyDesignRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(designService.modifyDesign(id, body));
    }

    @DeleteMapping("/design/{id}")
    public ResponseEntity<?> designRemove(@PathVariable(name = "id") Long id) {
        designService.removeDesign(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
