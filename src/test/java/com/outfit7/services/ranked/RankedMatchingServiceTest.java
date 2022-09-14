package com.outfit7.services.ranked;

import static com.outfit7.utils.TestUtils.user;
import static com.outfit7.utils.TestUtils.users;
import static com.outfit7.utils.TestUtils.usersRanked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.List;

import com.outfit7.entity.exception.EntityNotFoundException;
import com.outfit7.services.UserService;
import com.outfit7.services.ranked.RankedMatchingService;
import com.outfit7.services.ranked.exception.RankedMatchingNotCompleteException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outfit7.entity.User;

@ExtendWith(MockitoExtension.class)
class RankedMatchingServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    RankedMatchingService rankedMatchingService;

    @Test
    void shouldThrowExceptionIfRankedMatchingNotComplete() {
        //Given
        String userId = "some-user-id";

        given(userService.get(eq(userId)))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(users());

        // When
        Throwable thrown = catchThrowable(() -> rankedMatchingService.retrieveOpponents(userId));

        // Then
        assertThat(thrown)
                .isInstanceOf(RankedMatchingNotCompleteException.class);
    }

    @Test
    void shouldRetrieveOpponentsForUserId() {
        // Given
        String userId = "some-user-id";

        given(userService.get(eq(userId)))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(usersRanked());

        // When
        List<User> opponents = rankedMatchingService.retrieveOpponents(userId);

        // Then
        assertThat(opponents)
                .hasSize(5)
                .extracting(User::getId)
                //.contains("2", "4", "7", "8", "9");
                .isSubsetOf("2", "4", "7", "8", "9", "10");
    }

    @Test
    void checkForDuplicateOpponents() {
        // Given
        String userId = "some-user-id";

        given(userService.get(eq(userId)))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(usersRanked());

        // When
        List<User> opponents = rankedMatchingService.retrieveOpponents(userId);

        // Then
        assertThat(opponents)
                .extracting(User::getPlayerName)
                .doesNotHaveDuplicates();
    }

}