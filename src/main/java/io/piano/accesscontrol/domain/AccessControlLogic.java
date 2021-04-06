package io.piano.accesscontrol.domain;

import io.piano.accesscontrol.domain.entity.Key;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.Room;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.exception.NoSuchKeyException;
import io.piano.accesscontrol.repository.KeyRepository;
import io.piano.accesscontrol.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlLogic {
    private final KeyRepository keyRepository;
    private final RoomRepository roomRepository;
    @Qualifier("startChecking")
    private final IChecking startChecking;

    @PostConstruct // TODO: 4/7/21 remove!!
    public void init() {
        Stream.iterate(1, i -> i++)
                .limit(1000)
                .map(i -> new Key(i, null))
                .forEach(keyRepository::save);
        Stream.iterate(1, i -> i++)
                .limit(5)
                .map(Room::new)
                .forEach(roomRepository::save);
    }

    public Result check(int keyId, int roomId, boolean entrance) {
        Key key = keyRepository.findById(keyId)
                .orElseThrow(() -> NoSuchKeyException.init(keyId));
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
}
