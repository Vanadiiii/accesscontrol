package ru.dexsys.accesscontrol.domain.checking;

import ru.dexsys.accesscontrol.domain.IChecking;
import ru.dexsys.accesscontrol.domain.entity.Key;
import ru.dexsys.accesscontrol.domain.entity.Result;
import ru.dexsys.accesscontrol.domain.entity.Room;
import ru.dexsys.accesscontrol.domain.entity.User;
import ru.dexsys.accesscontrol.exception.CheckProcessException;
import ru.dexsys.accesscontrol.repository.KeyRepository;
import ru.dexsys.accesscontrol.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkedRoomsChecking implements IChecking {
    private final KeyRepository keyRepository;
    private final RoomRepository roomRepository;

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
        Key key = user.getKey();
        int keyId = key.getId();
        int currentRoomId = key.getRoom().getId();
        int destinationRoomId = user.getRoomId();
        List<Integer> linkedRooms = key.getRoom()
                .getNextRooms()
                .stream()
                .map(Room::getId)
                .collect(Collectors.toList());

        if (!linkedRooms.contains(destinationRoomId)) {
            log.error(
                    "User #{} from room #{} try to enter in room #{}, but this rooms have no links or the same",
                    keyId, currentRoomId, destinationRoomId
            );
            return new Result(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "You can't enter into room #" + user.getRoomId() + " without living room #" + user.getKey().getRoom().getId()
            );
        } else {
            keyRepository.updateRoom(
                    roomRepository.getOne(destinationRoomId),
                    keyId
            );

            log.info("User #{} go from room #{} to room #{}", keyId, currentRoomId, destinationRoomId);
            return new Result(
                    HttpStatus.OK,
                    "Welcome to the room #" + destinationRoomId + " from room #" + currentRoomId
            );
        }
    }
}
