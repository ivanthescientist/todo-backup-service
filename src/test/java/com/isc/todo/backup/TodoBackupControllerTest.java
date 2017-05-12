package com.isc.todo.backup;

import com.isc.todo.backup.configuration.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TestConfiguration.class, TodoBackupConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Yes, I'm THAT lazy.
public class TodoBackupControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void testGettingAllBackups() throws Exception {
        mockMvc.perform(get("/backups"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreatingBackup() throws Exception {
        mockMvc.perform(get("/backups/1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/backups"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/backups/1"))
                .andExpect(status().isOk());
    }
}
