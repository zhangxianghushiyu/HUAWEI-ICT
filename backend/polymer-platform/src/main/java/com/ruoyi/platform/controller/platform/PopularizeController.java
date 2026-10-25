package com.ruoyi.platform.controller.platform;

import com.ruoyi.common.utils.DatesUtil;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.domain.TApplyAgent;
import com.ruoyi.platform.domain.TUserRole;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.mapper.UserDao;
import com.ruoyi.platform.service.BillCashService;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.service.ITApplyAgentService;
import com.ruoyi.platform.service.ITUserRoleService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.BillCashListVO;
import com.ruoyi.platform.vo.BillGoldListVO;
import com.ruoyi.platform.vo.GainsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "我的推广API")
@RestController
@Slf4j
@RequestMapping("/platform/popularize")
public class PopularizeController {

    @Autowired
    private BillGoldService billGoldService;

    @Autowired
    private BillCashService billCashService;

    @Autowired
    private ITUserRoleService userRoleService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ITApplyAgentService applyAgentService;

    @ApiOperation(value = "收益信息")
    @GetMapping("/gainsInfo")
    public R<GainsVO> gainsInfo() {
        GainsVO gainsVO = new GainsVO();
        //获取我的收益信息
        List<BillGoldListVO> billList = billGoldService.getMyBillGoldListVO();

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        //收益
        billList = billList.stream().filter(data -> data.getScene() == 1).collect(Collectors.toList());
        //今日收益
        Optional<BigDecimal> dayReduce = billList.stream().filter(data -> data.getCreateTime().after(startDate) && data.getCreateTime().before(endDate))
                .map(BillGoldListVO::getMoney).reduce(BigDecimal::add);
        if (dayReduce.isPresent()) {
            int dayGains = dayReduce.get().setScale(2, RoundingMode.HALF_UP).intValue();
            gainsVO.setDayGains(dayGains);
        }
        //累计收益
        Optional<BigDecimal> totalReduce = billList.stream().map(BillGoldListVO::getMoney).reduce(BigDecimal::add);
        if (totalReduce.isPresent()) {
            int totalGains = totalReduce.get().setScale(2, RoundingMode.HALF_UP).intValue();
            gainsVO.setTotalGains(totalGains);
        }

        //获取用户信息
        User user = userDao.selectByPrimaryKey(JwtUtil.getRequestUserId(request));
        //判断用户身份
        if (Objects.nonNull(user)) {
            TUserRole tUserRole = userRoleService.selectTUserRoleById(user.getRoleId().longValue());
            //如果是团长
            if (tUserRole.getRoleType() == 2) {
                //获取所有下级用户
                gainsVO.setTeamNums(userDao.selectByParentId(user.getId()).size());
            }
        }
        return R.ok(gainsVO);
    }

