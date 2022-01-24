package com.kanrisoft.kanri.space.domain;

import com.kanrisoft.kanri.space.model.SpaceRequest;
import com.kanrisoft.kanri.user.UserModule;
import com.kanrisoft.kanri.user.domain.UserId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceServiceImplTest {
    @InjectMocks
    SpaceServiceImpl underTest;

    @Mock
    SpaceRepository repository;

    @Mock
    UserModule userModule;

    @Nested
    class CreateSpace {
        @Test
        void shouldCreateSpace() {
            when(userModule.getCurrentUserId()).thenReturn(Optional.of(UserId.of(1L)));
            when(repository.save(any())).thenReturn(mock(SpaceEntity.class));
            var request = new SpaceRequest("Test");

            var space = underTest.createSpace(request);

            assertNotNull(space);
        }
    }
}