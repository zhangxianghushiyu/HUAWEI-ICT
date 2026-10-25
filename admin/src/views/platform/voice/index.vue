<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="类型" prop="type">-->
<!--        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable size="small">-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.voice_type"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
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
<!--          v-hasPermi="['platform:voice:add']"-->
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
<!--          v-hasPermi="['platform:voice:edit']"-->
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
<!--          v-hasPermi="['platform:voice:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:voice:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="voiceList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index"/>
<!--      <el-table-column label="类型" align="center" prop="type">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.voice_type" :value="scope.row.type"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="语音" align="center" prop="url">
        <template slot-scope="scope">
          <span
            style="color: red; cursor: pointer"
            @click="playAudio(scope.row.url)"
          >{{ scope.row.remarks }}.mp3</span
          >
          <audio ref="audioPlayer" :src="playAudeoSrc"></audio>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:voice:edit']"
          >修改</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['platform:voice:remove']"-->
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

    <!-- 添加或修改语音信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="类型" prop="type">-->
<!--          <el-select v-model="form.type" placeholder="请选择类型">-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.voice_type"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--:value="parseInt(dict.value)"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="上传语音" prop="url">
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
        <el-form-item label="语音" prop="url">
          <el-input v-model="form.url" type="textarea" placeholder="请输入语音地址" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
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
import { listVoice, getVoice, delVoice, addVoice, updateVoice } from "@/api/platform/voice";
import {getToken} from "@/utils/auth";

export default {
  name: "Voice",
  dicts: ['voice_type'],
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
      fileList: [],
      //上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      //请求头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 语音信息表格数据
      voiceList: [],
      // 弹出层标题
      title: "",
      playAudeoSrc: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        url: null,
        remarks: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        url: [{ required: true, message: "地址不能为空", trigger: "blur" }],
        remarks: [{ required: true, message: "备注不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询语音信息列表 */
    getList() {
      this.loading = true;
      listVoice(this.queryParams).then(response => {
        this.voiceList = response.rows;
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
        type: null,
        url: null,
        remarks: null,
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
      this.title = "添加语音信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getVoice(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改语音信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateVoice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addVoice(this.form).then(response => {
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
        return delVoice(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/voice/export', {
        ...this.queryParams
      }, `voice_${new Date().getTime()}.xlsx`)
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
    playAudio(item) {
      this.$refs.audioPlayer.src = item;
      this.$refs.audioPlayer.play();
    },
  }
};
</script>
