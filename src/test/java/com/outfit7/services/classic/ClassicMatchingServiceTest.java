package com.outfit7.services.classic;

import static com.outfit7.utils.TestUtils.user;
import static com.outfit7.utils.TestUtils.users;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.List;

import com.outfit7.services.UserService;
import com.outfit7.services.classic.ClassicMatchingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outfit7.entity.User;

@ExtendWith(MockitoExtension.class)
class ClassicMatchingServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    ClassicMatchingService classicMatchingService;

    @Test
    void shouldRetrieveOpponentsForUserId() {
        // Given
        String userId = "some-user-id";
        given(userService.get(eq(userId)))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(users());

        // When
        List<User> opponents = classicMatchingService.retrieveOpponents(userId);

        // Then
        assertThat(opponents)
                .hasSize(3)
                .extracting(User::getId)
                .containsExactly("2", "3", "5");
                /*
                .hasSize(4)
                .extracting(User::getId)
                .containsExactly("2", "3", "5", "6");
                */
    }

    @Test
    void checkForDuplicateOpponents() {
        // Given
        String userId = "d7fc5c61-ac15-48ca-9b14-f3d8f55b1946";

        given(userService.get(eq(userId)))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(users());


        // When
        List<User> opponents = classicMatchingService.retrieveOpponents(userId);

        // Then
        assertThat(opponents)
                .extracting(User::getPlayerName)
                .doesNotHaveDuplicates();

    }

}