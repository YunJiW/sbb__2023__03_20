package com.mysite.sbb.user;

import lombok.Getter;

//유저 권한 -> admin(관리자), user(그냥 유저)
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),USER("ROLE_USER");
    
    UserRole(String value){
        this.value = value;
    }
    
    private String value;
}
