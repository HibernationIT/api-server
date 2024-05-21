package app.hbnationit.apiserver.apis.homepage.services;

import app.hbnationit.apiserver.apis.homepage.models.Stack;
import app.hbnationit.apiserver.apis.homepage.models.dto.AddStackRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyStackRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.StackResponse;
import app.hbnationit.apiserver.apis.homepage.repositories.StackRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StackService {
    private final StackRepository repository;

    public StackService(StackRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Page<StackResponse>> findStacks(Pageable pageable, String type) {
        Page<Stack> stacks;
        if (type == null) {
            stacks = repository.findAll(pageable);
        } else {
            stacks = repository.findByStackType(pageable, type);
        }

        return ResponseEntity.status(HttpStatus.OK).body(stacks.map(this::toVo));
    }

    @Transactional
    public ResponseEntity<StackResponse> addStack(AddStackRequest dto) {
        if (repository.findById(dto.getName()).isPresent()) {
            throw new EntityExistsException("Stack is already exist");
        }

        Stack dao = Stack.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .stackType(dto.getStackType())
                .proficiency(dto.getProficiency())
                .build();
        repository.save(dao);

        return ResponseEntity.status(HttpStatus.CREATED).body(toVo(dao));
    }

    @Transactional
    public ResponseEntity<StackResponse> modifyStack(String name, ModifyStackRequest dto) {
        Stack dao = repository.findById(name).orElseThrow(() ->
                new EntityNotFoundException("Not found Stack"));
        dao.setImage(dto.getImage());
        dao.setStackType(dto.getStackType());
        dao.setProficiency(dto.getProficiency());

        return ResponseEntity.status(HttpStatus.OK).body(toVo(dao));
    }

    @Transactional
    public ResponseEntity<?> removeStack(String name) {
        repository.deleteById(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private StackResponse toVo(Stack dao) {
        return StackResponse.builder()
                .name(dao.getName())
                .image(dao.getImage())
                .stackType(dao.getStackType())
                .proficiency(dao.getProficiency())
                .build();
    }
}
