package io.piano.accesscontrol.domain.checking;

import io.piano.accesscontrol.domain.IChecking;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHasGrantsChecking_4 implements IChecking {
    private final UserEnterChecking_5 userEnterCheck;
    private final Predicate<User> CONDITION = user -> user.getKey().getId() % user.getRoomId() == 0;

    @Override
    public IChecking next(User user) {
        if (CONDITION.test(user)) {
            return userEnterCheck;
        }
        return null;
    }

    @Override
    public boolean hasNext(User user) {
        return CONDITION.test(user);
    }

    @Override
    public Result getResponse(User user) {
        if (CONDITION.test(user)) {
            return null;
        }
        log.info("User #{} is not allowed to enter room #{}", user.getKey().getId(), user.getRoomId());
        return new Result(HttpStatus.FORBIDDEN, "You has no privileges to enter this room");
    }
}
