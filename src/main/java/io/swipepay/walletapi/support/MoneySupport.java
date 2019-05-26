package io.swipepay.walletapi.support;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class MoneySupport {
	
	public String formatWithSeparators(BigDecimal bigDecimal) {
		return new DecimalFormat("#,###.00").format(bigDecimal);
	}
}