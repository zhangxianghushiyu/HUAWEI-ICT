<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="场景" prop="scene">-->
<!--        <el-input-->
<!--          v-model="queryParams.scene"-->
<!--          placeholder="请输入场景"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>   -->
      <el-form-item label="广告平台" prop="networkFirmId">
        <el-select
          v-model="queryParams.networkFirmId"
          placeholder="请选择广告平台"
          clearable
          size="small"
        >
          <el-option label="Meta(Facebook)" :value="1"/>
          <el-option label="Admob" :value="2"/>
          <el-option label="Inmobi" :value="3"/>
          <el-option label="Applovin" :value="5"/>
          <el-option label="Mintegral" :value="6"/>
          <el-option label="腾讯广告" :value="8"/>
          <el-option label="Chartboost" :value="9"/>
          <el-option label="ironSource" :value="11"/>
          <el-option label="UnityAds" :value="12"/>
          <el-option label="Liftoff(Vungle)" :value="13"/>
          <el-option label="穿山甲（CSJ）" :value="15"/>
          <el-option label="百度联盟" :value="22"/>
          <el-option label="Nend" :value="23"/>
          <el-option label="Maio" :value="24"/>
          <el-option label="Start.io" :value="25"/>
          <el-option label="快手" :value="28"/>
          <el-option label="Sigmob" :value="29"/>
          <el-option label="VK（myTarget）" :value="32"/>
          <el-option label="Google Ad Manager" :value="33"/>
          <el-option label="Yandex" :value="34"/>
          <el-option label="Ogury" :value="36"/>
          <el-option label="Digital Turbine(Fyber)" :value="37"/>
          <el-option label="华为广告" :value="39"/>
          <el-option label="Kidoz" :value="45"/>
          <el-option label="A4G(Admob)" :value="48"/>
          <el-option label="米盟（Mi Uion）" :value="49"/>
          <el-option label="Pangle" :value="50"/>
          <el-option label="ReklamUp" :value="57"/>
          <el-option label="Verve Group" :value="58"/>
          <el-option label="Bigo" :value="59"/>
          <el-option label="Bidmachine" :value="65"/>
          <el-option label="TaKu" :value="66"/>
          <el-option label="TapTap" :value="69"/>
          <el-option label="京媒平台" :value="72"/>
          <el-option label="趣盟" :value="74"/>
          <el-option label="Amazon Publisher" :value="75"/>
          <el-option label="VIVO 广告联盟" :value="79"/>
          <el-option label="OPPO 广告联盟" :value="80"/>
          <el-option label="小米 Columbus" :value="81"/>
          <el-option label="阿里妈妈 Tanx" :value="82"/>
          <el-option label="TaurusX(Webeye)" :value="83"/>
          <el-option label="Smaato" :value="84"/>
          <el-option label="美数AdMateX" :value="93"/>
          <el-option label="爱奇艺联盟" :value="94"/>
        </el-select>
      </el-form-item>
      <el-form-item label="场景" prop="scene">
        <el-select
          v-model="queryParams.scene"
          placeholder="请选择场景"
          clearable
          size="small"
        >
          <el-option label="解锁短剧" :value="1"/>
          <el-option label="通关奖励" :value="2"/>
          <el-option label="幸运奖励" :value="3"/>
          <el-option label="红包奖励" :value="4"/>
          <el-option label="小说奖励" :value="5"/>
        </el-select>
      </el-form-item>
      <el-form-item label="账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="金额" prop="money">-->
