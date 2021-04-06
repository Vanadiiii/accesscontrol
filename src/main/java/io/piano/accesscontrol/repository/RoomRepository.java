package io.piano.accesscontrol.repository;

import io.piano.accesscontrol.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
