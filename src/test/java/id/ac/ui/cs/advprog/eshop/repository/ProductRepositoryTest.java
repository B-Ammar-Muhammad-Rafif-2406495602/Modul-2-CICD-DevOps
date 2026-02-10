package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product foundProduct = productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(foundProduct);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", foundProduct.getProductId());
        assertEquals("Sampo Cap Bambang", foundProduct.getProductName());
        assertEquals(100, foundProduct.getProductQuantity());
    }

    @Test
    void testFindByIdNotFound() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product foundProduct = productRepository.findByID("non-existent-id");
        assertNull(foundProduct);
    }

    @Test
    void testFindByIdInEmptyRepository() {
        Product foundProduct = productRepository.findByID("any-id");
        assertNull(foundProduct);
    }

    @Test
    void testUpdateProductSuccess() {
        // Create initial product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Update product
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Updated");
        updatedProduct.setProductQuantity(150);

        Product result = productRepository.update(updatedProduct);

        // Verify update was successful
        assertNotNull(result);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", result.getProductId());
        assertEquals("Sampo Cap Bambang Updated", result.getProductName());
        assertEquals(150, result.getProductQuantity());

        // Verify the product in repository is updated
        Product foundProduct = productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Bambang Updated", foundProduct.getProductName());
        assertEquals(150, foundProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductOnlyName() {
        // Create initial product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Update only name
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(100);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(100, result.getProductQuantity());
    }

    @Test
    void testUpdateProductOnlyQuantity() {
        // Create initial product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Update only quantity
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Cap Bambang", result.getProductName());
        assertEquals(200, result.getProductQuantity());
    }

    @Test
    void testUpdateProductWithNonExistentId() {
        // Create a product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Try to update product with non-existent ID
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(150);

        Product result = productRepository.update(updatedProduct);

        // Verify update failed
        assertNull(result);

        // Verify original product is unchanged
        Product originalProduct = productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Bambang", originalProduct.getProductName());
        assertEquals(100, originalProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductMultipleTimes() {
        // Create initial product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Original Name");
        product.setProductQuantity(100);
        productRepository.create(product);

        // First update
        Product update1 = new Product();
        update1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        update1.setProductName("First Update");
        update1.setProductQuantity(150);
        productRepository.update(update1);

        // Second update
        Product update2 = new Product();
        update2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        update2.setProductName("Second Update");
        update2.setProductQuantity(200);
        Product result = productRepository.update(update2);

        assertNotNull(result);
        assertEquals("Second Update", result.getProductName());
        assertEquals(200, result.getProductQuantity());
    }

    @Test
    void testUpdateOneProductAmongMany() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setProductId("id-3");
        product3.setProductName("Product 3");
        product3.setProductQuantity(300);
        productRepository.create(product3);

        // Update only product 2
        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-2");
        updatedProduct.setProductName("Product 2 Updated");
        updatedProduct.setProductQuantity(250);
        productRepository.update(updatedProduct);

        // Verify only product 2 was updated
        Product found1 = productRepository.findByID("id-1");
        assertEquals("Product 1", found1.getProductName());
        assertEquals(100, found1.getProductQuantity());

        Product found2 = productRepository.findByID("id-2");
        assertEquals("Product 2 Updated", found2.getProductName());
        assertEquals(250, found2.getProductQuantity());

        Product found3 = productRepository.findByID("id-3");
        assertEquals("Product 3", found3.getProductName());
        assertEquals(300, found3.getProductQuantity());
    }

    @Test
    void testDeleteProductSuccess() {
        // Create a product
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Verify product exists
        Product foundProduct = productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(foundProduct);

        // Delete the product
        productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        // Verify product no longer exists
        Product deletedProduct = productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNull(deletedProduct);

        // Verify repository is empty
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteOneProductAmongMany() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setProductId("id-3");
        product3.setProductName("Product 3");
        product3.setProductQuantity(300);
        productRepository.create(product3);

        // Delete product 2
        productRepository.delete("id-2");

        // Verify product 2 is deleted
        Product deletedProduct = productRepository.findByID("id-2");
        assertNull(deletedProduct);

        // Verify other products still exist
        Product found1 = productRepository.findByID("id-1");
        assertNotNull(found1);
        assertEquals("Product 1", found1.getProductName());

        Product found3 = productRepository.findByID("id-3");
        assertNotNull(found3);
        assertEquals("Product 3", found3.getProductName());

        // Verify only 2 products remain
        Iterator<Product> productIterator = productRepository.findAll();
        int count = 0;
        while (productIterator.hasNext()) {
            productIterator.next();
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testDeleteFirstProduct() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        // Delete first product
        productRepository.delete("id-1");

        // Verify first product is deleted
        assertNull(productRepository.findByID("id-1"));

        // Verify second product still exists
        Product found2 = productRepository.findByID("id-2");
        assertNotNull(found2);
        assertEquals("Product 2", found2.getProductName());
    }

    @Test
    void testDeleteLastProduct() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        // Delete last product
        productRepository.delete("id-2");

        // Verify last product is deleted
        assertNull(productRepository.findByID("id-2"));

        // Verify first product still exists
        Product found1 = productRepository.findByID("id-1");
        assertNotNull(found1);
        assertEquals("Product 1", found1.getProductName());
    }

    @Test
    void testDeleteAllProducts() {
        // Create multiple products
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);

        Product product3 = new Product();
        product3.setProductId("id-3");
        product3.setProductName("Product 3");
        product3.setProductQuantity(300);
        productRepository.create(product3);

        // Delete all products one by one
        productRepository.delete("id-1");
        productRepository.delete("id-2");
        productRepository.delete("id-3");

        // Verify all products are deleted
        assertNull(productRepository.findByID("id-1"));
        assertNull(productRepository.findByID("id-2"));
        assertNull(productRepository.findByID("id-3"));

        // Verify repository is empty
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testCreateUpdateDelete() {
        // Create
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Update
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Updated");
        updatedProduct.setProductQuantity(150);
        productRepository.update(updatedProduct);

        Product foundProduct = productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Bambang Updated", foundProduct.getProductName());
        assertEquals(150, foundProduct.getProductQuantity());

        // Delete
        productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNull(productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }


}