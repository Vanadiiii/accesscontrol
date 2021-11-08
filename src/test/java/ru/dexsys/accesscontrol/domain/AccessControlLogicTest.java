package ru.dexsys.accesscontrol.domain;

import ru.dexsys.accesscontrol.domain.entity.Key;
import ru.dexsys.accesscontrol.domain.entity.Room;
import ru.dexsys.accesscontrol.domain.entity.RoomInfo;
import ru.dexsys.accesscontrol.domain.entity.UserInfo;
import ru.dexsys.accesscontrol.repository.KeyRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessControlLogicTest {
    @InjectMocks
    private AccessControlLogic accessControlLogic;
    @Mock
    private KeyRepository keyRepository;

    @Test
    void getUserStatisticTest() {
        List<Key> keyList = Lists.newArrayList(
                new Key(1, new Room(1, null, null)),
                new Key(2, new Room(1, null, null)),
                new Key(3, new Room(3, null, null)),
                new Key(6, new Room(3, null, null))
        );
        List<UserInfo> userInfoList = Lists.newArrayList(
                new UserInfo(1, 1),
                new UserInfo(2, 1),
                new UserInfo(3, 3),
                new UserInfo(6, 3)

        );
        Pageable pageable = PageRequest.of(0, 20);
        when(keyRepository.findAllByOrderById(eq(pageable)))
                .thenReturn(new PageImpl<>(keyList));

        assertEquals(userInfoList, accessControlLogic.getUserStatistic(PageRequest.of(0, 20)));
    }

    @Test
    void getRoomStatisticTest() {
        List<Key> keyList = Lists.newArrayList(
                new Key(1, new Room(1, null, null)),
                new Key(2, new Room(1, null, null)),
                new Key(3, new Room(3, null, null)),
                new Key(6, new Room(3, null, null))
        );

        List<RoomInfo> roomInfoList = Lists.newArrayList(
                new RoomInfo(1, List.of(1, 2)),
                new RoomInfo(3, List.of(3, 6))
        );
        when(keyRepository.findAllWhereRoomNotNull())
                .thenReturn(keyList);

        assertEquals(roomInfoList, accessControlLogic.getRoomStatistic());
    }
}