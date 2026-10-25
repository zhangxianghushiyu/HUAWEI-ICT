<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="短剧ID" prop="dramaId">
        <el-input
          v-model="queryParams.dramaId"
          placeholder="请输入短剧ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="短剧名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入短剧名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="短剧地址" prop="videoUrl">
        <el-input
          v-model="queryParams.videoUrl"
          placeholder="请输入短剧地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="封面图" prop="titleImg">
        <el-input
          v-model="queryParams.titleImg"
          placeholder="请输入封面图"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="点赞数" prop="likeCount">
        <el-input
          v-model="queryParams.likeCount"
          placeholder="请输入点赞数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="播放量" prop="playCount">
        <el-input
          v-model="queryParams.playCount"
          placeholder="请输入播放量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="删除状态" prop="delStatus">
        <el-select v-model="queryParams.delStatus" placeholder="请选择删除状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.del_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否上架" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择是否上架" clearable size="small">
          <el-option
            v-for="dict in dict.type.up_down"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入价格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否收费" prop="isPrice">
        <el-select v-model="queryParams.isPrice" placeholder="请选择是否收费" clearable size="small">
          <el-option
            v-for="dict in dict.type.is_price"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['platform:dramaDetails:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['platform:dramaDetails:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['platform:dramaDetails:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['platform:dramaDetails:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dramaDetailsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="短剧ID" align="center" prop="dramaId" />
      <el-table-column label="短剧名称" align="center" prop="name" />
      <el-table-column label="短剧地址" align="center" prop="videoUrl" />
      <el-table-column label="封面图" align="center" prop="titleImg" />
      <el-table-column label="介绍" align="center" prop="content" />
      <el-table-column label="点赞数" align="center" prop="likeCount" />
      <el-table-column label="播放量" align="center" prop="playCount" />
      <el-table-column label="删除状态" align="center" prop="delStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.del_status" :value="scope.row.delStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="是否上架" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.up_down" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="是否收费" align="center" prop="isPrice">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_price" :value="scope.row.isPrice"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:dramaDetails:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['platform:dramaDetails:remove']"
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

    <!-- 添加或修改短剧详细对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="短剧ID" prop="dramaId">
          <el-input v-model="form.dramaId" placeholder="请输入短剧ID" />
        </el-form-item>
        <el-form-item label="短剧名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入短剧名称" />
        </el-form-item>
        <el-form-item label="短剧地址" prop="videoUrl">
          <el-input v-model="form.videoUrl" placeholder="请输入短剧地址" />
        </el-form-item>
        <el-form-item label="封面图" prop="titleImg">
          <el-input v-model="form.titleImg" placeholder="请输入封面图" />
        </el-form-item>
        <el-form-item label="介绍">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="点赞数" prop="likeCount">
          <el-input v-model="form.likeCount" placeholder="请输入点赞数" />
        </el-form-item>
        <el-form-item label="播放量" prop="playCount">
          <el-input v-model="form.playCount" placeholder="请输入播放量" />
        </el-form-item>
        <el-form-item label="删除状态" prop="delStatus">
          <el-select v-model="form.delStatus" placeholder="请选择删除状态">
            <el-option
              v-for="dict in dict.type.del_status"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否上架" prop="status">
          <el-select v-model="form.status" placeholder="请选择是否上架">
            <el-option
              v-for="dict in dict.type.up_down"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="是否收费" prop="isPrice">
          <el-select v-model="form.isPrice" placeholder="请选择是否收费">
            <el-option
              v-for="dict in dict.type.is_price"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
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
import { listDramaDetails, getDramaDetails, delDramaDetails, addDramaDetails, updateDramaDetails } from "@/api/platform/dramaDetails";

export default {
  name: "DramaDetails",
  dicts: ['del_status', 'is_price', 'up_down'],
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
      // 短剧详细表格数据
      dramaDetailsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dramaId: null,
        name: null,
        videoUrl: null,
        titleImg: null,
        content: null,
        likeCount: null,
        playCount: null,
        delStatus: null,
        status: null,
        price: null,
        isPrice: null,
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
    /** 查询短剧详细列表 */
    getList() {
      this.loading = true;
      listDramaDetails(this.queryParams).then(response => {
        this.dramaDetailsList = response.rows;
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
        dramaId: null,
        name: null,
        videoUrl: null,
        titleImg: null,
        content: null,
        likeCount: null,
        playCount: null,
        delStatus: null,
        status: null,
        price: null,
        isPrice: null,
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
      this.title = "添加短剧详细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDramaDetails(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改短剧详细";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDramaDetails(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDramaDetails(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除短剧详细编号为"' + ids + '"的数据项？').then(function() {
        return delDramaDetails(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/dramaDetails/export', {
        ...this.queryParams
      }, `dramaDetails_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
