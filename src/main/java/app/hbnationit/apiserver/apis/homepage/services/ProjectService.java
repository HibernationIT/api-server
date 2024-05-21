package app.hbnationit.apiserver.apis.homepage.services;

import app.hbnationit.apiserver.apis.homepage.models.Project;
import app.hbnationit.apiserver.apis.homepage.models.dto.AddProjectRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyProjectRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.ProjectResponse;
import app.hbnationit.apiserver.apis.homepage.models.vo.ProjectsResponse;
import app.hbnationit.apiserver.apis.homepage.repositories.ProjectRepository;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static app.hbnationit.apiserver.apis.homepage.models.QProject.*;
import static app.hbnationit.apiserver.apis.homepage.models.QStack.*;
import static com.querydsl.core.group.GroupBy.*;

@Service
public class ProjectService {
    private final ProjectRepository repository;
    private final JPAQueryFactory queryFactory;

    public ProjectService(ProjectRepository repository, JPAQueryFactory queryFactory) {
        this.repository = repository;
        this.queryFactory = queryFactory;
    }

    public ResponseEntity<ProjectResponse> findProject(Long id) {
        ProjectResponse vo = getOne(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(vo);
    }

    public ResponseEntity<Page<ProjectsResponse>> findProjects(
            Pageable pageable, String name, String stacks, String description
    ) {
        JPAQuery<?> countQuery = queryFactory.select(project.count()).from(project);
        JPAQuery<?> contentsQuery = queryFactory
                .from(project)
                .join(stack).on(project.stacks.contains(stack.name));
        setQuery(countQuery, name, stacks, description);
        setQuery(contentsQuery, name, stacks, description);

        List<ProjectsResponse> contents = contentsQuery.transform(
                groupBy(project.id).list(
                        Projections.fields(ProjectsResponse.class,
                                project.id,
                                project.name,
                                project.link,
                                project.description,
                                GroupBy.set(Projections.fields(ProjectsResponse.Stack.class,
                                        stack.name,
                                        stack.image
                                )).as("stacks"),
                                project.image,
                                project.createdAt
                        )
                )
        );
        Long count = (Long) countQuery.fetchOne();

        Page<ProjectsResponse> vo = new PageImpl<>(contents, pageable, count);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    @Transactional
    public ResponseEntity<ProjectResponse> addProject(AddProjectRequest dto) {
        String stacks = String.join(",", dto.getStacks());

        Project dao = Project.builder()
                .name(dto.getName())
                .link(dto.getLink())
                .description(dto.getDescription())
                .stacks(stacks)
                .view(dto.getView())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
        dao = repository.save(dao);
;
        return ResponseEntity.status(HttpStatus.CREATED).body(getOne(dao.getId()));
    }

    @Transactional
    public ResponseEntity<ProjectResponse> modifyProject(Long id, ModifyProjectRequest dto) {
        Project dao = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Project"));
        String stacks = String.join(",", dto.getStacks());
        dao.setName(dto.getName());
        dao.setLink(dto.getLink());
        dao.setDescription(dto.getDescription());
        dao.setStacks(stacks);
        dao.setView(dto.getView());
        dao.setImage(dto.getImage());
        dao.setContent(dto.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(getOne(dao.getId()));
    }

    @Transactional
    public ResponseEntity<?> removeProject(Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ProjectResponse getOne(Long id) {
        List<ProjectResponse> vo = queryFactory
                .from(project)
                .join(stack).on(project.stacks.contains(stack.name))
                .where(project.id.eq(id))
                .transform(
                        groupBy(project.id).list(
                                Projections.fields(ProjectResponse.class,
                                        project.id,
                                        project.name,
                                        project.link,
                                        project.description,
                                        GroupBy.set(Projections.fields(ProjectResponse.Stack.class,
                                                stack.name,
                                                stack.image
                                        )).as("stacks"),
                                        project.image,
                                        project.content,
                                        project.createdAt
                                )
                        )
                );
        if (vo.size() == 0) {
            throw new EntityNotFoundException("Not found Project");
        }

        return vo.get(0);
    }

    private void setQuery(JPAQuery<?> query, String name, String stacks, String description) {
        if (name != null && description == null) {
            query.where(project.name.toUpperCase().contains(name.toUpperCase()));
        } else if (name == null && description != null) {
            query.where(project.description.toUpperCase().contains(description.toUpperCase()));
        } else if (name != null) {
            query.where(project.name.toUpperCase().contains(name.toUpperCase())
                    .or(project.description.toUpperCase().contains(description.toUpperCase())));
        }
        if (stacks != null) {
            for (String s : stacks.trim().split(",")) {
                query.where(project.stacks.contains(s));
            }
        }
    }
}
