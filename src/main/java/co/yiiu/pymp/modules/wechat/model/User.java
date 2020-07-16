package co.yiiu.pymp.modules.wechat.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String fromUserName;
    private Date createTime;
    private String status;

    // 用户的详细信息，json格式
    @Column(columnDefinition = "text")
    private String userInfo;

    // 最后更新userInfo时间
    private Date lastUpdateTime;

}
