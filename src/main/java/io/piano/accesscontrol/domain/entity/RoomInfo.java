package io.piano.accesscontrol.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.SwaggerDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfo {
    private int roomId;
    @ApiModelProperty(notes = "All users in the room")
    private List<Integer> userIds;
}
