package com.azizutku.hipo.models;

import java.util.ArrayList;

public class Company {

    public String company;
    public String team;
    public ArrayList<Member> members;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Company{" +
                "company='" + company + '\'' +
                ", team='" + team + '\'' +
                ", members=" + members +
                '}';
    }
}
