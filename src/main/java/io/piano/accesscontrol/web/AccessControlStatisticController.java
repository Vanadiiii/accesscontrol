package io.piano.accesscontrol.web;

import io.piano.accesscontrol.domain.entity.Key;
import io.piano.accesscontrol.domain.entity.Room;
import io.piano.accesscontrol.domain.entity.RoomInfo;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.service.AccessControlStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/info")
@RequiredArgsConstructor
public class AccessControlStatisticController {
    private final AccessControlStatisticService statisticService;

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomInfo>> getRoomsInfo() {
        return ResponseEntity.ok(statisticService.getRoomsStatistic());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersInfo() {
        return ResponseEntity.ok(statisticService.getUserStatistic());
    }
}
