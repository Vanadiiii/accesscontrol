package io.piano.accesscontrol.web;

import io.piano.accesscontrol.domain.entity.RoomInfo;
import io.piano.accesscontrol.domain.entity.UserInfo;
import io.piano.accesscontrol.service.AccessControlStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/info")
@RequiredArgsConstructor
public class StatisticController {
    private final AccessControlStatisticService statisticService;

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomInfo>> getRoomsInfo() {
        return ResponseEntity.ok(statisticService.getRoomsStatistic());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getUsersInfo(
            @RequestParam(required = false, defaultValue = "0") int start,
            @RequestParam(required = false, defaultValue = "20") int end
    ) {
        return ResponseEntity.ok(statisticService.getUserStatistic(PageRequest.of(start, end)));
    }
}
