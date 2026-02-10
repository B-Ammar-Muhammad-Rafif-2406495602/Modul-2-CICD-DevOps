package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        testProduct.setProductName("Sampo Cap Bambang");
        testProduct.setProductQuantity(100);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(any(Product.class))).thenReturn(testProduct);

        Product result = productService.create(testProduct);

        assertNotNull(result);
        assertEquals(testProduct.getProductId(), result.getProductId());
        assertEquals(testProduct.getProductName(), result.getProductName());
        assertEquals(testProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).create(testProduct);
    }

    @Test
    void testFindAllProducts() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getProductName());
        assertEquals("Product 2", result.get(1).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAllProductsWhenEmpty() {
        List<Product> emptyList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(emptyList.iterator());

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        when(productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(testProduct);

        Product result = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertNotNull(result);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", result.getProductId());
        assertEquals("Sampo Cap Bambang", result.getProductName());
        assertEquals(100, result.getProductQuantity());
        verify(productRepository, times(1)).findByID("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findByID("non-existent-id")).thenReturn(null);

        Product result = productService.findById("non-existent-id");

        assertNull(result);
        verify(productRepository, times(1)).findByID("non-existent-id");
    }

    @Test
    void testFindByIdWithNullId() {
        when(productRepository.findByID(null)).thenReturn(null);

        Product result = productService.findById(null);

        assertNull(result);
        verify(productRepository, times(1)).findByID(null);
    }

    @Test
    void testFindByIdWithEmptyId() {
        when(productRepository.findByID("")).thenReturn(null);

        Product result = productService.findById("");

        assertNull(result);
        verify(productRepository, times(1)).findByID("");
    }

    @Test
    void testUpdateProductSuccess() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Updated");
        updatedProduct.setProductQuantity(150);

        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        productService.update(updatedProduct);

        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductChangename() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(100);

        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        productService.update(updatedProduct);

        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductChangeQuantity() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(100);

        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        productService.update(updatedProduct);

        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductMultipleTimes() {
        Product firstUpdate = new Product();
        firstUpdate.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        firstUpdate.setProductName("First Update");
        firstUpdate.setProductQuantity(150);

        Product secondUpdate = new Product();
        secondUpdate.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        secondUpdate.setProductName("Second Update");
        secondUpdate.setProductQuantity(200);

        when(productRepository.update(firstUpdate)).thenReturn(firstUpdate);
        when(productRepository.update(secondUpdate)).thenReturn(secondUpdate);

        productService.update(firstUpdate);
        productService.update(secondUpdate);

        verify(productRepository, times(1)).update(firstUpdate);
        verify(productRepository, times(1)).update(secondUpdate);
        verify(productRepository, times(2)).update(any(Product.class));
    }

    @Test
    void testUpdateProductWithZeroQuantity() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(0);

        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        productService.update(updatedProduct);

        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductWithNegativeQuantity() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(-10);

        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        productService.update(updatedProduct);

        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductWithEmptyName() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("");
        updatedProduct.setProductQuantity(100);

        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        productService.update(updatedProduct);

        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testUpdateProductWithNullName() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName(null);
        updatedProduct.setProductQuantity(100);

        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);

        productService.update(updatedProduct);

        verify(productRepository, times(1)).update(updatedProduct);
    }

    @Test
    void testDeleteProductSuccess() {
        doNothing().when(productRepository).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        productService.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        verify(productRepository, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testDeleteProductWithNonExistentId() {
        doNothing().when(productRepository).delete("non-existent-id");

        productService.delete("non-existent-id");

        verify(productRepository, times(1)).delete("non-existent-id");
    }

    @Test
    void testDeleteProductWithNullId() {
        doNothing().when(productRepository).delete(null);

        productService.delete(null);

        verify(productRepository, times(1)).delete(null);
    }

    @Test
    void testDeleteProductWithEmptyId() {
        doNothing().when(productRepository).delete("");

        productService.delete("");

        verify(productRepository, times(1)).delete("");
    }

    @Test
    void testDeleteMultipleProducts() {
        doNothing().when(productRepository).delete(anyString());

        productService.delete("id-1");
        productService.delete("id-2");
        productService.delete("id-3");

        verify(productRepository, times(1)).delete("id-1");
        verify(productRepository, times(1)).delete("id-2");
        verify(productRepository, times(1)).delete("id-3");
        verify(productRepository, times(3)).delete(anyString());
    }

    @Test
    void testDeleteSameProductTwice() {
        doNothing().when(productRepository).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        productService.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        productService.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        verify(productRepository, times(2)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testCompleteWorkflow() {
        // Create
        when(productRepository.create(any(Product.class))).thenReturn(testProduct);
        Product created = productService.create(testProduct);
        assertNotNull(created);
        verify(productRepository, times(1)).create(testProduct);

        // Find by ID
        when(productRepository.findByID("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(testProduct);
        Product found = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(found);
        verify(productRepository, times(1)).findByID("eb558e9f-1c39-460e-8860-71af6af63bd6");

        // Update
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(150);
        when(productRepository.update(any(Product.class))).thenReturn(updatedProduct);
        productService.update(updatedProduct);
        verify(productRepository, times(1)).update(updatedProduct);

        // Delete
        doNothing().when(productRepository).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        productService.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        verify(productRepository, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

}