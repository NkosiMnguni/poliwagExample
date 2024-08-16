package swe.poliwagexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swe.poliwagexample.service.Neo4jService;


@RestController
@RequestMapping("/neo4j")
public class Neo4jController {

    private final Neo4jService neo4jService;

    @Autowired
    public Neo4jController(Neo4jService neo4jService){
        this.neo4jService = neo4jService;
    }

    @PostMapping("/addUsers")
    public ResponseEntity addUsers(){
        neo4jService.addUsers();
        return ResponseEntity.ok().build();
    }
}
