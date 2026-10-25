<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!--      <el-form-item label="备注" prop="remarks">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.remarks"-->
      <!--          placeholder="请输入备注"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item>-->
      <!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
      <!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
      <!--      </el-form-item>-->
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <!--        <el-button-->
        <!--          type="primary"-->
        <!--          plain-->
        <!--          icon="el-icon-plus"-->
        <!--          size="mini"-->
        <!--          @click="handleAdd"-->
        <!--          v-hasPermi="['platform:detection:add']"-->
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
        <!--          v-hasPermi="['platform:detection:edit']"-->
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
        <!--          v-hasPermi="['platform:detection:remove']"-->
        <!--        >删除</el-button>-->
        <!--      </el-col>-->
        <!--      <el-col :span="1.5">-->
        <!--        <el-button-->
        <!--          type="warning"-->
        <!--          plain-->
        <!--          icon="el-icon-download"-->
        <!--          size="mini"-->
        <!--          @click="handleExport"-->
        <!--          v-hasPermi="['platform:detection:export']"-->
        <!--        >导出</el-button>-->
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="detectionList"
      @selection-change="handleSelectionChange"
    >
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index" />
      <!--      <el-table-column label="1：root检测，2：模拟器检测，3：两次广告间隔时间检测，4：单次收益大于某个金额检测，5：连续N条收益低于某个金额检测，6：同一个IP地址N个账号使用检测，7：每个用户每天激励广告限制数量" align="center" prop="type" />-->
      <!--      <el-table-column label="0：关闭，1：开启" align="center" prop="status" />-->
      <el-table-column align="center" prop="remarks" label="备注">
        <template slot-scope="scope">
          <span
            v-if="scope.row.type === 1"
            style="color: #ff4949; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 2"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 3"
            style="color: #ffba00; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 4"
            style="color: #ff4949; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 5"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 6"
            style="color: #ffba00; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 7"
            style="color: #ff4949; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 8"
            style="color: #ff4949; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
        </template>
      </el-table-column>
      <el-table-column label="值1" align="center" prop="value1" />
      <el-table-column label="值2" align="center" prop="value2" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <span
            v-if="scope.row.status === 0"
            style="color: #1c84c6; font-weight: bold"
            >关闭</span
          >
          <span
            v-if="scope.row.status === 1"
            style="color: red; font-weight: bold"
            >开启</span
          >
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <!--          <el-button v-if="scope.row.status===1"-->
          <!--                     size="mini"-->
          <!--                     type="text"-->
          <!--                     icon="el-icon-edit"-->
          <!--                     @click="handleDeactivate(scope.row)"-->
          <!--          >关闭-->
          <!--          </el-button>-->
          <!--          <el-button v-if="scope.row.status===0"-->
          <!--                     size="mini"-->
          <!--                     type="text"-->
          <!--                     icon="el-icon-edit"-->
          <!--                     @click="handleRestore(scope.row)"-->
          <!--          >开启-->
          <!--          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:detection:edit']"
            >修改
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['platform:detection:remove']"-->
          <!--          >删除</el-button>-->
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

    <!-- 添加或修改风控管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="备注" prop="remarks">-->
        <!--          <el-input v-model="form.remarks" placeholder="请输入备注"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="form.status"
            placeholder="请选择是否开启"
            clearable
            size="small"
          >
            <el-option label="开启" :value="1" />
            <el-option label="关闭" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item
          label="秒数"
          prop="value1"
          v-if="[3].includes(form.type) && form.status === 1"
        >
          <el-input v-model="form.value1" placeholder="请输入秒数" />
        </el-form-item>
        <el-form-item
          label="金额"
          prop="value1"
          v-if="[4].includes(form.type) && form.status === 1"
        >
          <el-input v-model="form.value1" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item
          label="广告条数"
          prop="value1"
          v-if="[5].includes(form.type) && form.status === 1"
        >
          <el-input v-model="form.value1" placeholder="请输入连续广告条数" />
        </el-form-item>
        <el-form-item
          label="金额"
          prop="value2"
          v-if="[5].includes(form.type) && form.status === 1"
        >
          <el-input v-model="form.value2" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item
          label="账号数量"
          prop="value1"
          v-if="[6].includes(form.type) && form.status === 1"
        >
          <el-input v-model="form.value1" placeholder="请输入账号数量" />
        </el-form-item>
        <el-form-item
          label="最多广告"
          prop="value1"
          v-if="[7].includes(form.type) && form.status === 1"
        >
          <el-input v-model="form.value1" placeholder="请输入最多广告" />
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
  listDetection,
  getDetection,
  delDetection,
  addDetection,
  updateDetection,
  modifyStatusDeactivate,
} from "@/api/platform/detection";

export default {
  name: "Detection",
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
      // 风控管理表格数据
      detectionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        status: null,
        remarks: null,
        value1: null,
        value2: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        status: [{ required: true, message: "状态不能为空", trigger: "blur" }],
        value1: [{ required: true, message: "值1不能为空", trigger: "blur" }],
        value2: [{ required: true, message: "值2不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 停用按钮操作 */
    handleDeactivate(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认关闭数据项？")
        .then(function () {
          return modifyStatusDeactivate(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("设置成功");
        })
        .catch(() => {});
    },

    /** 恢复按钮操作 */
    handleRestore(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认开启数据项？")
        .then(function () {
          return modifyStatusDeactivate(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("设置成功");
        })
        .catch(() => {});
    },

    /** 查询风控管理列表 */
    getList() {
      this.loading = true;
      listDetection(this.queryParams).then((response) => {
        this.detectionList = response.rows;
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
        type: null,
        status: 0,
        remarks: null,
        createTime: null,
        updateTime: null,
        value1: null,
        value2: null,
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
      this.title = "添加风控管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDetection(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改风控管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateDetection(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDetection(this.form).then((response) => {
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
        .confirm('是否确认删除风控管理编号为"' + ids + '"的数据项？')
        .then(function () {
          return delDetection(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "platform/detection/export",
        {
          ...this.queryParams,
        },
        `detection_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
