package ru.dexsys.accesscontrol.domain.checking;

import ru.dexsys.accesscontrol.domain.IChecking;
import ru.dexsys.accesscontrol.domain.entity.Result;
import ru.dexsys.accesscontrol.domain.entity.User;
import ru.dexsys.accesscontrol.exception.CheckProcessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("startChecking")
@RequiredArgsConstructor
public class UserIntoRoomChecking implements IChecking {
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
