<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!--      <el-form-item label="用户ID" prop="userId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.userId"-->
      <!--          placeholder="请输入用户ID"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="订单号" prop="orderNo">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.orderNo"-->
      <!--          placeholder="请输入订单号"-->
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
      <el-form-item label="用户账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称" prop="goodsName">
        <el-input
          v-model="queryParams.goodsName"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="商品价格" prop="goodsPrice">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.goodsPrice"-->
      <!--          placeholder="请输入商品价格"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="支付金额" prop="payMoney">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.payMoney"-->
      <!--          placeholder="请输入支付金额"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="购买数量" prop="payCount">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.payCount"-->
      <!--          placeholder="请输入购买数量"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="收货人姓名" prop="getName" label-width="20">
        <el-input
          v-model="queryParams.getName"
          placeholder="请输入收货人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收货人手机号" prop="getPhone" label-width="20">
        <el-input
          v-model="queryParams.getPhone"
          placeholder="请输入收货人手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        >
          <el-option label="待支付" value="0" />
          <el-option label="待发货" value="1" />
          <el-option label="已发货" value="2" />
          <el-option label="已收货" value="3" />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="收货人城市" prop="getCity">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.getCity"-->
      <!--          placeholder="请输入收货人城市"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="收货人地址" prop="getAddress">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.getAddress"-->
      <!--          placeholder="请输入收货人地址"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
      <!--          v-hasPermi="['platform:goodsOrder:add']"-->
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
      <!--          v-hasPermi="['platform:goodsOrder:edit']"-->
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
      <!--          v-hasPermi="['platform:goodsOrder:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['platform:goodsOrder:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="goodsOrderList"
      @selection-change="handleSelectionChange"
    >
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column label="账号" align="center" prop="userName" />
      <el-table-column property="avatar" label="头像" align="center">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="订单号" align="center" prop="orderNo" />
      <el-table-column label="商品名称" align="center" prop="goodsName" />
      <el-table-column label="商品价格" align="center" prop="goodsPrice" />
      <!--      <el-table-column label="商品图片" align="center" prop="goodsUrl" />-->
      <el-table-column label="商品图片" align="center" prop="goodsUrl">
        <template slot-scope="scope">
          <el-image
            :src="scope.row.goodsUrl"
            :preview-src-list="[scope.row.goodsUrl]"
            fit="cover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="支付金额" align="center" prop="payMoney" />
      <el-table-column label="购买数量" align="center" prop="payCount" />
      <!--      <el-table-column label="状态" align="center" prop="status" />-->
      <el-table-column prop="status" label="状态" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0" style="color: red">待支付</span>
          <span v-if="scope.row.status === 1" style="color: #00afff"
            >待发货</span
          >
          <span v-if="scope.row.status === 2" style="color: #ffba00"
            >已发货</span
          >
          <span v-if="scope.row.status === 3" style="color: red">已收货</span>
        </template>
      </el-table-column>
      <el-table-column label="收货人姓名" align="center" prop="getName" />
      <el-table-column label="收货人手机号" align="center" prop="getPhone" />
      <el-table-column label="收货人城市" align="center" prop="getCity" />
      <el-table-column label="收货人地址" align="center" prop="getAddress" />
      <el-table-column label="下单时间" align="center" prop="createTime" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
      >
        <template slot-scope="scope">
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-edit"-->
          <!--            @click="handleUpdate(scope.row)"-->
          <!--            v-hasPermi="['platform:goodsOrder:edit']"-->
          <!--          >修改</el-button>-->
          <el-button
            v-if="scope.row.status === 1"
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['platform:goodsOrder:remove']"
            >发货</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改商城订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="商品名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品价格" prop="goodsPrice">
          <el-input v-model="form.goodsPrice" placeholder="请输入商品价格" />
        </el-form-item>
        <el-form-item label="商品图片" prop="goodsUrl">
          <el-input
            v-model="form.goodsUrl"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="支付金额" prop="payMoney">
          <el-input v-model="form.payMoney" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="购买数量" prop="payCount">
          <el-input v-model="form.payCount" placeholder="请输入购买数量" />
        </el-form-item>
        <el-form-item label="收货人姓名" prop="getName">
          <el-input v-model="form.getName" placeholder="请输入收货人姓名" />
        </el-form-item>
        <el-form-item label="收货人手机号" prop="getPhone">
          <el-input v-model="form.getPhone" placeholder="请输入收货人手机号" />
        </el-form-item>
        <el-form-item label="收货人城市" prop="getCity">
          <el-input v-model="form.getCity" placeholder="请输入收货人城市" />
        </el-form-item>
        <el-form-item label="收货人地址" prop="getAddress">
          <el-input v-model="form.getAddress" placeholder="请输入收货人地址" />
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
import {
  listGoodsOrder,
  getGoodsOrder,
  delGoodsOrder,
  addGoodsOrder,
  updateGoodsOrder,
} from "@/api/platform/goodsOrder";

export default {
  name: "GoodsOrder",
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
      // 商城订单表格数据
      goodsOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        orderNo: null,
        goodsName: null,
        goodsPrice: null,
        goodsUrl: null,
        payMoney: null,
        payCount: null,
        status: null,
        getName: null,
        getPhone: null,
        getCity: null,
        getAddress: null,
        nickName: null,
        userName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商城订单列表 */
    getList() {
      this.loading = true;
      listGoodsOrder(this.queryParams).then((response) => {
        this.goodsOrderList = response.rows;
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
        userId: null,
        orderNo: null,
        goodsName: null,
        goodsPrice: null,
        goodsUrl: null,
        payMoney: null,
        payCount: null,
        status: 0,
        getName: null,
        getPhone: null,
        getCity: null,
        getAddress: null,
        createTime: null,
        updateTime: null,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商城订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGoodsOrder(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商城订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGoodsOrder(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGoodsOrder(this.form).then((response) => {
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
      this.$modal
        .confirm("是否确认发货？")
        .then(function () {
          return delGoodsOrder(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("发货成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "platform/goodsOrder/export",
        {
          ...this.queryParams,
        },
        `goodsOrder_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
