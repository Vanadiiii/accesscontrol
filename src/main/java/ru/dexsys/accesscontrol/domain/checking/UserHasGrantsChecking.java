package ru.dexsys.accesscontrol.domain.checking;

import ru.dexsys.accesscontrol.domain.IChecking;
import ru.dexsys.accesscontrol.domain.entity.Key;
import ru.dexsys.accesscontrol.domain.entity.Result;
import ru.dexsys.accesscontrol.domain.entity.Room;
import ru.dexsys.accesscontrol.domain.entity.User;
import ru.dexsys.accesscontrol.exception.CheckProcessException;
import ru.dexsys.accesscontrol.domain.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHasGrantsChecking implements IChecking {
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

        if (user.getKey().getId() % user.getRoomId() == 0) {
            Key updatedKey = new Key(keyId, Room.builder().id(roomId).build());
            keyRepository.deleteById(keyId);
            keyRepository.saveAndFlush(updatedKey);
            log.info("User #{} enter to room #{}", keyId, roomId);
            return new Result(HttpStatus.OK, "You are welcome!");
        } else {
            log.info("User #{} is not allowed to enter room #{}", keyId, roomId);
            return new Result(HttpStatus.FORBIDDEN, "You has no privileges to enter this room");
        }
    }
}
