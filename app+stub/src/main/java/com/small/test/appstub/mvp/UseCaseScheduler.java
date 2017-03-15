package com.small.test.appstub.mvp;

public interface UseCaseScheduler
{
    public void execute(Runnable runnable);
    
    public <V extends UseCase.ResponseValue> void notifyResponse(final V response,
                                                                 final UseCase.UseCaseCallback<V> useCaseCallback);
    
    public <V extends UseCase.ResponseValue> void onError(final UseCase.UseCaseCallback<V> useCaseCallback,
                                                          final Exception exception);
}
