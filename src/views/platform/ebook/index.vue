<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="字数" prop="words">-->
<!--        <el-input-->
<!--          v-model="queryParams.words"-->
<!--          placeholder="请输入字数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="排序" prop="sort">-->
<!--        <el-input-->
<!--          v-model="queryParams.sort"-->
<!--          placeholder="请输入排序"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="分数" prop="fraction">-->
<!--        <el-input-->
<!--          v-model="queryParams.fraction"-->
<!--          placeholder="请输入分数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="是否完结" prop="isEnd">-->
<!--        <el-input-->
<!--          v-model="queryParams.isEnd"-->
<!--          placeholder="请输入是否完结"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
      <el-form-item label="是否完结" prop="isEnd">
        <el-select
          v-model="queryParams.isEnd"
          placeholder="请选择是否完结"
          clearable
          size="small"
        >
          <el-option label="连载" :value="1"/>
          <el-option label="完结" :value="2"/>
        </el-select>
      </el-form-item>
<!--      <el-form-item label="是否精品" prop="delicate">-->
<!--        <el-input-->
<!--          v-model="queryParams.delicate"-->
<!--          placeholder="请输入是否精品"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="是否精品" prop="delicate">
        <el-select
          v-model="queryParams.delicate"
          placeholder="请选择是否精品"
          clearable
          size="small"
        >
          <el-option label="是" :value="1"/>
          <el-option label="否" :value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['platform:ebook:add']"
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
<!--          v-hasPermi="['platform:ebook:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-bottom"
          size="mini"
          :disabled="multiple"
          @click="undercarriage"
          v-hasPermi="['platform:ebook:remove']"
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
          v-hasPermi="['platform:ebook:remove']"
        >上架</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['platform:ebook:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:ebook:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ebookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index"/>
      <el-table-column label="小说ID" align="center" prop="id" />
      <el-table-column label="字数" align="center" prop="words" />
<!--      <el-table-column label="封面图" align="center" prop="url" />-->
      <el-table-column label="封面图" align="center" prop="url">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.url"
            :preview-src-list="[scope.row.url]"
            fit="cover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="分数" align="center" prop="fraction" />
      <el-table-column label="标题" align="center" prop="title" />
<!--      <el-table-column label="是否完结" align="center" prop="isEnd" />-->
      <el-table-column label="是否完结" align="center" prop="isEnd">
        <template slot-scope="scope">
          <span v-if="scope.row.isEnd === 1" style="color: #1c84c6">连载</span>
          <span v-if="scope.row.isEnd === 2" style="color: red">完结</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="是否精品" align="center" prop="delicate" />-->
      <el-table-column label="是否精品" align="center" prop="delicate">
        <template slot-scope="scope">
          <span v-if="scope.row.delicate === 1" style="color: #1c84c6">是</span>
          <span v-if="scope.row.delicate === 2" style="color: #5a5e66">否</span>
        </template>
      </el-table-column>
      <el-table-column label="是否上架" align="center" prop="upStatus">
        <template slot-scope="scope">
          <span v-if="scope.row.upStatus === 1" style="color: #1c84c6">是</span>
          <span v-if="scope.row.upStatus === 0" style="color: #5a5e66">否</span>
        </template>
      </el-table-column>
      <el-table-column label="作者" align="center" prop="author" />
<!--      <el-table-column label="介绍" align="center" prop="introduce" />-->
      <el-table-column
        label="介绍"
        align="center"
        prop="introduce"
        width="110"
      >
        <template slot-scope="scope">
          <el-popover
            placement="top-start"
            width="100"
            trigger="hover"
            :disabled="scope.row.introduce.length <= 5"
          >
            <div>{{ scope.row.introduce }}</div>
            <span slot="reference" v-if="scope.row.introduce.length <= 5">{{
                scope.row.introduce
              }}</span>
            <span slot="reference" v-if="scope.row.introduce.length > 5">{{
                scope.row.introduce.substr(0, 5) + '...'
              }}</span>
          </el-popover>
        </template>
      </el-table-column>
<!--      <el-table-column label="是否删除" align="center" prop="delStatus" />-->
<!--      <el-table-column label="是否上架" align="center" prop="upStatus" />-->
      <el-table-column label="更新时间" align="center" prop="updateTime" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:ebook:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-menu"
            @click="handleItem(scope.row)"
            v-hasPermi="['platform:ebook:edit']"
          >章节</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['platform:ebook:remove']"
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

    <!-- 添加或修改电子书主信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body  :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字数" prop="words">
          <el-input v-model="form.words" placeholder="请输入字数" />
        </el-form-item>
        <el-form-item label="封面链接" prop="url">
          <el-input v-model="form.url" type="textarea" placeholder="请输入封面链接" />
        </el-form-item>
        <el-form-item label="上传封面" prop="url">
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
            <img
              v-if="form.url"
              :src="form.url"
              class="title-image"
            />
            <i v-else class="el-icon-plus title-image-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="分数" prop="fraction">
          <el-input v-model="form.fraction" placeholder="请输入分数" />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
<!--        <el-form-item label="是否完结" prop="isEnd">-->
<!--          <el-input v-model="form.isEnd" placeholder="请输入是否完结" />-->
<!--        </el-form-item>-->
        <el-form-item label="是否完结" prop="isEnd">
          <el-select
            v-model="form.isEnd"
            placeholder="请选择是否完结"
            clearable
            size="small"
          >
            <el-option label="连载" :value="1"/>
            <el-option label="完结" :value="2"/>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="是否精品" prop="delicate">-->
<!--          <el-input v-model="form.delicate" placeholder="请输入是否精品" />-->
<!--        </el-form-item>-->
        <el-form-item label="是否精品" prop="delicate">
          <el-select
            v-model="form.delicate"
            placeholder="请选择是否精品"
            clearable
            size="small"
          >
            <el-option label="是" :value="1"/>
            <el-option label="否" :value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="介绍">
          <el-input v-model="form.introduce" type="textarea" placeholder="请输入介绍" />
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
  listEbook,
  getEbook,
  delEbook,
  addEbook,
  updateEbook,
  ebookGrounding,
  ebookUndercarriage
} from "@/api/platform/ebook";
import {getToken} from "@/utils/auth";

