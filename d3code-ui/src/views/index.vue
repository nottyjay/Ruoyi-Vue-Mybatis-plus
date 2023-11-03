<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          领取阿里云通用云产品
          <br/>
          <el-link
            href="https://www.aliyun.com/minisite/goods?userCode=f8dwblym"
            type="primary"
            target="_blank"
          >https://www.aliyun.com/minisite/goods?userCode=f8dwblym
          </el-link
          >
          <br/>
          领取腾讯云通用云产品
          <br/>
          <el-link
            href="https://cloud.tencent.com/act/cps/redirect?redirect=2446&cps_key=6e68eb5fe0bdd03984b484f3ed0d0fb3&from=console"
            type="primary"
            target="_blank"
          >
            https://cloud.tencent.com/act/cps/redirect?redirect=2446&cps_key=6e68eb5fe0bdd03984b484f3ed0d0fb3&from=console
          </el-link
          >
          <br/>
          阿里云服务器折扣区
          <el-link href="https://www.aliyun.com/daily-act/ecs/activity_selection?userCode=f8dwblym" type="primary"
                   target="_blank"
          >>☛☛点我进入☚☚
          </el-link
          >
          &nbsp;&nbsp;&nbsp; 腾讯企业上云优惠
          <el-link
            href="https://cloud.tencent.com/act/cps/redirect?redirect=1060&cps_key=6e68eb5fe0bdd03984b484f3ed0d0fb3&from=console"
            type="primary" target="_blank"
          >>☛☛点我进入☚☚
          </el-link
          >
          <br/>
        </blockquote>

        <hr/>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>D3Code后台管理框架</h2>
        <p>
          D3Code后台管理框架脱胎于
          <el-link
            href="https://gitee.com/y_project/RuoYi-Vue"
            type="primary"
            target="_blank"
          >[Ruoyi-Vue]
          </el-link
          >
          ，在保留原有功能的基础之上增加了Mybatis-plus支持，同时提供了BaseMapper与ServiceImpl的增强类BaseMapperX与ServiceImplX，
          在保留原有的操作习惯下更进一步简化对数据库的操作。目前单表操作的情况下可以完全免去手写SQL的烦恼。
        </p>
        <p>此外，本框架还提供了基于用户组的权限控制策略，例如当成立某个独立小组需要更多权限时可以使用，省去了对每个组员单独添加新角色的繁琐
          操作。另外，框架集成了Flowable的流程控制，动态表单制作【基于&lt;ng-form-element&gt;制作】，在开发需要流程控制的项目时提供
          更好的开发体验。</p>
        <p>
          <b>当前版本:</b> <span>v1.0.2</span>
        </p>
        <p>
          <el-tag type="danger">&yen;免费开源</el-tag>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://gitee.com/anhui-infomation/d3code')"
          >访问码云
          </el-button
          >
          <el-button
            size="mini"
            icon="el-icon-s-home"
            plain
            @click="goTarget('http://www.alphay-tech.com')"
          >访问主页
          </el-button
          >
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis-plus</li>
              <li>Flowable</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li><a href="https://gitee.com/jjxliu306/ng-form-element" target="_blank">ng-form-element</a></li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider/>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> 官网：
              <el-link
                href="http://www.alphay-tech.com"
                target="_blank"
              >http://www.alphay-tech.com
              </el-link
              >
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v1.0.3 - 2023-11-3">
              <ol>
                <li>优化附件管理器UI</li>
                <li>修复用户列表与系统公告列表数据表格渲染bug</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.2 - 2023-10-25">
              <ol>
                <li>角色权限可以授予用户组</li>
                <li>用户组编辑功能优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.1 - 2023-8-20">
              <ol>
                <li>新增工作流程功能</li>
                <li>集成Flowable流程控制引擎</li>
                <li>动态表单制作</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>捐赠支持</span>
          </div>
          <div class="body">
            <img
              src="https://vkceyugu.cdn.bspapp.com/VKCEYUGU-76eddc9e-0b4f-4996-8d0c-8018690010d4/b714caae-1a2b-4e74-a2bf-94596c52c5e3.png"
              alt="donate"
              width="100%"
            />
            <span style="display: inline-block; height: 30px; line-height: 30px"
            >你可以请作者喝杯咖啡表示鼓励</span
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      // 版本号
      version: '3.8.2'
    }
  },
  methods: {
    goTarget(href) {
      window.open(href, '_blank')
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

