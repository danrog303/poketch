package com.github.danrog303.poketch.pokemon;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data @AllArgsConstructor @NoArgsConstructor
public class Pokemon {
    @Id @Column
    private Long pokedexNumber;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    @Min(1)
    private Integer generation;

    @NotNull
    @JoinColumn
    @OneToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private PokemonType primaryType;

    @JoinColumn
    @OneToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private PokemonType secondaryType;

    @Column
    private String imageUrl;

    @Column
    private String articleUrl;
}
