package io.piano.accesscontrol.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Key key;
    private int roomId;
    /**
     * note:  <br>true - request to enter into room
     */
    private boolean entrance;
}
