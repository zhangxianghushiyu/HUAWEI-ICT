<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="歌名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入歌名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="答案" prop="answer">
        <el-input
          v-model="queryParams.answer"
          placeholder="请输入答案"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.up_down"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
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
          v-hasPermi="['platform:musicStore:add']"
          >新增</el-button
        >
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['platform:musicStore:edit']"-->
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
                v-hasPermi="['platform:musicStore:remove']"
              >删除</el-button>
            </el-col>
      <el-col :span="1.5">
        <!--        <el-button-->
        <!--          type="warning"-->
        <!--          plain-->
        <!--          icon="el-icon-download"-->
        <!--          size="mini"-->
        <!--          @click="handleExport"-->
        <!--          v-hasPermi="['platform:musicStore:export']"-->
        <!--        >导出</el-button>-->
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            >导入
          </el-button>
        </el-col>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="musicStoreList"
      @selection-change="handleSelectionChange"
    >
            <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column align="name" prop="name" label="歌名">
        <template slot-scope="scope">
          <span style="color: #ffba00; font-weight: bold">{{
            scope.row.name
          }}</span>
        </template>
      </el-table-column>
      <el-table-column align="answer" prop="answer" label="答案">
        <template slot-scope="scope">
          <span style="color: #00afff; font-weight: bold">{{
            scope.row.answer
          }}</span>
        </template>
      </el-table-column>
      <el-table-column align="wrong" prop="wrong" label="错误">
        <template slot-scope="scope">
          <span style="color: #ff4949; font-weight: bold">{{
            scope.row.wrong
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="地址" align="center">
        <template slot-scope="scope">
          <span
            style="color: #00afff; cursor: pointer"
            @click="playAudio(scope.row.url)"
            >{{ scope.row.name }}.mp3</span
          >
          <audio ref="audioPlayer" :src="playAudeoSrc"></audio>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.up_down" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <!--      <el-table-column label="更新时间" align="center" prop="updateTime" />-->
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
            v-hasPermi="['platform:musicStore:edit']"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDelete(scope.row)"
            v-hasPermi="['platform:musicStore:remove']"
            >删除</el-button
          >
          <el-button
            v-if="scope.row.status === 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDeactivate(scope.row)"
            v-hasPermi="['platform:musicStore:remove']"
            >禁用</el-button
          >
          <el-button
            v-if="scope.row.status === 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleRestore(scope.row)"
            v-hasPermi="['platform:musicStore:remove']"
            >恢复</el-button
          >
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

    <!-- 添加或修改曲库信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="歌名" prop="name">
          <el-input v-model="form.name" placeholder="请输入歌名" />
        </el-form-item>
        <el-form-item label="答案" prop="answer">
          <el-input v-model="form.answer" placeholder="请输入答案" />
        </el-form-item>
        <el-form-item label="错误" prop="wrong">
          <el-input v-model="form.wrong" placeholder="请输入错误" />
        </el-form-item>
        <el-form-item label="地址" prop="url">
          <el-input v-model="form.url" placeholder="请输入地址" />
        </el-form-item>
        <!--        <el-form-item label="状态">-->
        <!--          <el-radio-group v-model="form.status">-->
        <!--            <el-radio-->
        <!--              v-for="dict in dict.type.up_down"-->
        <!--              :key="dict.value"-->
        <!--:label="parseInt(dict.value)"-->
        <!--            >{{dict.label}}</el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-form-item>-->
        <el-form-item label="上传mp3" prop="url">
          <el-upload
            class="upload-demo"
            :action="uploadImgUrl"
            :headers="headers"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-upload="beforeMp3Upload"
            multiple
            :limit="1"
            :on-exceed="handleExceed"
            :on-success="handleMp3Success"
            :file-list="fileList"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">
              只能上传mp3文件，且不超过20mb
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input
            v-model="form.remarks"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
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
          一次提交模版数据最多10000<br />将文件拖到此处，或<em>点击上传</em>
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
  listMusicStore,
  getMusicStore,
  delMusicStore,
  addMusicStore,
  updateMusicStore,
  modifyStatusDeactivate,
} from "@/api/platform/musicStore";
import { modifyUserStatusDeactivate } from "@/api/platform/user";
import { getToken } from "@/utils/auth";

export default {
  name: "MusicStore",
  dicts: ["up_down"],
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
      // 曲库信息表格数据
      musicStoreList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        answer: null,
        wrong: null,
        url: null,
        status: null,
        remarks: null,
      },
      // 表单参数
      form: {},
      playAudeoSrc: "",
      fileList: [],
      // 表单校验
      rules: {
        name: [{ required: true, message: "歌名不能为空", trigger: "blur" }],
        answer: [{ required: true, message: "答案不能为空", trigger: "blur" }],
        wrong: [{ required: true, message: "错误不能为空", trigger: "blur" }],
        url: [{ required: true, message: "地址不能为空", trigger: "blur" }],
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
        url: process.env.VUE_APP_BASE_API + "/platform/musicStore/importData",
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    beforeMp3Upload(file) {
      const isMedia = file.type === "audio/mpeg";
      const isLt20M = file.size / 1024 / 1024 < 20;

      if (!isMedia) {
        this.$message.error("只允许上传mp3文件!");
      }
      if (!isLt20M) {
        this.$message.error("上传mp3大小不能超过 20MB!");
      }
      return isMedia && isLt20M;
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleMp3Success(response, file, fileList) {
      this.form.url = response.url;
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    playAudio(item) {
      this.$refs.audioPlayer.src = item;
      this.$refs.audioPlayer.play();
    },
    /** 查询曲库信息列表 */
    getList() {
      this.loading = true;
      listMusicStore(this.queryParams).then((response) => {
        this.musicStoreList = response.rows;
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
        name: null,
        answer: null,
        url: null,
        status: null,
        remarks: null,
        createTime: null,
        updateTime: null,
        wrong: null,
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
      this.title = "添加曲库信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMusicStore(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改曲库信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateMusicStore(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMusicStore(this.form).then((response) => {
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
      this.$modal
        .confirm('是否确认删除数据项？')
        .then(function () {
          return delMusicStore(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    /** 停用按钮操作 */
    handleDeactivate(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认停用数据项？")
        .then(function () {
          return modifyStatusDeactivate(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("设置成功");
        })
        .catch(() => {});
    },

    /** 恢复按钮操作 */
    handleRestore(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认恢复数据项？")
        .then(function () {
          return modifyStatusDeactivate(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("设置成功");
        })
        .catch(() => {});
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "platform/musicStore/export",
        {
          ...this.queryParams,
        },
        `musicStore_${new Date().getTime()}.xlsx`
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
        "platform/musicStore/importTemplate",
        {},
        `musicStore_template_${new Date().getTime()}.xlsx`
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
