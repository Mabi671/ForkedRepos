package org.example.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class RequestSender {
    public static String getRepositories(String user){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.github.com/users/"+user+"/repos";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println(response.getStatusCode());
            return response.getBody();
        }catch(HttpClientErrorException e){
            return ResponseEntity.notFound().build().toString();
        }

    }
    public static String getBranches(String repoName, String user){
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.github.com/repos/"+user+"/"+repoName+"/branches";

            return restTemplate.getForObject(url, String.class);
    }
}
