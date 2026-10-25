<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
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
      <!--          v-hasPermi="['platform:taKuConfig:add']"-->
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
      <!--          v-hasPermi="['platform:taKuConfig:edit']"-->
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
      <!--          v-hasPermi="['platform:taKuConfig:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['platform:taKuConfig:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taKuConfigList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" align="center" type="index"/>
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <!--      <el-table-column label="类型" align="center" prop="type" />-->
      <el-table-column align="center" prop="remarks" label="备注">
        <template slot-scope="scope">
          <span  style="color: #ff4949;font-weight: bold">{{scope.row.remarks}}</span>
        </template>
      </el-table-column>
      <el-table-column label="值" align="center" prop="value">
        <template slot-scope="scope">
          <el-popover
            placement="top-start"
            width="100"
            trigger="hover"
            :disabled="scope.row.value.length <= 10"
          >
            <div>{{ scope.row.value }}</div>
            <span slot="reference" v-if="scope.row.value.length <= 10">{{scope.row.value}}</span>
            <span slot="reference"
                  v-if="scope.row.value.length > 10">{{scope.row.value.substr(0, 10) + "..."}}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="签名" align="center" prop="sign" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:taKuConfig:edit']"
          >修改</el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['platform:taKuConfig:remove']"-->
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

    <!-- 添加或修改taKu广告配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="值" prop="value">
          <el-input v-model="form.value" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="签名" prop="sign" v-if="[2].includes(form.type)">
          <el-input v-model="form.sign" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!--        <el-form-item label="备注" prop="remarks">-->
        <!--          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />-->
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
import { listTaKuConfig, getTaKuConfig, delTaKuConfig, addTaKuConfig, updateTaKuConfig } from "@/api/platform/taKuConfig";

export default {
  name: "TaKuConfig",
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
      // taKu广告配置表格数据
      taKuConfigList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        value: null,
        sign: null,
        remarks: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        value: [{ required: true, message: '值不能为空', trigger: 'blur' }],
        sign: [{ required: true, message: '签名不能为空', trigger: 'blur' }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询taKu广告配置列表 */
    getList() {
      this.loading = true;
      listTaKuConfig(this.queryParams).then(response => {
        this.taKuConfigList = response.rows;
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
        value: null,
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
      this.title = "添加taKu广告配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTaKuConfig(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改taKu广告配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTaKuConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTaKuConfig(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除taKu广告配置编号为"' + ids + '"的数据项？').then(function() {
        return delTaKuConfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/taKuConfig/export', {
        ...this.queryParams
      }, `taKuConfig_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
