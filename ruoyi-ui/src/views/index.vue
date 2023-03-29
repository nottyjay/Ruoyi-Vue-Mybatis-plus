<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          领取阿里云通用云产品
          <br />
          <el-link
            href="https://www.aliyun.com/minisite/goods?userCode=f8dwblym"
            type="primary"
            target="_blank"
            >https://www.aliyun.com/minisite/goods?userCode=f8dwblym</el-link
          >
          <br />
          领取腾讯云通用云产品
          <br />
          <el-link
            href="https://cloud.tencent.com/act/cps/redirect?redirect=2446&cps_key=6e68eb5fe0bdd03984b484f3ed0d0fb3&from=console"
            type="primary"
            target="_blank"
            >https://cloud.tencent.com/act/cps/redirect?redirect=2446&cps_key=6e68eb5fe0bdd03984b484f3ed0d0fb3&from=console</el-link
          >
          <br />
          阿里云服务器折扣区
          <el-link href="https://www.aliyun.com/daily-act/ecs/activity_selection?userCode=f8dwblym" type="primary" target="_blank"
            >>☛☛点我进入☚☚</el-link
          >
          &nbsp;&nbsp;&nbsp; 腾讯企业上云优惠
          <el-link href="https://cloud.tencent.com/act/cps/redirect?redirect=1060&cps_key=6e68eb5fe0bdd03984b484f3ed0d0fb3&from=console" type="primary" target="_blank"
            >>☛☛点我进入☚☚</el-link
          ><br />
        </blockquote>

        <hr />
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>若依后台管理框架</h2>
        <p>
          一直使用若依框架<el-link
          href="https://gitee.com/y_project/RuoYi-Vue"
          type="primary"
          target="_blank"
        >[Ruoyi-Vue]</el-link
        >，但使用越多越发现部分功能有心无力，慢慢动了自行改造的念头。目前从若依后台管理框架1月1日的版本开始正式衍生，添加了Mybatis-plus组件来强化CURD的操作，逐渐又加入了多数据源管理器（原来的数据管理器不方便对接不同的数据库产品），
          慢慢的衍变成了当前这套。若依提供的功能我全提供，若依不能提供的功能我也提供。后续会不断增加各类快捷功能的支持。
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
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
            @click="goTarget('https://gitee.com/nottyjay/ruoyi-vue-mybatis-plus')"
            >访问码云</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-s-home"
            plain
            @click="goTarget('http://ruoyi.vip')"
            >访问主页</el-button
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
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> 官网：<el-link
                href="http://www.ruoyi.vip"
                target="_blank"
                >http://www.ruoyi.vip</el-link
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
            <el-collapse-item title="v3.8.6 - 2023-03-28">
              <ol>
                <li>修改多源数据库方案为dynamic-datasource方案</li>
                <li>修复生成的vue文件中remark字段无填充问题</li>
                <li>修复生成的vue文件中包含deleted字段的问题</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.8.5 - 2023-01-01">
              <ol>
                <li>同步版本至Ruoyi-Vue v.3.8.5</li>
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
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.2",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
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

