package ru.dexsys.accesscontrol.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Информация о комнате")
public class RoomInfo {
    private int roomId;
    @ApiModelProperty(notes = "Все пользователи, которые находятся в комнате")
    private List<Integer> userIds;
}
