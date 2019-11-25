<template>
  <div>
    <a-card class="card" title="创作文章" :bordered="false">
      <a-form :form="form" class="form">
        <a-row class="form-row" :gutter="16">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item label="标题">
              <a-input
                placeholder="请输入标题"
                v-decorator="[
              'repository.name',
              {rules: [{ required: true, message: '请输入标题', whitespace: true}]}
            ]"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="{span: 7, offset: 1}" :lg="{span: 8}" :md="{span: 12}" :sm="24">
            <a-form-item label="作者">
              <a-input
                placeholder="焦宁波"
                v-decorator="[
              'repository.name',
              {rules: [{ required: true, message: '请输入标题', whitespace: true}]}
            ]"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="{span: 9, offset: 1}" :lg="{span: 10}" :md="{span: 24}" :sm="24">
            <a-form-item label="栏目">
              <a-select
                placeholder="请选择栏目"
                v-decorator="[ 'repository.manager', {rules: [{ required: true, message: '请选择栏目'}]} ]"
              >
                <a-select-option value="转载">转载</a-select-option>
                <a-select-option value="笔记">笔记</a-select-option>
                <a-select-option value="杂谈">杂谈</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="16">
          <a-col :lg="6" :md="12" :sm="24">
            <a-form-item label="关键字" fieldDecoratorId="owner" initialValue="['1', '2']">
              <a-select mode="multiple" style="max-width: 286px">
                <a-select-option value="3">我自己</a-select-option>
                <a-select-option value="1">吴家豪</a-select-option>
                <a-select-option value="2">周星星</a-select-option>
                <a-select-option value="4">李宁</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="{span: 7, offset: 1}" :lg="{span: 8}" :md="{span: 12}" :sm="24">
            <a-form-item label="摘要">
              <a-input
                placeholder="请输入摘要"
                v-decorator="[
              'repository.name',
              {rules: [{ required: true, message: '请输入摘要', whitespace: true}]}
            ]"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="{span: 9, offset: 1}" :lg="{span: 10}" :md="{span: 24}" :sm="24">
            <a-form-item label="封面图片">
              <a-input
                placeholder="请上传图片"
                v-decorator="[
              'repository.name',
              {rules: [{ required: true, message: '请上传图片', whitespace: true}]}
            ]"
              />
            </a-form-item>
          </a-col>
        </a-row>

        <a-row class="form-row">
          <a-col>
            <a-form-item label="文章正文"></a-form-item>
          </a-col>
          <quill-editor
            v-model="content"
            ref="myQuillEditor"
            :options="editorOption"
            @blur="onEditorBlur($event)"
            @focus="onEditorFocus($event)"
            @change="onEditorChange($event)"
          ></quill-editor>
        </a-row>

        <a-row class="form-row">
          <a-form-item label></a-form-item>

          <a-form-item>
            <a-button type="primary">发布</a-button>
            <a-button style="margin-left: 8px">存草稿</a-button>
          </a-form-item>
        </a-row>
      </a-form>
    </a-card>
  </div>
</template> 
<script>
import { quillEditor } from 'vue-quill-editor'
export default {
  data() {
    return {
      article: {},
      content: `<p>hello world</p>`,
      editorOption: {
        theme: 'snow'
      }
    }
  },
  methods: {
    onEditorBlur() {
      //失去焦点事件
    },
    onEditorFocus() {
      //获得焦点事件
    },
    onEditorChange() {
      console.log('test')
      //内容改变事件
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
.card {
  margin-bottom: 24px;
}
</style>