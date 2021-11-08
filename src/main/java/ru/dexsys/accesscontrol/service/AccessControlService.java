package ru.dexsys.accesscontrol.service;

import ru.dexsys.accesscontrol.domain.AccessControlLogic;
import ru.dexsys.accesscontrol.domain.entity.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlService {
    private final AccessControlLogic accessControlLogic;

    public Result check(int keyId, int roomId, boolean entrance) {
        log.debug("user #{} tries to {} room #{}", keyId, entrance ? "enter into" : "leave", roomId);
        return accessControlLogic.check(keyId, roomId, entrance);
    }
}
