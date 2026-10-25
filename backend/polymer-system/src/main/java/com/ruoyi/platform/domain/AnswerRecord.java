package com.ruoyi.platform.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 答题记录对象 t_answer_record
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public class AnswerRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Integer userId;

    /** 关卡 */
    @Excel(name = "关卡")
    private Long barrier;

    /** 歌名 */
    @Excel(name = "歌名")
    private String name;

    /** 答案 */
    @Excel(name = "答案")
    private String answer;

    /** 错误 */
    @Excel(name = "错误")
    private String wrong;

    /** 歌曲地址 */
    @Excel(name = "歌曲地址")
    private String url;

    /** 回答 */
    @Excel(name = "回答")
    private String write;

    /** 结果 */
    @Excel(name = "结果")
    private Integer answerResult;


    private String nickName;

    private String avatar;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }
    public void setBarrier(Long barrier) 
    {
        this.barrier = barrier;
    }

    public Long getBarrier() 
    {
        return barrier;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAnswer(String answer) 
    {
        this.answer = answer;
    }

    public String getAnswer() 
    {
        return answer;
    }
    public void setWrong(String wrong) 
    {
        this.wrong = wrong;
    }

    public String getWrong() 
    {
        return wrong;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setWrite(String write) 
    {
        this.write = write;
    }

    public String getWrite() 
    {
        return write;
    }
    public void setAnswerResult(Integer answerResult) 
    {
        this.answerResult = answerResult;
    }

    public Integer getAnswerResult() 
    {
        return answerResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("barrier", getBarrier())
            .append("name", getName())
            .append("answer", getAnswer())
            .append("wrong", getWrong())
            .append("url", getUrl())
            .append("write", getWrite())
            .append("answerResult", getAnswerResult())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
