package swe.poliwagexample.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import swe.poliwagexample.dto.UserDto;


@Repository
public class Neo4jDao {

    private final Neo4jClient neo4jClient;

    @Autowired
    public Neo4jDao(Neo4jClient neo4jClient){
        this.neo4jClient = neo4jClient;
    }

    public void addUsers(List<UserDto> users){
        String cypherQuery = """
                CREATE (u:User {name: $name})
                """;

        for (UserDto user: users){
            neo4jClient.query(cypherQuery)
                    .bind(user.userName()).to("name").run();
        }

    }

}
