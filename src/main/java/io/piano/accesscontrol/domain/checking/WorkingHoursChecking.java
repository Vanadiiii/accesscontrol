package io.piano.accesscontrol.domain.checking;

import io.piano.accesscontrol.domain.IChecking;
import io.piano.accesscontrol.domain.config.WorkingHoursConfiguration;
import io.piano.accesscontrol.domain.entity.Result;
import io.piano.accesscontrol.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkingHoursChecking implements IChecking {
    private final UserHasGrantsChecking userHasGrantsChecking;
    private final WorkingHoursConfiguration workingHours;

    @Override
    public IChecking next(User user) {
        return userHasGrantsChecking;
    }

    @Override
    public boolean hasNext(User user) {
        return check(LocalTime.now().getHour());
    }

    @Override
    public Result getResponse(User user) {
        log.warn("User #" + user.getKey() + " try to work at " + LocalDateTime.now());

        boolean isItTooEarly = LocalTime.now().getHour() < workingHours.getStartTime();
        String message = isItTooEarly
                ? "Too early to work now. Please come back later, in " + workingHours.getStartTime() + ".00"
                : "Too late to work now. Please come back tomorrow, in " + workingHours.getStartTime() + ".00";
        return new Result(HttpStatus.FORBIDDEN, message);
    }

    private boolean check(int currentHour) {
        return currentHour < workingHours.getEndTime() && currentHour >= workingHours.getStartTime();
    }
}
