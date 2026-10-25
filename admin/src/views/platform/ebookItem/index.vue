<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="主信息ID" prop="ebookId">-->
<!--        <el-input-->
<!--          v-model="queryParams.ebookId"-->
<!--          placeholder="请输入主信息ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="章节号" prop="chapter">
        <el-input
          v-model="queryParams.chapter"
          placeholder="请输入章节号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否上架" prop="upStatus">
        <el-select
          v-model="queryParams.upStatus"
          placeholder="请选择是否上架"
          clearable
          size="small"
        >
          <el-option label="是" :value="1"/>
          <el-option label="否" :value="0"/>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="内容" prop="content">-->
<!--        <el-input-->
<!--          v-model="queryParams.content"-->
<!--          placeholder="请输入内容"-->
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['platform:ebookItem:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['platform:ebookItem:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-bottom"
          size="mini"
          :disabled="multiple"
          @click="undercarriage"
          v-hasPermi="['platform:ebookItem:remove']"
        >下架</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-top"
          size="mini"
          :disabled="multiple"
          @click="grounding"
          v-hasPermi="['platform:ebookItem:remove']"
        >上架</el-button>
      </el-col>
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['platform:ebookItem:remove']"
        >删除</el-button>
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
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:ebookItem:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ebookItemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index"/>
<!--      <el-table-column label="主信息ID" align="center" prop="ebookId" />-->
      <el-table-column label="章节号" align="center" prop="chapter" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="是否上架" align="center" prop="upStatus">
        <template slot-scope="scope">
          <span v-if="scope.row.upStatus === 1" style="color: #1c84c6">是</span>
          <span v-if="scope.row.upStatus === 0" style="color: #5a5e66">否</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="是否删除" align="center" prop="delStatus" />-->
<!--      <el-table-column label="是否上架" align="center" prop="upStatus" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:ebookItem:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['platform:ebookItem:remove']"
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
          '&ebookId=' +
          this.queryParams.ebookId
        "
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess2"
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
    <!-- 添加或修改电子书章节对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="主信息ID" prop="ebookId">-->
<!--          <el-input v-model="form.ebookId" placeholder="请输入主信息ID" />-->
<!--        </el-form-item>-->
        <el-form-item label="章节号" prop="chapter">
          <el-input v-model="form.chapter" placeholder="请输入章节号" />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="章节链接" prop="content">
          <el-input v-model="form.content" placeholder="请输入章节链接" />
        </el-form-item>
        <el-form-item label="文件" prop="content">

          <el-upload
            class="upload-demo"
            drag
            :action="uploadImgUrl"
            :headers="headers"
            :data="{ type: 'info' }"
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
  listEbookItem,
  getEbookItem,
  delEbookItem,
  addEbookItem,
  updateEbookItem,
  ebookItemGrounding, ebookItemUndercarriage
} from "@/api/platform/ebookItem";
import {getToken} from "@/utils/auth";

export default {
  name: "EbookItem",
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
      // 电子书章节表格数据
      ebookItemList: [],
      //上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + '/common/upload',
      //请求头
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
        url: process.env.VUE_APP_BASE_API + "/platform/ebookItem/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ebookId: null,
        chapter: null,
        title: null,
        content: null,
        delStatus: null,
        upStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        chapter: [{ required: true, message: '章节号不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
        content: [{ required: true, message: '内容不能为空', trigger: 'blur' }],
      }
    };
  },
  created() {
    this.getList();
    this.queryParams.ebookId=this.$route.query.ebookId
  },
  methods: {
    /** 查询电子书章节列表 */
    getList() {
      this.loading = true;
      this.queryParams.ebookId=this.$route.query.ebookId
      listEbookItem(this.queryParams).then(response => {
        this.ebookItemList = response.rows;
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
        ebookId: null,
        chapter: null,
        title: null,
        content: null,
        delStatus: null,
        upStatus: null,
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
      this.title = "添加电子书章节";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEbookItem(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改电子书章节";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEbookItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.ebookId=this.$route.query.ebookId
            addEbookItem(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除电子书章节编号为"' + ids + '"的数据项？').then(function() {
        return delEbookItem(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/ebookItem/export', {
        ...this.queryParams
      }, `ebookItem_${new Date().getTime()}.xlsx`)
    },
    handleFileSuccess(res, file) {
      if (res.code === 200) {
        this.form.content = res.url;
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
      this.content = "";
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "platform/ebookItem/importTemplate",
        {},
        `ebookItem_template_${new Date().getTime()}.xlsx`
      );
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess2(response, file, fileList) {
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
    /** 下架按钮操作 */
    undercarriage(row) {
      const ids = row.id || this.ids
      // 编号为"' + ids + '"
      this.$modal
        .confirm('是否确认下架小说数据项？')
        .then(function () {
          return ebookItemUndercarriage(ids)
        }).then(() => {
        this.getList()
        this.$modal.msgSuccess('下架成功')
      }).catch(() => {
      })
    },

    /** 上架按钮操作 */
    grounding(row) {
      const ids = row.id || this.ids
      // 编号为"' + ids + '"
      this.$modal
        .confirm('是否确认上架小说数据项？')
        .then(function () {
          return ebookItemGrounding(ids)
        }).then(() => {
        this.getList()
        this.$modal.msgSuccess('上架成功')
      }).catch(() => {
      })
    },
  }
};
</script>
