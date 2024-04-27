package com.group3.finalprojectbe.system.excption;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j

public class BizExceptionKit {
    public static BizException of(String message, Object... args) {
        return of(Status.Unknown, null, message, args);
    }

    public static BizException of(Throwable cause, String message, Object... args) {
        return of(Status.Unknown, cause, message, args);
    }

    public static BizException of(Status code, String message, Object... args) {
        return of(code, null, message, args);
    }

    public static BizException of(Status code, Throwable cause, String message, Object... varargs) {
        String formattedMessage = CharSequenceUtil.format(message, varargs);


        log.error("BizException has been created - Status: {}, Message: {}, Cause: '{}', Additional Info: {}\n",
                code, formattedMessage, (cause != null ? cause.toString() : ""), Arrays.toString(varargs));


        return new BizException(code, CharSequenceUtil.format(message, (Object[]) varargs), cause);
    }
}
