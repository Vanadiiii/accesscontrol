package ru.dexsys.accesscontrol.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Информация о пользователе (ключе)")
public class UserInfo {
    private int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "Комната, в которой он находится", allowEmptyValue = true)
    private Integer roomId;
}
