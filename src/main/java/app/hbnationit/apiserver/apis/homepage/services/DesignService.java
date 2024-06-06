package app.hbnationit.apiserver.apis.homepage.services;

import app.hbnationit.apiserver.apis.homepage.models.HpDesign;
import app.hbnationit.apiserver.apis.homepage.models.dto.AddHpDesignRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyHpDesignRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.HpDesignResponse;
import app.hbnationit.apiserver.apis.homepage.models.vo.HpDesignsResponse;
import app.hbnationit.apiserver.apis.homepage.repositories.DesignRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static app.hbnationit.apiserver.apis.homepage.models.QDesign.*;

@Service
public class DesignService {
    private final DesignRepository repository;
    private final JPAQueryFactory queryFactory;

    public DesignService(DesignRepository repository, JPAQueryFactory queryFactory) {
        this.repository = repository;
        this.queryFactory = queryFactory;
    }

    public HpDesign findDesign(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Design"));
    }

    public HpDesignResponse findDesignVo(Long id) {
        HpDesign dao = findDesign(id);
        if (!dao.getView()) { throw new EntityNotFoundException("Not found Design"); }
        return HpDesignResponse.builder()
                .id(dao.getId())
                .name(dao.getName())
                .link(dao.getLink())
                .description(dao.getDescription())
                .image(dao.getImage())
                .designs(new HashSet<>(Arrays.asList(dao.getDesigns().split(","))))
                .createdAt(dao.getCreatedAt())
                .build();
    }

    public Page<HpDesign> findDesigns(
            Pageable pageable, String name, String description
    ) {
        JPAQuery<Long> countQuery = queryFactory.select(design.count()).from(design);
        JPAQuery<HpDesign> contentQuery = queryFactory.selectFrom(design);
        setQuery(countQuery, name, description);
        setQuery(contentQuery, name, description);

        List<HpDesign> contents = contentQuery.fetch();
        Long count = countQuery.fetchOne();

        return new PageImpl<>(contents, pageable, count);
    }

    public Page<HpDesignsResponse> findDesignsVo(
            Pageable pageable, String name, String description
    ) {
        JPAQuery<Long> countQuery = queryFactory.select(design.count()).from(design).where(design.view.isTrue());
        JPAQuery<HpDesign> contentQuery = queryFactory.selectFrom(design).where(design.view.isTrue());
        setQuery(countQuery, name, description);
        setQuery(contentQuery, name, description);

        List<HpDesign> contents = contentQuery.fetch();
        Long count = countQuery.fetchOne();

        return new PageImpl<>(contents, pageable, count)
                .map(dao ->  HpDesignsResponse.builder()
                        .id(dao.getId())
                        .name(dao.getName())
                        .link(dao.getLink())
                        .description(dao.getDescription())
                        .image(dao.getImage())
                        .createdAt(dao.getCreatedAt())
                        .build()
                );
    }

    @Transactional
    public HpDesign addDesign(AddHpDesignRequest dto) {
        String designs = String.join(",", dto.getDesigns());

        HpDesign dao = HpDesign.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .link(dto.getLink())
                .view(dto.getView())
                .image(dto.getImage())
                .designs(designs)
                .build();
        return repository.save(dao);
    }

    @Transactional
    public HpDesign modifyDesign(Long id, ModifyHpDesignRequest dto) {
        HpDesign dao = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Blog"));
        String designs = String.join(",", dto.getDesigns());
        dao.setName(dto.getName());
        dao.setDescription(dto.getDescription());
        dao.setLink(dto.getLink());
        dao.setView(dto.getView());
        dao.setImage(dto.getImage());
        dao.setDesigns(designs);
        return dao;
    }

    @Transactional
    public void removeDesign(Long id) {
        repository.deleteById(id);
    }

    private void setQuery(JPAQuery<?> query, String name, String description) {
        if (name != null && description == null) {
            query.where(design.name.toUpperCase().contains(name.toUpperCase()));
        } else if (name == null && description != null) {
            query.where(design.description.toUpperCase().contains(description.toUpperCase()));
        } else if (name != null) {
            query.where(design.name.toUpperCase().contains(name.toUpperCase())
                    .or(design.description.toUpperCase().contains(description.toUpperCase())));
        }
    }
}
