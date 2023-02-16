package com.github.danrog303.poketch.user.details;

import com.github.danrog303.poketch.user.credentials.AppUser;
import com.github.danrog303.poketch.pokemon.Pokemon;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
/**
 *
 */
@Table
@Entity
@Data
public class AppUserDetails {
    @Column @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String nickname;

    @OneToOne(mappedBy="details", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @ToString.Exclude
    private AppUser user;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable
    @ToString.Exclude
    private List<Pokemon> pokemonCollection;

    public AppUserDetails() {
    }

    public AppUserDetails(String nickname) {
        this.nickname = nickname;
        this.pokemonCollection = new ArrayList<Pokemon>();
    }

    public AppUserDetails(String nickname, AppUser user) {
        this(nickname);
        this.user = user;
    }
}
