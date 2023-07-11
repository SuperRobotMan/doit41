package kunkun;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserInfo {
    private int id;
    private String name;
    private int age;
    private String gender;
}
