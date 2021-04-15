package io.piano.accesscontrol.service;

import io.piano.accesscontrol.domain.AccessControlLogic;
import io.piano.accesscontrol.domain.entity.RoomInfo;
import io.piano.accesscontrol.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlStatisticService {
    private final AccessControlLogic accessControlLogic;

    public List<RoomInfo> getRoomsStatistic() {
        return accessControlLogic.getRoomStatistic();
    }

    public List<User> getUserStatistic() {
        return accessControlLogic.getUserStatistic();
    }
}
