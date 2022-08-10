package msa.study.house.infrastructure.exception;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
public class ExceptionResponseFactory {
    
    /**
     * HttpStatus 추출
     * @param throwable: 에러
     * @return HttpStatus: Http 결과
     */
    private HttpStatus getHttpStatus(Throwable throwable) {
        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations
            .from(throwable.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        
        if (throwable instanceof ResponseStatusException) {
            return ((ResponseStatusException) throwable).getStatus();
        }
        return responseStatusAnnotation.getValue("code", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Exception Response 구성
     * @param throwable: 에러
     * @return Response: 에러 결과
     */
    public Map<String, Object> responseBuilder(Throwable throwable) {
    
        HttpStatus errorStatus = this.getHttpStatus(throwable);
        
        int rt = errorStatus.value();
        String rtMsg = errorStatus.getReasonPhrase();
        
        if (throwable instanceof GlobalException) {
            // 사용자 정의 에러일 경우, GlobalException을 통해 따로 처리된다.
            GlobalException ex = (GlobalException) throwable;
            rt = ex.getStatus().value();
            rtMsg = ex.getReason();
        }
    
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.add("rt", rt);
        exceptionDTO.add("rtMsg", rtMsg);
        
        return exceptionDTO.getResponse();
    }
}
