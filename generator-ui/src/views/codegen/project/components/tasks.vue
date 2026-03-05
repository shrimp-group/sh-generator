<template>
  <el-dialog :title="title" v-model="open" width="90%">
    <el-table v-loading="loading" :data="dataList" border>
      <el-table-column label="任务名称" prop="taskName" width="160">
        <template #default="{row}">
          <el-input v-model="row.taskName" placeholder="请输入任务名称"/>
        </template>
      </el-table-column>
      <el-table-column label="模板" prop="tempCode" width="240">
        <template #default="{row}">
          <el-select v-model="row.tempCode" dbCode="请选择 模板" clearable filterable style="width: 206px">
            <el-option v-for="item in TEMPLATE_OPTIONS" :key="item.tempCode" :label="item.tempCode + ' ('+item.tempName+')'" :value="item.tempCode" />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="是否生成" prop="createSwitch" width="100">
        <template #default="{row}">
          <el-radio-group v-model="row.createSwitch" size="small">
            <el-radio-button v-for="item in BOOLEAN" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </template>
      </el-table-column>
      <el-table-column label="是否删除" prop="deleteSwitch" width="100">
        <template #default="{row}">
          <el-radio-group v-model="row.deleteSwitch" size="small">
            <el-radio-button v-for="item in BOOLEAN" :key="item.value" :label="item.label" :value="item.value" />
          </el-radio-group>
        </template>
      </el-table-column>
      <el-table-column label="任务项目基本路径" prop="projectBasePath" width="200">
        <template #default="{row}">
          <el-input v-model="row.projectBasePath" placeholder="请输入项目基本路径"/>
        </template>
      </el-table-column>
      <el-table-column label="任务包路径" prop="packagePath" width="320">
        <template #default="{row}">
          <el-input v-model="row.packagePath" placeholder="请输入包路径"/>
        </template>
      </el-table-column>
      <el-table-column label="任务描述" prop="taskDesc" width="160">
        <template #default="{row}">
          <el-input v-model="row.taskDesc" placeholder="请输入任务描述"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" width="160">
        <template #default="{row}">
          <el-input v-model="row.remark" placeholder="请输入备注"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort" width="80">
        <template #default="{row}">
          <el-input v-model.number="row.sort" type="number" placeholder="请输入排序"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="160">
        <template #default="{row}">{{ parseTime(row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="创建人" prop="createBy" width="120"/>
      <el-table-column label="更新时间" prop="updateTime" width="160">
        <template #default="{row}">{{ parseTime(row.updateTime) }}</template>
      </el-table-column>
      <el-table-column label="更新人" prop="updateBy" width="120"/>
      <el-table-column label="操作" fixed="right" width="60">
        <template #default="{row}">
          <el-icon @click="addTask(row)"><CirclePlusFilled /></el-icon>
          <el-icon @click="removeTask(row)" v-if="dataList.length > 1"><RemoveFilled /></el-icon>
        </template>
      </el-table-column>
    </el-table>

    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup name="GenTask">
import {taskList, taskSave} from "@/api/task";
import {templateOptions} from "@/api/template.js";

defineExpose({init});
const { proxy } = getCurrentInstance();

const { BOOLEAN } = proxy.useDict("BOOLEAN");
const open = ref(false);
const title = ref("");
const dataList = ref([]);
const loading = ref(false);

const TEMPLATE_OPTIONS = ref([]);

const queryParams = ref({
  projectCode: undefined,
});

function init(row) {
  if (!row || !row.projectCode) {
    return;
  }
  queryParams.value.projectCode = row.projectCode;
  getList();
}

/** 查询参数列表 */
function getList() {
  loading.value = true;
  initTemplateOptions();
  taskList(queryParams.value).then(res => {
    let data = res.data;
    if (data == null || data.length === 0) {
      data = [{
        taskName: "demo",
        tempCode: queryParams.value.projectCode,
        createSwitch: 1,
        deleteSwitch: 1,
        sort: 1
      }];
    }
    dataList.value = data;
    open.value = true;
    title.value = "修改项目任务: " + queryParams.value.projectCode;
  }).finally(() => {
    loading.value = false;
  });
}

function initTemplateOptions() {
  templateOptions().then(res => {
    TEMPLATE_OPTIONS.value = res.data;
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  dataList.value = [];
}

/** 新增任务 */
function addTask(row) {
  // 复制当前行的数据作为新任务的基础
  const newTask = JSON.parse(JSON.stringify(row));
  // 重置ID和时间戳等字段
  delete newTask.id;
  delete newTask.createTime;
  delete newTask.createBy;
  delete newTask.updateTime;
  delete newTask.updateBy;
  // 生成新的排序值
  newTask.sort = (Math.max(...dataList.value.map(item => item.sort || 0)) || 0) + 1;
  // 添加到数据列表
  dataList.value.push(newTask);
}

/** 移除任务 */
function removeTask(row) {
  const index = dataList.value.findIndex(item => item.id === row.id);
  if (index !== -1) {
    dataList.value.splice(index, 1);
  }
}

/** 提交表单 */
function submitForm() {
  // 校验所有字段
  const validationErrors = [];

  dataList.value.forEach((item, index) => {
    if (!item.taskName) {
      validationErrors.push(`第${index + 1}行：任务名称不能为空`);
    }
    if (!item.tempCode) {
      validationErrors.push(`第${index + 1}行：模板编码不能为空`);
    }
    if (item.sort === undefined || item.sort === null) {
      validationErrors.push(`第${index + 1}行：排序不能为空`);
    }
    if (item.createSwitch === undefined || item.createSwitch === null) {
      validationErrors.push(`第${index + 1}行：是否生成不能为空`);
    }
    if (item.deleteSwitch === undefined || item.deleteSwitch === null) {
      validationErrors.push(`第${index + 1}行：是否删除不能为空`);
    }
    if (!item.projectBasePath) {
      validationErrors.push(`第${index + 1}行：任务项目基本路径不能为空`);
    }
    if (!item.packagePath) {
      validationErrors.push(`第${index + 1}行：任务包路径不能为空`);
    }
  });

  // 如果有校验错误，显示错误信息
  if (validationErrors.length > 0) {
    proxy.$modal.msgError(validationErrors.join('\n'));
    return;
  }

  // 过滤掉不需要编辑的字段
  const submitData = dataList.value.map(item => {
    const { createTime, createBy, updateTime, updateBy, ...rest } = item;
    return rest;
  });

  loading.value = true;
  taskSave(submitData).then(res => {
    proxy.$modal.msgSuccess("保存成功");
    getList();
  }).finally(() => {
    loading.value = false;
  });
}

</script>

<style scoped lang="scss">
.el-table {
  height: calc(100vh - 300px);
  min-height: 320px;
}
</style>
