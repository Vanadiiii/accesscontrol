package io.piano.accesscontrol.domain.checking;

import io.piano.accesscontrol.domain.IChecking;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.exception.CheckProcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEnterChecking implements IChecking {
    private final WorkingHoursChecking workingHoursChecking;
    private final Predicate<User> CONDITION = User::isEntrance;

    @Override
    public IChecking next(User user) {
        if (CONDITION.test(user)) {
            return workingHoursChecking;
        }
        log.error("Unprocessed method 'next()' in class - " + this.getClass());
        throw CheckProcessException.init(user.getKey().getId());
    }

    @Override
    public boolean hasNext(User user) {
        return CONDITION.test(user);
    }

    @Override
    public Result getResponse(User user) {
        int keyId = user.getKey().getId();
        int roomId = user.getRoomId();

        if (!CONDITION.test(user)) {
            log.error("User #{} is outside and try to leave room #{}", keyId, roomId);
            return new Result(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can't leave room #" + roomId + " cause you're already outside"
            );
        }
        log.error("Unprocessed method 'getResponse()' in class - " + this.getClass());
        throw CheckProcessException.init(keyId);
    }
}
