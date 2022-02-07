package com.pluralsight.conference.core.model;

import javax.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descr;

    private Integer type;

    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private User userid;

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
    
    public User getUserid() {
        return this.userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }
}
