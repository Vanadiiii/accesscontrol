package io.piano.accesscontrol.domain.checking;

import io.piano.accesscontrol.domain.IChecking;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.exception.CheckProcessException;
import io.piano.accesscontrol.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomsChecking implements IChecking {
    private final KeyRepository keyRepository;

    @Override
    public IChecking next(User user) {
        log.error("Unprocessed method 'next()' in class - " + this.getClass());
        throw CheckProcessException.init(user.getKey().getId());
    }

    @Override
    public boolean hasNext(User user) {
        return false;
    }

    @Override
    public Result getResponse(User user) {
        int keyId = user.getKey().getId();
        int roomId = user.getRoomId();

        System.out.println(user);

        if (user.getRoomId() == user.getKey().getRoom().getId()) {
            user.getKey().setRoom(null);
            keyRepository.deleteById(keyId);
            keyRepository.saveAndFlush(user.getKey());
            log.info("User #{} leave room #{}", keyId, roomId);
            return new Result(HttpStatus.OK, "Goodbye!");
        } else {
            log.error("User #{} located in room #{} and try to enter in room #{}",
                    user.getKey().getId(), user.getKey().getRoom().getId(), user.getRoomId());
            return new Result(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can't enter into room #" + user.getRoomId() + " without living room #" + user.getKey().getRoom().getId()
            );
        }
    }
}