<!--        <el-input-->
<!--          v-model="queryParams.money"-->
<!--          placeholder="请输入金额"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="用户id(调用SDK透传，应用对用户的唯一标识)" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入用户id(调用SDK透传，应用对用户的唯一标识)"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交易id(完成观看的唯一交易ID)" prop="transId">-->
<!--        <el-input-->
<!--          v-model="queryParams.transId"-->
<!--          placeholder="请输入交易id(完成观看的唯一交易ID)"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="请求ID" prop="reqId">-->
<!--        <el-input-->
<!--          v-model="queryParams.reqId"-->
<!--          placeholder="请输入请求ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="激励名称" prop="rewardName">-->
<!--        <el-input-->
<!--          v-model="queryParams.rewardName"-->
<!--          placeholder="请输入激励名称"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="激励数量" prop="rewardAmount">-->
<!--        <el-input-->
<!--          v-model="queryParams.rewardAmount"-->
<!--          placeholder="请输入激励数量"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Taku广告位ID" prop="placementId">-->
<!--        <el-input-->
<!--          v-model="queryParams.placementId"-->
<!--          placeholder="请输入Taku广告位ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Taku聚合的广告平台ID" prop="networkFirmId">-->
<!--        <el-input-->
<!--          v-model="queryParams.networkFirmId"-->
<!--          placeholder="请输入Taku聚合的广告平台ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Taku广告场景ID" prop="scenarioId">-->
<!--        <el-input-->
<!--          v-model="queryParams.scenarioId"-->
<!--          placeholder="请输入Taku广告场景ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="开发者应用的包名或BundleID" prop="packageName">-->
<!--        <el-input-->
<!--          v-model="queryParams.packageName"-->
<!--          placeholder="请输入开发者应用的包名或BundleID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="系统平台，Android 或 iOS" prop="platform">-->
<!--        <el-input-->
<!--          v-model="queryParams.platform"-->
<!--          placeholder="请输入系统平台，Android 或 iOS"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="签名信息" prop="sign">-->
<!--        <el-input-->
<!--          v-model="queryParams.sign"-->
<!--          placeholder="请输入签名信息"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="备注" prop="remarks">-->
<!--        <el-input-->
<!--          v-model="queryParams.remarks"-->
<!--          placeholder="请输入备注"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="时间" prop="time">
        <el-date-picker
          v-model="queryParams.time"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right"
          value-format="yyyy-MM-dd HH:mm:ss"
          size="small"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['platform:taKuCallback:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['platform:taKuCallback:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['platform:taKuCallback:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
     <el-col :span="1.5">
       <el-button
         type="warning"
         plain
         icon="el-icon-download"
         size="mini"
         @click="handleExport"
         v-hasPermi="['platform:taKuCallback:export']"
       >导出</el-button>
     </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taKuCallbackList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index"/>
      <el-table-column property="avatar" label="头像" align="center">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="scene" label="场景">
        <template slot-scope="scope">
          <span v-if="scope.row.scene===1" style="color: #ff4949;font-weight: bold">解锁短剧</span>
          <span v-if="scope.row.scene===2" style="color: #13ce66;font-weight: bold">通关奖励</span>
          <span v-if="scope.row.scene===3" style="color: #00afff;font-weight: bold">幸运奖励</span>
          <span v-if="scope.row.scene===4" style="color: blueviolet;font-weight: bold">红包奖励</span>
          <span v-if="scope.row.scene===5" style="color: #ff4949;font-weight: bold">小说奖励</span>
        </template>
      </el-table-column>
      <el-table-column label="账号" align="center" prop="userName"/>
      <el-table-column label="昵称" align="center" prop="nickName"/>
<!--      <el-table-column label="场景" align="center" prop="scene" />-->
      <el-table-column label="金额" align="center" prop="money" />
      <el-table-column label="ECPM" align="center" prop="adsourcePrice" />
      <el-table-column label="收益（元）" align="center" prop="publisherRevenue" />
<!--      <el-table-column label="用户id(调用SDK透传，应用对用户的唯一标识)" align="center" prop="userId" />-->
<!--      <el-table-column label="用户业务参数" align="center" prop="extraData" />-->
<!--      <el-table-column label="交易ID" align="center" prop="transId">-->
<!--        <template slot-scope="scope">-->
<!--          <el-popover-->
<!--            placement="top-start"-->
<!--            width="100"-->
<!--            trigger="hover"-->
<!--            :disabled="scope.row.transId.length <= 10"-->
<!--          >-->
<!--            <div>{{ scope.row.transId }}</div>-->
<!--            <span slot="reference" v-if="scope.row.transId.length <= 10">{{ scope.row.transId }}</span>-->
<!--            <span slot="reference"-->
<!--                  v-if="scope.row.transId.length > 10">{{ scope.row.transId.substr(0, 10) + "..." }}</span>-->
<!--          </el-popover>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="请求ID" align="center" prop="reqId">-->
<!--        <template slot-scope="scope">-->
<!--          <el-popover-->
<!--            placement="top-start"-->
<!--            width="100"-->
<!--            trigger="hover"-->
<!--            :disabled="scope.row.reqId.length <= 10"-->
<!--          >-->
<!--            <div>{{ scope.row.reqId }}</div>-->
<!--            <span slot="reference" v-if="scope.row.reqId.length <= 10">{{ scope.row.reqId }}</span>-->
<!--            <span slot="reference"-->
<!--                  v-if="scope.row.reqId.length > 10">{{ scope.row.reqId.substr(0, 10) + "..." }}</span>-->
<!--          </el-popover>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="签名" align="center" prop="sign">
        <template slot-scope="scope">
          <el-popover
            placement="top-start"
            width="100"
            trigger="hover"
            :disabled="scope.row.sign.length <= 10"
          >
            <div>{{ scope.row.sign }}</div>
            <span slot="reference" v-if="scope.row.sign.length <= 10">{{ scope.row.sign }}</span>
            <span slot="reference"
                  v-if="scope.row.sign.length > 10">{{ scope.row.sign.substr(0, 10) + "..." }}</span>
          </el-popover>
        </template>
      </el-table-column>
<!--      <el-table-column label="激励名称" align="center" prop="rewardName" />-->
<!--      <el-table-column label="激励数量" align="center" prop="rewardAmount" />-->
<!--      <el-table-column label="广告位ID" align="center" prop="placementId" />-->
<!--      <el-table-column label="Taku聚合的广告平台ID" align="center" prop="networkFirmId" />-->
      <el-table-column label="广告平台" align="center" prop="networkFirmIdStr" />
<!--      <el-table-column label="Taku广告场景ID" align="center" prop="scenarioId" />-->
<!--      <el-table-column label="开发者应用的包名或BundleID" align="center" prop="packageName" />-->
<!--      <el-table-column label="系统平台，Android 或 iOS" align="center" prop="platform" />-->
<!--      <el-table-column label="数据" align="center" prop="ilrd">-->
<!--        <template slot-scope="scope">-->
<!--          <el-popover-->
<!--            placement="top-start"-->
<!--            width="100"-->
<!--            trigger="hover"-->
<!--            :disabled="scope.row.ilrd.length <= 10"-->
<!--          >-->
<!--            <div>{{ scope.row.ilrd }}</div>-->
<!--            <span slot="reference" v-if="scope.row.ilrd.length <= 10">{{ scope.row.ilrd }}</span>-->
<!--            <span slot="reference"-->
<!--                  v-if="scope.row.ilrd.length > 10">{{ scope.row.ilrd.substr(0, 10) + "..." }}</span>-->
<!--          </el-popover>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="备注" align="center" prop="remarks" />-->
<!--      <el-table-column align="center" prop="ilrd" label="数据">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{getShortenedText(scope.row.ilrd)}}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="数据" align="center" prop="ilrd">
        <template slot-scope="scope">
          <el-popover
            placement="top-start"
            width="100"
            trigger="hover"
            :disabled="scope.row.ilrd.length <= 10"
          >
            <div>{{ scope.row.ilrd }}</div>
            <span slot="reference" v-if="scope.row.ilrd.length <= 10">{{ scope.ilrd.sign }}</span>
            <span slot="reference"
                  v-if="scope.row.ilrd.length > 10">{{ scope.row.ilrd.substr(0, 10) + "..." }}</span>
          </el-popover>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" prop="remarks" label="备注">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{getShortenedText(scope.row.remarks)}}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="备注" align="center" prop="remarks">
        <template slot-scope="scope">
          <el-popover
            placement="top-start"
            width="100"
            trigger="hover"
          >
            <div>{{ scope.row.remarks }}</div>
            <span slot="reference" v-if="scope.row.remarks!==null && scope.row.remarks.length <= 5">{{ scope.remarks.sign }}</span>
            <span slot="reference"
                  v-if="scope.row.remarks!==null && scope.row.remarks.length > 5">{{ scope.row.remarks.substr(0, 5) + "..." }}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="回调时间" align="center" prop="createTime" />
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['platform:taKuCallback:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['platform:taKuCallback:remove']"-->
<!--          >删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改taKu广告回调对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="场景" prop="scene">
          <el-input v-model="form.scene" placeholder="请输入场景" />
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input v-model="form.money" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="用户id(调用SDK透传，应用对用户的唯一标识)" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id(调用SDK透传，应用对用户的唯一标识)" />
        </el-form-item>
        <el-form-item label="用户业务参数" prop="extraData">
          <el-input v-model="form.extraData" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="交易id(完成观看的唯一交易ID)" prop="transId">
          <el-input v-model="form.transId" placeholder="请输入交易id(完成观看的唯一交易ID)" />
        </el-form-item>
        <el-form-item label="请求ID" prop="reqId">
          <el-input v-model="form.reqId" placeholder="请输入请求ID" />
        </el-form-item>
        <el-form-item label="激励名称" prop="rewardName">
          <el-input v-model="form.rewardName" placeholder="请输入激励名称" />
        </el-form-item>
        <el-form-item label="激励数量" prop="rewardAmount">
          <el-input v-model="form.rewardAmount" placeholder="请输入激励数量" />
        </el-form-item>
        <el-form-item label="Taku广告位ID" prop="placementId">
          <el-input v-model="form.placementId" placeholder="请输入Taku广告位ID" />
        </el-form-item>
        <el-form-item label="Taku聚合的广告平台ID" prop="networkFirmId">
          <el-input v-model="form.networkFirmId" placeholder="请输入Taku聚合的广告平台ID" />
        </el-form-item>
        <el-form-item label="Taku广告场景ID" prop="scenarioId">
          <el-input v-model="form.scenarioId" placeholder="请输入Taku广告场景ID" />
        </el-form-item>
        <el-form-item label="开发者应用的包名或BundleID" prop="packageName">
          <el-input v-model="form.packageName" placeholder="请输入开发者应用的包名或BundleID" />
        </el-form-item>
        <el-form-item label="系统平台，Android 或 iOS" prop="platform">
          <el-input v-model="form.platform" placeholder="请输入系统平台，Android 或 iOS" />
        </el-form-item>
        <el-form-item label="Taku SDK回调的展示级别数据. 数据为json字符串" prop="ilrd">
          <el-input v-model="form.ilrd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="签名信息" prop="sign">
          <el-input v-model="form.sign" placeholder="请输入签名信息" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTaKuCallback, getTaKuCallback, delTaKuCallback, addTaKuCallback, updateTaKuCallback } from "@/api/platform/taKuCallback";

export default {
  name: "TaKuCallback",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // taKu广告回调表格数据
      taKuCallbackList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        scene: null,
        money: null,
        userId: null,
        userName:null,
        nickName:null,
        extraData: null,
        transId: null,
        reqId: null,
        time:null,
        rewardName: null,
        rewardAmount: null,
        placementId: null,
        networkFirmId: null,
        scenarioId: null,
        packageName: null,
        platform: null,
        ilrd: null,
        sign: null,
        remarks: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    getShortenedText(text) {
      if (text!==null){
        return text.length > 10 ? text.substring(0, 10) + '...' : text;
      }
    },

    /** 查询taKu广告回调列表 */
    getList() {
      this.loading = true;
      listTaKuCallback(this.queryParams).then(response => {
        this.taKuCallbackList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        scene: null,
        money: null,
        userId: null,
        extraData: null,
        transId: null,
        reqId: null,
        rewardName: null,
        rewardAmount: null,
        placementId: null,
        networkFirmId: null,
        scenarioId: null,
        packageName: null,
        platform: null,
        ilrd: null,
        sign: null,
        remarks: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加taKu广告回调";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTaKuCallback(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改taKu广告回调";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTaKuCallback(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTaKuCallback(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除taKu广告回调编号为"' + ids + '"的数据项？').then(function() {
        return delTaKuCallback(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/taKuCallback/export', {
        ...this.queryParams
      }, `taKuCallback_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
