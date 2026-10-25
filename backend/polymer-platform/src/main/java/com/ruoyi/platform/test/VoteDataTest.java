package com.ruoyi.platform.test;

import com.ruoyi.platform.domain.VoteActivity;
import com.ruoyi.platform.domain.VoteOption;
import com.ruoyi.platform.mapper.VoteActivityDao;
import com.ruoyi.platform.mapper.VoteOptionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 投票功能数据测试类
 * 使用 CommandLineRunner 在应用启动时自动执行测试
 * 
 * 使用方法：
 * 1. 启动 Spring Boot 应用
 * 2. 查看控制台输出，会看到投票数据的测试结果
 * 3. 测试完成后，如果想停止自动测试，可以将 @Component 注释掉
 */
@Component
public class VoteDataTest implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(VoteDataTest.class);

    @Resource
    private VoteActivityDao voteActivityDao;

    @Resource
    private VoteOptionDao voteOptionDao;

    @Override
    public void run(String... args) throws Exception {
        log.info("\n");
        log.info("===========================================");
        log.info("开始测试：投票功能数据查询");
        log.info("===========================================");

        try {
            // 测试1：查询投票活动列表
            testQueryVoteList();

            // 测试2：查询投票活动详情
            testQueryVoteDetail();

        } catch (Exception e) {
            log.error("测试过程中出现异常", e);
        }

        log.info("===========================================");
        log.info("测试完成");
        log.info("===========================================\n");
    }

    /**
     * 测试1：查询投票活动列表
     */
    private void testQueryVoteList() {
        log.info("\n【测试1】查询投票活动列表");

        try {
            // 查询进行中的投票活动（userId传1即可，实际不使用）
            List<VoteActivity> activities = voteActivityDao.selectActiveList(1);

            if (activities == null) {
                log.error("❌ 查询结果为 null");
                log.error("可能原因：MyBatis Mapper XML 未正确加载");
                return;
            }

            log.info("✅ 查询成功！");
            log.info("找到 {} 个投票活动", activities.size());

            if (activities.isEmpty()) {
                log.warn("⚠️  数据库中没有投票活动数据");
                log.info("💡 建议：执行 vote_init.sql 插入测试数据");
            } else {
                // 打印活动信息
                for (int i = 0; i < activities.size(); i++) {
                    VoteActivity activity = activities.get(i);
                    log.info("\n  【活动 {}】", (i + 1));
                    log.info("    ID: {}", activity.getId());
                    log.info("    标题: {}", activity.getTitle());
                    log.info("    描述: {}", activity.getDescription());
                    
                    if (activity.getCoverImage() != null && !activity.getCoverImage().isEmpty()) {
                        log.info("    封面图: {}", activity.getCoverImage());
                    } else {
                        log.info("    封面图: (无)");
                    }
                    
                    log.info("    状态: {} ({})", activity.getStatus(), getStatusText(activity.getStatus()));
                    log.info("    是否多选: {}", activity.getIsMultiple() != null && activity.getIsMultiple() == 1 ? "是" : "否");
                    if (activity.getMaxOptions() != null) {
                        log.info("    最大可选数: {}", activity.getMaxOptions());
                    }
                    log.info("    总投票人数: {}", activity.getTotalVoters());
                    log.info("    总投票数: {}", activity.getTotalVotes());
                    log.info("    开始时间: {}", activity.getStartTime());
                    log.info("    结束时间: {}", activity.getEndTime());

                    // 查询该活动的选项
                    List<VoteOption> options = voteOptionDao.selectByActivityId(activity.getId());
                    if (options != null && !options.isEmpty()) {
                        log.info("    选项列表 (共{}个):", options.size());
                        for (VoteOption option : options) {
                            log.info("      - {} (票数: {})", option.getOptionContent(), option.getVoteCount());
                        }
                    } else {
                        log.info("    选项列表: 无");
                    }
                }
            }

        } catch (Exception e) {
            log.error("❌ 查询投票活动列表失败", e);
            printErrorHelp(e);
        }
    }

    /**
     * 测试2：查询投票活动详情
     */
    private void testQueryVoteDetail() {
        log.info("\n【测试2】查询投票活动详情");

        try {
            // 先查询一个活动
            List<VoteActivity> activities = voteActivityDao.selectActiveList(1);

            if (activities == null || activities.isEmpty()) {
                log.warn("⚠️  没有可用的投票活动");
                log.info("请先确认数据库中有投票活动数据");
                return;
            }

            Integer activityId = activities.get(0).getId();
            log.info("查询活动ID: {} 的详情", activityId);

            // 查询活动基本信息
            VoteActivity activity = voteActivityDao.selectByPrimaryKey(activityId);
            if (activity != null) {
                log.info("✅ 活动基本信息查询成功");
                log.info("  标题: {}", activity.getTitle());
                log.info("  描述: {}", activity.getDescription());
                
                if (activity.getCoverImage() != null && !activity.getCoverImage().isEmpty()) {
                    log.info("  封面图: {}", activity.getCoverImage());
                } else {
                    log.info("  封面图: (无)");
                }
            } else {
                log.error("❌ 活动基本信息查询失败");
                return;
            }

            // 查询选项列表
            List<VoteOption> options = voteOptionDao.selectByActivityId(activityId);
            log.info("✅ 选项列表查询成功");
            log.info("共 {} 个选项", options != null ? options.size() : 0);

            if (options != null && !options.isEmpty()) {
                for (int i = 0; i < options.size(); i++) {
                    VoteOption option = options.get(i);
                    log.info("  {}. {} | 票数: {} | 排序: {}",
                            (i + 1), option.getOptionContent(),
                            option.getVoteCount(), option.getSort());
                }
            }

        } catch (Exception e) {
            log.error("❌ 查询投票活动详情失败", e);
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
                log.error("  1. 确认 VoteActivityDao.xml 文件存在");
                log.error("  2. 检查 application.yml 中 mapperLocations 配置");
                log.error("  3. 重新启动应用");
            } else if (errorMsg.contains("Table") || errorMsg.contains("table")) {
                log.error("\n💡 错误原因: 数据库表不存在");
                log.error("解决方法:");
                log.error("  执行 vote_init.sql 创建投票相关表");
            } else if (errorMsg.contains("Connection") || errorMsg.contains("connection")) {
                log.error("\n💡 错误原因: 数据库连接失败");
                log.error("解决方法:");
                log.error("  1. 检查数据库是否启动");
                log.error("  2. 检查 application-prod.yml 中的数据库配置");
            }
        }
    }
}
