package com.small.test.appstub.mvp;

public class UseCaseHandler
{
    private static UseCaseHandler INSTANCE;
    
    private final UseCaseScheduler mUseCaseScheduler;
    
    private UseCaseHandler(UseCaseScheduler useCaseScheduler)
    {
        mUseCaseScheduler = useCaseScheduler;
    }
    
    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(final UseCase<T, R> useCase,
        T request, UseCase.UseCaseCallback<R> callback)
    {
        useCase.setRequestValues(request);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));
        
        mUseCaseScheduler.execute(new Runnable()
        {
            @Override
            public void run()
            {
                useCase.run();
            }
        });
    }
    
    public <V extends UseCase.ResponseValue> void notifyResponse(final V response,
        final UseCase.UseCaseCallback<V> useCaseCallback)
    {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }
    
    private <V extends UseCase.ResponseValue> void notifyError(final UseCase.UseCaseCallback<V> useCaseCallback,
        final Exception exception)
    {
        mUseCaseScheduler.onError(useCaseCallback, exception);
    }
    
    private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements UseCase.UseCaseCallback<V>
    {
        private final UseCase.UseCaseCallback<V> mCallback;
        
        private final UseCaseHandler mUseCaseHandler;
        
        public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback, UseCaseHandler useCaseHandler)
        {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }
        
        @Override
        public void onSuccess(V response)
        {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }
        
        @Override
        public void onError(Exception exception)
        {
            mUseCaseHandler.notifyError(mCallback, exception);
        }
    }
    
    public static UseCaseHandler getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return INSTANCE;
    }
}
