package ru.dexsys.accesscontrol.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private boolean entrance;
}
