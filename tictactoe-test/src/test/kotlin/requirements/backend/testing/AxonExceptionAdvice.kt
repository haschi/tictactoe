package requirements.backend.testing

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AxonExceptionAdvice : ResponseEntityExceptionHandler() {


}