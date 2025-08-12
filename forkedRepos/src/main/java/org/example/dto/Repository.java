package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Repository {
    private String name;
    @JsonIgnore
    private String forked;
    private String owner;
    private Branch[] branches;

    public Repository(){}

    public Repository(String name, String forked, String owner, int branchCount){
        this.name = name;
        this.forked = forked;
        this.owner = owner;
        this.branches = new Branch[branchCount];
    }

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", forked='" + forked + '\'' +
                ", owner='" + owner + '\'';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForked() {
        return forked;
    }

    public void setForked(String forked) {
        this.forked = forked;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void addBranch(int i, Branch branch){
        this.branches[i] = branch;
    }

    public Branch[] getBranches() {
        return branches;
    }
}



