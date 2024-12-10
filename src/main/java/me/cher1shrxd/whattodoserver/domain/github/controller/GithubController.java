package me.cher1shrxd.whattodoserver.domain.github.controller;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.whattodoserver.domain.github.dto.request.GithubWebhookPayload;
import me.cher1shrxd.whattodoserver.domain.github.service.GithubService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/whattodo-webhook")
public class GithubController {
    private final GithubService githubService;

    @PostMapping
    public ResponseEntity<String> webhookEvent(
            @RequestHeader HttpHeaders headers,
            @RequestBody GithubWebhookPayload payload
    ) {
        System.out.println(payload);
        return githubService.webhookEvent(headers, payload);
//        return ResponseEntity.ok("hi");
    }
}
