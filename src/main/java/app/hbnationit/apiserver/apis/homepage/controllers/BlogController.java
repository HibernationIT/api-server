package app.hbnationit.apiserver.apis.homepage.controllers;

import app.hbnationit.apiserver.apis.homepage.models.dto.AddBlogRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyBlogRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.BlogResponse;
import app.hbnationit.apiserver.apis.homepage.models.vo.BlogsResponse;
import app.hbnationit.apiserver.apis.homepage.services.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/homepage/blogs")
public class BlogController {
    private final BlogService service;

    public BlogController(BlogService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> blogDetails(@PathVariable(name = "id") Long id) {
        return service.findBlog(id);
    }

    @GetMapping
    public ResponseEntity<Page<BlogsResponse>> blogList(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String description
    ) {
        return service.findBlogs(pageable, name, tags, description);
    }

    @PostMapping
    public ResponseEntity<BlogResponse> blogAdd(@RequestBody AddBlogRequest body) {
        return service.addBlog(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> blogModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyBlogRequest body
    ) {
        return service.modifyBlog(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> blogRemove(@PathVariable(name = "id") Long id) {
        return service.removeBlog(id);
    }
}
