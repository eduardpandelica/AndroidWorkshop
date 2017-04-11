package com.example.adrian.workshop1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("space")
    @Expose
    private Integer space;
    @SerializedName("private_repos")
    @Expose
    private Integer privateRepos;
    @SerializedName("collaborators")
    @Expose
    private Integer collaborators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpace() {
        return space;
    }

    public void setSpace(Integer space) {
        this.space = space;
    }

    public Integer getPrivateRepos() {
        return privateRepos;
    }

    public void setPrivateRepos(Integer privateRepos) {
        this.privateRepos = privateRepos;
    }

    public Integer getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(Integer collaborators) {
        this.collaborators = collaborators;
    }

}