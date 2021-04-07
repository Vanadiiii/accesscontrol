package io.piano.accesscontrol.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Key {
    @Id
    private int id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "key_room_link",
            joinColumns = @JoinColumn(name = "key_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Room room;
}
