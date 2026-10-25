<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="封面图" prop="titleImg">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.titleImg"-->
      <!--          placeholder="请输入封面图"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="是否推荐" prop="isRecommend">
        <el-select
          v-model="queryParams.isRecommend"
          placeholder="请选择是否推荐"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.is_recommend"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否完结" prop="isEnd">
        <el-select
          v-model="queryParams.isEnd"
          placeholder="请选择是否完结"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.is_end"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="分类" prop="classifyId">
        <el-select
          v-model="queryParams.classifyId"
          placeholder="请选择"
          clearable
        >
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="是否删除" prop="delStatus">-->
      <!--        <el-select v-model="queryParams.delStatus" placeholder="请选择是否删除" clearable size="small">-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.del_status"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item label="是否上架" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择是否上架"
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
      <!--      <el-form-item label="价格" prop="price">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.price"-->
      <!--          placeholder="请输入价格"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="是否免费" prop="isPrice">
        <el-select
          v-model="queryParams.isPrice"
          placeholder="请选择是否免费"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.is_price"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="最近更新时间" prop="upTime">-->
      <!--        <el-date-picker clearable size="small"-->
      <!--          v-model="queryParams.upTime"-->
      <!--          type="date"-->
      <!--          value-format="yyyy-MM-dd"-->
      <!--          placeholder="选择最近更新时间">-->
      <!--        </el-date-picker>-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置
        </el-button>
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
          v-hasPermi="['platform:drama:add']"
          >新增
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
      <!--          v-hasPermi="['platform:drama:edit']"-->
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
          v-hasPermi="['platform:drama:remove']"
          >下架</el-button
        >
      </el-col>
      <el-col :span="1.5">
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
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['platform:drama:remove']"
          >删除</el-button
        >
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['platform:drama:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="dramaList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="封面图" align="center" prop="titleImg">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.titleImg"
            :preview-src-list="[scope.row.titleImg]"
            fit="cover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="是否推荐" align="center" prop="isRecommend">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.is_recommend"
            :value="scope.row.isRecommend"
          />
        </template>
      </el-table-column>
      <el-table-column label="是否完结" align="center" prop="isEnd">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_end" :value="scope.row.isEnd" />
        </template>
      </el-table-column>
      <!--      <el-table-column label="分类ID" align="center" prop="classifyId" />-->
      <el-table-column label="分类" align="center" prop="className" />
      <!--      <el-table-column label="短剧介绍" align="center" prop="details" />-->
      <el-table-column
        label="短剧介绍"
        align="center"
        prop="details"
        width="110"
      >
        <template slot-scope="scope">
          <el-popover
            placement="top-start"
            width="100"
            trigger="hover"
            :disabled="scope.row.details.length <= 5"
          >
            <div>{{ scope.row.details }}</div>
            <span slot="reference" v-if="scope.row.details.length <= 5">{{
              scope.row.details
            }}</span>
            <span slot="reference" v-if="scope.row.details.length > 5">{{
              scope.row.details.substr(0, 5) + "..."
            }}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="是否删除"
        width="80"
        align="center"
        prop="delStatus"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.del_status"
            :value="scope.row.delStatus"
          />
        </template>
      </el-table-column>
      <el-table-column label="是否上架" width="80" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.up_down" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="播放量"
        width="60"
        align="center"
        prop="playCount"
      />
      <!--      <el-table-column label="点赞量" width="60" align="center" prop="likeCount"/>-->
      <el-table-column
        label="是否免费"
        width="80"
        align="center"
        prop="isPrice"
      >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_price" :value="scope.row.isPrice" />
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        width="150"
        align="center"
        prop="createTime"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
      >
        <template slot-scope="scope">
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-edit"-->
          <!--            @click="handleUpdate(scope.row)"-->
          <!--            v-hasPermi="['platform:drama:edit']"-->
          <!--          >修改-->
          <!--          </el-button-->
          <!--          >-->
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-view"-->
          <!--            @click="handleResource(scope.row)"-->
          <!--          >资源-->
          <!--          </el-button-->
          <!--          >-->
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['platform:drama:remove']"-->
          <!--          >删除-->
          <!--          </el-button-->
          <!--          >-->
          <el-dropdown
            size="mini"
            @command="(command) => handleCommand(command, scope.row)"
          >
            <span class="el-dropdown-link">
              <i class="el-icon-d-arrow-right el-icon--right"></i>更多
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleResource">资源</el-dropdown-item>
              <el-dropdown-item command="handleUpdate">修改</el-dropdown-item>
              <el-dropdown-item command="handleDelete">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <!-- 添加或修改短剧信息对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1000px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类" prop="classifyId">
              <el-select
                v-model="form.classifyId"
                placeholder="请选择"
                clearable
                style="width: 150px"
                @change="getCategory"
              >
                <el-option
                  v-for="(item, index) in categoryList"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="是否推荐" prop="isRecommend">
              <el-select
                v-model="form.isRecommend"
                placeholder="请选择是否推荐"
              >
                <el-option
                  v-for="dict in dict.type.is_recommend"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否完结" prop="isEnd">
              <el-select v-model="form.isEnd" placeholder="请选择是否完结">
                <el-option
                  v-for="dict in dict.type.is_end"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="是否免费" prop="isPrice">
              <el-select v-model="form.isPrice" placeholder="请选择是否免费">
                <el-option
                  v-for="dict in dict.type.is_price"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
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

        <!--        <el-form-item label="是否删除" prop="delStatus">-->
        <!--          <el-select v-model="form.delStatus" placeholder="请选择是否删除">-->
        <!--            <el-option-->
        <!--              v-for="dict in dict.type.del_status"-->
        <!--              :key="dict.value"-->
        <!--              :label="dict.label"-->
        <!--:value="parseInt(dict.value)"-->
        <!--            ></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->

        <!--        <el-form-item label="点赞量" prop="likeCount">-->
        <!--          <el-input v-model="form.likeCount" placeholder="请输入点赞量" />-->
        <!--        </el-form-item>-->
        <el-row>
          <el-col :span="8">
            <el-form-item label="播放量" prop="playCount">
              <el-input v-model="form.playCount" placeholder="请输入播放量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="短剧介绍" prop="details">
          <el-input
            v-model="form.details"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="封面链接" prop="titleImg">
          <el-input v-model="form.titleImg" placeholder="请输入封面链接" />
        </el-form-item>
        <el-form-item label="封面图" prop="titleImg">
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
              v-if="form.titleImg"
              :src="form.titleImg"
              class="title-image"
            />
            <i v-else class="el-icon-plus title-image-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <!--        <el-form-item label="最近更新时间" prop="upTime">-->
        <!--          <el-date-picker clearable size="small"-->
        <!--            v-model="form.upTime"-->
        <!--            type="date"-->
        <!--            value-format="yyyy-MM-dd"-->
        <!--            placeholder="选择最近更新时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--资源列表-->
    <Resource ref="resourceRef" />
  </div>
</template>

<script>
import {
  listDrama,
  getDrama,
  delDrama,
  addDrama,
  updateDrama,
  dramaUndercarriage,
  dramaGrounding,
} from "@/api/platform/drama";
import { getToken } from "@/utils/auth";
import { listCategory } from "@/api/platform/dramaCategory";
import Resource from "./resource.vue";

export default {
  name: "Drama",
  components: {
    Resource,
  },
  dicts: [
    "del_status",
    "is_vip",
    "is_price",
    "is_recommend",
    "up_down",
    "is_end",
  ],
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
      // 短剧信息表格数据
      dramaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //分类信息
      categoryList: [],
      //上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      //请求头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        titleImg: null,
        isVip: null,
        isRecommend: null,
        classifyId: null,
        details: null,
        delStatus: null,
        status: null,
        playCount: null,
        likeCount: null,
        price: null,
        isPrice: null,
        upTime: null,
        isEnd: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        titleImg: [
          { required: true, message: "封面图不能为空", trigger: "blur" },
        ],
        isVip: [
          { required: true, message: "是否VIP不能为空", trigger: "blur" },
        ],
        isRecommend: [
          { required: true, message: "是否推荐不能为空", trigger: "blur" },
        ],
        classifyId: [
          { required: true, message: "分类不能为空", trigger: "blur" },
        ],
        details: [
          { required: true, message: "短剧介绍不能为空", trigger: "blur" },
        ],
        price: [{ required: true, message: "价格不能为空", trigger: "blur" }],
        isPrice: [
          { required: true, message: "是否收费不能为空", trigger: "blur" },
        ],
        status: [
          { required: true, message: "是否上架不能为空", trigger: "blur" },
        ],
        isEnd: [
          { required: true, message: "是否完结不能为空", trigger: "blur" },
        ],
        playCount: [
          { required: true, message: "播放量不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    listCategory().then((response) => {
      this.categoryList = response.data;
    });
  },
  methods: {
    /** 查询短剧信息列表 */
    getList() {
      this.loading = true;
      listDrama(this.queryParams).then((response) => {
        this.dramaList = response.rows;
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
        title: null,
        titleImg: null,
        isVip: null,
        isRecommend: null,
        classifyId: null,
        details: null,
        delStatus: null,
        status: null,
        playCount: null,
        likeCount: null,
        price: null,
        isPrice: null,
        createTime: null,
        upTime: null,
        updateTime: null,
        isEnd: null,
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
      this.title = "添加短剧信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDrama(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改短剧信息";
      });
    },
    /** 资源按钮操作 */
    handleResource(row = null) {
      const id = row.id;
      this.$refs.resourceRef.openModal(id);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateDrama(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDrama(this.form).then((response) => {
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
        .confirm("是否确认删除短剧信息数据项？")
        .then(function () {
          return delDrama(ids);
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
        "platform/drama/export",
        {
          ...this.queryParams,
        },
        `drama_${new Date().getTime()}.xlsx`
      );
    },

    //获取分类
    getCategory() {
      listCategory().then((response) => {
        this.categoryList = response.data;
      });
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
    /** 下架按钮操作 */
    undercarriage(row) {
      const ids = row.id || this.ids;
      // 编号为"' + ids + '"
      this.$modal
        .confirm("是否确认下架短剧数据项？")
        .then(function () {
          return dramaUndercarriage(ids);
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
        .confirm("是否确认上架短剧数据项？")
        .then(function () {
          return dramaGrounding(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("上架成功");
        })
        .catch(() => {});
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResource":
          this.handleResource(row);
          break;
        case "handleUpdate":
          this.handleUpdate(row);
          break;
        case "handleDelete":
          this.handleDelete(row);
          break;
        default:
          break;
      }
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
