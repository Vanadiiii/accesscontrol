package ru.dexsys.accesscontrol.domain.repository;

import ru.dexsys.accesscontrol.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
