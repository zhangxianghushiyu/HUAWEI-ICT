<template>
  <div class="app-container">
<!--    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">-->
<!--      <el-form-item label="天" prop="sort">-->
<!--        <el-input-->
<!--          v-model="queryParams.sort"-->
<!--          placeholder="请输入天"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="金币数量" prop="price">-->
<!--        <el-input-->
<!--          v-model="queryParams.price"-->
<!--          placeholder="请输入金币数量"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['platform:taskNewPeopleSign:add']"-->
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
<!--          v-hasPermi="['platform:taskNewPeopleSign:edit']"-->
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
<!--          v-hasPermi="['platform:taskNewPeopleSign:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:taskNewPeopleSign:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskNewPeopleSignList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" align="center" type="index" />
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="主键" align="center" prop="id" />-->
<!--      <el-table-column label="天" align="center" prop="sort" />-->
      <el-table-column align="center" prop="sort" label="天">
        <template slot-scope="scope">
          <span>第{{scope.row.sort}}天</span>
        </template>
      </el-table-column>
      <el-table-column label="金币" align="center" prop="price" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:taskNewPeopleSign:edit']"
          >修改</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['platform:taskNewPeopleSign:remove']"-->
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

    <!-- 添加或修改新人七日签到配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="天" prop="sort">-->
<!--          <el-input v-model="form.sort" placeholder="请输入天" />-->
<!--        </el-form-item>-->
        <el-form-item label="金币" prop="price">
          <el-input v-model="form.price" placeholder="请输入金币数量" />
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
import { listTaskNewPeopleSign, getTaskNewPeopleSign, delTaskNewPeopleSign, addTaskNewPeopleSign, updateTaskNewPeopleSign } from "@/api/platform/taskNewPeopleSign";

export default {
  name: "TaskNewPeopleSign",
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
      // 新人七日签到配置表格数据
      taskNewPeopleSignList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sort: null,
        price: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        price: [
          { required: true, message: "金币不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询新人七日签到配置列表 */
    getList() {
      this.loading = true;
      listTaskNewPeopleSign(this.queryParams).then(response => {
        this.taskNewPeopleSignList = response.rows;
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
        sort: null,
        price: null,
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
      this.title = "添加新人七日签到配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTaskNewPeopleSign(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改新人七日签到配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTaskNewPeopleSign(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTaskNewPeopleSign(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除新人七日签到配置编号为"' + ids + '"的数据项？').then(function() {
        return delTaskNewPeopleSign(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/taskNewPeopleSign/export', {
        ...this.queryParams
      }, `taskNewPeopleSign_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
