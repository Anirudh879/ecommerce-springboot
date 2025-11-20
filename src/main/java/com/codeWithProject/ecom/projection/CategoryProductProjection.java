package com.codeWithProject.ecom.projection;

import java.math.BigDecimal;

public interface CategoryProductProjection {
	Long getCategoryId();
	String getCategoryName();
	Long getProductId();
	String getProductName();
	BigDecimal getProductPrice();
}
