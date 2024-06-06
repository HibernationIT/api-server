package app.hbnationit.apiserver.apis.homepage.controllers;

import app.hbnationit.apiserver.apis.homepage.models.dto.*;
import app.hbnationit.apiserver.apis.homepage.services.*;
import lombok.RequiredArgsConstructor;
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
    private final IconService iconService;

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
    public ResponseEntity<?> stackAdd(@RequestBody AddHpStackRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stackService.addStack(body));
    }
    @PutMapping("/stacks/{name}")
    public ResponseEntity<?> stackModify(
            @PathVariable(name = "name") String name,
            @RequestBody ModifyHpStackRequest body
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
    public ResponseEntity<?> projectAdd(@RequestBody AddHpProjectRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.addProject(body));
    }
    @PutMapping("/projects/{id}")
    public ResponseEntity<?> projectModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyHpProjectRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.modifyProject(id, body));
    }
    @DeleteMapping("/projects/{id}")
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
    public ResponseEntity<?> blogAdd(@RequestBody AddHpBlogRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(blogService.addBlog(body));
    }
    @PutMapping("/blogs/{id}")
    public ResponseEntity<?> blogModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyHpBlogRequest body
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
    @GetMapping("/designs/{id}")
    public ResponseEntity<?> designDetails(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(designService.findDesign(id));
    }
    @GetMapping("/designs")
    public ResponseEntity<?> designList(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(designService.findDesigns(pageable, name, description));
    }
    @PostMapping("/designs")
    public ResponseEntity<?> designAdd(@RequestBody AddHpDesignRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(designService.addDesign(body));
    }
    @PutMapping("/designs/{id}")
    public ResponseEntity<?> designModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyHpDesignRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(designService.modifyDesign(id, body));
    }
    @DeleteMapping("/designs/{id}")
    public ResponseEntity<?> designRemove(@PathVariable(name = "id") Long id) {
        designService.removeDesign(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    /*
    ========================================
    Icons
    ========================================
    */
    @GetMapping("/icons")
    public ResponseEntity<?> iconsList(
            Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iconService.findIcons(pageable, name));
    }

    @PostMapping("/icons")
    public ResponseEntity<?> iconAdd(@RequestBody AddHpIconRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(iconService.addIcon(body));
    }

    @PutMapping("/icons/{name}")
    public ResponseEntity<?> iconModify(
            @PathVariable(name = "name") String name,
            @RequestBody ModifyHpIconRequest body
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iconService.modifyIcon(name, body));
    }

    @DeleteMapping("/icons/{name}")
    public ResponseEntity<?> iconRemove(
            @PathVariable(name = "name") String name
    ) {
        iconService.removeIcon(name);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
