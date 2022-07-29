package station3.assignment.member.infrastructure.restdocs;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public class RestdocsDocumentUtil {
    
    public static OperationRequestPreprocessor requestPrettyPrint() {
        return preprocessRequest(prettyPrint());  // prettyPrint()는 JSON 포맷 정렬
    }
    
    public static OperationResponsePreprocessor responsePrettyPrint() {
        return preprocessResponse(prettyPrint());
    }
}
