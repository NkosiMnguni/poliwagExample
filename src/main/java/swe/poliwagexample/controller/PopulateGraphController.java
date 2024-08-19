package swe.poliwagexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import swe.poliwagexample.service.PopulateGraphService;


@RestController
public class PopulateGraphController {

    private final PopulateGraphService populateGraphService;

    @Autowired
    public PopulateGraphController(PopulateGraphService populateGraphService){
        this.populateGraphService = populateGraphService;
    }

    @PostMapping("/populate-neo4j-graph")
    public ResponseEntity populateGraph(){
        populateGraphService.populateGraph();
        return ResponseEntity.ok().build();
    }
}
