package app.hbnationit.apiserver.apis.homepage.services;

import app.hbnationit.apiserver.apis.homepage.models.HpProject;
import app.hbnationit.apiserver.apis.homepage.models.dto.AddHpProjectRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyHpProjectRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.HpProjectResponse;
import app.hbnationit.apiserver.apis.homepage.models.vo.HpProjectsResponse;
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

    public HpProject findProject(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Project"));
    }

    public HpProjectResponse findProjectVo(Long id) {
        List<HpProjectResponse> vo = queryFactory
                .from(project)
                .join(stack).on(project.stacks.contains(stack.name))
                .where(project.id.eq(id))
                .where(project.view.isTrue())
                .transform(
                        groupBy(project.id).list(
                                Projections.fields(HpProjectResponse.class,
                                        project.id,
                                        project.name,
                                        project.link,
                                        project.description,
                                        GroupBy.set(Projections.fields(HpProjectResponse.Stack.class,
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

    public Page<HpProject> findProjects(
            Pageable pageable, String name, String stacks, String description
    ) {
        JPAQuery<Long> countQuery = queryFactory.select(project.count()).from(project);
        JPAQuery<HpProject> contentsQuery = queryFactory.selectFrom(project);
        setQuery(countQuery, name, stacks, description);
        setQuery(contentsQuery, name, stacks, description);

        List<HpProject> contents = contentsQuery.fetch();
        Long count = countQuery.fetchOne();

        return new PageImpl<>(contents, pageable, count);
    }

    public Page<HpProjectsResponse> findProjectsVo(
            Pageable pageable, String name, String stacks, String description
    ) {
        JPAQuery<?> countQuery = queryFactory.select(project.count()).from(project).where(project.view.isTrue());
        JPAQuery<?> contentsQuery = queryFactory
                .from(project).where(project.view.isTrue())
                .join(stack).on(project.stacks.contains(stack.name));
        setQuery(countQuery, name, stacks, description);
        setQuery(contentsQuery, name, stacks, description);

        List<HpProjectsResponse> contents = contentsQuery.transform(
                groupBy(project.id).list(
                        Projections.fields(HpProjectsResponse.class,
                                project.id,
                                project.name,
                                project.link,
                                project.description,
                                GroupBy.set(Projections.fields(HpProjectsResponse.Stack.class,
                                        stack.name,
                                        stack.image
                                )).as("stacks"),
                                project.image,
                                project.createdAt
                        )
                )
        );
        Long count = (Long) countQuery.fetchOne();

        return new PageImpl<>(contents, pageable, count);
    }

    @Transactional
    public HpProject addProject(AddHpProjectRequest dto) {
        String stacks = String.join(",", dto.getStacks());

        HpProject dao = HpProject.builder()
                .name(dto.getName())
                .link(dto.getLink())
                .description(dto.getDescription())
                .stacks(stacks)
                .view(dto.getView())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
        return repository.save(dao);
    }

    @Transactional
    public HpProject modifyProject(Long id, ModifyHpProjectRequest dto) {
        HpProject dao = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Project"));
        String stacks = String.join(",", dto.getStacks());
        dao.setName(dto.getName());
        dao.setLink(dto.getLink());
        dao.setDescription(dto.getDescription());
        dao.setStacks(stacks);
        dao.setView(dto.getView());
        dao.setImage(dto.getImage());
        dao.setContent(dto.getContent());
        return dao;
    }

    @Transactional
    public void removeProject(Long id) {
        repository.deleteById(id);
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
