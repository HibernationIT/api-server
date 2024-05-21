package app.hbnationit.apiserver.apis.homepage.controllers;

import app.hbnationit.apiserver.apis.homepage.models.dto.AddStackRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyStackRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.StackResponse;
import app.hbnationit.apiserver.apis.homepage.services.StackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/homepage/stacks")
public class StackController {
    private final StackService service;

    public StackController(StackService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<StackResponse>> stackList(Pageable pageable, @RequestParam(required = false) String type) {
        return service.findStacks(pageable, type);
    }

    @PostMapping
    public ResponseEntity<StackResponse> stackAdd(@RequestBody AddStackRequest body) {
        return service.addStack(body);
    }

    @PutMapping("/{name}")
    public ResponseEntity<StackResponse> stackModify(
            @PathVariable(name = "name") String name,
            @RequestBody ModifyStackRequest body
    ) {
        return service.modifyStack(name, body);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> stackRemove(@PathVariable(name = "name") String name) {
        return service.removeStack(name);
    }
}
