package io.piano.accesscontrol.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    private int id;

    @ManyToMany(mappedBy = "nextRoomFor", fetch = FetchType.LAZY)
    private List<Room> nextRooms;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_room_link",
            joinColumns = @JoinColumn(name = "next_room_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> nextRoomFor;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", nextRooms=" + nextRooms.stream().map(Room::getId).collect(Collectors.toList()) +
                ", nextRoomFor=" + nextRoomFor.stream().map(Room::getId).collect(Collectors.toList()) +
                '}';
    }
}
