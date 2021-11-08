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
public class UserExitChecking implements IChecking {// TODO: 4/16/21 rename!
    private final ThisRoomsChecking thisRoomsChecking;
    private final LinkedRoomsChecking linkedRoomsChecking;
    private final Predicate<User> CONDITION = user -> !user.isEntrance();

    @Override
    public IChecking next(User user) {
        if (CONDITION.test(user)) {
            return thisRoomsChecking;
        } else {
            return linkedRoomsChecking;
        }
    }

    @Override
    public boolean hasNext(User user) {
        return true;
    }

    @Override
    public Result getResponse(User user) {
        int keyId = user.getKey().getId();
        int destinationRoomId = user.getRoomId();
        int presentRoomId = user.getKey().getRoom().getId();

        log.error("User #{} can't enter into room #{}, cause he's in room #{}", keyId, destinationRoomId, presentRoomId);
        return new Result(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "You can't enter into room #" + destinationRoomId + " cause you're inside in room #" + presentRoomId
        );
    }
}
