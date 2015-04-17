/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamone.onlinemart.dao;

import com.teamone.onlinemart.models.Product;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eGanbaatar
 */
public class ProductDAOTest {
    
    public ProductDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of update method, of class ProductDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Product product = ProductDAO.getById(6);
        product.setDescription("test desc");
        boolean expResult = true;
        boolean result = ProductDAO.update(product);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAll method, of class ProductDAO.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        int expResult = 63;
        int result = ProductDAO.getAll().length;
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllVendorProducts method, of class ProductDAO.
     */
    @Test
    public void testGetAllVendorProducts() {
        System.out.println("getAllVendorProducts");
        int vendor_id = 1;
        int expResult = 3;
        int result = ProductDAO.getAllVendorProducts(vendor_id).size();
        assertEquals(expResult, result);
    }
    
}
