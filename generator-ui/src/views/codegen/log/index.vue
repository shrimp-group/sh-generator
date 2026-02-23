<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" @submit.native.prevent>
      <el-form-item prop="projectCode">
        <el-input v-model="queryParams.projectCode" placeholder="项目编码" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="dateRange" style='width: 380px'
                        value-format="YYYY-MM-DD HH:mm:ss"
                        :shortcuts="timeRangeShortcuts"
                        type="datetimerange" range-separator="-" start-placeholder="开始时间" end-placeholder="结束时间"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="ID" prop="id" width="80"/>
      <el-table-column label="项目编码" prop="projectCode" width="160"/>
      <el-table-column label="生成路径" prop="genPath" min-width="120"/>
      <el-table-column label="开始时间" prop="startTime" width="160">
        <template #default="{row}">{{ parseTime(row.startTime) }}</template>
      </el-table-column>
      <el-table-column label="结束时间" prop="endTime" width="160">
        <template #default="{row}">{{ parseTime(row.endTime) }}</template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="160">
        <template #default="{row}">{{ parseTime(row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="创建人" prop="createBy" width="120"/>
    </el-table>
    <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.current"
        v-model:limit="queryParams.size"
        @pagination="getList"
    />
  </div>
</template>

<script setup name="GenLog">
import { logPage } from "@/api/log";
import {timeRangeShortcuts} from "@/utils/shrimp.js";

const { proxy } = getCurrentInstance();

const dataList = ref([]);
const loading = ref(false);
const total = ref(0);
const dateRange = ref([]);


const queryParams = ref({
  current: 1,
  size: 20,
  projectCode: undefined,
  authCode: undefined,
});


function init() {
  const now = new Date();
  dateRange.value = [
    proxy.parseTime(now.setMinutes(now.getDay()-7), '{y}-{m}-{d}T{h}:{i}:{s}'),
    proxy.parseTime(new Date(), '{y}-{m}-{d}') + 'T23:59:59'
  ];
  getList();
}

/** 查询参数列表 */
function getList() {
  loading.value = true;
  logPage(proxy.addDateRange(queryParams.value, dateRange.value)).then(res => {
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


init();
</script>

