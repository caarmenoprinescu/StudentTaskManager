import com.carmen.studenttaskmanager.DTOs.AuthenticationRequest;
import com.carmen.studenttaskmanager.DTOs.AuthenticationResponse;
import com.carmen.studenttaskmanager.DTOs.RegisterRequest;
import com.carmen.studenttaskmanager.models.Role;
import com.carmen.studenttaskmanager.models.User;
import com.carmen.studenttaskmanager.repositories.UserRepository;
import com.carmen.studenttaskmanager.services.AuthService;
import com.carmen.studenttaskmanager.services.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserAndReturnToken() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("carmen");
        request.setPassword("parola123");

        when(userRepository.findByUsername("carmen")).thenReturn(Optional.empty());
        when(encoder.encode("parola123")).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("token123");

        AuthenticationResponse response = authService.register(request);

        assertThat(response.getToken()).isEqualTo("token123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUsernameExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("carmen");
        request.setPassword("parola123");

        when(userRepository.findByUsername("carmen")).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Username already exists");
    }

    @Test
    void shouldLoginAndReturnToken() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("carmen");
        request.setPassword("parola123");

        User user = new User();
        user.setUsername("carmen");
        user.setRole(Role.ROLE_USER);

        when(userRepository.findByUsername("carmen")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("token123");

        AuthenticationResponse response = authService.login(request);

        assertThat(response.getToken()).isEqualTo("token123");
    }
}