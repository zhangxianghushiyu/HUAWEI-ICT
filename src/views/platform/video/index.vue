<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="视频名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入视频名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="classifyId">
        <el-select
          v-model="queryParams.classifyId"
          placeholder="请选择分类"
          clearable
          size="small"
          style="width: 160px"
        >
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="系列" prop="seriesName">
        <el-select
          v-model="queryParams.seriesName"
          placeholder="请选择系列"
          clearable
          filterable
          size="small"
          style="width: 200px"
        >
          <el-option
            v-for="name in seriesNameList"
            :key="name"
            :label="name"
            :value="name"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="播放量" prop="playCount">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.playCount"-->
      <!--          placeholder="请输入播放量"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="点赞量" prop="likeCount">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.likeCount"-->
      <!--          placeholder="请输入点赞量"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="视频路径" prop="videoUrl">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.videoUrl"-->
      <!--          placeholder="请输入视频路径"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="视频封面" prop="videoCover">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.videoCover"-->
      <!--          placeholder="请输入视频封面"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="是否上架" prop="status">-->
      <!--        <el-select v-model="queryParams.status" placeholder="请选择是否上架" clearable size="small">-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.up_down"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="删除状态" prop="delStatus">-->
      <!--        <el-select v-model="queryParams.delStatus" placeholder="请选择删除状态" clearable size="small">-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.del_status"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['platform:video:add']"
          >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-bottom"
          size="mini"
          :disabled="multiple"
          @click="undercarriage"
          v-hasPermi="['platform:video:remove']"
          >下架
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-top"
          size="mini"
          :disabled="multiple"
          @click="grounding"
          v-hasPermi="['platform:video:remove']"
          >上架
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['platform:video:edit']"-->
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
          v-hasPermi="['platform:video:remove']"
          >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-top"
          size="mini"
          :disabled="!queryParams.classifyId"
          @click="handleClassifyGrounding"
          v-hasPermi="['platform:video:remove']"
          >分类上架
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-bottom"
          size="mini"
          :disabled="!queryParams.classifyId"
          @click="handleClassifyUndercarriage"
          v-hasPermi="['platform:video:remove']"
          >分类下架
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="!queryParams.classifyId"
          @click="handleClassifyDelete"
          v-hasPermi="['platform:video:remove']"
          >分类删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-top"
          size="mini"
          :disabled="!queryParams.seriesName"
          @click="handleSeriesGrounding"
          v-hasPermi="['platform:video:remove']"
          >系列上架
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-bottom"
          size="mini"
          :disabled="!queryParams.seriesName"
          @click="handleSeriesUndercarriage"
          v-hasPermi="['platform:video:remove']"
          >系列下架
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="!queryParams.seriesName"
          @click="handleSeriesDelete"
          v-hasPermi="['platform:video:remove']"
          >系列删除
        </el-button>
      </el-col>
      <!--            <el-col :span="1.5">-->
      <!--              <el-button-->
      <!--                type="warning"-->
      <!--                plain-->
      <!--                icon="el-icon-download"-->
      <!--                size="mini"-->
      <!--                @click="handleExport"-->
      <!--                v-hasPermi="['platform:video:export']"-->
      <!--              >导出</el-button>-->
      <!--            </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button type="warning" plain  icon="el-icon-upload2"-->
      <!--                   size="mini" @click="handleImport"-->
      <!--                   v-hasPermi="['platform:video:export']"-->
      <!--        >导入</el-button-->
      <!--        >-->
      <!--      </el-col>-->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="videoList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="视频名称" align="center" prop="name" />
      <!--      <el-table-column label="播放量" align="center" prop="playCount" />-->
      <el-table-column label="点赞量" align="center" prop="likeCount" />
      <el-table-column label="短剧ID" align="center" prop="dramaId" />
      <el-table-column label="收藏量" align="center" prop="collectCount" />
      <el-table-column
        label="分类"
        align="center"
        prop="classifyId"
        :formatter="classifyFormatter"
      />
      <el-table-column label="系列" align="center" prop="seriesName" />
      <!--      <el-table-column label="视频简介" align="center" prop="content" />-->
      <el-table-column
        label="视频介绍"
        align="center"
        prop="content"
        width="110"
      >
        <template slot-scope="scope">
          <el-popover
            placement="top-start"
            width="100"
            trigger="hover"
            :disabled="scope.row.content.length <= 5"
          >
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

      <!--      <el-table-column label="视频路径" align="center" prop="videoUrl"/>-->

      <!--      <el-table-column label="封面图" align="center" prop="videoCover" />-->

      <el-table-column label="封面图" align="center" prop="videoCover">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.videoCover"
            :preview-src-list="[scope.row.videoCover]"
            fit="cover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column property="videoUrl" label="短视频" width="145">
        <template slot-scope="scope">
          <video :src="scope.row.videoUrl" controls style="width: 100%"></video>
        </template>
      </el-table-column>
      <el-table-column label="是否上架" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.up_down" :value="scope.row.status" />
        </template>
      </el-table-column>
      <!--      <el-table-column label="删除状态" align="center" prop="delStatus">-->
      <!--        <template slot-scope="scope">-->
      <!--          <dict-tag :options="dict.type.del_status" :value="scope.row.delStatus"/>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="创建时间" align="center" prop="createTime" />
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
            v-hasPermi="['platform:video:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['platform:video:remove']"
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

    <!-- 添加或修改视频对话框 -->
    <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="视频名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入视频名称" />
        </el-form-item>
        <el-form-item label="短剧id" prop="dramaId">
          <el-input v-model="form.dramaId" placeholder="请输入短剧id" />
        </el-form-item>
        <!--        <el-form-item label="播放量" prop="playCount">-->
        <!--          <el-input v-model="form.playCount" placeholder="请输入播放量" />-->
        <!--        </el-form-item>-->
        <el-row>
          <el-col :span="12">
            <el-form-item label="点赞量" prop="likeCount">
              <el-input v-model="form.likeCount" placeholder="请输入点赞量" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收藏量" prop="collectCount">
              <el-input
                v-model="form.collectCount"
                placeholder="请输入收藏量"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="视频简介" prop="content">
              <el-input
                v-model="form.content"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="视频链接" prop="videoUrl">
              <el-input v-model="form.videoUrl" placeholder="视频链接" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="封面链接" prop="videoCover">
              <el-input v-model="form.videoCover" placeholder="封面链接" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="视频" prop="videoUrl">
              <!--          使用COS SDK直接从浏览器分片上传，避免后端中转大文件导致签名错误 -->
              <el-upload
                class="title-image-uploader"
                action="#"
                :headers="headers"
                :show-file-list="false"
                :http-request="handleVideoUpload"
                :before-upload="beforeVideoUpload"
                v-loading="uploading"
              >
                <video
                  v-if="form.videoUrl"
                  :src="form.videoUrl"
                  class="title-image"
                />
                <i v-else class="el-icon-plus title-image-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="封面图" prop="videoCover">
              <el-upload
                class="title-image-uploader"
                :action="uploadImgUrl"
                :headers="headers"
                :data="{ type: 'videoCover' }"
                :show-file-list="false"
                :on-success="handleBannerImgSuccess"
                :before-upload="beforeBannerImgUpload"
                :on-remove="handleRemove"
              >
                <img
                  v-if="form.videoCover"
                  :src="form.videoCover"
                  class="title-image"
                />
                <i v-else class="el-icon-plus title-image-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        <el-form-item label="视频封面" prop="videoCover">-->
        <!--          <el-input v-model="form.videoCover" placeholder="请输入视频封面" />-->
        <!--        </el-form-item>-->

        <!--        <el-form-item label="删除状态" prop="delStatus">-->
        <!--          <el-select v-model="form.delStatus" placeholder="请选择删除状态">-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.del_status"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--              :value="parseInt(dict.value)"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
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
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
  listVideo,
  getVideo,
  delVideo,
  addVideo,
  updateVideo,
  undercarriage,
  grounding,
  delVideoByClassify,
  delVideoBySeries,
  groundingByClassify,
  undercarriageByClassify,
  groundingBySeries,
  undercarriageBySeries,
  listSeriesNames,
} from "@/api/platform/video";
import { listVideoCategory } from "@/api/platform/videoCategory";
import { getToken } from "@/utils/auth";
import { multipartUploadToCos } from "@/oss/upload";

