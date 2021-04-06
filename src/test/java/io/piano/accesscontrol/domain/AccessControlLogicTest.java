package io.piano.accesscontrol.domain;

import io.piano.accesscontrol.domain.checking.*;
import io.piano.accesscontrol.domain.entity.Key;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;
import io.piano.accesscontrol.repository.KeyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessControlLogicTest {
    @InjectMocks
    private AccessControlLogic accessControlLogic;
    @Mock
    private KeyRepository keyRepository;
    @Mock
    private UserIntoRoomChecking_1 userInRoomChecking;
    @Mock
    private EqualsRoomsChecking_2 equalsRoomsChecking;
    @Mock
    private UserExitChecking_3 userExitChecking;
    @Mock
    private UserEnterChecking_5 userEnterChecking;
    @Mock
    private UserHasGrantsChecking_4 userHasGrantsChecking;

    @Test
    @DisplayName("Check granted user can enter into room")
    public void test1() {
        int keyId = 1;
        int roomId = 1;
        Key key = new Key(keyId, null);
        User user = User.builder()
                .entrance(false)
                .key(key)
                .roomId(roomId)
                .build();
        when(userInRoomChecking.hasNext(eq(user)))
                .thenReturn(true);
        when(userInRoomChecking.next(eq(user)))
                .thenReturn(userHasGrantsChecking);

        when(userHasGrantsChecking.next(eq(user)))
                .thenReturn(userEnterChecking);
        when(userHasGrantsChecking.hasNext(eq(user)))
                .thenReturn(true);

        when(keyRepository.findById(eq(keyId)))
                .thenReturn(Optional.of(key));
        Result check = accessControlLogic.check(keyId, roomId, false);
        assertEquals(HttpStatus.OK, check.getStatus());
    }
}