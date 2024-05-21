package app.hbnationit.apiserver.apis.homepage.services;

import app.hbnationit.apiserver.apis.homepage.models.Blog;
import app.hbnationit.apiserver.apis.homepage.models.dto.AddBlogRequest;
import app.hbnationit.apiserver.apis.homepage.models.dto.ModifyBlogRequest;
import app.hbnationit.apiserver.apis.homepage.models.vo.BlogResponse;
import app.hbnationit.apiserver.apis.homepage.models.vo.BlogsResponse;
import app.hbnationit.apiserver.apis.homepage.repositories.BlogRepository;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static app.hbnationit.apiserver.apis.homepage.models.QBlog.*;

@Service
public class BlogService {
    private final BlogRepository repository;
    private final JPAQueryFactory queryFactory;

    public BlogService(BlogRepository repository, JPAQueryFactory queryFactory) {
        this.repository = repository;
        this.queryFactory = queryFactory;
    }

    public ResponseEntity<BlogResponse> findBlog(Long id) {
        Blog dao = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Blog"));

        return ResponseEntity.status(HttpStatus.OK).body(toVo(dao));
    }

    public ResponseEntity<Page<BlogsResponse>> findBlogs(
            Pageable pageable, String name, String tags, String description
    ) {
        JPAQuery<Long> countQuery = queryFactory.select(blog.count()).from(blog);
        JPAQuery<Blog> contentQuery = queryFactory.selectFrom(blog);
        setQuery(countQuery, name, tags, description);
        setQuery(contentQuery, name, tags, description);

        List<BlogsResponse> contents = contentQuery.fetch().stream().map(this::toVos).toList();
        Long count = (Long) countQuery.fetchOne();

        Page<BlogsResponse> vo = new PageImpl<>(contents, pageable, count);
        return ResponseEntity.status(HttpStatus.OK).body(vo);
    }

    @Transactional
    public ResponseEntity<BlogResponse> addBlog(AddBlogRequest dto) {
        String tags = String.join(",", dto.getTags());

        Blog dao = Blog.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .tags(tags)
                .view(dto.getView())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
        dao = repository.save(dao);

        return ResponseEntity.status(HttpStatus.CREATED).body(toVo(dao));
    }

    @Transactional
    public ResponseEntity<BlogResponse> modifyBlog(Long id, ModifyBlogRequest dto) {
        Blog dao = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Blog"));
        String tags = String.join(",", dto.getTags());
        dao.setName(dto.getName());
        dao.setDescription(dto.getDescription());
        dao.setTags(tags);
        dao.setView(dto.getView());
        dao.setImage(dto.getImage());
        dao.setContent(dto.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(toVo(dao));
    }

    @Transactional
    public ResponseEntity<?> removeBlog(Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private BlogResponse toVo(Blog dao) {
        Set<String> tags = new HashSet<>(Arrays.asList(dao.getTags().split(",")));

        return BlogResponse.builder()
                .id(dao.getId())
                .name(dao.getName())
                .description(dao.getDescription())
                .tags(tags)
                .view(dao.getView())
                .image(dao.getImage())
                .content(dao.getContent())
                .createdAt(dao.getCreatedAt())
                .build();
    }

    private BlogsResponse toVos(Blog dao) {
        Set<String> tags = new HashSet<>(Arrays.asList(dao.getTags().split(",")));

        return BlogsResponse.builder()
                .id(dao.getId())
                .name(dao.getName())
                .description(dao.getDescription())
                .tags(tags)
                .view(dao.getView())
                .image(dao.getImage())
                .createdAt(dao.getCreatedAt())
                .build();
    }

    private void setQuery(JPAQuery<?> query, String name, String tags, String description) {
        if (name != null && description == null) {
            query.where(blog.name.toUpperCase().contains(name.toUpperCase()));
        } else if (name == null && description != null) {
            query.where(blog.description.toUpperCase().contains(description.toUpperCase()));
        } else if (name != null) {
            query.where(blog.name.toUpperCase().contains(name.toUpperCase())
                    .or(blog.description.toUpperCase().contains(description.toUpperCase())));
        }
        if (tags != null) {
            for (String s : tags.trim().split(",")) {
                query.where(blog.tags.contains(s));
            }
        }
    }
}
