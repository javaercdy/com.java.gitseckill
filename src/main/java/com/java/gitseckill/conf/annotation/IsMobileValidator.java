package com.java.gitseckill.conf.annotation;

import com.java.gitseckill.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * @author javaercdy
 * @create 2021-12-01$-{TIME}
 */

public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private Boolean require =false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        require = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (require){
            return ValidatorUtil.isMobile(s);
        }else {
            if (StringUtils.isBlank(s)){
                return true;
            }else {
               return ValidatorUtil.isMobile(s);
            }
        }
    }
}
