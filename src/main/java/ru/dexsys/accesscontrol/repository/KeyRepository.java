package ru.dexsys.accesscontrol.repository;

import ru.dexsys.accesscontrol.domain.entity.Key;
import ru.dexsys.accesscontrol.domain.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface KeyRepository extends JpaRepository<Key, Integer> {
    @Query(value = "SELECT k FROM Key k WHERE k.room != null ORDER BY k.id")
    List<Key> findAllWhereRoomNotNull();

    @Modifying
    @Query(value = "UPDATE Key k SET k.room = ?1 WHERE k.id = ?2")
    @Transactional
    void updateRoom(Room room, int id);

    Page<Key> findAllByOrderById(Pageable pageable);
}
