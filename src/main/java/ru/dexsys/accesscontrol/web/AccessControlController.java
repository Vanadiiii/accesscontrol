package ru.dexsys.accesscontrol.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ru.dexsys.accesscontrol.service.AccessControlService;
import ru.dexsys.accesscontrol.domain.entity.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dexsys.accesscontrol.web.dto.EntranceType;

@Slf4j
@RestController
@RequestMapping(value = "/check")
@RequiredArgsConstructor
@Api("Контроллер доступа")
public class AccessControlController {
    private final AccessControlService accessControlService;

    @ApiOperation("Проверка возможности входа в комнату (или выхода из комнаты) по ключу")
    @GetMapping
    public ResponseEntity<Object> check(@RequestParam @ApiParam("номер ключа") int keyId,
                                        @RequestParam @ApiParam("номер комнаты") int roomId,
                                        @RequestParam @ApiParam("вход или выход") EntranceType entrance) {
        Result result = accessControlService.check(keyId, roomId, entrance.isValue());
        return ResponseEntity
                .status(result.getStatus())
                .body(result.getMessage());
    }
}
