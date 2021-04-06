package io.piano.accesscontrol.domain;

import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;


public interface IChecking {
    IChecking next(User user);

    boolean hasNext(User user);

    Result getResponse(User user);
}
