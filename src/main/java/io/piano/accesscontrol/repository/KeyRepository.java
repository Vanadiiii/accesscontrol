package io.piano.accesscontrol.repository;

import io.piano.accesscontrol.domain.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeyRepository extends JpaRepository<Key, Integer> {
    @Query(value = "SELECT k FROM Key k WHERE k.room != null")
    List<Key> findAllWhereRoomNotNull();
}
