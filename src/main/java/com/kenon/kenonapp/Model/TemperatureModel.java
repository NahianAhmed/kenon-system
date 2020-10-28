package com.kenon.kenonapp.Model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "temparature_mesurement")
public class TemperatureModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name = "社員番号", nullable = false)
    private String userId;
    @Column(name = "体温", nullable = false)
    private double temperature;
    @Column(name = "症状の有無", nullable = false)
    private boolean symtoms;
    @Column(name = "更新日時", nullable = false)
    @CreationTimestamp
    private Timestamp lastUsed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isSymtoms() {
        return symtoms;
    }

    public void setSymtoms(boolean symtoms) {
        this.symtoms = symtoms;
    }

    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }


}
