<template>
  <div class="app-container">
    <el-form :inline="true" label-width="80px" class="upload-filter">
      <el-form-item label="视频分类" required>
        <el-select
          v-model="classifyId"
          placeholder="请选择分类"
          clearable
          size="small"
          style="width: 200px"
          @change="onClassifyChange"
        >
          <el-option
            v-for="c in categoryList"
            :key="c.id"
            :label="c.name"
            :value="c.id"
          />
        </el-select>
        <el-button
          type="primary"
          plain
          size="small"
          icon="el-icon-plus"
          style="margin-left: 8px"
          @click="openAddCategory"
          >新建分类
        </el-button>
      </el-form-item>
      <el-form-item label="视频系列" required>
        <el-select
          v-model="seriesName"
          placeholder="选择或输入新系列名，回车确认"
          clearable
          filterable
          allow-create
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="name in seriesNameList"
            :key="name"
            :label="name"
            :value="name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="系列封面">
        <el-upload
          class="cover-uploader"
          :action="uploadImgUrl"
          :headers="headers"
          :data="{ type: 'videoCover' }"
          :show-file-list="false"
          :on-success="handleCoverSuccess"
          :before-upload="beforeCoverUpload"
        >
          <img v-if="coverUrl" :src="coverUrl" class="cover-preview" />
          <div v-else class="cover-placeholder">
            <i class="el-icon-plus"></i>
            <span>上传封面</span>
          </div>
        </el-upload>
        <span class="cover-tip">封面将用于合集和娱乐模块展示</span>
      </el-form-item>
    </el-form>

    <el-upload
      ref="videoUpload"
      multiple
      accept="video/*"
      :auto-upload="false"
      :file-list="fileList"
      action="#"
      drag
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将视频拖到此处，或<em>点击选择</em>（可多选，同一系列分集建议按集数命名如「剧名01」）
      </div>
    </el-upload>

    <el-progress
      v-if="uploading"
      :percentage="percent"
      :status="uploadStatus"
      style="margin-top: 16px"
    ></el-progress>

    <div style="margin-top: 16px">
      <el-button type="primary" :loading="uploading" @click="startUpload"
        >开始上传</el-button
      >
      <el-button @click="clearFiles">清空</el-button>
    </div>

    <!-- 新建分类弹窗 -->
    <el-dialog
      title="新建分类"
      :visible.sync="categoryDialogVisible"
      width="400px"
      append-to-body
    >
      <el-form
        ref="categoryFormRef"
        :model="categoryForm"
        :rules="categoryRules"
        label-width="80px"
      >
        <el-form-item label="分类名" prop="name">
          <el-input v-model="categoryForm.name" placeholder="请输入分类名" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCategory">确 定</el-button>
        <el-button @click="categoryDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listVideoCategory, addVideoCategory } from "@/api/platform/videoCategory";
import { batchAddVideo, listSeriesNames } from "@/api/platform/video";
import { getToken } from "@/utils/auth";
import { multipartUploadToCos } from "@/oss/upload";

export default {
  name: "VideoUpload",
  data() {
    return {
      // 分类
      categoryList: [],
      classifyId: null,
      // 系列名（去重列表 + 当前选中）
      seriesNameList: [],
      seriesName: null,
      // 系列封面
      coverUrl: null,
      // 待上传文件
      fileList: [],
      // 上传状态
      uploading: false,
      percent: 0,
      uploadStatus: "",
      // 封面上传地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      headers: { Authorization: "Bearer " + getToken() },
      // 新建分类弹窗
      categoryDialogVisible: false,
      categoryForm: { name: null },
      categoryRules: {
        name: [{ required: true, message: "分类名不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getCategoryList();
    this.getSeriesNameList();
  },
  methods: {
    getCategoryList() {
      listVideoCategory({ pageNum: 1, pageSize: 100 }).then((res) => {
        this.categoryList = res.rows || [];
      });
    },
    getSeriesNameList(classifyId) {
      listSeriesNames(classifyId).then((res) => {
        this.seriesNameList = res.data || [];
      });
    },
    onClassifyChange(val) {
      this.seriesName = null;
      this.getSeriesNameList(val);
    },
    openAddCategory() {
      this.categoryForm = { name: null };
      this.categoryDialogVisible = true;
      this.$nextTick(() => {
        this.$refs.categoryFormRef &&
          this.$refs.categoryFormRef.clearValidate();
      });
    },
    submitCategory() {
      this.$refs.categoryFormRef.validate((valid) => {
        if (!valid) return;
        addVideoCategory(this.categoryForm).then(() => {
          this.$modal.msgSuccess("新建分类成功");
          this.categoryDialogVisible = false;
          this.getCategoryList();
        });
      });
    },
    handleCoverSuccess(res) {
      if (res.code === 200) {
        this.coverUrl = res.url;
        this.$message.success("封面上传成功");
      } else {
        this.$message.error(res.msg || "封面上传失败");
      }
    },
    beforeCoverUpload(file) {
      const isImage = file.type.startsWith("image/");
      const isLt5M = file.size / 1024 / 1024 < 5;
      if (!isImage) {
        this.$message.error("封面只能上传图片格式！");
        return false;
      }
      if (!isLt5M) {
        this.$message.error("封面大小不能超过 5MB！");
        return false;
      }
      return true;
    },
    clearFiles() {
      this.$refs.videoUpload.clearFiles();
    },
    startUpload() {
      const files = (this.$refs.videoUpload.uploadFiles || [])
        .map((f) => f.raw)
        .filter(Boolean);
      if (!this.classifyId) {
        this.$message.warning("请选择视频分类");
        return;
      }
      if (!this.seriesName) {
        this.$message.warning("请选择或输入系列名");
        return;
      }
      if (!files.length) {
        this.$message.warning("请选择要上传的视频");
        return;
      }

      this.uploading = true;
      this.percent = 0;
      this.uploadStatus = "";
      const videos = [];
      let index = 0;

      const uploadOne = () => {
        if (index >= files.length) {
          this.submitBatch(videos);
          return;
        }
        const file = files[index];
        multipartUploadToCos(
          file,
          (result) => {
            videos.push({
              name: file.name.replace(/\.[^.]+$/, ""),
              videoUrl: result.url,
              videoCover: this.coverUrl || null,
            });
            index++;
            this.percent = Math.round((index / files.length) * 100);
            uploadOne();
          },
          (error) => {
            this.uploading = false;
            this.uploadStatus = "exception";
            this.$message.error(
              typeof error === "string" ? error : "视频上传失败"
            );
          },
          () => {}
        );
      };
      uploadOne();
    },
    submitBatch(videos) {
      batchAddVideo({
        seriesName: this.seriesName,
        classifyId: this.classifyId,
        videos: videos,
      })
        .then(() => {
          this.$modal.msgSuccess("上传成功");
          this.uploading = false;
          this.percent = 100;
          this.uploadStatus = "success";
          this.$refs.videoUpload.clearFiles();
          this.getSeriesNameList(this.classifyId);
        })
        .catch(() => {
          this.uploading = false;
          this.uploadStatus = "exception";
        });
    },
  },
};
</script>

<style scoped>
.upload-filter {
  margin-bottom: 8px;
}
.cover-uploader {
  display: inline-block;
  vertical-align: middle;
}
.cover-preview {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
  display: block;
}
.cover-placeholder {
  width: 80px;
  height: 80px;
  border: 1px dashed #d9d9d9;
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #8c939d;
  font-size: 12px;
}
.cover-placeholder i {
  font-size: 24px;
  margin-bottom: 4px;
}
.cover-tip {
  display: block;
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}
</style>
