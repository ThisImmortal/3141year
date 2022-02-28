package com.task.year.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lords", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private long age;  //Humanoids in 3141 may find longevity. That's why as a data type I choose long :)

    @OneToMany(mappedBy = "lord",
               cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private List<Planet> planets = new ArrayList<>();

    public Lord(String name, long age) {
        this.name = name;
        this.age = age;
    }

    //convenience method for bi-directional mapping
    public void addPlanets(Planet planet) {

        planets.add(planet);
        planet.setLord(this);
    }
}
