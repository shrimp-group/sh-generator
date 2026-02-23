<template>
  <el-dialog :title="title" v-model="open" width="1080px">
    <el-form ref="editRef" :model="form" :rules="rules" label-width="108px">

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="数据源编码" prop="dbCode">
            <el-input v-model="form.dbCode" :disabled="!!form.id" placeholder="请输入数据源编码, 留空自动生成" />
          </el-form-item>
          <el-form-item label="数据库类型" prop="dbType">
            <el-radio-group v-model="form.dbType">
              <el-radio-button v-for="item in DATASOURCE_TYPE" :key="item.value" :label="item.label" :value="item.value" />
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注" type="textarea" :rows="6"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="主机名" prop="dbHost">
            <el-input v-model="form.dbHost" placeholder="请输入主机名" />
          </el-form-item>
          <el-form-item label="端口" prop="dbPort">
            <el-input v-model="form.dbPort" type="number" placeholder="请输入端口" />
          </el-form-item>
          <el-form-item label="数据库名称" prop="dbSchema">
            <el-input v-model="form.dbSchema" placeholder="请输入数据库名称" />
          </el-form-item>
          <el-form-item label="数据库用户名" prop="dbUsername">
            <el-input v-model="form.dbUsername" placeholder="请输入数据库用户名" />
          </el-form-item>
          <el-form-item label="数据库密码" prop="dbPassword">
            <el-input v-model="form.dbPassword" placeholder="请输入数据库密码, 若无需改变请留空!" />
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

<script setup name="GenDatasourceEdit">
import {datasourceDetail, datasourceCreate, datasourceUpdate} from "@/api/datasource";

defineExpose({init});
const emit = defineEmits(["change"]);
const { proxy } = getCurrentInstance();
const { DATASOURCE_TYPE } = proxy.useDict("DATASOURCE_TYPE");
const open = ref(false);
const title = ref("");
const form = ref({});
const rules = ref({
  dbCode: [{ required: false, message: "数据源编码 不能为空", trigger: "blur"}],
  dbType: [{ required: true, message: "数据库类型 不能为空", trigger: "blur"}],
  dbHost: [{ required: true, message: "主机名 不能为空", trigger: "blur"}],
  dbPort: [{ required: true, message: "端口 不能为空", trigger: "blur"}],
  dbSchema: [{ required: true, message: "数据库名称 不能为空", trigger: "blur"}],
  dbUsername: [{ required: true, message: "数据库用户名 不能为空", trigger: "blur"}],
  remark: [{ required: true, message: "备注 不能为空", trigger: "blur"}],
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
  if (!row || !row.id) {
    open.value = true;
    title.value = "添加";
    form.value.sort = 99;
    form.value.dbType = 'MYSQL';
  } else {
    datasourceDetail({id: row.id}).then(res => {
      form.value = res.data;
      open.value = true;
      title.value = "修改";
    });
  }
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["editRef"].validate(valid => {
    if (valid) {
      if (form.value.id) {
        datasourceUpdate(form.value).then(res => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          emit("change", true);
        });
      } else {
        datasourceCreate(form.value).then(res => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          emit("change", true);
        });
      }
    }
  });
}
</script>

