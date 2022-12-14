package com.kedacom.device.common.utils;

import com.kedacom.device.common.exception.ParamException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

/**
 * @author hexijian
 * @date 2021/4/30 10:19
 */
public class ValidUtils {

    private ValidUtils() {

    }

    public static void paramValid(BindingResult result) {

        if (result.hasFieldErrors()) {
            String msg = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining("|"));

            //抛出异常
            throw new ParamException(msg);
        }
    }

}
