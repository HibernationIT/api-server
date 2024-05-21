package app.hbnationit.apiserver.apis.homepage.controllers;

import app.hbnationit.apiserver.apis.homepage.models.dto.AddProjectRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyProjectRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.ProjectResponse;
import app.hbnationit.apiserver.apis.homepage.models.vo.ProjectsResponse;
import app.hbnationit.apiserver.apis.homepage.services.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/homepage/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> projectDetails(@PathVariable(name = "id") Long id) {
        return service.findProject(id);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectsResponse>> projectList(
            Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String stacks,
            @RequestParam(required = false) String description
    ) {
        return service.findProjects(pageable, name, stacks, description);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> projectAdd(@RequestBody AddProjectRequest body) {
        return service.addProject(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> projectModify(
            @PathVariable(name = "id") Long id,
            @RequestBody ModifyProjectRequest body
    ) {
        return service.modifyProject(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> projectRemove(@PathVariable(name = "id") Long id) {
        return service.removeProject(id);
    }
}
