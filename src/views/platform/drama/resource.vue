<template>
  <div>
    <!-- 添加或修改短剧信息对话框 -->
    <el-dialog title="短剧资源" :visible.sync="open" width="90%" append-to-body :close-on-click-modal="false">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="openEditModalBtn(0)">新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" plain icon="el-icon-caret-bottom" size="mini" @click="dramaDetailsUndercarriageFn">
            下架
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="success" plain icon="el-icon-caret-top" size="mini" @click="dramaDetailsGroundingFn">上架
          </el-button>
        </el-col>
        <el-col :span="1.5">
<!--          <el-button-->
<!--            type="warning"-->
<!--            plain-->
<!--            icon="el-icon-download"-->
<!--            size="mini"-->
<!--            @click="handleExport"-->
<!--          >导出-->
<!--          </el-button>-->
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleDelete">删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-upload2"
                     size="mini" @click="handleImport"
          >导入
          </el-button
          >
        </el-col>
        <!--        <el-col :span="1.5">-->
        <!--          <el-button-->
        <!--            type="success"-->
        <!--            plain-->
        <!--            icon="el-icon-edit"-->
        <!--            size="mini"-->
        <!--            @click="openEditModalBtn(1)"-->
        <!--          >修改-->
        <!--          </el-button>-->
        <!--        </el-col>-->

        <!--        <el-col :span="1.5">-->
        <!--          <el-button-->
        <!--            type="warning"-->
        <!--            plain-->
        <!--            icon="el-icon-download"-->
        <!--            size="mini"-->
        <!--            @click="handleExport"-->
        <!--          >导出-->
        <!--          </el-button>-->
        <!--        </el-col>-->
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <el-table v-loading="loading" :data="dramaDetailsList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="序号" align="center" type="index"/>

        <!--        <el-table-column label="主键" align="center" prop="id"/>-->
        <el-table-column label="名称" align="center" prop="name"/>
        <el-table-column label="当前集数" align="center" prop="gather"/>
        <el-table-column property="videoUrl" label="短剧" width="145">
          <template slot-scope="scope">
            <video :src="scope.row.videoUrl" controls style="width: 100%"></video>
          </template>
        </el-table-column>
        <el-table-column label="封面图" align="center" prop="titleImg">
          <template slot-scope="scope">
            <el-image style="width: 100px; height: 100px" :src="scope.row.titleImg"
                      :preview-src-list="[scope.row.titleImg]" fit="cover"></el-image>
          </template>
        </el-table-column>
        <!--        <el-table-column label="介绍" align="center" prop="content"/>-->
        <el-table-column label="介绍" align="center" prop="content" width="110">
          <template slot-scope="scope">
            <el-popover placement="top-start" width="100" trigger="hover" :disabled="scope.row.content.length <= 5">
              <div>{{ scope.row.content }}</div>
              <span slot="reference" v-if="scope.row.content.length <= 5">{{
                  scope.row.content
                }}</span>
              <span slot="reference" v-if="scope.row.content.length > 5">{{
                  scope.row.content.substr(0, 5) + "..."
                }}</span>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column label="点赞数" align="center" prop="likeCount"/>
        <el-table-column label="收藏量" align="center" prop="collectCount"/>
        <el-table-column label="创建时间" align="center" prop="createTime"/>
        <!--        <el-table-column label="删除状态" align="center">-->
        <!--          <template slot-scope="scope">-->
        <!--            <el-tag :type="scope.row.delStatus ===0 ?'danger':'success'">{{-->
        <!--                scope.row.delStatus === 0 ? '删除' : '正常'-->
        <!--              }}-->
        <!--            </el-tag>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <el-table-column label="是否上架" align="center" prop="">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 0 ? '' : 'success'">{{ scope.row.status === 0 ? "否" : "是" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="是否免费" align="center" prop="">
          <template slot-scope="scope">
            <el-tag :type="scope.row.isPrice === 0 ? '' : 'success'">{{ scope.row.isPrice === 0 ? "是" : "否" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="text" icon="el-icon-edit" @click="openEditModal(scope.row)">修改
            </el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                  @pagination="getList"/>
    </el-dialog>

    <!-- 添加或修改短剧信息对话框 -->
    <el-dialog :title="title" :visible.sync="modalOpen" width="800px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="短剧名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入标题"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="当前集数" prop="gather">
              <el-input v-model="form.gather" placeholder="请输入当前集数"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="短剧链接" prop="videoUrl">
              <el-input v-model="form.videoUrl" placeholder="请输入短剧链接"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="封面链接" prop="titleImg">
              <el-input v-model="form.titleImg" placeholder="请输入封面链接"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="短剧" prop="videoUrl">
              <!--          <el-input v-model="form.videoUrl" placeholder="请输入短剧地址"/>-->
              <el-upload class="title-image-uploader" :action="uploadImgUrl" :headers="headers" :data="{ type: 'videoUrl' }"
                         :show-file-list="false" :on-success="handleVideoSuccess" :before-upload="beforeVideoUpload"
                         :on-progress="handleVideoProgress" :on-error="handleVideoError"
                         :on-remove="handleVideoRemove" v-loading="uploading">
                <video v-if="form.videoUrl" :src="form.videoUrl" class="title-image"/>
                <i v-else class="el-icon-plus title-image-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="封面图" prop="titleImg">
              <el-upload class="title-image-uploader" :action="uploadImgUrl" :headers="headers" :data="{ type: 'titleImg' }"
                         :show-file-list="false" :on-success="handleBannerImgSuccess" :before-upload="beforeBannerImgUpload"
                         :on-remove="handleRemove">
                <img v-if="form.titleImg" :src="form.titleImg" class="title-image"/>
                <i v-else class="el-icon-plus title-image-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        <el-form-item label="介绍">-->
        <!--          <editor v-model="form.content" :min-height="192"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="介绍" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入介绍"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="点赞数" prop="likeCount">
              <el-input v-model="form.likeCount" placeholder="请输入点赞数"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收藏量" prop="collectCount">
              <el-input v-model="form.collectCount" placeholder="请输入收藏量"/>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        <el-form-item label="播放量" prop="playCount">-->
        <!--          <el-input v-model="form.playCount" placeholder="请输入播放量"/>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="删除状态" prop="delStatus">-->
        <!--          <el-select v-model="form.delStatus" placeholder="请选择删除状态" style="width: 100%">-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.del_status"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--              :value="parseInt(dict.value)"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否上架" prop="status">
              <el-select v-model="form.status" placeholder="请选择是否上架" style="width: 100%">
                <el-option v-for="dict in dict.type.up_down" :key="dict.value" :label="dict.label"
                           :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否免费" prop="isPrice">
              <el-select v-model="form.isPrice" placeholder="请选择是否免费" style="width: 100%">
                <el-option v-for="dict in dict.type.is_price" :key="dict.value" :label="dict.label"
                           :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancelEditModal">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="上传文件进度" :visible.sync="progressVisible" :show-close="false" :close-on-press-escape="false"
               :close-on-click-modal="false" width="30%">
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
        :action="upload.url + '?updateSupport=' + upload.updateSupport+'&dramaId='+this.queryParams.dramaId"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">一次提交模版数据最多1000<br>将文件拖到此处，或<em>点击上传</em></div>
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
          </el-link
          >
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
import {getToken} from "@/utils/auth";

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
        dramaId: null,
        collectCount: null,
        publicizeCount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "短剧名称不能为空", trigger: "blur"},
        ],
        videoUrl: [
          {required: true, message: "短剧地址不能为空", trigger: "blur"},
        ],
        titleImg: [
          {required: true, message: "封面图不能为空", trigger: "blur"},
        ],
        content: [{required: true, message: "内容不能为空", trigger: "blur"}],
        status: [
          {required: true, message: "是否上架不能为空", trigger: "blur"},
        ],
        isPrice: [
          {required: true, message: "是否收费不能为空", trigger: "blur"},
        ],
        gather: [
          {required: true, message: "当前集数不能为空", trigger: "blur"},
        ],
        likeCount: [
          {required: true, message: "点赞量不能为空", trigger: "blur"},
        ],
        collectCount: [
          {required: true, message: "收藏量不能为空", trigger: "blur"},
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
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/platform/dramaDetails/importData",
      },
      progressVisible: false,
      percent: 0
    };
  },

  methods: {
    openModal(id) {
      this.open = true;
      this.queryParams.dramaId = id;
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
      listDramaDetails(this.queryParams).then((response) => {
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
      this.progressVisible = false;
      this.uploading = false;
      if (res.code === 200) {
        this.form.videoUrl = res.url;
        this.$modal.msgSuccess("视频上传成功");
      } else {
        this.$message.error(res.msg);
      }
    },
    handleVideoProgress(event, file, fileList) {
      this.progressVisible = true;
      this.percent = (event.percent || 0).toFixed(0);
    },
    handleVideoError(err, file, fileList) {
      this.progressVisible = false;
      this.uploading = false;
      this.$message.error("视频上传失败");
    },
    beforeVideoUpload(file) {
      const isVideo = file.type.indexOf("video") > -1;
      if (!isVideo) {
        this.$message.error("请上传视频格式文件!");
        return false;
      }
      const isLt1G = file.size / 1024 / 1024 < 1024;
      if (!isLt1G) {
        this.$message.error("上传视频大小不能超过 1GB!");
        return false;
      }
      this.uploading = true;
      return true;
    },
    // 删除视频
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
            updateDramaDetails(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.modalOpen = false;

              this.getList();
            });
          } else {
            this.form.dramaId = this.queryParams.dramaId;
            addDramaDetails(this.form).then((response) => {
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
          return delDramaDetails(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {
        });
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
        .catch(() => {
        });
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
        .catch(() => {
        });
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
        {dangerouslyUseHTMLString: true}
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
