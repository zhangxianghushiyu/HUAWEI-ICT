package com.ruoyi.platform.controller.platform;

import com.ruoyi.platform.dto.FeedbackDTO;
import com.ruoyi.platform.service.FeedbackService;
import com.ruoyi.platform.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Api(tags = "帮助反馈API")
@RestController
@Slf4j
@RequestMapping("/platform/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation(value = "提交反馈")
    @PostMapping("/add")
    public R<Void> add(@Valid @RequestBody FeedbackDTO dto) {
        feedbackService.addFeedback(dto);
        return R.ok();
    }
}
