<template>
  <div class="app-container">
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['platform:MemberManage:add']"
          >新增</el-button
        >
      </el-col>

      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row> -->

    <el-table
      v-loading="loading"
      :data="dramaCategoryList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="VIP等级名称" align="center" prop="levelName" />
      <el-table-column label="购买价格" align="center" prop="buyMoney" />
      <el-table-column label="用户比例(%)" align="center" prop="userRatio" />
      <el-table-column
        label="直推普通会员(%)"
        align="center"
        prop="directNomalRatio"
      />
      <el-table-column
        label="直推黄金会员(%)"
        align="center"
        prop="directGoldRatio"
      />
      <el-table-column
        label="直推钻石会员(%)"
        align="center"
        prop="directDiamondRatio"
      />
      <el-table-column
        label="直推广告分润(%)"
        align="center"
        prop="directRatio"
      />
      <el-table-column
        label="间推广告分润(%)"
        align="center"
        prop="indirectRatio"
      />
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
            v-hasPermi="['platform:dramaCategory:edit']"
            >修改</el-button
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

    <!-- 添加或修改短剧分类对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="排序" align="center" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="等级名称" align="center" prop="levelName">
          <el-input v-model="form.levelName" placeholder="请输入等级名称" />
        </el-form-item>
        <el-form-item label="购买价格" align="center" prop="buyMoney">
          <el-input v-model="form.buyMoney" placeholder="请输入条件" />
        </el-form-item>
        <el-form-item label="用户比例" align="center" prop="userRatio">
          <el-input v-model="form.userRatio" placeholder="请输入条件" />
        </el-form-item>
        <el-form-item
          label="直推普通会员"
          align="center"
          prop="directNomalRatio"
        >
          <el-input v-model="form.directNomalRatio" placeholder="请输入条件" />
        </el-form-item>
        <el-form-item
          label="直推黄金会员"
          align="center"
          prop="directGoldRatio"
        >
          <el-input v-model="form.directGoldRatio" placeholder="请输入条件" />
        </el-form-item>
        <el-form-item
          label="直推钻石会员"
          align="center"
          prop="directDiamondRatio"
        >
          <el-input
            v-model="form.directDiamondRatio"
            placeholder="请输入条件"
          />
        </el-form-item>
        <el-form-item label="直推广告分润" align="center" prop="directRatio">
          <el-input v-model="form.directRatio" placeholder="请输入条件" />
        </el-form-item>
        <el-form-item label="间推广告分润" align="center" prop="indirectRatio">
          <el-input v-model="form.indirectRatio" placeholder="请输入条件" />
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
  listMember,
  addVipConfig,
  delVipConfig,
  updateVipConfig,
  listVip,
} from "@/api/platform/vipManage";

export default {
  name: "VipManage",
  dicts: ["del_status"],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      options: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 短剧分类表格数据
      dramaCategoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sort: null,
        name: null,
        delStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      // 表单校验
      rules: {
        sort: [{ required: true, message: "排序不能为空", trigger: "blur" }],
        levelName: [
          { required: true, message: "名称不能为空", trigger: "blur" },
        ],
        conditionAmount: [
          { required: true, message: "数据不能为空", trigger: "blur" },
        ],
        userRatio: [
          { required: true, message: "数据不能为空", trigger: "blur" },
        ],
        directPushRatio: [
          { required: true, message: "数据不能为空", trigger: "blur" },
        ],
        indirectPushRatio: [
          { required: true, message: "数据不能为空", trigger: "blur" },
        ],
        teamRatio: [
          { required: true, message: "数据不能为空", trigger: "blur" },
        ],
        equalRatio: [
          { required: true, message: "数据不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询短剧分类列表 */
    getList() {
      this.loading = true;
      listMember().then((response) => {
        this.dramaCategoryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        sort: null,
        name: null,
        delStatus: null,
        createTime: null,
        updateTime: null,
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
      this.title = "添加会员配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      this.form = row;
      this.open = true;
      this.title = "修改会员配置";
      // getDramaCategory(id).then((response) => {
      //   this.form = response.data;
      //   this.open = true;
      //   this.title = "修改短剧分类";
      // });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateVipConfig(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.type = 2;
            addVipConfig(this.form).then((response) => {
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
        .confirm("是否确认删除数据项？")
        .then(function () {
          return delVipConfig(ids);
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
        "platform/dramaCategory/export",
        {
          ...this.queryParams,
        },
        `dramaCategory_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
