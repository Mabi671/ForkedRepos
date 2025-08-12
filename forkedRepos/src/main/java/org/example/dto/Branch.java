package org.example.dto;

public class Branch {
    private String name;
    private String sha;

    public Branch(){}

    public Branch(String name, String sha){
        this.name = name;
        this.sha = sha;
    }

    public String getName() {
        return name;
    }

    public String getSha() {
        return sha;
    }
}
