package org.example.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Error.NotFoundMessage;
import org.example.Service.RequestSender;
import org.example.dto.Branch;
import org.example.dto.Repository;
import org.example.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    private final ObjectMapper objectMapper;

    UserController(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @GetMapping("/users/{user}")
    public ResponseEntity<?> getData(@PathVariable String user) throws Exception {
        String jsonString = RequestSender.getRepositories(user);
        String notFoundMessage = objectMapper.writeValueAsString(new NotFoundMessage());
        if(jsonString.startsWith("<404")){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundMessage);}

        JsonNode jsonNode = objectMapper.readTree(jsonString);
        User githubUser = new User(jsonNode.size());

        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode jsonNod = jsonNode.get(i);
            String name = jsonNod.findValue("name").asText();
            String branchesJson = RequestSender.getBranches(name, user);
            JsonNode branchesNode = objectMapper.readTree(branchesJson);
            Repository rep = new Repository(
                    name,
                    jsonNod.findValue("fork").asText(),
                    jsonNod.findValue("login").asText(),
                    branchesNode.size()
            );
            if (rep.getForked().equals("false")){
                for(int y = 0; y < branchesNode.size(); y++){
                    String branchName = branchesNode.get(y).findValue("name").asText();
                    String sha = branchesNode.get(y).findValue("sha").asText();
                    Branch branch = new Branch(branchName, sha);
                    rep.addBranch(y, branch);
                }
                githubUser.addRepository(i, rep);
            }
        }
        String finalJson;
        try {
            finalJson = objectMapper.writeValueAsString(githubUser);
            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body(finalJson);
    }
}
