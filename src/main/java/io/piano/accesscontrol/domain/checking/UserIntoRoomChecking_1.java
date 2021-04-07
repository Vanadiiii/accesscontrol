package io.piano.accesscontrol.domain.checking;

import io.piano.accesscontrol.domain.IChecking;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.exception.CheckProcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("startChecking")
@RequiredArgsConstructor
public class UserIntoRoomChecking_1 implements IChecking {
    private final UserExitChecking userExitChecking;
    private final UserEnterChecking userEnterChecking;

    @Override
    public IChecking next(User user) {
        boolean isUserInRoom = user.getKey().getRoom() != null;
        if (isUserInRoom) {
            return userExitChecking;
        }
        return userEnterChecking;
    }

    @Override
    public boolean hasNext(User user) {
        return true;
    }

    @Override
    public Result getResponse(User user) {
        log.error("Unprocessed method 'getResponse()' in class - " + this.getClass());
        throw CheckProcessException.init(user.getKey().getId());
    }
}
