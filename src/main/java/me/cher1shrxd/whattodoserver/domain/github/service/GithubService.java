package me.cher1shrxd.whattodoserver.domain.github.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.github.dto.request.*;
import me.cher1shrxd.whattodoserver.domain.github.dto.response.BranchListResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {
    RestTemplate restTemplate;

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

                System.out.println("Pull Request '" + prTitle + " (" + ref.label() + ")" + " in " + repo.full_name() + "' was merged by " + mergedBy.login());

                return ResponseEntity.ok("Merge event processed");
            }
        }

        return ResponseEntity.ok("Event ignored");
    }
}
