package com.ruoyi.common.enums;




public enum UserStatusEnum {


    STATUS_0(0, "停用"), STATUS_1(1, "正常"), STATUS_2(2, "注销");

    private final Integer status;
    private final String info;

    UserStatusEnum(Integer status, String info)
    {
        this.status = status;
        this.info = info;
    }

    public Integer getStatus()
    {
        return status;
    }

    public String getInfo()
    {
        return info;
    }
}