export default {
  name: "Ebook",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      //上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + '/common/upload',
      //请求头
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 电子书主信息表格数据
      ebookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        words: null,
        url: null,
        sort: null,
        fraction: null,
        title: null,
        isEnd: null,
        delicate: null,
        author: null,
        introduce: null,
        delStatus: null,
        upStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        words: [{ required: true, message: '字数不能为空', trigger: 'blur' }],
        url: [{ required: true, message: '封面不能为空', trigger: 'blur' }],
        sort: [{ required: true, message: '排序不能为空', trigger: 'blur' }],
        fraction: [{ required: true, message: '分数不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
        isEnd: [{ required: true, message: '是否完结不能为空', trigger: 'blur' }],
        delicate: [{ required: true, message: '是否精品不能为空', trigger: 'blur' }],
        author: [{ required: true, message: '作者不能为空', trigger: 'blur' }],
        introduce: [{ required: true, message: '介绍不能为空', trigger: 'blur' }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleItem(row) {
      this.$router.push({ path: '/ebookManage/ebookItem', query: { ebookId: row.id } });
      // this.$router.push(path)
    },
    /** 查询电子书主信息列表 */
    getList() {
      this.loading = true;
      listEbook(this.queryParams).then(response => {
        this.ebookList = response.rows;
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
        words: null,
        url: null,
        sort: null,
        fraction: null,
        title: null,
        isEnd: null,
        delicate: null,
        author: null,
        introduce: null,
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
      this.title = "添加电子书主信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEbook(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改电子书主信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEbook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEbook(this.form).then(response => {
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
        return delEbook(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/ebook/export', {
        ...this.queryParams
      }, `ebook_${new Date().getTime()}.xlsx`)
    },
    handleBannerImgSuccess(res, file) {
      if (res.code === 200) {
        this.form.url = res.url
      } else {
        this.$message.error(res.msg)
      }
    },
    beforeBannerImgUpload(file) {
      const isJPG = file.type === 'image/jpeg' || 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 5

      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG或PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 5MB!')
      }
      return isJPG && isLt2M
    },
    // 删除图片
    handleRemove(file, fileList) {
      /* const findex = this.fileList.map(f => f.name).indexOf(file.name)
      if (findex > -1) {
        this.fileList.splice(findex, 1)
        this.$emit('input', this.listToString(this.fileList))
      } */
      this.url = ''
    },
    /** 下架按钮操作 */
    undercarriage(row) {
      const ids = row.id || this.ids
      // 编号为"' + ids + '"
      this.$modal
        .confirm('是否确认下架小说数据项？')
        .then(function () {
          return ebookUndercarriage(ids)
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
          return ebookGrounding(ids)
        }).then(() => {
        this.getList()
        this.$modal.msgSuccess('上架成功')
      }).catch(() => {
      })
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
