package app.hbnationit.apiserver.global.models;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract @Getter class DefaultEntity {
    @CreatedDate
    protected @Column(name = "created_at") LocalDateTime createdAt;

    @LastModifiedDate
    protected @Column(name = "updated_at") LocalDateTime updatedAt;

    @CreatedBy
    protected @Column(name = "created_by") String createdBy;

    @LastModifiedBy
    protected @Column(name = "updated_by") String updatedBy;
}
