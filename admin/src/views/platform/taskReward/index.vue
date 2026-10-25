<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.task_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.task_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="副类型" prop="followType">
        <el-select v-model="queryParams.followType" placeholder="请选择副类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.task_follow_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
      <!--      <el-form-item label="排序" prop="sort">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.sort"-->
      <!--          placeholder="请输入排序"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="奖励类型" prop="rewardType">
        <el-select v-model="queryParams.rewardType" placeholder="请选择奖励类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.task_reward_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="金额" prop="price">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.price"-->
      <!--          placeholder="请输入金额"-->
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
      <!--          v-hasPermi="['platform:taskReward:add']"-->
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
      <!--          v-hasPermi="['platform:taskReward:edit']"-->
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
      <!--          v-hasPermi="['platform:taskReward:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['platform:taskReward:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskRewardList" @selection-change="handleSelectionChange">
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index"/>

      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.task_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="副类型" align="center" prop="followType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.task_follow_type" :value="scope.row.followType"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.task_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="title"/>
      <el-table-column label="描述" align="center" prop="describe"/>
      <el-table-column label="排序" align="center" prop="sort"/>
      <el-table-column label="奖励类型" align="center" prop="rewardType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.task_reward_type" :value="scope.row.rewardType"/>
        </template>
      </el-table-column>
      <el-table-column label="金额" align="center" prop="price"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status===1"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleDeactivate(scope.row)"
          >关闭</el-button>
          <el-button v-if="scope.row.status===0"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleRestore(scope.row)"
          >开启</el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                       v-hasPermi="['platform:taskReward:edit']"
          >
            <span class="el-dropdown-link">
              <i class="el-icon-d-arrow-right el-icon--right"></i>更多
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="modifyInfo">修改</el-dropdown-item>
              <el-dropdown-item v-if="[2,5,4,6,7,8,10].includes(scope.row.followType)" command="configurationTaskInfo" >配置</el-dropdown-item>
              <el-dropdown-item v-if="[10].includes(scope.row.followType)" command="setInfo">设置</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['platform:taskReward:edit']"-->
<!--          >修改-->
<!--          </el-button>-->
<!--          <el-button v-if="[2,5,4,6,7,8,10].includes(scope.row.followType)"-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="configuration(scope.row)"-->
<!--            v-hasPermi="['platform:taskReward:edit']"-->
<!--          >配置-->
<!--          </el-button>-->
<!--          <el-button v-if="[10].includes(scope.row.followType)"-->
<!--                     size="mini"-->
<!--                     type="text"-->
<!--                     icon="el-icon-edit"-->
<!--                     @click="configurationInfo(scope.row)"-->
<!--                     v-hasPermi="['platform:taskReward:edit']"-->
<!--          >设置-->
<!--          </el-button>-->
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['platform:taskReward:remove']"-->
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

    <!-- 添加或修改任务对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="类型" prop="type">-->
        <!--          <el-select v-model="form.type" placeholder="请选择类型">-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.task_type"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--:value="parseInt(dict.value)"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="副类型" prop="followType">-->
        <!--          <el-select v-model="form.followType" placeholder="请选择副类型">-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.task_follow_type"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--:value="parseInt(dict.value)"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="描述" prop="describe">
          <el-input v-model="form.describe" placeholder="请输入描述"/>
        </el-form-item>
        <!--        <el-form-item label="排序" prop="sort">-->
        <!--          <el-input v-model="form.sort" placeholder="请输入排序" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="奖励类型" prop="rewardType">-->
        <!--          <el-select v-model="form.rewardType" placeholder="请选择奖励类型">-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.task_reward_type"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--:value="parseInt(dict.value)"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="金额" prop="price" v-if="[1,4,5,6,7,8,9,10].includes(form.followType)">
          <el-input v-model="form.price" placeholder="请输入金额"/>
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
  listTaskReward,
  getTaskReward,
  delTaskReward,
  addTaskReward,
  updateTaskReward,
  modifyTaskStatusDeactivate
} from "@/api/platform/taskReward";
import Resource from "@/views/platform/drama/resource.vue";
import {modifyUserStatusDeactivate} from "@/api/platform/user";

export default {
  name: "TaskReward",
  components: {Resource},
  dicts: ['task_follow_type', 'task_type','task_status', 'task_reward_type'],
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
      // 任务表格数据
      taskRewardList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        status:null,
        followType: null,
        title: null,
        describe: null,
        sort: null,
        rewardType: null,
        price: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        describe: [
          {required: true, message: "描述不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询任务列表 */
    getList() {
      this.loading = true;
      listTaskReward(this.queryParams).then(response => {
        this.taskRewardList = response.rows;
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
        followType: null,
        title: null,
        describe: null,
        sort: null,
        rewardType: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTaskReward(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改任务";
      });
    },
    /** 配置按钮操作 */
    configuration(row) {
      const followType = row.followType;
      if (followType===2){
        const path = "/taskManage/taskNewPeopleSign"
        this.$router.push(path)
      }if (followType===5){
        const path = "/taskManage/taskDailySign"
        this.$router.push(path)
      }if (followType===4){
        const path = "/taskManage/taskNewPeopleLookDrama"
        this.$router.push(path)
      }if (followType===6){
        const path = "/taskManage/taskDailyFoodPointLookDrama"
        this.$router.push(path)
      }if (followType===7){
        const path = "/taskManage/taskDailyLookDramaToCoin"
        this.$router.push(path)
      }if (followType===8){
        const path = "/taskManage/taskDailyLookVideoToCoin"
        this.$router.push(path)
      }if (followType===10){
        const path = "/taskManage/taskDailyAppointDramaCoin"
        this.$router.push(path)
      }

    },

    /** 配置信息按钮操作 */
    configurationInfo(row) {
      const followType = row.followType;
      if (followType===10){
        const path = "/taskManage/taskDailyAppointDramaCoinInfo"
        this.$router.push(path)
      }

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTaskReward(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTaskReward(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除任务编号为"' + ids + '"的数据项？').then(function () {
        return delTaskReward(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/taskReward/export', {
        ...this.queryParams
      }, `taskReward_${new Date().getTime()}.xlsx`)
    },
    /** 关闭按钮操作 */
    handleDeactivate(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认关闭数据项？').then(function() {
        return modifyTaskStatusDeactivate(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("设置成功");
      }).catch(() => {});
    },

    /** 开启按钮操作 */
    handleRestore(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认开启数据项？').then(function() {
        return modifyTaskStatusDeactivate(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("设置成功");
      }).catch(() => {});
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case 'modifyInfo':
          this.handleUpdate(row)
          break
        case 'configurationTaskInfo':
          this.configuration(row)
          break
        case 'setInfo':
          this.configurationInfo(row)
          break
        default:
          break
      }
    },
  }
};
</script>
