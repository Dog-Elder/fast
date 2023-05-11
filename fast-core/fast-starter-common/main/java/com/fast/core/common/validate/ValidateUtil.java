package com.fast.core.common.validate;

import com.fast.core.common.domain.domain.R;
import com.fast.core.common.util.Com;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

public class ValidateUtil {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToBeanResult<T> extends R {
        private T model;
    }

    /**
     * desc 验证BigDecimal
     *
     * @param scale            小数位数
     * @param value            必须得是BigDecimal
     * @param mustLessEqualOne 是否必须小于等于1
     **/
    public static boolean checkDecimal(boolean canBeZero, boolean canBeNegative, BigDecimal value, int scale, boolean mustLessEqualOne) {
        if (value == null) {
            return true;
        }


        if (value.signum() == 0 && !canBeZero) {
            return false;
        }

        if (value.signum() < 0 && !canBeNegative) {
            return false;
        }

        if (mustLessEqualOne && value.compareTo(BigDecimal.ONE) > 0) {
            return false;
        }

        if (value.scale() > scale) {
            return false;
        } else {
            //数据长度限制
            if (value.precision() > Com.MoneyPrecision + Com.DecimalPrecision) {
                return false;
            }
            return true;
        }

    }
}
