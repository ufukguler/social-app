package com.social.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class Notification extends BaseEntity implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id = System.currentTimeMillis();
    private Date createdAt;
    private Boolean seen;
    private String message;

    public Notification(String message) {
        this.message = message;
        this.createdAt = new Date();
        this.seen = false;
    }
}
