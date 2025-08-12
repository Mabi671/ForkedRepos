package org.example.dto;

public class User {
    private Repository[] repositories;

    public User(){}

    public User(int size){
        this.repositories = new Repository[size];
    }

    public void addRepository(int i, Repository repository){
        this.repositories[i] = repository;
    }

    public Repository[] getRepositories() {
        return repositories;
    }
}
