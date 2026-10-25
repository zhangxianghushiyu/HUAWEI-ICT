<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <!--      <el-form-item label="号" prop="versionNo">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.versionNo"-->
      <!--          placeholder="请输入号"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="说明" prop="releaseNote">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.releaseNote"-->
      <!--          placeholder="请输入说明"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="下载地址" prop="appUrl">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.appUrl"-->
      <!--          placeholder="请输入下载地址"-->
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
      <!--          v-hasPermi="['platform:version:add']"-->
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
      <!--          v-hasPermi="['platform:version:edit']"-->
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
      <!--          v-hasPermi="['platform:version:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['platform:version:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="aliPayConfigList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" align="center" type="index"/>
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <!--      <el-table-column label="类型" align="center" prop="type">-->
      <!--        <template slot-scope="scope">-->
      <!--          <dict-tag :options="dict.type.params_config_type" :value="scope.row.type"/>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column label="副类型" align="center" prop="followType" />-->
      <!--      <el-table-column label="值" align="center" prop="value" />-->
<!--      <el-table-column label="备注" align="center" prop="remarks"/>-->
      <el-table-column align="center" prop="remarks" label="备注">
        <template slot-scope="scope">
          <span  style="color: #ff4949;font-weight: bold">{{scope.row.remarks}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="值"
        align="center"
        prop="value"
      >
        <template slot-scope="scope">
          <el-popover
            placement="top-start"
            width="100"
            trigger="hover"
            :disabled="scope.row.value.length <= 5"
          >
            <div>{{ scope.row.value }}</div>
            <span slot="reference" v-if="scope.row.value.value <= 1">{{
                scope.row.value
              }}</span>
            <span slot="reference" v-if="scope.row.value.length > 1">{{
                scope.row.value.substr(0, 10) + '...'
              }}</span>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['platform:paramsConfig:remove']"-->
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

    <!-- 添加或修改信息对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="值" prop="value">
          <el-input v-model="form.value" placeholder="请输入值" type="textarea"/>
        </el-form-item>
        <el-form-item label="文件" prop="value"
                      v-if="[17,18,19].includes(form.followType) ">
          <el-upload
            class="upload-demo"
            drag
            :action="uploadUrl"
            :headers="headers"
            :data="{ type: 'aliPayConfig' }"
            :on-success="handleFileSuccess"
            :before-upload="beforeFileUpload"
            :on-remove="handleRemove"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div class="el-upload__tip" slot="tip">
              不超过500mb
            </div>
          </el-upload>
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
  listAliPayConfig,
  getAliPayConfig,
  delAliPayConfig,
  addAliPayConfig,
  updateAliPayConfig
} from "@/api/platform/aliPayConfig";
import {getToken} from "@/utils/auth";

export default {
  name: "AliPayConfig",
  dicts: ['params_config_type'],
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
      // 信息表格数据
      aliPayConfigList: [],
      // 弹出层标题
      title: "",
      //上传的图片服务器地址
      uploadUrl: process.env.VUE_APP_BASE_API + "/common/uploadLocal",
      //请求头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        versionNo: null,
        releaseNote: null,
        appUrl: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        value: [{ required: true, message: '值不能为空', trigger: 'blur' }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询信息列表 */
    getList() {
      this.loading = true;
      listAliPayConfig(this.queryParams).then(response => {
        this.aliPayConfigList = response.rows;
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
        versionNo: null,
        releaseNote: null,
        appUrl: null,
        createTime: null,
        updateTime: null,
        url: null
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
      this.title = "添加信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAliPayConfig(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAliPayConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAliPayConfig(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除信息编号为"' + ids + '"的数据项？').then(function () {
        return delAliPayConfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/aliPayConfig/export', {
        ...this.queryParams
      }, `version_${new Date().getTime()}.xlsx`)
    },
    handleFileSuccess(res, file) {
      if (res.code === 200) {
        this.form.value = res.url;
      } else {
        this.$message.error(res.msg);
      }
    },
    beforeFileUpload(file) {
      console.log(file.type);
      // const isJPG =
      //   file.type === "application/vnd.android.package-archive" ||
      //   "application/vnd.iphone";
      const isLt500M = file.size / 1024 / 1024 < 500;

      // if (!isJPG) {
      //   this.$message.error("上传图片只能是 apk或ipa 格式!");
      // }
      if (!isLt500M) {
        this.$message.error("上传图片大小不能超过 500MB!");
      }
      return isLt500M;
    },
    // 删除图片
    handleRemove(file, fileList) {
      /* const findex = this.fileList.map(f => f.name).indexOf(file.name)
      if (findex > -1) {
        this.fileList.splice(findex, 1)
        this.$emit('input', this.listToString(this.fileList))
      } */
      this.img = "";
    },

  }
};
</script>
<style>
.title-image-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.image-uploader .el-upload:hover {
  border-color: #409eff;
}

.title-image-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 80px;
  height: 80px;
  line-height: 80px;
  text-align: center;
}

.title-image {
  width: 80px;
  height: 80px;
  display: block;
}

.title-image-uploader {
  width: 80px;
  height: 80px;
  display: block;
}
</style>
}
};
