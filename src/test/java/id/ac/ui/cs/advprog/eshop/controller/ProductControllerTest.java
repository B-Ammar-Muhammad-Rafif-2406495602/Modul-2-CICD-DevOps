package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        testProduct.setProductName("Sampo Cap Bambang");
        testProduct.setProductQuantity(100);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        assertEquals("createProduct", viewName);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPost() {
        when(productService.create(any(Product.class))).thenReturn(testProduct);

        String viewName = productController.createProductPost(testProduct, model);

        assertEquals("redirect:list", viewName);
        verify(productService, times(1)).create(testProduct);
    }

    @Test
    void testProductListPage() {
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct);
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);

        assertEquals("listProduct", viewName);
        verify(productService, times(1)).findAll();
        verify(model, times(1)).addAttribute("products", productList);
    }

    @Test
    void testProductListPageWithEmptyList() {
        List<Product> emptyList = new ArrayList<>();
        when(productService.findAll()).thenReturn(emptyList);

        String viewName = productController.productListPage(model);

        assertEquals("listProduct", viewName);
        verify(productService, times(1)).findAll();
        verify(model, times(1)).addAttribute("products", emptyList);
    }

    @Test
    void testProductListPageWithMultipleProducts() {
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

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);

        assertEquals("listProduct", viewName);
        verify(productService, times(1)).findAll();
        verify(model, times(1)).addAttribute("products", productList);
    }

    @Test
    void testEditProductPageSuccess() {
        when(productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(testProduct);

        String viewName = productController.editProductPage("eb558e9f-1c39-460e-8860-71af6af63bd6", model);

        assertEquals("editProduct", viewName);
        verify(productService, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        verify(model, times(1)).addAttribute("product", testProduct);
    }

    @Test
    void testEditProductPageWithNonExistentId() {
        when(productService.findById("non-existent-id")).thenReturn(null);

        String viewName = productController.editProductPage("non-existent-id", model);

        assertEquals("editProduct", viewName);
        verify(productService, times(1)).findById("non-existent-id");
        verify(model, times(1)).addAttribute("product", null);
    }

    @Test
    void testEditProductPageWithEmptyId() {
        when(productService.findById("")).thenReturn(null);

        String viewName = productController.editProductPage("", model);

        assertEquals("editProduct", viewName);
        verify(productService, times(1)).findById("");
        verify(model, times(1)).addAttribute("product", null);
    }

    @Test
    void testEditProductPageWithNullId() {
        when(productService.findById(null)).thenReturn(null);

        String viewName = productController.editProductPage(null, model);

        assertEquals("editProduct", viewName);
        verify(productService, times(1)).findById(null);
        verify(model, times(1)).addAttribute("product", null);
    }

    @Test
    void testEditProductPostSuccess() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Updated");
        updatedProduct.setProductQuantity(150);

        doNothing().when(productService).update(any(Product.class));

        String viewName = productController.editProductPost(updatedProduct);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(updatedProduct);
    }

    @Test
    void testEditProductPostChangeName() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(100);

        doNothing().when(productService).update(any(Product.class));

        String viewName = productController.editProductPost(updatedProduct);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(updatedProduct);
    }

    @Test
    void testEditProductPostChangeQuantity() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(200);

        doNothing().when(productService).update(any(Product.class));

        String viewName = productController.editProductPost(updatedProduct);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(updatedProduct);
    }

    @Test
    void testEditProductPostWithNonExistentId() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(150);

        doNothing().when(productService).update(any(Product.class));

        String viewName = productController.editProductPost(updatedProduct);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(updatedProduct);
    }

    @Test
    void testEditProductPostWithZeroQuantity() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(0);

        doNothing().when(productService).update(any(Product.class));

        String viewName = productController.editProductPost(updatedProduct);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(updatedProduct);
    }

    @Test
    void testEditProductPostWithNegativeQuantity() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang");
        updatedProduct.setProductQuantity(-10);

        doNothing().when(productService).update(any(Product.class));

        String viewName = productController.editProductPost(updatedProduct);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(updatedProduct);
    }

    @Test
    void testEditProductPostWithEmptyName() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("");
        updatedProduct.setProductQuantity(100);

        doNothing().when(productService).update(any(Product.class));

        String viewName = productController.editProductPost(updatedProduct);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(updatedProduct);
    }

    @Test
    void testEditProductPostWithNullName() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName(null);
        updatedProduct.setProductQuantity(100);

        doNothing().when(productService).update(any(Product.class));

        String viewName = productController.editProductPost(updatedProduct);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).update(updatedProduct);
    }

    @Test
    void testDeleteProductSuccess() {
        doNothing().when(productService).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        String viewName = productController.deleteProduct("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testDeleteProductWithNonExistentId() {
        doNothing().when(productService).delete("non-existent-id");

        String viewName = productController.deleteProduct("non-existent-id");

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).delete("non-existent-id");
    }

    @Test
    void testDeleteProductWithEmptyId() {
        doNothing().when(productService).delete("");

        String viewName = productController.deleteProduct("");

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).delete("");
    }

    @Test
    void testDeleteProductWithNullId() {
        doNothing().when(productService).delete(null);

        String viewName = productController.deleteProduct(null);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, times(1)).delete(null);
    }

    @Test
    void testDeleteMultipleProducts() {
        doNothing().when(productService).delete(anyString());

        String viewName1 = productController.deleteProduct("id-1");
        String viewName2 = productController.deleteProduct("id-2");
        String viewName3 = productController.deleteProduct("id-3");

        assertEquals("redirect:/product/list", viewName1);
        assertEquals("redirect:/product/list", viewName2);
        assertEquals("redirect:/product/list", viewName3);
        verify(productService, times(1)).delete("id-1");
        verify(productService, times(1)).delete("id-2");
        verify(productService, times(1)).delete("id-3");
        verify(productService, times(3)).delete(anyString());
    }

    @Test
    void testDeleteSameProductTwice() {
        doNothing().when(productService).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        String viewName1 = productController.deleteProduct("eb558e9f-1c39-460e-8860-71af6af63bd6");
        String viewName2 = productController.deleteProduct("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertEquals("redirect:/product/list", viewName1);
        assertEquals("redirect:/product/list", viewName2);
        verify(productService, times(2)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testDeleteProductRedirectsToList() {
        doNothing().when(productService).delete(anyString());

        String viewName = productController.deleteProduct("any-id");

        assertTrue(viewName.contains("redirect"));
        assertTrue(viewName.contains("list"));
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testCreateEditDeleteWorkflow() {
        // Create
        Product newProduct = new Product();
        newProduct.setProductName("New Product");
        newProduct.setProductQuantity(100);
        when(productService.create(any(Product.class))).thenReturn(newProduct);
        String createView = productController.createProductPost(newProduct, model);
        assertEquals("redirect:list", createView);
        verify(productService, times(1)).create(newProduct);

        // Edit
        newProduct.setProductId("new-id");
        when(productService.findById("new-id")).thenReturn(newProduct);
        productController.editProductPage("new-id", model);
        verify(productService, times(1)).findById("new-id");

        newProduct.setProductName("Updated Product");
        doNothing().when(productService).update(newProduct);
        String editView = productController.editProductPost(newProduct);
        assertEquals("redirect:/product/list", editView);
        verify(productService, times(1)).update(newProduct);

        // Delete
        doNothing().when(productService).delete("new-id");
        String deleteView = productController.deleteProduct("new-id");
        assertEquals("redirect:/product/list", deleteView);
        verify(productService, times(1)).delete("new-id");
    }

    @Test
    void testMultipleEditsAndDeletes() {
        // Edit product 1
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        when(productService.findById("id-1")).thenReturn(product1);
        productController.editProductPage("id-1", model);
        verify(productService, times(1)).findById("id-1");

        product1.setProductName("Product 1 Updated");
        doNothing().when(productService).update(product1);
        productController.editProductPost(product1);
        verify(productService, times(1)).update(product1);

        // Delete product 2
        doNothing().when(productService).delete("id-2");
        productController.deleteProduct("id-2");
        verify(productService, times(1)).delete("id-2");

        // Edit product 3
        Product product3 = new Product();
        product3.setProductId("id-3");
        product3.setProductName("Product 3");
        product3.setProductQuantity(300);
        when(productService.findById("id-3")).thenReturn(product3);
        productController.editProductPage("id-3", model);
        verify(productService, times(1)).findById("id-3");

        product3.setProductName("Product 3 Updated");
        doNothing().when(productService).update(product3);
        productController.editProductPost(product3);
        verify(productService, times(1)).update(product3);
    }

    @Test
    void testEditPageCalledMultipleTimes() {
        when(productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(testProduct);

        productController.editProductPage("eb558e9f-1c39-460e-8860-71af6af63bd6", model);
        productController.editProductPage("eb558e9f-1c39-460e-8860-71af6af63bd6", model);
        productController.editProductPage("eb558e9f-1c39-460e-8860-71af6af63bd6", model);

        verify(productService, times(3)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        verify(model, times(3)).addAttribute("product", testProduct);
    }

    @Test
    void testDeleteThenList() {
        // Delete product
        doNothing().when(productService).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        String deleteView = productController.deleteProduct("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("redirect:/product/list", deleteView);
        verify(productService, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testEditNonExistentProductThenList() {
        // Try to edit non-existent product
        when(productService.findById("non-existent-id")).thenReturn(null);
        String editPageView = productController.editProductPage("non-existent-id", model);
        assertEquals("editProduct", editPageView);
        verify(model, times(1)).addAttribute("product", null);

        // View list
        List<Product> products = new ArrayList<>();
        when(productService.findAll()).thenReturn(products);
        String listView = productController.productListPage(model);
        assertEquals("listProduct", listView);
        verify(productService, times(1)).findAll();
    }
}