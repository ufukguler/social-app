package com.social.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Channel extends BaseEntity {

    @Column(name = "NAME", length = 55)
    private String name;

    @OneToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "CHANNEL_USER",
            joinColumns = @JoinColumn(name = "CHANNEL_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @JsonBackReference
    private List<User> subscribers;

}
