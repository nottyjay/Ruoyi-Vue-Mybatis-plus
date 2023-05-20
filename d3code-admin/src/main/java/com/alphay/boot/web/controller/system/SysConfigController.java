package com.alphay.boot.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.RandomUtil;
import com.alphay.boot.common.annotation.Log;
import com.alphay.boot.common.constant.UserConstants;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import com.alphay.boot.common.enums.BusinessType;
import com.alphay.boot.common.utils.poi.ExcelUtil;
import com.alphay.boot.system.domain.SysConfig;
import com.alphay.boot.system.service.ISysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数配置 信息操作处理
 *
 * @author d3code
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
  @Autowired
  private ISysConfigService configService;

  /**
   * 获取参数配置列表
   */
  @PreAuthorize("@ss.hasPermi('system:config:list')")
  @GetMapping("/list")
  public TableDataInfo list(SysConfig config) {
    startPage();
    List<SysConfig> list = configService.selectConfigList(config);
    return getDataTable(list);
  }

  @Log(title = "参数管理", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('system:config:export')")
  @PostMapping("/export")
  public void export(HttpServletResponse response, SysConfig config) {
    List<SysConfig> list = configService.selectConfigList(config);
    ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
    util.exportExcel(response, list, "参数数据");
  }

  /**
   * 根据参数编号获取详细信息
   */
  @PreAuthorize("@ss.hasPermi('system:config:query')")
  @GetMapping(value = "/{configId}")
  public AjaxResult getInfo(@PathVariable Long configId) {
    return success(configService.selectConfigById(configId));
  }

  /**
   * 根据参数键名查询参数值
   */
  @GetMapping(value = "/configKey/{configKey}")
  public AjaxResult getConfigKey(@PathVariable String configKey) {
    return success(configService.selectConfigByKey(configKey));
  }

  /**
   * 新增参数配置
   */
  @PreAuthorize("@ss.hasPermi('system:config:add')")
  @Log(title = "参数管理", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@Validated @RequestBody SysConfig config) {
    if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
      return error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
    }
    config.setCreateBy(getUsername());
    return toAjax(configService.insertConfig(config));
  }

  /**
   * 修改参数配置
   */
  @PreAuthorize("@ss.hasPermi('system:config:edit')")
  @Log(title = "参数管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@Validated @RequestBody SysConfig config) {
    if (config.getConfigId() != null) {
      if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
        return error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
      }
      config.setUpdateBy(getUsername());
    } else if (StringUtils.isNotBlank(config.getConfigKey())) {
      SysConfig persistent = configService.getConfigByKey(config.getConfigKey());
      config.setConfigId(persistent.getConfigId());
    } else {
      return AjaxResult.error();
    }
    return toAjax(configService.updateConfig(config));
  }

  @PreAuthorize("@ss.hasPermi('system:config:edit')")
  @Log(title = "参数管理", businessType = BusinessType.UPDATE)
  @PutMapping("/editByKey")
  public AjaxResult editByKey(@RequestBody SysConfig config) {
    if (StringUtils.isNotBlank(config.getConfigKey())) {
      SysConfig persistent = configService.getConfigByKey(config.getConfigKey());
      config.setConfigId(persistent.getConfigId());
    } else {
      config.setConfigName("系统自助生成" + RandomUtil.randomNumbers(6));
      config.setConfigType("Y");
      return toAjax(configService.insertConfig(config));
    }
    return toAjax(configService.updateConfig(config));
  }

  /**
   * 删除参数配置
   */
  @PreAuthorize("@ss.hasPermi('system:config:remove')")
  @Log(title = "参数管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{configIds}")
  public AjaxResult remove(@PathVariable Long[] configIds) {
    configService.deleteConfigByIds(configIds);
    return success();
  }

  /**
   * 刷新参数缓存
   */
  @PreAuthorize("@ss.hasPermi('system:config:remove')")
  @Log(title = "参数管理", businessType = BusinessType.CLEAN)
  @DeleteMapping("/refreshCache")
  public AjaxResult refreshCache() {
    configService.resetConfigCache();
    return success();
  }
}
