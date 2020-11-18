package com.social.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"channel", "subbedChannels", "roles"})
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "CHANNEL_USER_SUBSCRIBE",
            joinColumns = @JoinColumn(name = "CHANNEL_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @JsonIgnore
    private List<User> subscribers;

}
