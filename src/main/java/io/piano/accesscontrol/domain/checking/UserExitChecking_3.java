package io.piano.accesscontrol.domain.checking;

import io.piano.accesscontrol.domain.IChecking;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserExitChecking_3 implements IChecking {
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
            log.error("User #{} located and try to enter in room #{} at one time", keyId, roomId);
            return new Result(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can't enter into room #" + roomId + " cause you're inside it already"
            );
        } else {
            user.getKey().setRoom(null);
            keyRepository.deleteById(keyId);
            keyRepository.saveAndFlush(user.getKey());
            log.info("User #{} leave room #{}", keyId, roomId);
            return new Result(HttpStatus.OK, "Goodbye!");
        }
    }
}
