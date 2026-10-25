package com.ruoyi.common.enums;




public enum DelStatusEnum {


    STATUS_0(0, "删除"), STATUS_1(1, "正常");

    private final Integer status;
    private final String info;

    DelStatusEnum(Integer status, String info)
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
