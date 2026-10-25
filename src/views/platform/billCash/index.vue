<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.bill_cash_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="提现方式" prop="method">
        <el-select v-model="queryParams.method" placeholder="请选择提现方式" clearable size="small">
          <el-option
            v-for="dict in dict.type.bill_cash_method"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="订单号" prop="orderNo">-->
<!--        <el-input-->
<!--          v-model="queryParams.orderNo"-->
<!--          placeholder="请输入订单号"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="失败原因" prop="failureReason">-->
<!--        <el-input-->
<!--          v-model="queryParams.failureReason"-->
<!--          placeholder="请输入失败原因"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="用户昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场景" prop="scene">
        <el-select v-model="queryParams.scene" placeholder="请选择场景" clearable size="small">
          <el-option
            v-for="dict in dict.type.bill_scene"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="描述" prop="describe">-->
<!--        <el-input-->
<!--          v-model="queryParams.describe"-->
<!--          placeholder="请输入描述"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="选项卡金额" prop="money">-->
<!--        <el-input-->
<!--          v-model="queryParams.money"-->
<!--          placeholder="请输入选项卡金额"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.bill_cash_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="提现选项卡ID" prop="withdrawalTabId">-->
<!--        <el-input-->
<!--          v-model="queryParams.withdrawalTabId"-->
<!--          placeholder="请输入提现选项卡ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="自动打款" prop="autoAccount">
        <el-select v-model="queryParams.autoAccount" placeholder="请选择自动打款" clearable size="small">
          <el-option
            v-for="dict in dict.type.is_auto_account"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="代理" prop="commander">
        <el-select v-model="queryParams.commander" placeholder="请选择" clearable size="small">
          <el-option
            v-for="dict in dict.type.is_commander"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属代理" prop="commanderUserName">
        <el-input
          v-model="queryParams.commanderUserName"
          placeholder="请输入所属代理昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="打款时间" prop="payTime">-->
<!--        <el-date-picker clearable size="small"-->
<!--          v-model="queryParams.payTime"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="选择打款时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <div>
      <span style="color: red;font-weight: bold">金额总计：{{
          statisticsInfo.sumMoney
        }}</span>
    </div>



    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['platform:billCash:add']"-->
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
<!--          v-hasPermi="['platform:billCash:edit']"-->
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
<!--          v-hasPermi="['platform:billCash:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:billCash:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="billCashList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" align="center" type="index"/>
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="所属代理" align="center" prop="commanderUserName"/>
      <el-table-column label="用户昵称" align="center" prop="nickName" />
      <el-table-column property="avatar" label="头像" align="center">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="支付宝姓名" align="center" prop="zfbName"/>
      <el-table-column label="支付宝账号" align="center" prop="zfbUserName"/>
      <el-table-column label="代理" align="center" prop="commander">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_commander" :value="scope.row.commander"/>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bill_cash_type" :value="scope.row.type"/>
        </template>
      </el-table-column> <el-table-column label="提现方式" align="center" prop="method">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bill_cash_method" :value="scope.row.method"/>
        </template>
      </el-table-column>
      <el-table-column label="订单号" align="center" prop="orderNo" />

<!--      <el-table-column label="用户ID" align="center" prop="userId" />-->
      <el-table-column label="场景" align="center" prop="scene">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bill_scene" :value="scope.row.scene"/>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="describe" />
      <el-table-column label="金额" align="center" prop="money" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bill_cash_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="提现选项卡ID" align="center" prop="withdrawalTabId" />-->
      <el-table-column label="自动打款" align="center" prop="autoAccount">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_auto_account" :value="scope.row.autoAccount"/>
        </template>
      </el-table-column>
      <el-table-column label="打款时间" align="center" prop="payTime" />
<!--      <el-table-column label="失败原因" align="center" prop="failureReason" />-->
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" v-if="isAdmin()">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['platform:billCash:edit']"-->
<!--          >修改</el-button>-->
          <el-button v-if="scope.row.status===0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleAgreeWith(scope.row)"
            v-hasPermi="['platform:billCash:edit']"
          >同意</el-button>
          <el-button v-if="scope.row.status===0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleRefuse(scope.row)"
            v-hasPermi="['platform:billCash:edit']"
          >拒绝</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['platform:billCash:remove']"-->
