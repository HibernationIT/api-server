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
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static app.hbnationit.apiserver.apis.homepage.models.QBlog.*;

@Service
public class BlogService {
    private final BlogRepository repository;
    private final JPAQueryFactory queryFactory;

    public BlogService(BlogRepository repository, JPAQueryFactory queryFactory) {
        this.repository = repository;
        this.queryFactory = queryFactory;
    }

    public Blog findBlog(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Blog"));
    }

    public BlogResponse findBlogVo(Long id) {
        Blog dao = findBlog(id);
        if (!dao.getView()) { throw new EntityNotFoundException("Not found Blog"); }
        return BlogResponse.builder()
                .id(dao.getId())
                .name(dao.getName())
                .description(dao.getDescription())
                .tags(new HashSet<>(Arrays.asList(dao.getTags().split(","))))
                .image(dao.getImage())
                .content(dao.getContent())
                .createdAt(dao.getCreatedAt())
                .build();
    }

    public Page<Blog> findBlogs(
            Pageable pageable, String name, String tags, String description
    ) {
        JPAQuery<Long> countQuery = queryFactory.select(blog.count()).from(blog);
        JPAQuery<Blog> contentQuery = queryFactory.selectFrom(blog);
        setQuery(countQuery, name, tags, description);
        setQuery(contentQuery, name, tags, description);

        List<Blog> contents = contentQuery.fetch();
        Long count = countQuery.fetchOne();

        return new PageImpl<>(contents, pageable, count);
    }

    public Page<BlogsResponse> findBlogsVo(
            Pageable pageable, String name, String tags, String description
    ) {
        return (Page<BlogsResponse>) findBlogs(pageable, name, tags, description)
                .filter(Blog::getView)
                .map(dao -> BlogsResponse.builder()
                    .id(dao.getId())
                    .name(dao.getName())
                    .description(dao.getDescription())
                    .tags(new HashSet<>(Arrays.asList(dao.getTags().split(","))))
                    .image(dao.getImage())
                    .createdAt(dao.getCreatedAt())
                    .build())
                ;
    }

    @Transactional
    public Blog addBlog(AddBlogRequest dto) {
        String tags = String.join(",", dto.getTags());

        Blog dao = Blog.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .tags(tags)
                .view(dto.getView())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
        return repository.save(dao);
    }

    @Transactional
    public Blog modifyBlog(Long id, ModifyBlogRequest dto) {
        Blog dao = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Not found Blog"));
        String tags = String.join(",", dto.getTags());
        dao.setName(dto.getName());
        dao.setDescription(dto.getDescription());
        dao.setTags(tags);
        dao.setView(dto.getView());
        dao.setImage(dto.getImage());
        dao.setContent(dto.getContent());

        return dao;
    }

    @Transactional
    public void removeBlog(Long id) {
        repository.deleteById(id);
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
