package me.cher1shrxd.watodoserver.domain.github.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.github.dto.request.GithubWebhookPayload;
import me.cher1shrxd.watodoserver.domain.github.service.GithubService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/github")
@Tag(name = "GITHUB")
public class GithubController {
    private final GithubService githubService;

    @PostMapping("/watodo-webhook")
    public ResponseEntity<String> webhookEvent(
            @RequestHeader HttpHeaders headers,
            @RequestBody GithubWebhookPayload payload
    ) {
        System.out.println(payload);
        return githubService.webhookEvent(headers, payload);
    }

}
