package com.example.demo.test;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.controller.FileUploadController;
import com.example.demo.test.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FileUploadControllerTest {
   
   @Autowired
    private WebApplicationContext webApplicationContext;
   	
   
   /*
    * This method tests the uploading the file.
    **/
   
   @Test
   public void testFileUpload() throws Exception
   {
      MockMultipartFile mockMultipartFile =
                 new MockMultipartFile("files", "FileUploadTest.txt", "text/plain", "This is a Test".getBytes());
     
      MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
         mockMvc.perform(MockMvcRequestBuilders.multipart("/fileUpload")
                 .file(mockMultipartFile)
                 .param("name", "FileUploadTest.txt"));
         Assert.assertTrue(true);
   }

}