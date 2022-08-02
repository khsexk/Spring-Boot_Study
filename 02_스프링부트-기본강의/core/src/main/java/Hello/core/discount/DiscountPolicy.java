package Hello.core.discount;

import Hello.core.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}
