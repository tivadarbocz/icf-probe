package com.icf.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    protected Long id;

    @OneToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    protected User createdBy;

    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    protected User modifiedBy;

    @Column
    protected LocalDateTime modifiedAt;

    public BaseEntity() {
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.modifiedAt = now;
    }

}