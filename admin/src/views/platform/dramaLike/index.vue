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
      <el-form-item label="标题" prop="dramaTitle">
        <el-input
          v-model="queryParams.dramaTitle"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="短剧" prop="dramaDetailsName">
        <el-input
          v-model="queryParams.dramaDetailsName"
          placeholder="请输入短剧名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="短剧详细ID" prop="dramaDetailsId">-->
<!--        <el-input-->
<!--          v-model="queryParams.dramaDetailsId"-->
<!--          placeholder="请输入短剧详细ID"-->
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
<!--          v-hasPermi="['platform:dramaLike:add']"-->
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
<!--          v-hasPermi="['platform:dramaLike:edit']"-->
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
<!--          v-hasPermi="['platform:dramaLike:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:dramaLike:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dramaLikeList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index"/>
<!--      <el-table-column label="用户ID" align="center" prop="userId" />-->
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column property="avatar" label="头像" align="center">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="dramaTitle" />
<!--      <el-table-column label="短剧封面" align="center" prop="dramaTitleImg" />-->
      <el-table-column label="封面" align="center" prop="dramaTitleImg">
        <template slot-scope="scope">
          <el-image :preview-src-list="[scope.row.dramaTitleImg]" :src="scope.row.dramaTitleImg" style="width: 70px"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="短剧" align="center" prop="dramaDetailsName" />
      <!--      <el-table-column label="短剧封面" align="center" prop="dramaTitleImg" />-->
      <el-table-column label="短剧封面" align="center" prop="dramaDetailsTitleImg">
        <template slot-scope="scope">
          <el-image :preview-src-list="[scope.row.dramaDetailsTitleImg]" :src="scope.row.dramaDetailsTitleImg" style="width: 70px"></el-image>
        </template>
      </el-table-column>
<!--      <el-table-column label="短剧详细ID" align="center" prop="dramaDetailsId" />-->
      <el-table-column label="点赞时间" align="center" prop="createTime" />
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['platform:dramaLike:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['platform:dramaLike:remove']"-->
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

    <!-- 添加或修改短剧点赞对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="短剧详细ID" prop="dramaDetailsId">
          <el-input v-model="form.dramaDetailsId" placeholder="请输入短剧详细ID" />
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
import { listDramaLike, getDramaLike, delDramaLike, addDramaLike, updateDramaLike } from "@/api/platform/dramaLike";

export default {
  name: "DramaLike",
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
      // 短剧点赞表格数据
      dramaLikeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        nickName:null,
        dramaTitle:null,
        dramaDetailsName:null,
        dramaDetailsId: null,
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
    /** 查询短剧点赞列表 */
    getList() {
      this.loading = true;
      listDramaLike(this.queryParams).then(response => {
        this.dramaLikeList = response.rows;
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
        dramaDetailsId: null,
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
      this.title = "添加短剧点赞";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDramaLike(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改短剧点赞";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDramaLike(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDramaLike(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除短剧点赞编号为"' + ids + '"的数据项？').then(function() {
        return delDramaLike(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/dramaLike/export', {
        ...this.queryParams
      }, `dramaLike_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
