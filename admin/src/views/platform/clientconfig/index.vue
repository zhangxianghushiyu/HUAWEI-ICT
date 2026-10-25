<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="clientConfigList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column align="center" prop="remarks" label="备注">
        <template slot-scope="scope">
          <span
            v-if="scope.row.type === 5"
            style="color: #ff4949; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 6"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 7"
            style="color: #ffba00; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 8"
            style="color: #ff4949; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 10"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 11"
            style="color: #ffba00; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 12"
            style="color: #ff4949; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 13"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 14"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 15"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 16"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 17"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
          <span
            v-if="scope.row.type === 18"
            style="color: #00afff; font-weight: bold"
            >{{ scope.row.remarks }}</span
          >
        </template>
      </el-table-column>
      <el-table-column label="内容" align="center" prop="value">
        <template slot-scope="scope">
          <div v-if="scope.row.followType === 20">
            <el-image
              :preview-src-list="[scope.row.value]"
              :src="scope.row.value"
              style="width: 70px"
            ></el-image>
          </div>
          <div v-if="scope.row.followType === 17">
            <el-image
              :preview-src-list="[scope.row.value]"
              :src="scope.row.value"
              style="width: 70px"
            ></el-image>
          </div>
          <div
            v-if="scope.row.followType !== 20 && scope.row.followType !== 17"
          >
            <span>{{ scope.row.value }}</span>
          </div>
        </template>
      </el-table-column>

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
            >修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" />

    <!-- 添加或修改信息对话框 -->
    <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="值" prop="value">
          <el-input
            v-model="form.value"
            placeholder="请输入值"
            type="textarea"
          />
        </el-form-item>
        <el-form-item
          label="头像"
          prop="value"
          v-if="[20].includes(form.followType)"
        >
          <el-upload
            class="title-image-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :data="{ type: 'titleImg' }"
            :show-file-list="false"
            :on-success="handleBannerImgSuccess"
            :before-upload="beforeBannerImgUpload"
            :on-remove="handleRemove"
          >
            <img v-if="form.value" :src="form.value" class="title-image" />
            <i v-else class="el-icon-plus title-image-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item
          label="图片"
          prop="value"
          v-if="[17].includes(form.followType)"
        >
          <el-upload
            class="title-image-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :data="{ type: 'titleImg' }"
            :show-file-list="false"
            :on-success="handleBannerImgSuccess"
            :before-upload="beforeBannerImgUpload"
            :on-remove="handleRemove"
          >
            <img v-if="form.value" :src="form.value" class="title-image" />
            <i v-else class="el-icon-plus title-image-uploader-icon"></i>
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
  listClientConfig,
  getClientConfig,
  updateClientConfig,
} from "@/api/platform/clientConfig";
import { getToken } from "@/utils/auth";

export default {
  name: "AliPayConfig",
  dicts: ["params_config_type"],
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
      clientConfigList: [],
      // 弹出层标题
      title: "",
      //上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      //请求头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 15,
        versionNo: null,
        releaseNote: null,
        appUrl: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        value: [{ required: true, message: "值不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询信息列表 */
    getList() {
      this.loading = true;
      listClientConfig(this.queryParams).then((response) => {
        this.clientConfigList = response.rows;
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
        url: null,
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
      this.title = "添加信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getClientConfig(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateClientConfig(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
          }
        }
      });
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "platform/aliPayConfig/export",
        {
          ...this.queryParams,
        },
        `version_${new Date().getTime()}.xlsx`
      );
    },
    handleFileSuccess(res, file) {
      if (res.code === 200) {
        this.form.value = res.url;
      } else {
        this.$message.error(res.msg);
      }
    },
    handleBannerImgSuccess(res, file) {
      if (res.code === 200) {
        this.form.value = res.url;
      } else {
        this.$message.error(res.msg);
      }
    },
    beforeBannerImgUpload(file) {
      const isJPG = file.type === "image/jpeg" || "image/png";
      const isLt2M = file.size / 1024 / 1024 < 5;

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG或PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 5MB!");
      }
      return isJPG && isLt2M;
    },
    // 删除图片
    handleRemove(file, fileList) {
      /* const findex = this.fileList.map(f => f.name).indexOf(file.name)
      if (findex > -1) {
        this.fileList.splice(findex, 1)
        this.$emit('input', this.listToString(this.fileList))
      } */
      this.url = "";
    },
  },
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
} };
