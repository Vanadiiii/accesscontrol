package ru.dexsys.accesscontrol.domain.checking;

import ru.dexsys.accesscontrol.domain.IChecking;
import ru.dexsys.accesscontrol.domain.entity.Result;
import ru.dexsys.accesscontrol.domain.entity.User;
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
        return workingHoursChecking;
    }

    @Override
    public boolean hasNext(User user) {
        return CONDITION.test(user);
    }

    @Override
    public Result getResponse(User user) {
        int keyId = user.getKey().getId();
        int roomId = user.getRoomId();

        log.error("User #{} is outside and try to leave room #{}", keyId, roomId);
        return new Result(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "You can't leave room #" + roomId + " cause you're already outside"
        );
    }
}
