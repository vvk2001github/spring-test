package com.pluralsight.conference.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "exercises")
@JsonIgnoreProperties("exid")
@JsonFilter("userFilter")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min=3, max=60, message = "Description`s lenght shoud be 3-60 symbols")
    private String descr;

    @Min(0)
    @Max(3)
    private Integer type;

    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    @JsonBackReference
    private User user;

    @OneToMany()
    @JoinColumn(name = "exid", updatable = false)
    private List<Workout> workouts;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }
    
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Workout> getWorkout() {
        return this.workouts;
    }

    public void setWorkout(List<Workout> workouts) {
        this.workouts = workouts;
    }

}
