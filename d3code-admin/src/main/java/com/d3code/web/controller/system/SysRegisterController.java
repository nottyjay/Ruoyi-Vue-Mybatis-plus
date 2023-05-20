package com.d3code.web.controller.system;

import com.d3code.web.service.SysRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.d3code.common.core.controller.BaseController;
import com.d3code.common.core.domain.AjaxResult;
import com.d3code.common.core.domain.model.RegisterBody;
import com.d3code.common.utils.StringUtils;
import com.d3code.system.service.ISysConfigService;

/**
 * 注册验证
 *
 * @author d3code
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
