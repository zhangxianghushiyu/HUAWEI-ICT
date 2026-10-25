<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!--      <el-form-item label="免费集数" prop="freeNum">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.freeNum"-->
      <!--          placeholder="请输入免费集数"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="解锁集数" prop="unlockNum">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.unlockNum"-->
      <!--          placeholder="请输入解锁集数"-->
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
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['platform:dramaUnlock:add']"-->
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
      <!--          v-hasPermi="['platform:dramaUnlock:edit']"-->
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
      <!--          v-hasPermi="['platform:dramaUnlock:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['platform:dramaUnlock:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="dramaUnlockList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="序号" align="center" type="index" />
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <!--      <el-table-column label="免费集数" align="center" prop="freeNum" />-->
      <el-table-column align="center" prop="freeNum" label="免费集数">
        <template slot-scope="scope">
          <span>前 {{ scope.row.freeNum }} 集免费</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="解锁集数" align="center" prop="unlockNum" />-->
      <el-table-column align="center" prop="unlockNum" label="解锁集数">
        <template slot-scope="scope">
          <span>每看1个广告解锁 {{ scope.row.unlockNum }} 集</span>
        </template>
      </el-table-column>
      <!-- <el-table-column align="center" prop="money" label="金币解锁">
        <template slot-scope="scope">
          <span>每消耗{{scope.row.money}}金币，解锁 {{ scope.row.unlockNum }} 集</span>
        </template>
      </el-table-column> -->
      <!--      <el-table-column label="创建时间" align="center" prop="createTime"/>-->
      <!--      <el-table-column label="更新时间" align="center" prop="updateTIme"/>-->
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
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:dramaUnlock:edit']"
            >修改
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['platform:dramaUnlock:remove']"-->
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

    <!-- 添加或修改短剧解锁配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="免费集数" prop="freeNum">
          <el-input v-model="form.freeNum" placeholder="请输入免费集数" />
        </el-form-item>
        <el-form-item label="解锁集数" prop="unlockNum">
          <el-input v-model="form.unlockNum" placeholder="请输入解锁集数" />
        </el-form-item>
        <!-- <el-form-item label="消耗金币" prop="money">
          <el-input v-model="form.money" placeholder="请输入消耗金币" />
        </el-form-item> -->
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
  listDramaUnlock,
  getDramaUnlock,
  delDramaUnlock,
  addDramaUnlock,
  updateDramaUnlock,
} from "@/api/platform/dramaUnlock";

export default {
  name: "DramaUnlock",
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
      // 短剧解锁配置表格数据
      dramaUnlockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        freeNum: null,
        unlockNum: null,
        money: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        freeNum: [
          { required: true, message: "免费集数不能为空", trigger: "blur" },
        ],
        unlockNum: [
          { required: true, message: "解锁集数不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询短剧解锁配置列表 */
    getList() {
      this.loading = true;
      listDramaUnlock(this.queryParams).then((response) => {
        this.dramaUnlockList = response.rows;
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
        freeNum: null,
        unlockNum: null,
        createTime: null,
        updateTime: null,
        money: null,
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
      this.title = "添加短剧解锁配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDramaUnlock(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改短剧解锁配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateDramaUnlock(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDramaUnlock(this.form).then((response) => {
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
        .confirm('是否确认删除短剧解锁配置编号为"' + ids + '"的数据项？')
        .then(function () {
          return delDramaUnlock(ids);
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
        "platform/dramaUnlock/export",
        {
          ...this.queryParams,
        },
        `dramaUnlock_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
