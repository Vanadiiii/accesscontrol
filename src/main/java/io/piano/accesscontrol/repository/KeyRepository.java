package io.piano.accesscontrol.repository;

import io.piano.accesscontrol.domain.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<Key, Integer> {
}
