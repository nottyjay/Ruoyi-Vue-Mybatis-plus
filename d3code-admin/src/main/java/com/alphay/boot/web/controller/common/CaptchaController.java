package com.alphay.boot.web.controller.common;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.alphay.boot.common.constant.CacheConstants;
import com.alphay.boot.common.constant.Constants;
import com.alphay.boot.common.core.domain.AjaxResult;
import com.alphay.boot.common.core.redis.RedisCache;
import com.alphay.boot.common.utils.uuid.IdUtils;
import com.alphay.boot.system.service.ISysConfigService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码操作处理
 *
 * @author d3code
 */
@RestController
public class CaptchaController
{

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    // 验证码类型
    @Value("${d3code.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode() throws IOException
    {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled)
        {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String code = null;

        ajax.put("uuid", uuid);
        // 生成验证码
        if ("math".equals(captchaType))
        {
            ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36, 2);
            code = captcha.text();
            ajax.put("img", captcha.toBase64());
        }
        else if ("char".equals(captchaType))
        {
            SpecCaptcha captcha = new SpecCaptcha(111, 36, 4);
            code = captcha.text();
            ajax.put("img", captcha.toBase64());
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        return ajax;
    }
}
