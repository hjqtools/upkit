package upkit.bp.springmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 处理未知异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	String handlerException(Exception e) {
		return "Exception Deal! " + e.getMessage();
	}

	/**
	 * 处理所有业务异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	AppResponse handleBusinessException(BusinessException e) {
		LOGGER.error(e.getMessage(), e);
		AppResponse response = new AppResponse();
		response.setFail(e.getMessage());
		return response;
	}
	
	/**
     * 处理所有接口数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    AppResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        LOGGER.error(e.getMessage(), e);

        AppResponse response = new AppResponse();
        response.setFail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return response;
    }

	

}
