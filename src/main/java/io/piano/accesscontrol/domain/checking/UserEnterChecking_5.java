package io.piano.accesscontrol.domain.checking;

import io.piano.accesscontrol.domain.IChecking;
import io.piano.accesscontrol.domain.entity.Key;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.Room;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEnterChecking_5 implements IChecking {
    private final KeyRepository keyRepository;

    @Override
    public IChecking next(User user) {
        return null;
    }

    @Override
    public boolean hasNext(User user) {
        return false;
    }

    @Override
    public Result getResponse(User user) {
        int keyId = user.getKey().getId();
        int roomId = user.getRoomId();

        if (user.isEntrance()) {
            Key updatedKey = new Key(keyId, new Room(roomId));
            keyRepository.deleteById(keyId);
            keyRepository.saveAndFlush(updatedKey);
            log.info("User #{} enter to room #{}", keyId, roomId);
            return new Result(HttpStatus.OK, "You are welcome!");
        } else {
            log.error("User #{} is outside and try to leave room #{}", keyId, roomId);
            return new Result(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can't leave room #" + roomId + " cause you're already outside"
            );
        }
    }
}
