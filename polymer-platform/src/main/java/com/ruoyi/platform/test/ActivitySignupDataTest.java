package com.ruoyi.platform.test;

import com.ruoyi.platform.domain.ActivityRegistration;
import com.ruoyi.platform.domain.ActivitySignup;
import com.ruoyi.platform.mapper.ActivityRegistrationDao;
import com.ruoyi.platform.mapper.ActivitySignupDao;
import com.ruoyi.platform.vo.ActivityListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 报名功能数据测试类
 * 使用 CommandLineRunner 在应用启动时自动执行测试
 * 
 * 使用方法：
 * 1. 启动 Spring Boot 应用
 * 2. 查看控制台输出，会看到报名数据的测试结果
 * 3. 测试完成后，如果想停止自动测试，可以将 @Component 注释掉
 */
@Component
public class ActivitySignupDataTest implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ActivitySignupDataTest.class);

    @Resource
    private ActivityRegistrationDao activityRegistrationDao;

    @Resource
    private ActivitySignupDao activitySignupDao;

    @Override
    public void run(String... args) throws Exception {
        log.info("\n");
        log.info("===========================================");
        log.info("开始测试：报名功能数据查询");
        log.info("===========================================");

        try {
            // 测试1：查询活动列表及报名情况
            testQueryActivityList();

            // 测试2：查询活动详情及报名人数
            testQueryActivityDetail();

        } catch (Exception e) {
            log.error("测试过程中出现异常", e);
        }

        log.info("===========================================");
        log.info("测试完成");
        log.info("===========================================\n");
    }

    /**
     * 测试1：查询活动列表及报名情况
     */
    private void testQueryActivityList() {
        log.info("\n【测试1】查询活动列表及报名情况");

        try {
            // 查询正常状态的活动（delStatus=1）
            List<ActivityListVO> activities = activityRegistrationDao.selectActivityList(1);

            if (activities == null) {
                log.error("❌ 查询结果为 null");
                log.error("可能原因：MyBatis Mapper XML 未正确加载");
                return;
            }

            log.info("✅ 查询成功！");
            log.info("找到 {} 个活动", activities.size());

            if (activities.isEmpty()) {
                log.warn("⚠️  数据库中没有活动数据");
                log.info("💡 建议：执行 activity_signup.sql 插入测试数据");
            } else {
                // 打印活动信息
                for (int i = 0; i < activities.size(); i++) {
                    ActivityListVO activity = activities.get(i);
                    log.info("\n  【活动 {}】", (i + 1));
                    log.info("    ID: {}", activity.getId());
                    log.info("    标题: {}", activity.getTitle());
                    log.info("    描述: {}", activity.getDescription());
                    log.info("    地点: {}", activity.getLocation());
                    log.info("    主办方: {}", activity.getOrganizer());
                    log.info("    联系电话: {}", activity.getContactPhone());
                    log.info("    状态: {} ({})", activity.getStatus(), getStatusText(activity.getStatus()));
                    log.info("    报名人数: {}", activity.getSignupCount());
                    log.info("    开始时间: {}", activity.getStartTime());
                    log.info("    结束时间: {}", activity.getEndTime());

                    if (activity.getCoverImage() != null && !activity.getCoverImage().isEmpty()) {
                        log.info("    封面图: {}", activity.getCoverImage());
                    }
                }
            }

        } catch (Exception e) {
            log.error("❌ 查询活动列表失败", e);
            printErrorHelp(e);
        }
    }

    /**
     * 测试2：查询活动详情及报名记录
     */
    private void testQueryActivityDetail() {
        log.info("\n【测试2】查询活动详情及报名记录");

        try {
            // 先查询一个活动
            List<ActivityListVO> activities = activityRegistrationDao.selectActivityList(1);

            if (activities == null || activities.isEmpty()) {
                log.warn("⚠️  没有可用的活动");
                log.info("请先确认数据库中有活动数据");
                return;
            }

            Long activityId = activities.get(0).getId();
            log.info("查询活动ID: {} 的详情", activityId);

            // 查询活动基本信息
            ActivityRegistration activity = activityRegistrationDao.selectByPrimaryKey(activityId);
            if (activity != null) {
                log.info("✅ 活动基本信息查询成功");
                log.info("  标题: {}", activity.getTitle());
                log.info("  描述: {}", activity.getDescription());
                log.info("  地点: {}", activity.getLocation());
                log.info("  主办方: {}", activity.getOrganizer());
                log.info("  联系电话: {}", activity.getContactPhone());
                log.info("  状态: {} ({})", activity.getStatus(), getStatusText(activity.getStatus()));
                log.info("  开始时间: {}", activity.getStartTime());
                log.info("  结束时间: {}", activity.getEndTime());
            } else {
                log.error("❌ 活动基本信息查询失败");
                return;
            }

            // 统计报名人数
            int signupCount = activitySignupDao.countByActivityId(activityId);
            log.info("✅ 报名人数统计查询成功");
            log.info("  当前报名人数: {}", signupCount);

            // 注意：由于报名记录涉及用户ID，这里只展示统计信息
            // 如果需要查看具体的报名记录，可以登录App端查看"我的报名记录"
            log.info("\n  💡 提示：查看具体报名记录请通过App端接口：");
            log.info("     GET /platform/activity/mySignup");

        } catch (Exception e) {
            log.error("❌ 查询活动详情失败", e);
            printErrorHelp(e);
        }
    }

    /**
     * 获取状态文本
     */
    private String getStatusText(Integer status) {
        if (status == null) return "未知";
        switch (status) {
            case 0:
                return "未开始";
            case 1:
                return "进行中";
            case 2:
                return "已结束";
            default:
                return "未知";
        }
    }

    /**
     * 打印错误帮助信息
     */
    private void printErrorHelp(Exception e) {
        String errorMsg = e.getMessage();
        if (errorMsg != null) {
            if (errorMsg.contains("Invalid bound statement")) {
                log.error("\n💡 错误原因: MyBatis 找不到 Mapper XML 文件");
                log.error("解决方法:");
                log.error("  1. 确认 ActivityRegistrationDao.xml 和 ActivitySignupDao.xml 文件存在");
                log.error("  2. 检查 application.yml 中 mapperLocations 配置");
                log.error("  3. 重新启动应用");
            } else if (errorMsg.contains("Table") || errorMsg.contains("table")) {
                log.error("\n💡 错误原因: 数据库表不存在");
                log.error("解决方法:");
                log.error("  执行 activity_signup.sql 创建活动相关表");
            } else if (errorMsg.contains("Connection") || errorMsg.contains("connection")) {
                log.error("\n💡 错误原因: 数据库连接失败");
                log.error("解决方法:");
                log.error("  1. 检查数据库是否启动");
                log.error("  2. 检查 application-prod.yml 中的数据库配置");
            }
        }
    }
}
