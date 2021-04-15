package io.piano.accesscontrol.domain;

import io.piano.accesscontrol.domain.entity.Key;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.RoomInfo;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.exception.NoSuchKeyException;
import io.piano.accesscontrol.exception.NoSuchRoomException;
import io.piano.accesscontrol.repository.KeyRepository;
import io.piano.accesscontrol.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<User> getUserStatistic() {
        return keyRepository.findAll()
                .stream()
                .map(key -> User.builder().key(key).build())
                .collect(Collectors.toList());
    }

    public List<RoomInfo> getRoomStatistic() {
        return keyRepository.findAllWhereRoomNotNull()
                .stream()
                .collect(Collectors.toMap(
                        key -> key.getRoom().getId(),
                        key -> List.of(key.getId()),
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
