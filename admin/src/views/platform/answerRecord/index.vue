<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="用户ID" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入用户ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关卡" prop="barrier">
        <el-input
          v-model="queryParams.barrier"
          placeholder="请输入关卡"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="歌名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入歌名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="答案" prop="answer">-->
<!--        <el-input-->
<!--          v-model="queryParams.answer"-->
<!--          placeholder="请输入答案"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="错误" prop="wrong">-->
<!--        <el-input-->
<!--          v-model="queryParams.wrong"-->
<!--          placeholder="请输入错误"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="回答" prop="write">-->
<!--        <el-input-->
<!--          v-model="queryParams.write"-->
<!--          placeholder="请输入回答"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="结果" prop="answerResult">
        <el-select v-model="queryParams.answerResult" placeholder="请选择结果" clearable size="small">
          <el-option
            v-for="dict in dict.type.answer_result"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['platform:answerRecord:add']"-->
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
<!--          v-hasPermi="['platform:answerRecord:edit']"-->
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
<!--          v-hasPermi="['platform:answerRecord:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['platform:answerRecord:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="answerRecordList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index"/>
<!--      <el-table-column label="用户ID" align="center" prop="userId" />-->
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column property="avatar" label="头像" align="center">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="关卡" align="center" prop="barrier" />
      <el-table-column label="歌名" align="center" prop="name" />
      <el-table-column label="答案" align="center" prop="answer" />
      <el-table-column label="错误" align="center" prop="wrong" />
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
      <el-table-column label="回答" align="center" prop="write" />
      <el-table-column label="结果" align="center" prop="answerResult">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.answer_result" :value="scope.row.answerResult"/>
        </template>
      </el-table-column>
      <el-table-column label="答题时间" align="center" prop="createTime" />
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['platform:answerRecord:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['platform:answerRecord:remove']"-->
<!--          >删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改答题记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="用户ID" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入用户ID" />-->
<!--        </el-form-item>-->
        <el-form-item label="关卡" prop="barrier">
          <el-input v-model="form.barrier" placeholder="请输入关卡" />
        </el-form-item>
        <el-form-item label="歌名" prop="name">
          <el-input v-model="form.name" placeholder="请输入歌名" />
        </el-form-item>
        <el-form-item label="答案" prop="answer">
          <el-input v-model="form.answer" placeholder="请输入答案" />
        </el-form-item>
        <el-form-item label="错误" prop="wrong">
          <el-input v-model="form.wrong" placeholder="请输入错误" />
        </el-form-item>
        <el-form-item label="歌曲地址" prop="url">
          <el-input v-model="form.url" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="回答" prop="write">
          <el-input v-model="form.write" placeholder="请输入回答" />
        </el-form-item>
        <el-form-item label="结果" prop="answerResult">
          <el-select v-model="form.answerResult" placeholder="请选择结果">
            <el-option
              v-for="dict in dict.type.answer_result"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
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
import { listAnswerRecord, getAnswerRecord, delAnswerRecord, addAnswerRecord, updateAnswerRecord } from "@/api/platform/answerRecord";

export default {
  name: "AnswerRecord",
  dicts: ['answer_result'],
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
      // 答题记录表格数据
      answerRecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      playAudeoSrc: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        barrier: null,
        nickName:null,
        name: null,
        answer: null,
        wrong: null,
        url: null,
        write: null,
        answerResult: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询答题记录列表 */
    getList() {
      this.loading = true;
      listAnswerRecord(this.queryParams).then(response => {
        this.answerRecordList = response.rows;
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
        userId: null,
        barrier: null,
        name: null,
        answer: null,
        wrong: null,
        url: null,
        write: null,
        answerResult: null,
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
      this.title = "添加答题记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAnswerRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改答题记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAnswerRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAnswerRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除答题记录编号为"' + ids + '"的数据项？').then(function() {
        return delAnswerRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/answerRecord/export', {
        ...this.queryParams
      }, `answerRecord_${new Date().getTime()}.xlsx`)
    },
    playAudio(item) {
      this.$refs.audioPlayer.src = item;
      this.$refs.audioPlayer.play();
    },
  }
};
</script>
