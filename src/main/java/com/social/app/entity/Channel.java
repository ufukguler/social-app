package com.social.app.entity;

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
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "CHANNEL_USER_SUBSCRIBE",
            joinColumns = @JoinColumn(name = "CHANNEL_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private List<User> subscribers;

}
