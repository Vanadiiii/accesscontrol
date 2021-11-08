package ru.dexsys.accesscontrol.service;

import ru.dexsys.accesscontrol.domain.AccessControlLogic;
import ru.dexsys.accesscontrol.domain.entity.RoomInfo;
import ru.dexsys.accesscontrol.domain.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    public List<UserInfo> getUserStatistic(Pageable pageable) {
        return accessControlLogic.getUserStatistic(pageable);
    }
}
