package swe.poliwagexample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import swe.poliwagexample.dao.Neo4jDao;


@Service
public class Neo4jService {

    private final Neo4jDao neo4jDao;
    private final GitHubUserService gitHubUserService;

    @Autowired
    public Neo4jService(Neo4jDao neo4jDao, GitHubUserService gitHubUserService){
        this.neo4jDao = neo4jDao;
        this.gitHubUserService = gitHubUserService;
    }


    public void addUsers(){
        neo4jDao.addUsers(gitHubUserService.getUsers());
    }
}
