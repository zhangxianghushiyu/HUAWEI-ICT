<template>
  <div class="app-container">
    <el-form
      v-if="false"
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="商品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="upStatus">
        <el-select
          v-model="queryParams.upStatus"
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
      <!--      <el-form-item label="商品图" prop="url">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.url"-->
      <!--          placeholder="请输入商品图"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
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
          v-hasPermi="['platform:goods:add']"
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
      <!--          v-hasPermi="['platform:goods:edit']"-->
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
          v-hasPermi="['platform:goods:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5" v-if="false">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-bottom"
          size="mini"
          :disabled="multiple"
          @click="undercarriage"
          v-hasPermi="['platform:drama:remove']"
          >下架</el-button
        >
      </el-col>
      <el-col :span="1.5" v-if="false">
        <el-button
          type="success"
          plain
          icon="el-icon-caret-top"
          size="mini"
          :disabled="multiple"
          @click="grounding"
          v-hasPermi="['platform:drama:remove']"
          >上架</el-button
        >
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['platform:goods:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="goodsList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="行业名称" align="center" prop="industryName" />
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
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除</el-button
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

    <!-- 添加或修改商品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="行业名称" prop="industryName">
          <el-input v-model="form.industryName" placeholder="请输入行业名称" />
        </el-form-item>
        <!-- <el-form-item label="金币价格" prop="leafPrice">
          <el-input v-model="form.leafPrice" placeholder="请输入金币价格" />
        </el-form-item>
        <el-form-item label="分类" prop="goodsType">
          <el-select v-model="form.goodsType" placeholder="请选择分类">
            <el-option
              v-for="item in goodsTypeList"
              :key="item.id"
              :label="item.typeName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品图" prop="url">
          <el-input v-model="form.url" placeholder="请输入商品图地址" />
        </el-form-item>
        <el-form-item label="商品图" prop="url">
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
            <img v-if="form.url" :src="form.url" class="title-image" />
            <i v-else class="el-icon-plus title-image-uploader-icon"></i>
          </el-upload>
        </el-form-item> -->
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
  listIndustry,
  addIndustry,
  updateIndustry,
  delIndustry,
} from "@/api/platform/industryType";
import { getToken } from "@/utils/auth";

export default {
  name: "industryType",
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
      // 商品信息表格数据
      goodsList: [],
      //上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      //请求头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        leafPrice: null,
        upStatus: null,
        url: null,
      },
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      goodsTypeList: [],
      // 表单校验
      rules: {
        industryName: [
          { required: true, message: "名称不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品信息列表 */
    getList() {
      this.loading = true;
      listIndustry(this.queryParams).then((response) => {
        this.goodsList = response.rows;
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
        industryName: null,
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
      this.title = "添加商品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      this.form = row;
      this.open = true;
      this.title = "修改信息";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateIndustry(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIndustry(this.form).then((response) => {
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
        .confirm("是否确认删除商品数据项？")
        .then(function () {
          return delIndustry(ids);
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
        "platform/goods/export",
        {
          ...this.queryParams,
        },
        `goods_${new Date().getTime()}.xlsx`
      );
    },
    handleBannerImgSuccess(res, file) {
      if (res.code === 200) {
        this.form.url = res.url;
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
    /** 下架按钮操作 */
    undercarriage(row) {
      const ids = row.id || this.ids;
      // 编号为"' + ids + '"
      this.$modal
        .confirm("是否确认下架商品数据项？")
        .then(function () {
          return goodsUndercarriage(ids);
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
        .confirm("是否确认上架商品数据项？")
        .then(function () {
          return goodsGrounding(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("上架成功");
        })
        .catch(() => {});
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
