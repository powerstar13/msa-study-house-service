package msa.study.member.infrastructure.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

// DefaultErrorAttributes는 스프링이 자동으로 만들어내는 에러를 담고 있다.
@Component
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    
    private final ExceptionResponseFactory exceptionResponseFactory;
    
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable throwable = getError(request);
        return exceptionResponseFactory.responseBuilder(throwable);
    }
}
