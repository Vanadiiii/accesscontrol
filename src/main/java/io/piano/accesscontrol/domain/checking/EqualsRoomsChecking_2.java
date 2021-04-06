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
public class EqualsRoomsChecking_2 implements IChecking {
    private final UserExitChecking_3 userExitCheck;
    private final Predicate<User> CONDITION = user -> user.getRoomId() == user.getKey().getRoom().getId();

    @Override
    public IChecking next(User user) {
        return CONDITION.test(user) ? userExitCheck : null;
    }

    @Override
    public boolean hasNext(User user) {
        return CONDITION.test(user);
    }

    @Override
    public Result getResponse(User user) {
        if (CONDITION.negate().test(user)) {
            log.error("User #{} located in room #{} and try to enter in room #{}",
                    user.getKey().getId(), user.getKey().getRoom().getId(), user.getRoomId());
            return new Result(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can't enter into room #" + user.getRoomId() + " without living room #" + user.getKey().getRoom().getId()
            );
        }
        throw new RuntimeException("Proccess Exception"); // FIXME: 4/7/21
    }
}
