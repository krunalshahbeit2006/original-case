package com.afkl.cases.df.inceptors;

import com.afkl.cases.df.model.RequestStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by KRUNAL SHAH on 09/02/18.
 */
@Component
public class StatisticsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RequestStatistics statistics;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*log.debug("Pre Handle Started: ");*/

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*log.debug("Post Handle Started: ");*/

        /*long handleTime = System.currentTimeMillis();*/
        /*log.debug("handleTime: ", handleTime);*/
        /*throw new UnsupportedOperationException();*/
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*log.debug("After Completion Started: ");*/

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        request.setAttribute("endTime", endTime);

        statistics.getStatistics(response.getStatus(), (endTime - startTime));
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*log.debug("After Concurrent Handling Started: ");*/

        /*throw new UnsupportedOperationException();*/
    }

}