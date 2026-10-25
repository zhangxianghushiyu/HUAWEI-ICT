package com.ruoyi.common.enums;




public enum BillCashStatusEnum {


    STATUS_0(0, "审核中"), STATUS_1(1, "已打款"), STATUS_2(2, "已驳回"),
    STATUS_3(3, "已通过");

    private final Integer status;
    private final String info;

    BillCashStatusEnum(Integer status, String info)
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
