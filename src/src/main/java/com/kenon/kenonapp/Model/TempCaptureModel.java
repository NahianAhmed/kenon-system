package com.kenon.kenonapp.Model;

import javax.persistence.*;

@Entity
@Table(name = "temp_capture")
public class TempCaptureModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "userid", nullable = false)
    String userid;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "kananame", nullable = false)
    String kananame;
    @Column(name = "dept", nullable = false)
    String dept;

    @Column(name = "day1", nullable = true)
    String day1;
    @Column(name = "day2", nullable = true)
    String day2;
    @Column(name = "day3", nullable = true)
    String day3;
    @Column(name = "day4", nullable = true)
    String day4;
    @Column(name = "day5", nullable = true)
    String day5;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKananame() {
        return kananame;
    }

    public void setKananame(String kananame) {
        this.kananame = kananame;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getDay4() {
        return day4;
    }

    public void setDay4(String day4) {
        this.day4 = day4;
    }

    public String getDay5() {
        return day5;
    }

    public void setDay5(String day5) {
        this.day5 = day5;
    }
}
