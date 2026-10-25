<template>
  <div>
    <!-- 添加或修改短剧信息对话框 -->
    <el-dialog
      title="短剧分类"
      :visible.sync="open"
      width="90%"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="openEditModalBtn(0)"
            >新增
          </el-button>
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>
      <el-table
        v-loading="loading"
        :data="dramaDetailsList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center" type="index" />
        <el-table-column label="名称" align="center" prop="name" />
        <el-table-column label="创建时间" align="center" prop="createTime" />
        <el-table-column label="修改时间" align="center" prop="updateTime" />
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
              @click="openEditModal(scope.row)"
              >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              >删除
            </el-button>
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
    </el-dialog>

    <!-- 添加或修改短剧信息对话框 -->
    <el-dialog
      title="短剧分类"
      :visible.sync="modalOpen"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="分类名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入标题" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancelEditModal">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="上传文件进度"
      :visible.sync="progressVisible"
      :show-close="false"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="30%"
    >
      <el-progress :percentage="percent"></el-progress>
    </el-dialog>

    <!-- 数据导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="
          upload.url +
          '?updateSupport=' +
          upload.updateSupport +
          '&dramaId=' +
          this.queryParams.dramaId
        "
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          一次提交模版数据最多1000<br />将文件拖到此处，或<em>点击上传</em>
        </div>
        <div class="el-upload__tip text-center" slot="tip">
          <!--          <div class="el-upload__tip" slot="tip">-->
          <!--            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据-->
          <!--          </div>-->
          <!--          <span>仅允许导入xls、xlsx格式文件。</span>-->
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listDramaDetails,
  delDramaDetails,
  addDramaDetails,
  updateDramaDetails,
  dramaDetailsUndercarriage,
  dramaDetailsGrounding,
} from "@/api/platform/dramaDetails";
import {
  listDramaCategory,
  getDramaCategory,
  delDramaCategory,
  addDramaCategory,
  updateDramaCategory,
} from "@/api/platform/dramaCategory";
import { getToken } from "@/utils/auth";

export default {
  name: "resource",
  dicts: ["del_status", "is_price", "up_down"],
  data() {
    return {
      // 遮罩层
      loading: false,
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
      uploading: false,
      //添加弹出层
      modalOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentId: 0,
        dramaId: null,
        collectCount: null,
        publicizeCount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "短剧名称不能为空", trigger: "blur" },
        ],
        videoUrl: [
          { required: true, message: "短剧地址不能为空", trigger: "blur" },
        ],
        titleImg: [
          { required: true, message: "封面图不能为空", trigger: "blur" },
        ],
        content: [{ required: true, message: "内容不能为空", trigger: "blur" }],
        status: [
          { required: true, message: "是否上架不能为空", trigger: "blur" },
        ],
        isPrice: [
          { required: true, message: "是否收费不能为空", trigger: "blur" },
        ],
        gather: [
          { required: true, message: "当前集数不能为空", trigger: "blur" },
        ],
        likeCount: [
          { required: true, message: "点赞量不能为空", trigger: "blur" },
        ],
        collectCount: [
          { required: true, message: "收藏量不能为空", trigger: "blur" },
        ],
      },
      //上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      //请求头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 数据导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/platform/dramaDetails/importData",
      },
      progressVisible: false,
      percent: 0,
    };
  },

  methods: {
    openModal(id) {
      this.open = true;
      this.queryParams.parentId = id;
      this.dramaDetailsList = [];
      this.getList();
    },
    openEditModal(row = null) {
      this.modalOpen = true;
      this.reset();
      if (row) this.form = row;
      this.title = "修改短剧信息";
    },
    openEditModalBtn(type) {
      if (this.ids.length !== 1 && type === 1) {
        this.$message.error("请至少选中一条数据!");
        return;
      }
      this.modalOpen = true;
      this.reset();
      if (type === 1) {
        this.form = this.ids[0];
        this.title = "修改短剧信息";
      } else {
        this.title = "添加短剧信息";
      }
    },
    // 取消按钮
    cancelEditModal() {
      this.modalOpen = false;
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
        updateTime: null,
        gather: null,
        collectCount: null,
        publicizeCount: null,
      };
      this.resetForm("form");
    },
    getList() {
      this.loading = true;
      listDramaCategory(this.queryParams).then((response) => {
        console.log(response);
        this.dramaDetailsList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(this.dramaDetailsList, "========");
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection;
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleBannerImgSuccess(res, file) {
      if (res.code === 200) {
        this.form.titleImg = res.url;
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
    handleVideoSuccess(res, file) {
      if (res.code === 200) {
        this.uploading = false;
        this.form.videoUrl = res.url;
        console.log(this.form.videoUrl);
      } else {
        this.$message.error(res.msg);
      }
    },
    beforeVideoUpload(file) {
      this.uploading = true;
      return true;
    },
    // 删除图片
    handleVideoRemove(file, fileList) {
      /* const findex = this.fileList.map(f => f.name).indexOf(file.name)
      if (findex > -1) {
        this.fileList.splice(findex, 1)
        this.$emit('input', this.listToString(this.fileList))
      } */
      this.url = "";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateDramaCategory(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.modalOpen = false;
              this.getList();
            });
          } else {
            this.form.parentId = this.queryParams.parentId;
            addDramaCategory(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.modalOpen = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids.map((it) => it.id);
      this.$modal
        .confirm("是否确认删除短剧资源的数据项？")
        .then(function () {
          return delDramaCategory(ids);
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
        "platform/dramaDetails/export",
        {
          ...this.queryParams,
        },
        `dramaDetails_${new Date().getTime()}.xlsx`
      );
    },
    /** 下架按钮操作 */
    dramaDetailsUndercarriageFn() {
      const ids = this.ids.map((it) => it.id);
      // 编号为"' + ids + '"
      this.$modal
        .confirm("是否确认下架短剧资源数据项？")
        .then(function () {
          return dramaDetailsUndercarriage(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("下架成功");
        })
        .catch(() => {});
    },

    /** 上架按钮操作 */
    dramaDetailsGroundingFn(row) {
      const ids = this.ids.map((it) => it.id);
      // 编号为"' + ids + '"
      this.$modal
        .confirm("是否确认上架短剧资源数据项？")
        .then(function () {
          return dramaDetailsGrounding(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("上架成功");
        })
        .catch(() => {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "platform/dramaDetails/importTemplate",
        {},
        `dramaDetails_template_${new Date().getTime()}.xlsx`
      );
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>

<style scoped lang="scss"></style>
