package io.piano.accesscontrol.service;

import io.piano.accesscontrol.domain.AccessControlLogic;
import io.piano.accesscontrol.domain.entity.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlService {
    private final AccessControlLogic accessControlLogic;

    public Result check(int keyId, int roomId, boolean entrance) {
        return accessControlLogic.check(keyId, roomId, entrance);
    }
}
