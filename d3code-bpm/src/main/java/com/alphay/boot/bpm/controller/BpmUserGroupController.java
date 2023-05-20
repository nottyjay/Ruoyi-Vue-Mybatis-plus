package com.alphay.boot.bpm.controller;

import com.alphay.boot.bpm.domain.BpmUserGroup;
import com.alphay.boot.bpm.service.IBpmUserGroupService;
import com.alphay.boot.common.core.controller.BaseController;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * BPM 流程 用户分组
 *
 * @author Nottyjay
 */
@RestController
@RequestMapping("/bpm/user-group")
public class BpmUserGroupController extends BaseController {

  @Resource private IBpmUserGroupService userGroupService;

  @GetMapping("/list")
  public TableDataInfo list(BpmUserGroup userGroup) {
    startPage();
    List<BpmUserGroup> result = userGroupService.selectUserGroupList(userGroup);
    return getDataTable(result);
  }

  @GetMapping("/{id}")
  public AjaxResult getInfo(@PathVariable Long id) {
    return success(userGroupService.getById(id));
  }

  @PostMapping("/create")
  public AjaxResult add(@Valid @RequestBody BpmUserGroup userGroup) {
    return toAjax(userGroupService.save(userGroup));
  }

  @PutMapping("/update")
  public AjaxResult update(@Valid @RequestBody BpmUserGroup userGroup) {
    return toAjax(userGroupService.updateById(userGroup));
  }

  @DeleteMapping("/delete/{id}")
  public AjaxResult delete(@PathVariable Long id) {
    return toAjax(userGroupService.removeById(id));
  }
}
