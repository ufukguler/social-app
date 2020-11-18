package com.social.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"password", "subbedChannels"})
@EqualsAndHashCode(of = "id")
public class User extends BaseEntity {

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @JsonIgnore
    private String password;

    @OneToOne(mappedBy = "owner")
    @JsonBackReference
    private Channel channel;

    @ManyToMany(
            mappedBy = "subscribers",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private List<Channel> subbedChannels;

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
