package com.outfit7.services.ranked;

import static com.outfit7.utils.TestUtils.users;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.List;

import com.outfit7.services.classic.ClassicMatchingService;
import com.outfit7.services.classic.OpponentsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outfit7.entity.User;

@ExtendWith(MockitoExtension.class)
class RankedOpponentsServiceTest {

    @Mock
    RankedMatchingService rankedMatchingService;

    @InjectMocks
    RankedOpponentsService rankedOpponentsService;

    @Test
    void shouldRetrieveOpponentsForUserId() {
        // Given
        String userId = "some-user-id";
        given(rankedMatchingService.retrieveOpponents(eq(userId)))
                .willReturn(users());

        // When
        List<User> opponents = rankedOpponentsService.matchOpponents(userId);
        for (int i = 0; i < opponents.size(); i++)
            System.out.println(opponents.get(i));

        // Then
        assertThat(opponents)
                .hasSize(10);
    }

}