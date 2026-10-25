<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="任务ID" prop="taskRewardId">-->
<!--        <el-input-->
<!--          v-model="queryParams.taskRewardId"-->
<!--          placeholder="请输入任务ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="任务标题" prop="taskTitle">
        <el-input
          v-model="queryParams.taskTitle"
          placeholder="请输入任务标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="任务描述" prop="taskDescribe">-->
<!--        <el-input-->
<!--          v-model="queryParams.taskDescribe"-->
<!--          placeholder="请输入任务描述"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="类型" prop="taskType">
        <el-select v-model="queryParams.taskType" placeholder="请选择类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.task_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="副类型" prop="taskFollowType">
        <el-select v-model="queryParams.taskFollowType" placeholder="请选择副类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.task_follow_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="奖励类型" prop="taskRewardType">
        <el-select v-model="queryParams.taskRewardType" placeholder="请选择奖励类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.task_reward_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="金额" prop="taskPrice">-->
<!--        <el-input-->
<!--          v-model="queryParams.taskPrice"-->
<!--          placeholder="请输入金额"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="描述" prop="describe">-->
<!--        <el-input-->
<!--          v-model="queryParams.describe"-->
<!--          placeholder="请输入描述"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
<!--          v-hasPermi="['platform:taskCompleteRecord:add']"-->
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
<!--          v-hasPermi="['platform:taskCompleteRecord:edit']"-->
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
<!--          v-hasPermi="['platform:taskCompleteRecord:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:taskCompleteRecord:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskCompleteRecordList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="序号" align="center" type="index" />
<!--      <el-table-column label="用户ID" align="center" prop="userId" />-->
      <el-table-column label="用户昵称" align="center" prop="nickName" />
      <el-table-column property="avatar" label="头像" align="center">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center" prop="phone" />
<!--      <el-table-column label="任务ID" align="center" prop="taskRewardId" />-->
      <el-table-column label="任务标题" align="center" prop="taskTitle" />
      <el-table-column label="任务描述" align="center" prop="taskDescribe" />
      <el-table-column label="类型" align="center" prop="taskType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.task_type" :value="scope.row.taskType"/>
        </template>
      </el-table-column>
      <el-table-column label="副类型" align="center" prop="taskFollowType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.task_follow_type" :value="scope.row.taskFollowType"/>
        </template>
      </el-table-column>
      <el-table-column label="奖励类型" align="center" prop="taskRewardType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.task_reward_type" :value="scope.row.taskRewardType"/>
        </template>
      </el-table-column>
      <el-table-column label="金额" align="center" prop="taskPrice" />
      <el-table-column label="描述" align="center" prop="describe" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['platform:taskCompleteRecord:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['platform:taskCompleteRecord:remove']"-->
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

    <!-- 添加或修改任务完成记录对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="任务ID" prop="taskRewardId">
          <el-input v-model="form.taskRewardId" placeholder="请输入任务ID" />
        </el-form-item>
        <el-form-item label="任务标题" prop="taskTitle">
          <el-input v-model="form.taskTitle" placeholder="请输入任务标题" />
        </el-form-item>
        <el-form-item label="任务描述" prop="taskDescribe">
          <el-input v-model="form.taskDescribe" placeholder="请输入任务描述" />
        </el-form-item>
        <el-form-item label="类型" prop="taskType">
          <el-select v-model="form.taskType" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.task_type"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="副类型" prop="taskFollowType">
          <el-select v-model="form.taskFollowType" placeholder="请选择副类型">
            <el-option
              v-for="dict in dict.type.task_follow_type"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="奖励类型" prop="taskRewardType">
          <el-select v-model="form.taskRewardType" placeholder="请选择奖励类型">
            <el-option
              v-for="dict in dict.type.task_reward_type"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="金额" prop="taskPrice">
          <el-input v-model="form.taskPrice" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="描述" prop="describe">
          <el-input v-model="form.describe" placeholder="请输入描述" />
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
import { listTaskCompleteRecord, getTaskCompleteRecord, delTaskCompleteRecord, addTaskCompleteRecord, updateTaskCompleteRecord } from "@/api/platform/taskCompleteRecord";

export default {
  name: "TaskCompleteRecord",
  dicts: ['task_follow_type', 'task_type', 'task_reward_type'],
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
      // 任务完成记录表格数据
      taskCompleteRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        taskRewardId: null,
        taskTitle: null,
        taskDescribe: null,
        taskType: null,
        taskFollowType: null,
        taskRewardType: null,
        taskPrice: null,
        describe: null,
        nickName: null,
        avatar: null,
        phone: null,
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
    /** 查询任务完成记录列表 */
    getList() {
      this.loading = true;
      listTaskCompleteRecord(this.queryParams).then(response => {
        this.taskCompleteRecordList = response.rows;
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
        taskRewardId: null,
        taskTitle: null,
        taskDescribe: null,
        taskType: null,
        taskFollowType: null,
        taskRewardType: null,
        taskPrice: null,
        describe: null,
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
      this.title = "添加任务完成记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTaskCompleteRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改任务完成记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTaskCompleteRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTaskCompleteRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除任务完成记录编号为"' + ids + '"的数据项？').then(function() {
        return delTaskCompleteRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/taskCompleteRecord/export', {
        ...this.queryParams
      }, `taskCompleteRecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
