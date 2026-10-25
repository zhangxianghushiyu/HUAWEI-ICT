<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="电子书ID" prop="ebookId">
        <el-input
          v-model="queryParams.ebookId"
          placeholder="请输入电子书ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入分类ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
            <el-form-item label="小说" prop="ebookTitle">
              <el-input
                v-model="queryParams.ebookTitle"
                placeholder="请输入小说名称"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="分类" prop="categoryName">
              <el-input
                v-model="queryParams.categoryName"
                placeholder="请输入分类名称"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['platform:ebookCategoryInfo:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['platform:ebookCategoryInfo:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['platform:ebookCategoryInfo:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:ebookCategoryInfo:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ebookCategoryInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index"/>
      <el-table-column label="电子书ID" align="center" prop="ebookId" />
      <el-table-column label="电子书" align="center" prop="ebookTitle" />
      <el-table-column label="封面图" align="center" prop="ebookUrl">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.ebookUrl"
            :preview-src-list="[scope.row.ebookUrl]"
            fit="cover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="分类ID" align="center" prop="categoryId" />
      <el-table-column label="分类" align="center" prop="categoryName" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['platform:ebookCategoryInfo:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['platform:ebookCategoryInfo:remove']"
          >删除</el-button>
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

    <!-- 添加或修改分类及电子书关联对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="电子书ID" prop="ebookId">
          <el-input v-model="form.ebookId" placeholder="请输入电子书ID" />
        </el-form-item>
        <el-form-item label="分类ID" prop="categoryIdStr">
          <el-input v-model="form.categoryIdStr" placeholder="请输入分类ID，可使用英文逗号拼接多个" />
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
  listEbookCategoryInfo,
  getEbookCategoryInfo,
  delEbookCategoryInfo,
  updateEbookCategoryInfo,
  addEbookCategoryInfoInfo
} from "@/api/platform/ebookCategoryInfo";

export default {
  name: "EbookCategoryInfo",
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
      // 分类及电子书关联表格数据
      ebookCategoryInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ebookId: null,
        categoryId: null,
        categoryName:null,
        ebookTitle: null,
        categoryIdStr:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ebookId: [{ required: true, message: '电子书ID不能为空', trigger: 'blur' }],
        categoryIdStr: [{ required: true, message: '分类ID不能为空', trigger: 'blur' }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询分类及电子书关联列表 */
    getList() {
      this.loading = true;
      listEbookCategoryInfo(this.queryParams).then(response => {
        this.ebookCategoryInfoList = response.rows;
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
        ebookId: null,
        categoryId: null,
        createTime: null,
        updateTime: null,
        categoryIdStr:null
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
      this.title = "添加分类及电子书关联";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEbookCategoryInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分类及电子书关联";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEbookCategoryInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // addEbookCategoryInfo(this.form).then(response => {
            //   this.$modal.msgSuccess("新增成功");
            //   this.open = false;
            //   this.getList();
            // });
            addEbookCategoryInfoInfo(this.form.ebookId,this.form.categoryIdStr).then(response => {
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
      this.$modal.confirm('是否确认删除数据项？').then(function() {
        return delEbookCategoryInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/ebookCategoryInfo/export', {
        ...this.queryParams
      }, `ebookCategoryInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
