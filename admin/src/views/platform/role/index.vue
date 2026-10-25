<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8"> </el-row>

    <el-table
      v-loading="loading"
      :data="applyDestroyList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="身份" align="center" prop="roleType">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="自收益" align="center" prop="myRatio" />
      <el-table-column
        label="直推广告比例(%)"
        align="center"
        prop="subAdRatio"
      />
      <el-table-column
        label="直推商城比例(%)"
        align="center"
        prop="subMallRatio"
      />
      <el-table-column
        label="级差会员比例(%)"
        align="center"
        prop="indirectRatio"
      />
      <el-table-column
        label="级差贵宾比例(%)"
        align="center"
        prop="indirectTwoRatio"
      />
      <el-table-column
        label="直推团队抽佣比例(%)"
        align="center"
        prop="subTeamRatio"
      />
      <el-table-column
        label="团队抽佣比例(%)"
        align="center"
        prop="teamRatio"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDeactivate(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleRefuse(scope.row)"
            >删除</el-button
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

    <!-- 添加或修改申请注销对话框 -->
    <el-dialog
      :close-on-click-modal="false"
      title="修改用户比例"
      :visible.sync="open"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="自收益" prop="myRatio">
          <el-input v-model="form.myRatio" placeholder="请输入比例" />
        </el-form-item>
        <el-form-item label="直推广告比例" prop="subAdRatio">
          <el-input v-model="form.subAdRatio" placeholder="请输入比例" />
        </el-form-item>
        <el-form-item label="直推商城比例" prop="subMallRatio">
          <el-input v-model="form.subMallRatio" placeholder="请输入比例" />
        </el-form-item>
        <el-form-item label="级差会员比例" prop="indirectRatio">
          <el-input v-model="form.indirectRatio" placeholder="请输入比例" />
        </el-form-item>
        <el-form-item label="级差贵宾比例" prop="indirectTwoRatio">
          <el-input v-model="form.indirectTwoRatio" placeholder="请输入比例" />
        </el-form-item>
        <el-form-item label="直推团队抽佣比例" prop="subTeamRatio">
          <el-input v-model="form.subTeamRatio" placeholder="请输入比例" />
        </el-form-item>
        <el-form-item label="团队抽佣比例" prop="teamRatio">
          <el-input v-model="form.teamRatio" placeholder="请输入比例" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRole, updateRole, delRole } from "@/api/platform/role";

export default {
  name: "Role",
  dicts: ["apply_destroy_status", "user_status"],
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
      // 申请注销表格数据
      applyDestroyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        status: null,
        nickName: null,
        phone: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        myRatio: [{ required: true, message: "比例不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询申请注销列表 */
    getList() {
      this.loading = true;
      listRole(this.queryParams).then((response) => {
        this.applyDestroyList = response.rows;
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
        status: null,
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
      this.title = "添加申请注销";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getApplyDestroy(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改申请注销";
      });
    },
    /** 提交按钮 */
    submitForm() {
      updateRole(this.form).then((response) => {
        this.open = false;
        this.$modal.msgSuccess("设置成功");
        this.getList();
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除申请注销编号为"' + ids + '"的数据项？')
        .then(function () {
          return delApplyDestroy(ids);
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
        "platform/applyDestroy/export",
        {
          ...this.queryParams,
        },
        `applyDestroy_${new Date().getTime()}.xlsx`
      );
    },
    /** 注销同意按钮操作 */
    handleDeactivate(row) {
      this.open = true;
      this.form = row;
      console.log(this.form);
      // const ids = row.id || this.ids;
      // this.$prompt("请输入比例", "提示", {
      //   confirmButtonText: "确定",
      //   cancelButtonText: "取消",
      //   closeOnClickModal: false,
      //   inputPattern: /^.{1,20}$/,
      // })
      //   .then(({ value }) => {
      //     console.log(88989);
      //     updateRole(row.id, value).then((response) => {
      //       this.$modal.msgSuccess("设置成功");
      //       this.getList();
      //     });
      //   })
      //   .catch(() => {});
    },

    /** 注销拒绝按钮操作 */
    handleRefuse(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除数据项？")
        .then(function () {
          return delRole(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    /** 恢复按钮操作 */
    handleRestore(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认恢复数据项？")
        .then(function () {
          return modifyUserStatusDestroy(ids, 3);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("恢复成功");
        })
        .catch(() => {});
    },
  },
};
</script>
