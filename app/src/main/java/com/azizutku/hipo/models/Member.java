package com.azizutku.hipo.models;

import com.google.gson.annotations.SerializedName;

public class Member {

    public String name;
    public int age;
    public String location;
    public String github;
    public HipoDetails hipo;

    public Member(String name, int age, String location, String github) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.github = github;
    }

    public class HipoDetails {
        public String position;

        @SerializedName("years_in_hipo")
        public int yearsInHipo;

        public HipoDetails(String position, int yearsInHipo) {
            this.position = position;
            this.yearsInHipo = yearsInHipo;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getYearsInHipo() {
            return yearsInHipo;
        }

        public void setYearsInHipo(int yearsInHipo) {
            this.yearsInHipo = yearsInHipo;
        }

        @Override
        public String toString() {
            return "HipoDetails{" +
                    "position='" + position + '\'' +
                    ", yearsInHipo=" + yearsInHipo +
                    '}';
        }
    }

    public String getName() {
        return name;
    }

    public Member setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public HipoDetails getHipo() {
        return hipo;
    }

    public void setHipo(String position, int yearsInHipo) {
        this.hipo = new HipoDetails(position, yearsInHipo);
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", location='" + location + '\'' +
                ", github='" + github + '\'' +
                ", hipo=" + hipo +
                '}';
    }
}
