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
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
public class User extends BaseEntity {

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @JsonBackReference
    private String password;

    @OneToOne(mappedBy = "owner")
    private Channel channel;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private List<Channel> channels;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    @JsonBackReference
    private List<Role> roles;
}
