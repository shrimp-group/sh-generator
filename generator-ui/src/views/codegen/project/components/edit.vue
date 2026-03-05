<template>
  <el-dialog :title="title" v-model="open" width="1080px">
    <el-form ref="editRef" :model="form" :rules="rules" label-width="80px">

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="项目编码" prop="projectCode">
            <el-input v-model="form.projectCode" placeholder="请输入项目编码, 留空自动生成" />
          </el-form-item>
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="form.projectName" placeholder="请输入项目名称" />
          </el-form-item>
          <el-form-item label="项目说明" prop="projectDesc">
            <el-input v-model="form.projectDesc" placeholder="请输入项目说明" />
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="数据源" prop="dbCode">
            <el-select v-model="form.dbCode" dbCode="请选择数据源" clearable filterable style="width: 660px">
              <el-option v-for="item in DATASOURCE_OPTIONS" :key="item.dbCode" :label="item.dbCode" :value="item.dbCode" />
            </el-select>
          </el-form-item>
          <el-form-item label="表前缀" prop="tablePrefix">
            <el-input v-model="form.tablePrefix" placeholder="请输入表前缀" />
          </el-form-item>
          <el-form-item label="模块名" prop="moduleName">
            <el-input v-model="form.moduleName" placeholder="请输入模块名(英文)" />
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup name="GenProjectEdit">
import {projectDetail, projectCreate, projectUpdate} from "@/api/project";
import {datasourceOptions} from "@/api/datasource";
import {useDebounceFn} from "@vueuse/core";

defineExpose({init});
const emit = defineEmits(["change"]);
const { proxy } = getCurrentInstance();
const open = ref(false);
const title = ref("");
const form = ref({});
const DATASOURCE_OPTIONS = ref([]);

const rules = ref({
  projectCode: [{ required: false, message: "项目编码 不能为空", trigger: "blur"}],
  userCode: [{ required: true, message: "用户名 不能为空", trigger: "blur"}],
  dbCode: [{ required: true, message: "数据库编码 不能为空", trigger: "blur"}],
  tablePrefix: [{ required: true, message: "表前缀 不能为空", trigger: "blur"}],
  moduleName: [{ required: true, message: "模块名 不能为空", trigger: "blur"}],
  projectName: [{ required: true, message: "项目名称 不能为空", trigger: "blur"}],
  projectDesc: [{ required: true, message: "项目说明 不能为空", trigger: "blur"}],
  sort: [{ required: true, message: "排序 不能为空", trigger: "blur"}],
  remark: [{ required: false, message: "备注 不能为空", trigger: "blur"}],
})

/** 表单重置 */
function reset() {
  form.value = {};
  proxy.resetForm("editRef");
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

// 新增/修改按钮操作
function init(row) {
  reset();
  initDatasourceOptions();
  if (!row || !row.id) {
    open.value = true;
    title.value = "添加";
    form.value.sort = 99;
  } else {
    projectDetail({id: row.id}).then(res => {
      form.value = res.data;
      open.value = true;
      title.value = "修改";
    });
  }
}

function initDatasourceOptions() {
  datasourceOptions().then(res => {
    DATASOURCE_OPTIONS.value = res.data;
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["editRef"].validate(valid => {
    if (valid) {
      if (form.value.id) {
        projectUpdate(form.value).then(res => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          emit("change", true);
        });
      } else {
        projectCreate(form.value).then(res => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          emit("change", true);
        });
      }
    }
  });
}
</script>

