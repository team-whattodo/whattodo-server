package me.cher1shrxd.watodoserver.domain.github.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.github.dto.request.*;
import me.cher1shrxd.watodoserver.domain.schedule.entity.ScheduleEntity;
import me.cher1shrxd.watodoserver.domain.schedule.repository.ScheduleRepository;
import me.cher1shrxd.watodoserver.global.exception.CustomErrorCode;
import me.cher1shrxd.watodoserver.global.exception.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final ScheduleRepository scheduleRepository;

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

                ScheduleEntity scheduleEntity = scheduleRepository.findByBranch(repo.full_name()+":"+ref.ref())
                        .orElseThrow(() -> new CustomException(CustomErrorCode.SCHEDULE_NOT_FOUND));

                scheduleEntity.setDone(true);
                scheduleRepository.save(scheduleEntity);

                return ResponseEntity.ok("Merge event processed");
            }
        }

        return ResponseEntity.ok("Event ignored");
    }
}
