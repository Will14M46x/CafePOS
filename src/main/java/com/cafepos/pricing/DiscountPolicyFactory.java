package com.cafepos.pricing;

import com.cafepos.checkout.DiscountPolicy;
import com.cafepos.common.Money;

public class DiscountPolicyFactory {
    public static DiscountPolicy getDiscountPolicy(String discountCode) {
        if (discountCode == null){
            return new NoDiscount();
        }
        if (discountCode.equalsIgnoreCase("LOYAL5")){
            return new LoyaltyPercentDiscount(5);
        }else if (discountCode.equalsIgnoreCase("COUPON1")){
            return new FixedCouponDiscount(Money.of(1.00));
        }else if (discountCode.equalsIgnoreCase("NONE")){
            return new NoDiscount();
        }else{
            return new NoDiscount();
        }
    }
}
