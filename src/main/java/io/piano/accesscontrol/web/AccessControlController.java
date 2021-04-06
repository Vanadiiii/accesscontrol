package io.piano.accesscontrol.web;

import io.piano.accesscontrol.service.AccessControlService;
import io.piano.accesscontrol.domain.entity.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/check")
@RequiredArgsConstructor
public class AccessControlController {
    private final AccessControlService accessControlService;

    @GetMapping()
    public ResponseEntity<Object> check(@RequestParam int keyId, @RequestParam int roomId, @RequestParam boolean entrance) {
        Result result = accessControlService.check(keyId, roomId, entrance);
        return ResponseEntity
                .status(result.getStatus())
                .body(result.getMessage());
    }
}
