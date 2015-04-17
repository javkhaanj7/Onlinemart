/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.teamone.onlinemart.beans.CategoryBean;
import com.teamone.onlinemart.dao.CategoryDAO;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author javkhaa_j7
 */
public class CategoryTest {
    
    public CategoryTest() {
    }
    
    @Test
    public void test_getByCategory(){
        Assert.assertEquals("Category id is 3 which should return name \"Clothing\".", "Clothing", CategoryDAO.getByCategory(3).getName());
    }
    
    @Test
    public void isParentHasChild(){
        Assert.assertTrue("Category id is 3 which should be parent category.", new CategoryBean().isParentHasChild(3));
    }
}
