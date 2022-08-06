package station3.assignment.member.presentation.shared;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import station3.assignment.member.infrastructure.config.SecurityConfig;
import station3.assignment.member.infrastructure.exception.GlobalExceptionHandler;

@Import(SecurityConfig.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
@AutoConfigureWebTestClient
public class WebFluxSharedHandlerTest {

    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;
}