<!--          >删除</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改现金明细对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="提现方式" prop="method">-->
<!--          <el-select v-model="form.method" placeholder="请选择提现方式">-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.bill_cash_method"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--:value="parseInt(dict.value)"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="订单号" prop="orderNo">-->
<!--          <el-input v-model="form.orderNo" placeholder="请输入订单号" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="失败原因" prop="failureReason">-->
<!--          <el-input v-model="form.failureReason" placeholder="请输入失败原因" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="用户ID" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入用户ID" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="场景" prop="scene">-->
<!--          <el-select v-model="form.scene" placeholder="请选择场景">-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.bill_scene"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--:value="parseInt(dict.value)"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="描述" prop="describe">-->
<!--          <el-input v-model="form.describe" placeholder="请输入描述" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="选项卡金额" prop="money">-->
<!--          <el-input v-model="form.money" placeholder="请输入选项卡金额" />-->
<!--        </el-form-item>-->
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.bill_cash_status"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="提现选项卡ID" prop="withdrawalTabId">-->
<!--          <el-input v-model="form.withdrawalTabId" placeholder="请输入提现选项卡ID" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="自动打款" prop="autoAccount">-->
<!--          <el-select v-model="form.autoAccount" placeholder="请选择自动打款">-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.is_auto_account"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--:value="parseInt(dict.value)"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="打款时间" prop="payTime">-->
<!--          <el-date-picker clearable size="small"-->
<!--            v-model="form.payTime"-->
<!--            type="date"-->
<!--            value-format="yyyy-MM-dd"-->
<!--            placeholder="选择打款时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listBillCash,
  getBillCash,
  delBillCash,
  addBillCash,
  updateBillCash,
  handleAgreeWith, handleRefuse, getStatisticsInfo
} from "@/api/platform/billCash";

export default {
  name: "BillCash",
  dicts: ['bill_cash_method','bill_cash_type', 'bill_scene', 'is_auto_account', 'bill_cash_status','is_commander'],
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
      // 现金明细表格数据
      billCashList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      statisticsInfo: {
        sumMoney: 0
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        method: null,
        type:null,
        orderNo: null,
        nickName:null,
        commander:null,
        commanderUserName:null,
        failureReason: null,
        userId: null,
        scene: null,
        describe: null,
        money: null,
        status: null,
        withdrawalTabId: null,
        autoAccount: null,
        payTime: null,
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
    /** 查询现金明细列表 */
    getList() {
      this.loading = true;
      listBillCash(this.queryParams).then(response => {
        this.billCashList = response.rows;
        this.getStatisticsInfo();
        this.total = response.total;
        this.loading = false;
      });
    },
    getStatisticsInfo() {
      getStatisticsInfo(this.queryParams).then(response => {
        this.statisticsInfo.sumMoney = response.data;
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
        method: null,
        orderNo: null,
        failureReason: null,
        userId: null,
        scene: null,
        describe: null,
        money: null,
        status: null,
        withdrawalTabId: null,
        autoAccount: null,
        payTime: null,
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
      this.title = "添加现金明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBillCash(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改现金明细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBillCash(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBillCash(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除现金明细编号为"' + ids + '"的数据项？').then(function() {
        return delBillCash(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 统一按钮操作 */
    handleAgreeWith(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认同意数据项？').then(function() {
        return handleAgreeWith(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("修改成功");
      }).catch(() => {});
    },
    /** 拒绝按钮操作 */
    handleRefuse(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认拒绝数据项？').then(function() {
        return handleRefuse(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("修改成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/billCash/export', {
        ...this.queryParams
      }, `billCash_${new Date().getTime()}.xlsx`)
    },
    isAdmin() {
      return this.$auth.hasRole("admin");
    },

  }
};
</script>
