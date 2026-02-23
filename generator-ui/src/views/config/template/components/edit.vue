<template>
  <el-dialog :title="title" v-model="open" width="1080px">
    <el-form ref="editRef" :model="form" :rules="rules" label-width="80px">

      <el-row :gutter="20">
        <el-col :span="8">
          <el-form-item label="模板编码" prop="tempCode">
            <el-input v-model="form.tempCode" :disabled="!!form.id" placeholder="请输入模板编码, 留空时自动生成" />
          </el-form-item>
          <el-form-item label="模板Key" prop="tempKey">
            <el-input v-model="form.tempKey" placeholder="请输入模板Key" />
          </el-form-item>
          <el-form-item label="模板名称" prop="tempName">
            <el-input v-model="form.tempName" placeholder="请输入模板名称" />
          </el-form-item>
          <el-form-item label="文件后缀" prop="tempSubfix">
            <el-input v-model="form.tempSubfix" placeholder="请输入生成的文件后缀" />
          </el-form-item>
          <el-form-item label="模板描述" prop="tempDesc">
            <el-input v-model="form.tempDesc" placeholder="请输入模板描述" />
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input v-model="form.sort" type="number" placeholder="请输入排序" />
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注" type="textarea" :rows="4"/>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="模板内容" prop="tempContent">
            <monaco-editor v-model="form.tempContent" height="340px"/>
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

<script setup name="GenTemplateEdit">
import {templateDetail, templateCreate, templateUpdate} from "@/api/template";
import MonacoEditor from "@/components/MonacoEditor";

defineExpose({init});
const emit = defineEmits(["change"]);
const { proxy } = getCurrentInstance();
const { BOOLEAN } = proxy.useDict("BOOLEAN");
const open = ref(false);
const title = ref("");
const form = ref({});
const rules = ref({
  tempCode: [{ required: false, message: "模板编码 不能为空", trigger: "blur"}],
  tempKey: [{ required: true, message: "模板Key 不能为空", trigger: "blur"}],
  userCode: [{ required: true, message: "用户名 不能为空", trigger: "blur"}],
  tempName: [{ required: true, message: "模板名称 不能为空", trigger: "blur"}],
  tempSubfix: [{ required: true, message: "生成的文件后缀 不能为空", trigger: "blur"}],
  tempDesc: [{ required: true, message: "模板描述 不能为空", trigger: "blur"}],
  tempContent: [{ required: true, message: "模板内容 不能为空", trigger: "blur"}],
  sort: [{ required: true, message: "排序 不能为空", trigger: "blur"}],
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
  } else {
    templateDetail({id: row.id}).then(res => {
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
        templateUpdate(form.value).then(res => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          emit("change", true);
        });
      } else {
        templateCreate(form.value).then(res => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          emit("change", true);
        });
      }
    }
  });
}
</script>

