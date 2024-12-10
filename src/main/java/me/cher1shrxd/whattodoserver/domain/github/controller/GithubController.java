package me.cher1shrxd.whattodoserver.domain.github.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.github.dto.request.GithubWebhookPayload;
import me.cher1shrxd.whattodoserver.domain.github.dto.response.BranchListResponse;
import me.cher1shrxd.whattodoserver.domain.github.service.GithubService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/github")
@Tag(name = "GITHUB")
public class GithubController {
    private final GithubService githubService;

    @PostMapping("/whattodo-webhook")
    public ResponseEntity<String> webhookEvent(
            @RequestHeader HttpHeaders headers,
            @RequestBody GithubWebhookPayload payload
    ) {
        System.out.println(payload);
        return githubService.webhookEvent(headers, payload);
    }

}
