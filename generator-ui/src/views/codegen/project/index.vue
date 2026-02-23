<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" @submit.native.prevent>
      <el-form-item prop="projectCode">
        <el-input v-model="queryParams.projectCode" placeholder="项目编码, 模糊搜索" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item prop="projectName">
        <el-input v-model="queryParams.projectName" placeholder="项目名称, 模糊搜索" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item prop="moduleName">
        <el-input v-model="queryParams.moduleName" placeholder="模块名, 模糊搜索" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item style="float: right;">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="ID" prop="id" width="80" v-if="columns.id.visible"/>
      <el-table-column label="项目编码" prop="projectCode" width="160"/>
      <el-table-column label="项目名称" prop="projectName" width="200"/>
      <el-table-column label="模块名" prop="moduleName" width="120"/>
      <el-table-column label="数据库编码" prop="dbCode" width="120"/>
      <el-table-column label="表前缀" prop="tablePrefix" width="120"/>
      <el-table-column label="项目说明" prop="projectDesc" min-width="120"/>
      <el-table-column label="备注" prop="remark" min-width="120" v-if="columns.remark.visible"/>
      <el-table-column label="创建时间" prop="createTime" width="160" v-if="columns.createTime.visible">
        <template #default="{row}">{{ parseTime(row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="创建人" prop="createBy" width="120" v-if="columns.createBy.visible"/>
      <el-table-column label="更新时间" prop="updateTime" width="160" v-if="columns.updateTime.visible">
        <template #default="{row}">{{ parseTime(row.updateTime) }}</template>
      </el-table-column>
      <el-table-column label="更新人" prop="updateBy" width="120" v-if="columns.updateBy.visible"/>
      <el-table-column fixed="right" width="160">
        <template #header><table-setting v-model:columns="columns"/></template>
        <template #default="{row}">
          <el-button link type="primary" icon="Edit" @click="handleTask(row)">任务</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(row)" title="编辑"/>
          <el-button link type="danger" icon="Delete" @click="handleDelete(row)" title="删除"/>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.current"
        v-model:limit="queryParams.size"
        @pagination="getList"
    />
    <tasks ref="tasksRef"/>
    <edit ref="editRef" @change="getList"/>
  </div>
</template>

<script setup name="GenProject">
import { projectPage, projectRemove} from "@/api/project";
import Tasks from "./components/tasks"
import Edit from "./components/edit"

const { proxy } = getCurrentInstance();

const dataList = ref([]);
const loading = ref(false);
const total = ref(0);

const columns = ref({
  id: {label: "ID", visible: false},
  remark: {label: "备注", visible: true},
  updateBy: {label: "修改人", visible: false},
  updateTime: {label: "修改时间", visible: false},
  createBy: {label: "创建人", visible: false},
  createTime: {label: "创建时间", visible: false},
})

const queryParams = ref({
  current: 1,
  size: 20,
  projectCode: undefined,
  userCode: undefined,
  dbCode: undefined,
  tablePrefix: undefined,
  moduleName: undefined,
  projectName: undefined,
  projectDesc: undefined,
});

function init() {
  getList();
}

/** 查询参数列表 */
function getList() {
  loading.value = true;
  projectPage(queryParams.value).then(res => {
    const data = res.data;
    dataList.value = data.records;
    total.value = data.total;
  }).finally(() => {
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.current = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

function handleTask(row) {
  proxy.$refs["tasksRef"].init(row);
}

function handleAdd() {
  proxy.$refs["editRef"].init();
}
function handleUpdate(row) {
  proxy.$refs["editRef"].init(row);
}

/** 删除按钮操作 */
function handleDelete(row) {
  proxy.$modal.confirm('是否确认删除 :"' + row.id + '"？').then(() => {
    projectRemove({id: row.id}).then(res => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
  }).catch(() => {});
}

init();
</script>

