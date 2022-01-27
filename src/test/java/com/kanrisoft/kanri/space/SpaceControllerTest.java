package com.kanrisoft.kanri.space;

import com.kanrisoft.kanri.TestUtil;
import com.kanrisoft.kanri.board.domain.Permission;
import com.kanrisoft.kanri.config.MyControllerTest;
import com.kanrisoft.kanri.space.domain.Space;
import com.kanrisoft.kanri.space.domain.SpaceId;
import com.kanrisoft.kanri.space.domain.SpaceService;
import com.kanrisoft.kanri.space.model.AddUserToSpaceRequest;
import com.kanrisoft.kanri.space.model.SpaceDto;
import com.kanrisoft.kanri.space.model.SpaceRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MyControllerTest
@WebMvcTest(SpaceController.class)
@Import(SpaceController.class)
class SpaceControllerTest {
    @MockBean
    SpaceService spaceService;

    private String baseUrl = "/api/v1/spaces";

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    void shouldCreateSpace() throws Exception {
        var request = new SpaceRequest("test");
        var body = TestUtil.convertObjectToJsonBytes(request);
        when(spaceService.createSpace(any(SpaceRequest.class))).thenReturn(mock(Space.class));

        var result = mvc.perform(
                        post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                ).andExpect(status().isCreated())
                .andReturn();
        var responseJson = result.getResponse().getContentAsString();
        var respDto = TestUtil.convertJsonToType(responseJson, SpaceDto.class);

        assertNotNull(respDto);
    }

    @Test
    @WithMockUser
    void shouldCallCreateSpace() throws Exception {
        var request = new SpaceRequest("Test");
        var body = TestUtil.convertObjectToJsonBytes(request);
        var captor = ArgumentCaptor.forClass(SpaceRequest.class);
        when(spaceService.createSpace(any(SpaceRequest.class))).thenReturn(mock(Space.class));

        mvc.perform(
                post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        verify(spaceService).createSpace(captor.capture());
        assertEquals(captor.getValue(), request);
    }

    @Test
    @WithMockUser
    void shouldReturnNewlyCreatedSpace() throws Exception {
        var request = new SpaceRequest("Test");
        var body = TestUtil.convertObjectToJsonBytes(request);
        when(spaceService.createSpace(any(SpaceRequest.class))).thenReturn(mock(Space.class));

        var result = mvc.perform(
                post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andReturn();
        var responseJson = result.getResponse().getContentAsString();
        var respDto = TestUtil.convertJsonToType(responseJson, SpaceDto.class);

        assertNotNull(respDto);
        assertNotNull(respDto.id());
    }

    @Test
    void shouldThrowUnauthorizedError() throws Exception {
        var request = new SpaceRequest("Test");
        var body = TestUtil.convertObjectToJsonBytes(request);

        mvc.perform(
                post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldAddUserToTheSpace() throws Exception {
        var request = new AddUserToSpaceRequest(1L);
        var body = TestUtil.convertObjectToJsonBytes(request);
        var spaceId = 1L;

        mvc.perform(
                post(baseUrl + '/' + spaceId + "/add_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldCallAddUser() throws Exception {
        var userId = 2L;
        var request = new AddUserToSpaceRequest(userId);
        var body = TestUtil.convertObjectToJsonBytes(request);
        var reqArgCaptor = ArgumentCaptor.forClass(AddUserToSpaceRequest.class);
        var spaceArgCaptor = ArgumentCaptor.forClass(SpaceId.class);
        var spaceId = 1L;

        mvc.perform(
                post(baseUrl + '/' + spaceId + "/add_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().isOk());

        verify(spaceService).addUserToSpace(spaceArgCaptor.capture(), reqArgCaptor.capture());
        assertEquals(spaceId, spaceArgCaptor.getValue().id());
        assertEquals(userId, reqArgCaptor.getValue().userId());
    }
}