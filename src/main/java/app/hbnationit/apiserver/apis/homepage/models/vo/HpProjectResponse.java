package app.hbnationit.apiserver.apis.homepage.models.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
public @Getter class HpProjectResponse {
    private Long id;
    private String name;
    private String link;
    private String description;
    private Set<Stack> stacks;
    private String image;
    private String content;
    private LocalDateTime createdAt;

    @NoArgsConstructor
    public @Getter static class Stack {
        private String name;
        private String image;

        @Builder
        public Stack(String name, String image) {
            this.name = name;
            this.image = image;
        }

        @Override
        public String toString() {
            return "Stack{" +
                    "name='" + name + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }

    @Builder
    public HpProjectResponse(Long id, String name, String link, String description, Set<Stack> stacks, String image, String content, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.description = description;
        this.stacks = stacks;
        this.image = image;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ProjectResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", stacks=" + stacks +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
