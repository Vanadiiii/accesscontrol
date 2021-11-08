package ru.dexsys.accesscontrol.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntranceType {
    @ApiModelProperty("Вход")
    ENTRANCE(true),
    @ApiModelProperty("Выход")
    EXIT(false);

    private final boolean value;
}
