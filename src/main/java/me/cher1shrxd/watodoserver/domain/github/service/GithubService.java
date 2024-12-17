package me.cher1shrxd.watodoserver.domain.github.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.github.dto.request.*;
import me.cher1shrxd.watodoserver.domain.task.entity.TaskEntity;
import me.cher1shrxd.watodoserver.domain.task.repository.TaskRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final TaskRepository taskRepository;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public String getBranch(String repoName, String branchName, String pat) {
        try {
            String url = "https://api.github.com/repos/" + repoName + "/branches/" + branchName;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + pat)
                    .header("Accept", "application/vnd.github+json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode jsonNode = objectMapper.readTree(response.body());
                return jsonNode.get("name").asText();
            } else if (response.statusCode() == 404) {
                return createBranch(repoName, branchName, pat);
            } else {
                throw new CustomException(CustomErrorCode.CANNOT_GET_BRANCHES);
            }
        } catch (IOException | InterruptedException e) {
            throw new CustomException(CustomErrorCode.CANNOT_GET_BRANCHES);
        }
    }


    private String getDefaultBranch(String repoName, String pat) {
        try {
            String repoUrl = "https://api.github.com/repos/" + repoName;

            HttpRequest repoRequest = HttpRequest.newBuilder()
                    .uri(java.net.URI.create(repoUrl))
                    .header("Authorization", "Bearer " + pat)
                    .header("Accept", "application/vnd.github+json")
                    .GET()
                    .build();

            HttpResponse<String> repoResponse = httpClient.send(repoRequest, HttpResponse.BodyHandlers.ofString());

            if (repoResponse.statusCode() == 200) {
                JsonNode repoJson = objectMapper.readTree(repoResponse.body());
                String defaultBranch = repoJson.get("default_branch").asText();

                String branchUrl = "https://api.github.com/repos/" + repoName + "/git/ref/heads/" + defaultBranch;

                HttpRequest branchRequest = HttpRequest.newBuilder()
                        .uri(java.net.URI.create(branchUrl))
                        .header("Authorization", "Bearer " + pat)
                        .header("Accept", "application/vnd.github+json")
                        .GET()
                        .build();

                HttpResponse<String> branchResponse = httpClient.send(branchRequest, HttpResponse.BodyHandlers.ofString());

                if (branchResponse.statusCode() == 200) {
                    JsonNode branchJson = objectMapper.readTree(branchResponse.body());
                    return branchJson.get("object").get("sha").asText();
                } else {
                    throw new CustomException(CustomErrorCode.CANNOT_GET_BRANCHES);
                }
            } else {
                throw new CustomException(CustomErrorCode.CANNOT_GET_BRANCHES);
            }
        } catch (IOException | InterruptedException e) {
            throw new CustomException(CustomErrorCode.CANNOT_GET_BRANCHES);
        }
    }

    private String createBranch(String repoName, String branchName, String pat) {
        String defaultSha = getDefaultBranch(repoName, pat);

        try {
            String createBranchUrl = "https://api.github.com/repos/" + repoName + "/git/refs";

            ObjectNode createBranchPayload = objectMapper.createObjectNode();
            createBranchPayload.put("ref", "refs/heads/" + branchName);
            createBranchPayload.put("sha", defaultSha);

            HttpRequest createBranchRequest = HttpRequest.newBuilder()
                    .uri(java.net.URI.create(createBranchUrl))
                    .header("Authorization", "Bearer " + pat)
                    .header("Accept", "application/vnd.github+json")
                    .POST(HttpRequest.BodyPublishers.ofString(createBranchPayload.toString()))
                    .build();

            HttpResponse<String> createBranchResponse = httpClient.send(createBranchRequest, HttpResponse.BodyHandlers.ofString());

            if (createBranchResponse.statusCode() == 201) {
                JsonNode createdBranchJson = objectMapper.readTree(createBranchResponse.body());
                return createdBranchJson.get("ref").asText().replace("refs/heads/", "");
            } else {
                throw new CustomException(CustomErrorCode.CANNOT_GET_BRANCHES);
            }
        } catch (IOException | InterruptedException e) {
            throw new CustomException(CustomErrorCode.CANNOT_GET_BRANCHES);
        }
    }


    public ResponseEntity<String> webhookEvent(
            HttpHeaders headers,
            GithubWebhookPayload payload
    ) {
        String eventType = headers.getFirst("X-GitHub-Event");

        if ("pull_request".equals(eventType)) {
            String action = payload.action();

            if ("closed".equals(action) && payload.pull_request().merged()) {
                System.out.println(payload);
                PullRequest pullRequest = payload.pull_request();
                GithubRef ref = pullRequest.head();
                GithubRepo repo = ref.repo();
                String prTitle = pullRequest.title();
                PullRequestMergedBy mergedBy = pullRequest.merged_by();

                System.out.println("Pull Request '" + prTitle + " (" + ref.ref() + ")" + " in " + repo.full_name() + "' was merged by " + mergedBy.login());

                TaskEntity taskEntity = taskRepository.findByBranch(repo.full_name()+":"+ref.ref())
                        .orElseThrow(() -> new CustomException(CustomErrorCode.TASK_NOT_FOUND));

                taskEntity.setDone(true);
                taskRepository.save(taskEntity);

                return ResponseEntity.ok("Merge event processed");
            }
        }

        return ResponseEntity.ok("Event ignored");
    }
}
