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
public class UserExitChecking implements IChecking {
    private final RoomsChecking roomsChecking;
    private final Predicate<User> CONDITION = user -> !user.isEntrance();

    @Override
    public IChecking next(User user) {
        return roomsChecking;
    }

    @Override
    public boolean hasNext(User user) {
        return CONDITION.test(user);
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
