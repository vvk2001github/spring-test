package com.pluralsight.conference.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=0, max=60, message = "Description`s lenght shoud be 3-60 symbols")
    private String descr;
    
    private Timestamp createdat = Timestamp.valueOf(LocalDateTime.now());

    @Min(value = 0)
    private Integer count1 = 0;

    @Min(value = 0)
    private Integer count2 = 0;

    @Min(value = 0)
    private Float weight1 = 0.0f;

    @Min(value = 0)
    private Float weight2 = 0.0f;

    @ManyToOne
    @JoinColumn(name="exid", nullable=false)
    @JsonBackReference
    private Exercise exid;


    //
    // Getters - Setters
    //

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

    public Timestamp getCreatedat() {
        return this.createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Integer getCount1() {
        return this.count1;
    }

    public void setCount1(Integer count1) {
        this.count1 = count1;
    }

    public Integer getCount2() {
        return this.count2;
    }

    public void setCount2(Integer count2) {
        this.count2 = count2;
    }

    // Weight

    public Float getWeight1() {
        return this.weight1;
    }

    public void setWeight1(Float weight1) {
        this.weight1 = weight1;
    }

    public Float getWeight2() {
        return this.weight2;
    }

    public void setWeight2(Float weight2) {
        this.weight2 = weight2;
    }

    //
    //ExId
    //

    public Exercise getExid() {
        return this.exid;
    }

    public void setExid(Exercise exid) {
        this.exid = exid;
    }
}
