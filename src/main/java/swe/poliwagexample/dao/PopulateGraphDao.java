package swe.poliwagexample.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import swe.poliwagexample.dto.UserDto;


@Repository
public class PopulateGraphDao {

    private final Neo4jClient neo4jClient;

    @Autowired
    public PopulateGraphDao(Neo4jClient neo4jClient){
        this.neo4jClient = neo4jClient;
    }

    public void populateNeo4j(List<UserDto> users){
        // Query to create or update a user node with the given GitHub ID and username
        String createUserQuery = """
                MERGE (u:User {githubId: $githubId})
                ON CREATE SET u.userName = $userName
                """;

        // Query to create or update a follower node and establish a FOLLOWS relationship with the main user
        String createFollowsAndRelationQuery = """
                MERGE (f:User {githubId: $followerId})
                ON CREATE SET f.userName = $followerName
                WITH f
                MATCH (u:User {githubId: $userId})
                MERGE (f)-[:FOLLOWS]->(u)
                """;

        for (UserDto user: users) {
            // Create the main user
            neo4jClient.query(createUserQuery)
                    .bind(user.githubId()).to("githubId")
                    .bind(user.userName()).to("userName")
                    .run();

            for (UserDto follower : user.followers()) {
                // Create the follower user nodes and the follows relationship
                neo4jClient.query(createFollowsAndRelationQuery)
                        .bind(user.githubId()).to("userId")// Bind the main user's GitHub ID to the query parameter "userId"
                        .bind(follower.githubId()).to("followerId")// Bind the follower's GitHub ID to the query parameter "followerId"
                        .bind(follower.userName()).to("followerName")// Bind the follower's username to the query parameter "followerName"
                        .run();
            }
        }
    }

}
