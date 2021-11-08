package ru.dexsys.accesscontrol.domain;

import ru.dexsys.accesscontrol.domain.entity.Result;
import ru.dexsys.accesscontrol.domain.entity.User;


public interface IChecking {
    IChecking next(User user);

    boolean hasNext(User user);

    Result getResponse(User user);
}
