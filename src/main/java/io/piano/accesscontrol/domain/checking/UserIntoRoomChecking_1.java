package io.piano.accesscontrol.domain.checking;

import io.piano.accesscontrol.domain.IChecking;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("startChecking")
@RequiredArgsConstructor
public class UserIntoRoomChecking_1 implements IChecking {
    private final EqualsRoomsChecking_2 sameRoomsCheck;
    private final UserHasGrantsChecking_4 userHasGrantsCheck;

    @Override
    public IChecking next(User user) {
        if (user.getKey().getRoom() != null) {
            return sameRoomsCheck;
        }
        return userHasGrantsCheck;
    }

    @Override
    public boolean hasNext(User user) {
        return true;
    }

    @Override
    public Result getResponse(User user) {
        return null;
    }
}
