package com.avas.movieratingsystem.business.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;

    @OneToMany( fetch =FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "userId")
    private List<Review> reviewIds;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId",cascade = CascadeType.REMOVE )
    List<UserLike> userLikes;

    public User(Long id) {
        this.id = id;
    }
}