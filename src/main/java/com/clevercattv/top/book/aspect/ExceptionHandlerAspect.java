package com.clevercattv.top.book.aspect;

import com.clevercattv.top.book.dto.ApiResponse;
import com.clevercattv.top.book.dto.client.BookResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class ExceptionHandlerAspect {

    /**
     * Handles client's exceptions and map them to ApiResponse.
     */
    @Around("execution(* com.clevercattv.top.book.client.*.last(..)) ||" +
            "execution(* com.clevercattv.top.book.client.*.search(..)) ||" +
            "execution(* com.clevercattv.top.book.client.*.detailed(..))")
    public Object handleException(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (ResourceAccessException exception) {
            if (exception.getCause() instanceof ConnectException) {
                String errorClient = pjp.getTarget()
                        .getClass()
                        .getSimpleName()
                        .replace("Client", " ");
                List<String> errorResponse = Collections.singletonList(errorClient + exception.getCause().getMessage());

                return Optional.of(new ApiResponse<BookResponse>(errorResponse));
            }
            return Optional.of(new ApiResponse<BookResponse>(Collections.singletonList(exception.getMessage())));
        } catch (Throwable throwable) {
            return Optional.of(new ApiResponse<BookResponse>(Collections.singletonList(throwable.getMessage())));
        }
    }

}
