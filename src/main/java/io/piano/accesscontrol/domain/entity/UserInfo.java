package io.piano.accesscontrol.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int id;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer roomId;
}
