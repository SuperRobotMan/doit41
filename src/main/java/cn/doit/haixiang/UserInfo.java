package cn.doit.haixiang;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class UserInfo {
    private String rowkey;
    private String name;
    private String age;
    private String gender;
}
