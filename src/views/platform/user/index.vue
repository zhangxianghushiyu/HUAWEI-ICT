<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="VIP" prop="isVip">-->
      <!--        <el-select v-model="queryParams.isVip" placeholder="请选择" clearable size="small">-->
      <!--          <el-option-->
      <!--            v-for="dict in dict.type.user_is_vip"-->
      <!--            :key="dict.value"-->
      <!--            :label="dict.label"-->
      <!--            :value="dict.value"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item label="代理" prop="commander">
        <el-select
          v-model="queryParams.commander"
          placeholder="请选择"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.is_commander"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属代理" prop="commanderUserName">
        <el-input
          v-model="queryParams.commanderUserName"
          placeholder="请输入所属代理昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="头像" prop="avatar">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.avatar"-->
      <!--          placeholder="请输入头像"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="密码" prop="password">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.password"-->
      <!--          placeholder="请输入密码"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.user_status"
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
          v-hasPermi="['platform:user:add']"
          >新增
        </el-button>
        <!--      </el-col>-->
        <!--      <el-col :span="1.5">-->
        <!--        <el-button-->
        <!--          type="success"-->
        <!--          plain-->
        <!--          icon="el-icon-edit"-->
        <!--          size="mini"-->
        <!--          :disabled="single"-->
        <!--          @click="handleUpdate"-->
        <!--          v-hasPermi="['platform:user:edit']"-->
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
        <!--          v-hasPermi="['platform:user:remove']"-->
        <!--        >删除</el-button>-->
        <!--      </el-col>-->
        <!--      <el-col :span="1.5">-->
        <!--        <el-button-->
        <!--          type="warning"-->
        <!--          plain-->
        <!--          icon="el-icon-download"-->
        <!--          size="mini"-->
        <!--          @click="handleExport"-->
        <!--          v-hasPermi="['platform:user:export']"-->
        <!--        >导出</el-button>-->
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="userList"
      @selection-change="handleSelectionChange"
    >
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column
        label="所属上级"
        align="center"
        prop="commanderUserName"
      />
      <el-table-column label="昵称" align="center" prop="nickName" />
      <!-- <el-table-column label="手机号" align="center" prop="phone" /> -->
      <el-table-column property="avatar" label="头像" align="center">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="账号" align="center" prop="userName" />
      <!-- <el-table-column
        label="支付宝姓名"
        align="center"
        prop="zfbName"
        width="85"
      />
      <el-table-column
        label="支付宝账号"
        align="center"
        prop="zfbUserName"
        width="85"
      /> -->
      <el-table-column
        label="用户身份"
        align="center"
        prop="zfbUserName"
        width="85"
      >
        <template slot-scope="scope">
          <span>{{
            scope.row.roleId == 1
              ? "星辰会员"
              : scope.row.roleId == 2
              ? "星辰贵宾"
              : scope.row.roleId == 3
              ? "联盟理事"
              : "代理"
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        v-if="false"
        label="用户身份"
        align="center"
        prop="vipLevelName"
        width="85"
      >
      </el-table-column>
      <el-table-column label="微信昵称" align="center" prop="wxNickName" />
      <!--      <el-table-column label="微信头像" align="center" prop="wxAvatar"/>-->
      <el-table-column property="wxAvatar" label="微信头像" align="center">
        <template slot-scope="scope">
          <el-avatar
            :src="scope.row.wxAvatar"
            v-if="scope.row.wxAvatar !== null"
          ></el-avatar>
        </template>
      </el-table-column>
      <!-- <el-table-column label="关卡" align="center" prop="barrier" /> -->
      <!-- <el-table-column label="等级" align="center" prop="grade" /> -->
      <!--      <el-table-column label="密码" align="center" prop="password"/>-->
      <!--      <el-table-column label="VIP" align="center" prop="isVip">-->
      <!--        <template slot-scope="scope">-->
      <!--          <dict-tag :options="dict.type.user_is_vip" :value="scope.row.isVip"/>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!-- <el-table-column label="代理" align="center" prop="commander">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.is_commander"
            :value="scope.row.commander"
          />
        </template>
      </el-table-column> -->
      <!--      <el-table-column label="代理比例(百分比)" align="center" prop="commanderRatio"/>-->
      <!-- <el-table-column label="代理比例" align="center" prop="commanderRatio">
        <template slot-scope="scope">
          <span>{{ scope.row.commanderRatio }}%</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column
        prop="getType"
        label="代理收益方式"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.getType === 1" style="color: #00afff"
            >ECPM</span
          >
          <span v-if="scope.row.getType === 2" style="color: red">区间值</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column
        label="最小金币"
        align="center"
        prop="goldMin"
        v-if="isAdmin()"
      /> -->
      <!-- <el-table-column
        label="最大金币"
        align="center"
        prop="goldMax"
        v-if="isAdmin()"
      /> -->
      <!-- <el-table-column
        prop="juniorGetType"
        label="下级收益方式"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.juniorGetType === 1" style="color: #00afff"
            >ECPM</span
          >
          <span v-if="scope.row.juniorGetType === 2" style="color: red"
            >区间值</span
          >
        </template>
      </el-table-column> -->
      <!-- <el-table-column
        label="下级最小金币"
        align="center"
        prop="juniorGoldMin"
        width="100"
        v-if="isAdmin()"
      /> -->
      <!-- <el-table-column
        label="下级最大金币"
        align="center"
        prop="juniorGoldMax"
        width="100"
        v-if="isAdmin()"
      /> -->
      <!--      <el-table-column label="有上级比例(百分比)" align="center" prop="haveManagerRatio"/>-->
      <!-- <el-table-column
        label="有上级比例"
        align="center"
        prop="haveManagerRatio"
        width="90"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.haveManagerRatio }}%</span>
        </template>
      </el-table-column> -->
      <!--      <el-table-column label="无上级比例(百分比)" align="center" prop="noManagerRatio"/>-->
      <!-- <el-table-column
        label="无上级比例"
        align="center"
        prop="noManagerRatio"
        width="90"
        v-if="isAdmin()"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.noManagerRatio }}%</span>
        </template>
      </el-table-column> -->
      <!--      <el-table-column label="是否VIP" align="center" prop="isVip" />-->

      <el-table-column label="金币" align="center" prop="goldCoin" />
      <el-table-column label="积分" align="center" prop="points" />
      <el-table-column label="现金" align="center" prop="cash" />
      <el-table-column label="邀请码" align="center" prop="invitationCode" />
      <!--      <el-table-column label="0：删除，1：正常" align="center" prop="delStatus" />-->
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.user_status"
            :value="scope.row.status"
          />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="注销时间" align="center" prop="destroyTime" />
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
          <!--            v-hasPermi="['platform:user:edit']"-->
          <!--          >修改</el-button>-->
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['platform:user:remove']"-->
          <!--          >删除</el-button>-->
          <el-button
            v-if="scope.row.status === 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDeactivate(scope.row)"
            >停用
          </el-button>
          <el-button
            v-if="scope.row.status === 0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleRestore(scope.row)"
            >恢复
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-user-solid"-->
          <!--            @click="handleInvitationInfo(scope.row)"-->
          <!--          >我的下级-->
          <!--          </el-button>-->
          <el-dropdown
            size="mini"
            @command="(command) => handleCommand(command, scope.row)"
            v-hasPermi="['platform:user:more']"
          >
            <span class="el-dropdown-link">
              <i class="el-icon-d-arrow-right el-icon--right"></i>更多
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="setNickNameInfo" v-if="isAdmin()"
                >修改昵称</el-dropdown-item
              >
              <el-dropdown-item command="resetPassword" v-if="isAdmin()"
                >重置密码</el-dropdown-item
              >
              <el-dropdown-item command="handleInvitationInfo" v-if="isAdmin()"
                >我的下级</el-dropdown-item
              >
              <el-dropdown-item command="resetInvitationCode" v-if="isAdmin()"
                >重置邀请码</el-dropdown-item
              >
              <el-dropdown-item command="resetUserVip" v-if="isAdmin()"
                >修改用户vip等级</el-dropdown-item
              >
              <!-- <el-dropdown-item command="rechargeGold" v-if="isAdmin()"
                >金币充值</el-dropdown-item
              > -->
              <!-- <el-dropdown-item
                command="setCommander"
                v-if="scope.row.commander === 0 && isAdmin()"
                >设置代理</el-dropdown-item
              > -->
              <!-- <el-dropdown-item
                command="cancelCommander"
                v-if="scope.row.commander === 1 && isAdmin()"
                >取消代理</el-dropdown-item
              > -->
              <!-- <el-dropdown-item
                command="setCommanderValue"
                v-if="scope.row.commander === 1 && isAdmin()"
                >代理比例</el-dropdown-item
              > -->
              <!-- <el-dropdown-item
                command="setGetTypeInfo"
                v-if="scope.row.commander === 1 && isAdmin()"
                >代理收益方式</el-dropdown-item
              > -->
              <!-- <el-dropdown-item
                command="setJuniorGetTypeInfo"
                v-if="scope.row.commander === 1 && isAdmin()"
                >下级收益方式</el-dropdown-item
              > -->
              <!-- <el-dropdown-item command="ordinaryHaveManagerRatio"
                >有上级比例</el-dropdown-item
              > -->
              <!-- <el-dropdown-item
                command="ordinaryNoManagerRatio"
                v-if="isAdmin()"
                >无上级比例</el-dropdown-item
              > -->
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

    <!--我的下级列表-->
    <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="invitation.open"
      width="500px"
      append-to-body
    >
      <el-table v-loading="invitation.loading" :data="invitation.gridData">
        <el-table-column
          property="nickName"
          label="昵称"
          width="150"
        ></el-table-column>
        <el-table-column property="avatar" label="头像" width="150">
          <template slot-scope="scope">
            <el-avatar :src="scope.row.avatar"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column
          property="invitationTime"
          label="邀请时间"
          width="150"
        ></el-table-column>
      </el-table>
      <pagination
        v-show="invitation.total > 0"
        :total="invitation.total"
        :page-sizes="[5, 10, 20, 50]"
        :page.sync="invitation.queryParams.pageNum"
        :limit.sync="invitation.queryParams.pageSize"
        @pagination="handleInvitationInfoShow"
      />
    </el-dialog>

    <!-- 添加或修改用户信息对话框 -->
    <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设置最小最大金币对话框 -->
    <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="getTypeOpen"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="收益方式" prop="getType">
          <el-select
            v-model="form.getType"
            placeholder="请选择收益方式"
            clearable
            size="small"
          >
            <el-option label="ECPM" :value="1" />
            <el-option label="区间值" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="最小金币" prop="goldMin" v-if="form.getType === 2">
          <el-input v-model="form.goldMin" placeholder="请输入最小金币" />
        </el-form-item>
        <el-form-item label="最大金币" prop="goldMax" v-if="form.getType === 2">
          <el-input v-model="form.goldMax" placeholder="请输入最大金币" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancelGetType">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设置下级最小最大金币对话框 -->
    <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="juniorGetTypeOpen"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="收益方式" prop="juniorGetType">
          <el-select
            v-model="form.juniorGetType"
            placeholder="请选择收益方式"
            clearable
            size="small"
          >
            <el-option label="ECPM" :value="1" />
            <el-option label="区间值" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item
          label="最小金币"
          prop="juniorGoldMin"
          v-if="form.juniorGetType === 2"
        >
          <el-input v-model="form.juniorGoldMin" placeholder="请输入最小金币" />
        </el-form-item>
        <el-form-item
          label="最大金币"
          prop="juniorGoldMax"
          v-if="form.juniorGetType === 2"
        >
          <el-input v-model="form.juniorGoldMax" placeholder="请输入最大金币" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancelJuniorGetType">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 充值金币 -->
    <el-dialog
      :close-on-click-modal="false"
      title="充值金币"
      :visible.sync="recharge"
      width="500px"
      append-to-body
    >
      <el-form ref="form2" :model="form2" :rules="rules2" label-width="80px">
        <el-form-item label="充值数量" prop="coinNum">
          <el-input v-model="form2.coinNum" placeholder="请输入充值数量" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form2.remarks" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRecharge">确 定</el-button>
        <el-button @click="recharge = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改用户vip -->
    <el-dialog
      :close-on-click-modal="false"
      title="修改用户vip"
      :visible.sync="changeVip"
      width="500px"
      append-to-body
    >
      <el-form ref="form3" :model="form3" :rules="rules2" label-width="80px">
        <el-form-item label="vip等级" prop="coinNum">
          <el-select
            @change="vipChange"
            v-model="changeVipId"
            placeholder="请选择"
          >
            <el-option
              v-for="item in vipConfigList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitVip">确 定</el-button>
        <el-button @click="changeVip = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listUser,
  getUser,
  delUser,
  addUser,
  updateUser,
  invitationInfo,
  modifyUserStatusDeactivate,
  resetPassword,
  resetInvitationCode,
  resetUserVip,
  setCommanderInfo,
  setCommanderValue,
  setHaveManagerRatio,
  setNoManagerRatio,
  setGetType,
  setWithdrawal,
  setUserNickName,
  getVipConfig,
} from "@/api/platform/user";
import { listRole } from "@/api/platform/role";

