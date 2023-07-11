package kunkun;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventInfo {
    private int guid;
    private String eventId;
    private long timestamp;
    private int isNew;
}
