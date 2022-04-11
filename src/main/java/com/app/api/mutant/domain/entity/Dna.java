package com.app.api.mutant.domain.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table
public class Dna implements Serializable {

    @Id
    private Long id;
    @Column
    private String dnaSequence;
    @Column
    private Boolean isMutant;


    public Dna() {
    }

    public Dna(String dnaSequence, Boolean isMutant) {
        this.dnaSequence = dnaSequence;
        this.isMutant = isMutant;
    }

    @Builder
    public Dna(Long id, String dnaSequence, Boolean isMutant) {
        this.id = id;
        this.dnaSequence = dnaSequence;
        this.isMutant = isMutant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDnaSequence() {
        return dnaSequence;
    }

    public void setDnaSequence(String    dnaSequence) {
        this.dnaSequence = dnaSequence;
    }

    public Boolean getMutant() {
        return isMutant;
    }

    public void setMutant(Boolean mutant) {
        isMutant = mutant;
    }

    @Override
    public String toString() {
        return "Dna{" +
                "id=" + id +
                ", dnaSequence=" + dnaSequence +
                ", isMutant=" + isMutant +
                '}';
    }
}
