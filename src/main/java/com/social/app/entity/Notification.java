package com.social.app.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Notification extends BaseEntity implements Serializable {
    private String notificationId;
    private Date createdAt;
    private Boolean seen;
    private String message;
}
