package swe.poliwagexample.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import swe.poliwagexample.entity.User;


@Repository
public class Neo4jDao {

    private final Neo4jClient neo4jClient;

    @Autowired
    public Neo4jDao(Neo4jClient neo4jClient){
        this.neo4jClient = neo4jClient;
    }

    public void addUsers(List<User> users){
        String cypherQuery = """
                CREATE (u:User {name: $name})
                """;

        for (User user: users){
            neo4jClient.query(cypherQuery)
                    .bind(user.getLogin()).to("name").run();
        }

    }

}