import { addUserRechargeLog } from "@/api/platform/userRechargeLog";

export default {
  name: "User",
  dicts: ["user_status", "user_is_vip", "is_commander"],
  data() {
    return {
      // 遮罩层
      loading: true,
      recharge: false,
      // 选中数组
      ids: [],
      changeVip: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户信息表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      getTypeOpen: false,
      juniorGetTypeOpen: false,
      invitationParentId: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nickName: null,
        userName: null,
        phone: null,
        avatar: null,
        password: null,
        commander: null,
        delStatus: null,
        status: null,
        commanderUserName: null,
      },
      //我的下级列表参数
      invitation: {
        userId: null,
        //遮罩层
        loading: false,
        // 是否显示照片弹出层
        open: false,
        //照片表格数据
        gridData: [],
        //查询参数
        queryParams: {
          parentId: null,
          pageNum: 1,
          pageSize: 5,
        },
        // 总条数
        total: 0,
      },
      changeVipId: -1,
      // 表单参数
      form: {},
      form2: {},
      form3: {},
      // 表单校验
      rules: {
        getType: [
          { required: true, message: "代理收益方式不能为空", trigger: "blur" },
        ],
        goldMin: [
          { required: true, message: "最小金币不能为空", trigger: "blur" },
        ],
        goldMax: [
          { required: true, message: "最大金币不能为空", trigger: "blur" },
        ],
        juniorGetType: [
          { required: true, message: "下级收益方式不能为空", trigger: "blur" },
        ],
        juniorGoldMin: [
          { required: true, message: "最小金币不能为空", trigger: "blur" },
        ],
        juniorGoldMax: [
          { required: true, message: "最大金币不能为空", trigger: "blur" },
        ],
        userName: [
          { required: true, message: "账号不能为空", trigger: "blur" },
          {
            min: 8,
            max: 11,
            message: "用户账号长度必须介于 8 和 11 之间",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          {
            min: 6,
            max: 10,
            message: "用户密码长度必须介于 6 和 10 之间",
            trigger: "blur",
          },
        ],
      },
      rules2: {
        coinNum: [
          { required: true, message: "充值数量不能为空", trigger: "blur" },
        ],
      },
      vipConfigList: [],
    };
  },
  created() {
    this.getList();
    this.getVipConfigList();
  },
  methods: {
    //查询vip配置
    getVipConfigList() {
      listRole().then((response) => {
        this.vipConfigList = response.rows;
      });
    },
    /** 查询用户信息列表 */
    getList() {
      this.loading = true;
      listUser(this.queryParams).then((response) => {
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancelGetType() {
      this.getTypeOpen = false;
      this.reset();
    },
    // 取消按钮
    cancelJuniorGetType() {
      this.juniorGetTypeOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        nickName: null,
        phone: null,
        createTime: null,
        avatar: null,
        updateTime: null,
        password: null,
        delStatus: null,
        status: null,
        userName: null,
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
      this.title = "添加用户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getUser(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户信息";
      });
    },
    /** 修改按钮操作 */
    handleUpdateGetType(row) {
      this.reset();
      const id = row.id || this.ids;
      getUser(id).then((response) => {
        this.form = response.data;
        this.getTypeOpen = true;
        this.title = "修改代理收益方式";
      });
    },
    /** 修改按钮操作 */
    handleUpdateJuniorGetType(row) {
      this.reset();
      const id = row.id || this.ids;
      getUser(id).then((response) => {
        this.form = response.data;
        this.juniorGetTypeOpen = true;
        this.title = "修改下级收益方式";
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateUser(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getTypeOpen = false;
              this.juniorGetTypeOpen = false;
              this.getList();
            });
          } else {
            addUser(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getTypeOpen = false;
              this.juniorGetTypeOpen = false;
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
          return delUser(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    vipChange(e) {
      this.changeVipId = e;
      // this.form3.vipLevelId = this.vipConfigList[e - 1].id;
      // this.form3.vipLevelName = this.vipConfigList[e - 1].levelName;
    },

    /** 停用按钮操作 */
    handleDeactivate(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认停用数据项？")
        .then(function () {
          return modifyUserStatusDeactivate(ids);
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
          return modifyUserStatusDeactivate(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("设置成功");
        })
        .catch(() => {});
    },

    /** 设置代理按钮操作 */
    setCommanderInfo(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认设置数据项？")
        .then(function () {
          return setCommanderInfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("设置成功");
        })
        .catch(() => {});
    },

    /** 取消代理按钮操作 */
    cancelCommander(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认取消数据项？")
        .then(function () {
          return setCommanderInfo(ids);
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
        "platform/user/export",
        {
          ...this.queryParams,
        },
        `user_${new Date().getTime()}.xlsx`
      );
    },

    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "setNickNameInfo":
          this.setNickNameInfo(row);
          break;
        case "setWithdrawalInfo":
          this.setWithdrawal(row);
          break;
        case "setJuniorGetTypeInfo":
          this.setJuniorGetTypeInfo(row);
          break;
        case "removeVip":
          this.removeVip(row);
          break;
        case "handleInvitationInfo":
          this.handleInvitationInfo(row);
          break;
        case "resetPassword":
          this.resetPassword(row);
          break;
        case "resetInvitationCode":
          this.resetInvitationCode(row);
          break;
        case "resetUserVip":
          this.resetUserVip(row);
          break;
        case "rechargeGold":
          this.rechargeGold(row);
          break;
        case "setCommander":
          this.setCommanderInfo(row);
          break;
        case "cancelCommander":
          this.cancelCommander(row);
          break;
        case "setCommanderValue":
          this.setCommanderValue(row);
          break;
        case "ordinaryHaveManagerRatio":
          this.setHaveManagerRatio(row);
          break;
        case "ordinaryNoManagerRatio":
          this.setNoManagerRatio(row);
          break;
        case "setGetTypeInfo":
          this.setGetTypeInfo(row);
          break;
        default:
          break;
      }
    },
    /** 我的下级列表 */
    handleInvitationInfo(row) {
      this.invitation.queryParams.userId = "";
      this.invitationParentId = "";
      this.invitation.open = true;
      this.invitation.queryParams.parentId = row.id;
      this.invitationParentId = row.id;
      this.title = "邀请列表";
      this.invitation.loading = true;
      invitationInfo(this.invitation.queryParams).then((response) => {
        this.invitation.gridData = response.rows;
        this.invitation.total = response.total;
        this.invitation.loading = false;
      });
    },
    /** 我的下级列表分页 */
    handleInvitationInfoShow(row) {
      this.invitation.queryParams.parentId = this.invitationParentId;
      this.title = "邀请列表";
      this.invitation.loading = true;
      invitationInfo(this.invitation.queryParams).then((response) => {
        this.invitation.gridData = response.rows;
        this.invitation.total = response.total;
        this.invitation.loading = false;
      });
    },
    /** 重置密码按钮操作 */
    resetPassword(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{6,10}$/,
        inputErrorMessage: "用户密码长度必须介于 6 和 10 之间",
      })
        .then(({ value }) => {
          resetPassword(row.id, value).then((response) => {
            this.$modal.msgSuccess("修改成功，新密码是：" + value);
            this.getList();
          });
        })
        .catch(() => {});
    },

    /** 设置用户昵称按钮操作 */
    setNickNameInfo(row) {
      this.$prompt('请输入"' + row.userName + '"的用户昵称', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{1,20}$/,
        inputErrorMessage: "昵称不符合要求",
      })
        .then(({ value }) => {
          setUserNickName(row.id, value).then((response) => {
            this.$modal.msgSuccess("设置成功");
            this.getList();
          });
        })
        .catch(() => {});
    },

    /** 设置下级是否可以提现按钮操作 */
    setWithdrawal(row) {
      this.$prompt(
        '请输入"' +
          row.userName +
          '"的下级是否可提现（0：不可提现，1：可以提现）',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          closeOnClickModal: false,
          inputPattern: /^.{1}$/,
          inputErrorMessage: "值不符合要求",
        }
      )
        .then(({ value }) => {
          setWithdrawal(row.id, value).then((response) => {
            this.$modal.msgSuccess("设置成功");
            this.getList();
          });
        })
        .catch(() => {});
    },

    /** 设置代理收益类型按钮操作 */
    setGetTypeInfo(row) {
      this.handleUpdateGetType(row);
    },

    /** 设置下级收益类型按钮操作 */
    setJuniorGetTypeInfo(row) {
      this.handleUpdateJuniorGetType(row);
    },

    /** 取消会员按钮操作 */
    removeVip(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认取消会员数据项？")
        .then(function () {
          return removeVip(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("取消成功");
        })
        .catch(() => {});
    },

    /** 设置代理比例按钮按钮操作 */
    setCommanderValue(row) {
      this.$prompt('请输入"' + row.userName + '"的代理比例值', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{1,100}$/,
        inputErrorMessage: "请输入代理比例",
      })
        .then(({ value }) => {
          setCommanderValue(row.id, value).then((response) => {
            this.$modal.msgSuccess("修改成功，新比例是：" + value + "%");
            this.getList();
          });
        })
        .catch(() => {});
    },
    /** 设置有上级比例按钮按钮操作 */
    setHaveManagerRatio(row) {
      this.$prompt('请输入"' + row.userName + '"的有上级比例值', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{1,100}$/,
        inputErrorMessage: "请输入有上级比例",
      })
        .then(({ value }) => {
          setHaveManagerRatio(row.id, value).then((response) => {
            this.$modal.msgSuccess("修改成功，新比例是：" + value + "%");
            this.getList();
          });
        })
        .catch(() => {});
    },
    /** 设置无上级比例按钮按钮操作 */
    setNoManagerRatio(row) {
      this.$prompt('请输入"' + row.userName + '"的无上级比例值', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{1,100}$/,
        inputErrorMessage: "请输入有无级比例",
      })
        .then(({ value }) => {
          setNoManagerRatio(row.id, value).then((response) => {
            this.$modal.msgSuccess("修改成功，新比例是：" + value + "%");
            this.getList();
          });
        })
        .catch(() => {});
    },
    /** 重置邀请码按钮操作 */
    resetInvitationCode(row) {
      this.$prompt('请输入"' + row.userName + '"的邀请码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{6}$/,
        inputErrorMessage: "用户邀请码长度必须是6位",
      })
        .then(({ value }) => {
          resetInvitationCode(row.id, value).then((response) => {
            this.$modal.msgSuccess("修改成功，新邀请码是：" + value);
            this.getList();
          });
        })
        .catch(() => {});
    },
    resetUserVip(row) {
      this.changeVip = true;
      this.form3 = row;
      this.changeVipId = row.roleId;
    },
    //充值金币
    rechargeGold(row) {
      this.recharge = true;
      this.form2.userId = row.id;
      // addUserRechargeLog
      // this.$prompt('请输入"' + row.userName + '"的邀请码', "提示", {
      //   confirmButtonText: "确定",
      //   cancelButtonText: "取消",
      //   closeOnClickModal: false,
      //   inputPattern: /^.{6}$/,
      //   inputErrorMessage: "用户邀请码长度必须是6位",
      // })
      //   .then(({ value }) => {
      //     resetInvitationCode(row.id, value).then((response) => {
      //       this.$modal.msgSuccess("修改成功，新邀请码是：" + value);
      //       this.getList();
      //     });
      //   })
      //   .catch(() => {});
    },
    submitRecharge() {
      this.$refs["form2"].validate((valid) => {
        if (valid) {
          addUserRechargeLog(this.form2).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.recharge = false;
          });
        }
      });
    },
    submitVip() {
      this.form3.roleId = this.changeVipId;
      updateUser(this.form3).then((response) => {
        this.$modal.msgSuccess("修改成功");
        this.changeVip = false;
        this.getList();
      });
      return;
      resetUserVip(
        this.form3.id,
        this.form3.vipLevelId,
        this.form3.vipLevelName
      ).then((response) => {
        this.$modal.msgSuccess("修改成功");
        this.changeVip = false;
        this.getList();
      });
    },
    isAdmin() {
      return this.$auth.hasRole("admin");
    },
  },
};
</script>
