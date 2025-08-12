package org.example.forkedRepos;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "github.api.base-url=http://localhost:${mock.server.port}"
        }
)
class ForkedReposIntegrationTest {

    private static MockWebServer mockGitHub;

    @LocalServerPort
    private int port;

    RestTemplate restTemplate = new RestTemplate();

    @BeforeAll
    static void startMockServer() throws IOException {
        mockGitHub = new MockWebServer();
        mockGitHub.start();
        System.setProperty("mock.server.port", String.valueOf(mockGitHub.getPort()));
    }

    @AfterAll
    static void stopMockServer() throws IOException {
        mockGitHub.shutdown();
    }

    @Test
    void happyPath() {
        String githubJson = """
                [
                  {"name": "alpha", "html_url": "https://github.com/user/alpha", "description": "First repo", "fork": false},
                  {"name": "beta", "html_url": "https://github.com/user/beta", "description": "Second repo", "fork": true},
                  {"name": "gamma", "html_url": "https://github.com/user/gamma", "description": "Third repo", "fork": false}
                ]
                """;
        mockGitHub.enqueue(new MockResponse()
                .setBody(githubJson)
                .addHeader("Content-Type", "application/json"));
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/users/mabi671", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody())
                .contains("name")
                .contains("owner")
                .doesNotContain("fork");
    }
}
