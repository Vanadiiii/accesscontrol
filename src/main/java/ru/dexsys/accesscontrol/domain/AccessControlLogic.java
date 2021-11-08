package ru.dexsys.accesscontrol.domain;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.dexsys.accesscontrol.domain.entity.*;
import ru.dexsys.accesscontrol.exception.NoSuchKeyException;
import ru.dexsys.accesscontrol.exception.NoSuchRoomException;
import ru.dexsys.accesscontrol.repository.KeyRepository;
import ru.dexsys.accesscontrol.repository.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlLogic {
    private final KeyRepository keyRepository;
    private final RoomRepository roomRepository;
    @Qualifier("startChecking")
    private final IChecking startChecking;

    public Result check(int keyId, int roomId, boolean entrance) {
        Key key = keyRepository.findById(keyId)
                .orElseThrow(() -> NoSuchKeyException.init(keyId));
        roomRepository.findById(roomId)
                .orElseThrow(() -> NoSuchRoomException.init(roomId));
        User user = User
                .builder()
                .key(key)
                .roomId(roomId)
                .entrance(entrance)
                .build();

        IChecking checking = startChecking;
        while (checking.hasNext(user)) {
            checking = checking.next(user);
        }
        return checking.getResponse(user);
    }

    public List<UserInfo> getUserStatistic(Pageable pageable) {
        return keyRepository.findAllByOrderById(pageable)
                .stream()
                .map(key -> UserInfo.builder()
                        .id(key.getId())
                        .roomId(
                                Optional.ofNullable(key.getRoom())
                                        .map(Room::getId)
                                        .orElse(null)
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<RoomInfo> getRoomStatistic() {
        return keyRepository.findAllWhereRoomNotNull()
                .stream()
                .collect(Collectors.toMap(
                        key -> key.getRoom().getId(),
                        key -> Lists.newArrayList(key.getId()),
                        (keys, otherKeys) -> {
                            keys.addAll(otherKeys);
                            return keys;
                        }
                ))
                .entrySet()
                .stream()
                .map(entry -> RoomInfo.builder()
                        .roomId(entry.getKey())
                        .userIds(entry.getValue())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
