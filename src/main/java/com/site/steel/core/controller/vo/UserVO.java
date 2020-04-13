package com.site.steel.core.controller.vo;

import lombok.Data;

/**
 * @author :  yuxuenan
 * @Date : 2020年04月13日
 * @Description :
 */
@Data
public class UserVO {
    // 主键
    private Integer userId;
    // 用户名
    private String loginUserName;
    // 明文密码
    private String loginPassword;
    // 昵称
    private String nickName;
    // 手机号
    private String phone;
    //
    private Byte locked;

}
