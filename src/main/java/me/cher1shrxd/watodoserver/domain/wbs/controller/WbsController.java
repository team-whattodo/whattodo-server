package me.cher1shrxd.watodoserver.domain.wbs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.EditSprintRequest;
import me.cher1shrxd.watodoserver.domain.sprint.dto.request.MakeSprintRequest;
import me.cher1shrxd.watodoserver.domain.sprint.dto.response.SprintResponse;
import me.cher1shrxd.watodoserver.domain.wbs.dto.request.EditWbsRequest;
import me.cher1shrxd.watodoserver.domain.wbs.dto.request.MaksWbsRequest;
import me.cher1shrxd.watodoserver.domain.wbs.dto.response.WbsResponse;
import me.cher1shrxd.watodoserver.domain.wbs.service.WbsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wbs")
@Tag(name = "WBS")
public class WbsController {
    private final WbsService wbsService;

    @PostMapping
    public void makeWbs(@RequestBody MaksWbsRequest maksWbsRequest) {
        wbsService.makeWbs(maksWbsRequest);
    }

    @DeleteMapping("/{wbsId}")
    public void deleteWbs(@PathVariable String wbsId) {
        wbsService.deleteWbs(wbsId);
    }

    @PatchMapping("/{wbsId}")
    public WbsResponse editWbs(@RequestBody EditWbsRequest editWbsRequest, @PathVariable String wbsId) {
        return wbsService.editWbs(editWbsRequest, wbsId);
    }
}