package swe.poliwagexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import swe.poliwagexample.dao.PopulateGraphDao;


@Service
public class PopulateGraphService {

    private final PopulateGraphDao populateGraphDao;
    private final GitHubApiService gitHubApiService;

    @Autowired
    public PopulateGraphService(PopulateGraphDao populateGraphDao, GitHubApiService gitHubApiService){
        this.populateGraphDao = populateGraphDao;
        this.gitHubApiService = gitHubApiService;
    }


    public boolean populateGraph(){
        try {
            populateGraphDao.populateNeo4j(gitHubApiService.getUsers());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
