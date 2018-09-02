package com.afkl.cases.df.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by KRUNAL SHAH on 09/02/18.
 */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class RequestStatistics {

    private long totalResponseTime;
    private long totalRequestsProcessed;
    private long totalRequestsOKResponse;
    private long totalRequests4xxResponse;
    private long totalRequests5xxResponse;
    private long totalRequestsXxxResponse;
    private long averageResponseTime;
    private long minResponseTime;
    private long maxResponseTime;

    public void getStatistics(final int statusCode, final long responseTime) {
        totalResponseTime += totalResponseTime + responseTime;
        totalRequestsProcessed += 1;

        if (statusCode == 200) {
            totalRequestsOKResponse += 1;
        } else if (statusCode >= 400 && statusCode < 500) {
            totalRequests4xxResponse += 1;
        } else if (statusCode >= 500 && statusCode < 600) {
            totalRequests5xxResponse += 1;
        } else {
            totalRequestsXxxResponse += 1;
        }

        averageResponseTime = totalResponseTime / totalRequestsProcessed;
        maxResponseTime = responseTime > maxResponseTime ? responseTime : maxResponseTime;
        minResponseTime = (minResponseTime == 0 || responseTime < minResponseTime) ? responseTime : minResponseTime;
    }

    public long getNumberOfRequests() {
        return totalRequestsProcessed;
    }

    public long getFailedRequests() {
        return totalRequests4xxResponse + totalRequests5xxResponse + totalRequestsXxxResponse;
    }

    public long getSuccessRequests() {
        return totalRequestsOKResponse;
    }

    public long getAvResponseTime() {
        return averageResponseTime;
    }

    public long getMaxResTime() {
        return maxResponseTime;
    }

    public long getMinResponseTime() {
        return minResponseTime;
    }

}
