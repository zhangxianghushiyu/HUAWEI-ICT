package com.ruoyi.platform.vo;

import com.ruoyi.common.annotation.Excel;

public class ClientConfigListVO {

    /** 主键 */
    private Long id;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    /** 副类型 */
    @Excel(name = "副类型")
    private Integer followType;

    /** 值 */
    @Excel(name = "值")
    private String value;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