export default {
  name: "Video",
  dicts: ["del_status", "up_down"],
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
      // 视频表格数据
      videoList: [],
      // 分类信息
      categoryList: [],
      // 系列名去重列表
      seriesNameList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      uploading: false,
      //上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        playCount: null,
        likeCount: null,
        content: null,
        videoUrl: null,
        videoCover: null,
        status: null,
        delStatus: null,
        collectCount: null,
        classifyId: null,
        seriesName: null,
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
        url: process.env.VUE_APP_BASE_API + "/platform/video/importData",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        content: [{ required: true, message: "内容不能为空", trigger: "blur" }],
        likeCount: [
          { required: true, message: "点赞量不能为空", trigger: "blur" },
        ],
        videoUrl: [
          { required: true, message: "视频路径不能为空", trigger: "blur" },
        ],
        videoCover: [
          { required: true, message: "视频封面不能为空", trigger: "blur" },
        ],
        status: [
          { required: true, message: "是否上架不能为空", trigger: "change" },
        ],
        collectCount: [
          { required: true, message: "收藏量不能为空", trigger: "change" },
        ],
        // delStatus: [{ required: true, message: '删除状态不能为空', trigger: 'change' }]
      },
      progressVisible: false,
      percent: 0,
    };
  },
  created() {
    this.getList();
    this.getCategoryList();
    this.getSeriesNameList();
  },
  watch: {
    "queryParams.classifyId"(val) {
      // 分类变化时重新拉系列列表，并清空已选系列
      this.queryParams.seriesName = null;
      this.getSeriesNameList(val);
    },
  },
  methods: {
    /** 查询视频列表 */
    getList() {
      this.loading = true;
      listVideo(this.queryParams).then((response) => {
        this.videoList = (response.rows || []).map((v) => ({
          ...v,
          content: v.content == null ? "" : v.content,
        }));
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询视频分类列表 */
    getCategoryList() {
      listVideoCategory({ pageNum: 1, pageSize: 100 }).then((response) => {
        this.categoryList = response.rows || [];
      });
    },
    /** 查询系列名去重列表（可按分类过滤） */
    getSeriesNameList(classifyId) {
      listSeriesNames(classifyId).then((response) => {
        this.seriesNameList = response.data || [];
      });
    },
    /** 分类名称格式化 */
    classifyFormatter(row) {
      const item = this.categoryList.find((c) => c.id === row.classifyId);
      return item ? item.name : row.classifyId;
    },
    /** 按分类上架按钮操作 */
    handleClassifyGrounding() {
      const classifyId = this.queryParams.classifyId;
      if (!classifyId) {
        this.$modal.msgWarning("请先选择分类");
        return;
      }
      this.$modal
        .confirm("是否确认上架所选分类下的全部视频？")
        .then(function () {
          return groundingByClassify(classifyId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("上架成功");
        })
        .catch(() => {});
    },
    /** 按分类下架按钮操作 */
    handleClassifyUndercarriage() {
      const classifyId = this.queryParams.classifyId;
      if (!classifyId) {
        this.$modal.msgWarning("请先选择分类");
        return;
      }
      this.$modal
        .confirm("是否确认下架所选分类下的全部视频？")
        .then(function () {
          return undercarriageByClassify(classifyId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("下架成功");
        })
        .catch(() => {});
    },
    /** 按分类删除按钮操作 */
    handleClassifyDelete() {
      const classifyId = this.queryParams.classifyId;
      if (!classifyId) {
        this.$modal.msgWarning("请先选择分类");
        return;
      }
      this.$modal
        .confirm("是否确认删除所选分类下的全部视频？")
        .then(function () {
          return delVideoByClassify(classifyId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 按系列上架按钮操作 */
    handleSeriesGrounding() {
      const seriesName = this.queryParams.seriesName;
      if (!seriesName) {
        this.$modal.msgWarning("请先选择系列");
        return;
      }
      this.$modal
        .confirm("是否确认上架所选系列下的全部视频？")
        .then(function () {
          return groundingBySeries(seriesName);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("上架成功");
        })
        .catch(() => {});
    },
    /** 按系列下架按钮操作 */
    handleSeriesUndercarriage() {
      const seriesName = this.queryParams.seriesName;
      if (!seriesName) {
        this.$modal.msgWarning("请先选择系列");
        return;
      }
      this.$modal
        .confirm("是否确认下架所选系列下的全部视频？")
        .then(function () {
          return undercarriageBySeries(seriesName);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("下架成功");
        })
        .catch(() => {});
    },
    /** 按系列删除按钮操作 */
    handleSeriesDelete() {
      const seriesName = this.queryParams.seriesName;
      if (!seriesName) {
        this.$modal.msgWarning("请先选择系列");
        return;
      }
      this.$modal
        .confirm("是否确认删除所选系列下的全部视频？")
        .then(function () {
          return delVideoBySeries(seriesName);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
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
        name: null,
        playCount: null,
        likeCount: null,
        content: null,
        videoUrl: null,
        videoCover: null,
        status: null,
        delStatus: null,
        createTime: null,
        updateTime: null,
        collectCount: null,
      };
      this.resetForm("form");
    },

    handleBannerImgSuccess(res, file) {
      if (res.code === 200) {
        this.form.videoCover = res.url;
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
    // 视频上传：通过COS SDK直接从浏览器分片上传到腾讯云COS
    handleVideoUpload(option) {
      this.progressVisible = true;
      multipartUploadToCos(
        option.file,
        (result) => {
          this.progressVisible = false;
          this.uploading = false;
          this.form.videoUrl = result.url;
          this.$modal.msgSuccess("视频上传成功");
        },
        (error) => {
          this.progressVisible = false;
          this.uploading = false;
          const errMsg = error instanceof Error ? error.message : (typeof error === 'string' ? error : '视频上传失败');
          this.$message.error(errMsg);
        },
        (percent) => {
          this.percent = Math.round(percent * 100);
        }
      );
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
    fileUpLoading() {},
    handleVideoRemove(file, fileList) {
      /* const findex = this.fileList.map(f => f.name).indexOf(file.name)
      if (findex > -1) {
        this.fileList.splice(findex, 1)
        this.$emit('input', this.listToString(this.fileList))
      } */
      this.url = "";
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

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.getSeriesNameList(); // 重置后拉回全量系列列表
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
      this.title = "添加视频";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getVideo(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改视频";
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateVideo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addVideo(this.form).then((response) => {
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
      // 编号为"' + ids + '"
      this.$modal
        .confirm("是否确认删除视频数据项？")
        .then(function () {
          return delVideo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 下架按钮操作 */
    undercarriage(row) {
      const ids = row.id || this.ids;
      // 编号为"' + ids + '"
      this.$modal
        .confirm("是否确认下架视频数据项？")
        .then(function () {
          return undercarriage(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("下架成功");
        })
        .catch(() => {});
    },

    /** 上架按钮操作 */
    grounding(row) {
      const ids = row.id || this.ids;
      // 编号为"' + ids + '"
      this.$modal
        .confirm("是否确认上架视频数据项？")
        .then(function () {
          return grounding(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("上架成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "platform/video/export",
        {
          ...this.queryParams,
        },
        `video_${new Date().getTime()}.xlsx`
      );
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "platform/video/importTemplate",
        {},
        `video_template_${new Date().getTime()}.xlsx`
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
