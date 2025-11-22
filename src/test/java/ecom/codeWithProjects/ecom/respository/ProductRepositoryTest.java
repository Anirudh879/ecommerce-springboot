package ecom.codeWithProjects.ecom.respository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;

import com.codeWithProject.ecom.entity.Category;
import com.codeWithProject.ecom.entity.Product;
import com.codeWithProject.ecom.repository.CategoryRepository;
import com.codeWithProject.ecom.repository.ProductRepository;

@DataLdapTest
public class ProductRepositoryTest {
	
	@Autowired
	ProductRepository productRepository;
	
	 @Autowired
	 private CategoryRepository categoryRepository;
	
	// Helper to satisfy FK constraint
    private Category createCategory() {
        Category cat = new Category();
        cat.setName("Electronics");
        return categoryRepository.save(cat);
    }
    
    @Test
    void testFindAllByName() {
    	Category category = createCategory();

        Product p1 = new Product();
        p1.setName("Keyboard");
        p1.setPrice(new BigDecimal(1200));
        p1.setCategory(category);

        Product p2 = new Product();
        p2.setName("Keyboard");
        p2.setPrice(new BigDecimal(1500));
        p2.setCategory(category);

        productRepository.save(p1);
        productRepository.save(p2);

        List<Product> results = productRepository.findAllByName("Keyboard");

        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(Product::getName)
                .containsOnly("Keyboard");
    	
    }
}