    @ApiOperation("数据统计")
    @GetMapping("/queryGainsInfo")
    public R queryGainsInfo(){
        Date todayBegin = DatesUtil.getDayBegin();
        Date todayEnd = DatesUtil.getDayEnd();
        Date ydBegin = DatesUtil.getBeginDayOfYesterday();
        Date ydEnd = DatesUtil.getEndDayOfYesterDay();
        Map map = new HashMap();
        //总流水
        List<BillGoldListVO> billList = billGoldService.getMyBillGoldListVO();
        List<BillCashListVO> billCashList = billCashService.getMyBillCashListVO();
        //今日流水
        List<BillGoldListVO> todayBillList = billList.stream().filter(data -> data.getCreateTime().after(todayBegin) && data.getCreateTime().before(todayEnd)).collect(Collectors.toList());
        //今日收益
        BigDecimal todayIncome = todayBillList.stream().filter(data -> data.getScene() == 1).map(BillGoldListVO::getMoney).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        //个人收益
        BigDecimal personIncome = todayBillList.stream().filter(data -> data.getScene() == 1 && !data.getDescribe().equals("分销奖励")).map(BillGoldListVO::getMoney).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        //团队分佣
        BigDecimal teamIncome = todayBillList.stream().filter(data -> data.getScene() == 1 && data.getDescribe().equals("分销奖励")).map(BillGoldListVO::getMoney).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        //今日新增人数
        Integer todayJuniorCount = userDao.selectTodayJuniorCount(JwtUtil.getRequestUserId(request));
        //昨日新增人数
        Integer yesterdayJuniorCount = userDao.selectYesterdayJuniorCount(JwtUtil.getRequestUserId(request));
        //昨日收益
        BigDecimal yesterdayIncome = billList.stream().filter(data -> data.getCreateTime().after(ydBegin) && data.getCreateTime().before(ydEnd) && data.getScene() == 1).map(BillGoldListVO::getMoney).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        //累计收益
        BigDecimal cumulativeIncome = billList.stream().filter(data -> data.getScene() == 1).map(BillGoldListVO::getMoney).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        //已兑换
        BigDecimal alreadyRedeemed = billList.stream().filter(data -> data.getScene() == 2).map(BillGoldListVO::getMoney).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        //金币余额
        BigDecimal coinBalance = cumulativeIncome.subtract(alreadyRedeemed);
        //已提现现金
        BigDecimal withdrawalMoney = billCashList.stream().filter(data -> data.getScene() == 2 && (data.getStatus() == 1 || data.getStatus() == 3)).map(BillCashListVO::getMoney).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        //现金余额
        BigDecimal moneyBalance = billCashList.stream().filter(data -> data.getScene() == 1).map(BillCashListVO::getMoney).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).subtract(withdrawalMoney);
        map.put("todayIncome", todayIncome);
        map.put("personIncome", personIncome);
        map.put("teamIncome", teamIncome);
        map.put("todayJuniorCount", todayJuniorCount);
        map.put("yesterdayJuniorCount", yesterdayJuniorCount);
        map.put("yesterdayIncome", yesterdayIncome);
        map.put("cumulativeIncome", cumulativeIncome);
        map.put("alreadyRedeemed", alreadyRedeemed);
        map.put("coinBalance", coinBalance);
        map.put("withdrawalMoney", withdrawalMoney);
        map.put("moneyBalance", moneyBalance);
        return R.ok(map);
    }

    @ApiOperation("团队数据")
    @GetMapping("/queryTeamByUserId")
    public R queryTeamByUserId(@RequestParam(required = false) String userId) {
        User user = userDao.selectByPrimaryKey(JwtUtil.getRequestUserId(request));
        TUserRole tUserRole = userRoleService.selectTUserRoleById(user.getRoleId().longValue());
        Map map = new HashMap();
        List<User> aUser = new ArrayList<>();
        List<User> bUser = new ArrayList<>();
        if (tUserRole.getRoleType() == 1) {
            aUser = userDao.selectByParentId(user.getId());
            if(userId == null && !aUser.isEmpty()){
                bUser = userDao.selectByParentId(aUser.get(0).getId());
            }
        }else if(tUserRole.getRoleType() == 2){
            aUser.add(user);
            bUser = userDao.selectByParentId(user.getId());
        }
        map.put("aUser", aUser);
        map.put("bUser", bUser);
        return R.ok(map);
    }

    @RepeatSubmit(interval = 60000)
    @ApiOperation(value = "申请代理")
    @PostMapping("/createAgent")
    public R createAgent() {
        //获取用户信息
        User user = userDao.selectByPrimaryKey(JwtUtil.getRequestUserId(request));
        //获取用户身份
        TUserRole tUserRole = userRoleService.selectTUserRoleById(user.getRoleId().longValue());
        if (tUserRole.getRoleType() == 1 || tUserRole.getRoleType() == 2) return R.error("当前身份不能申请代理");
        //判断申请信息
        TApplyAgent query = new TApplyAgent();
        query.setUserId(user.getId().longValue()).setStatus(0L);
        List<TApplyAgent> tApplyAgents = applyAgentService.selectTApplyAgentList(query);
        if (!tApplyAgents.isEmpty()) return R.error("申请已提交，请勿重复申请！");
        applyAgentService.insertTApplyAgent(query);
        return R.ok();
    }
}
