package ru.dexsys.accesscontrol.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ru.dexsys.accesscontrol.domain.entity.RoomInfo;
import ru.dexsys.accesscontrol.domain.entity.UserInfo;
import ru.dexsys.accesscontrol.service.AccessControlStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
@Api("Контроллер статистики")
public class InfoController {
    private final AccessControlStatisticService statisticService;

    @ApiOperation("Получить информацию по всем комнатам")
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomInfo>> getFilledRoomsInfo() {
        return ResponseEntity.ok(statisticService.getRoomsStatistic());
    }

    @ApiOperation("Получить информацию по всем пользователям (ключам)")
    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getUsersInfo(
            @RequestParam(required = false, defaultValue = "0")
            @ApiParam(value = "начало отсчёта страницы пользователей", defaultValue = "0") int start,
            @RequestParam(required = false, defaultValue = "20")
            @ApiParam(value = "конец отсчёта страницы пользователей", defaultValue = "20") int end
    ) {
        return ResponseEntity.ok(statisticService.getUserStatistic(PageRequest.of(start, end)));
    }
}
