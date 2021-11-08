package ru.dexsys.accesscontrol.domain.checking;

import ru.dexsys.accesscontrol.domain.IChecking;
import ru.dexsys.accesscontrol.domain.entity.Result;
import ru.dexsys.accesscontrol.domain.entity.User;
import ru.dexsys.accesscontrol.exception.CheckProcessException;
import ru.dexsys.accesscontrol.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThisRoomsChecking implements IChecking {
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

        if (user.getRoomId() == user.getKey().getRoom().getId()) {
            user.getKey().setRoom(null);
            keyRepository.deleteById(keyId);
            keyRepository.saveAndFlush(user.getKey());
            log.info("User #{} leave room #{}", keyId, roomId);
            return new Result(HttpStatus.OK, "Goodbye!");
        } else {
            log.error("User #{} located in room #{} and try to enter in room #{}",
                    user.getKey().getId(), user.getKey().getRoom().getId(), user.getRoomId());
            return new Result(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can't enter into room #" + user.getRoomId() + " without living room #" + user.getKey().getRoom().getId()
            );
        }
    }
}
