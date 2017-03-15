package com.small.test.appstub.mvp;

public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValue>
{
    private Q mRequestValues;
    
    private UseCaseCallback<P> mUseCaseCallback;
    
    public void setRequestValues(Q requestValues)
    {
        mRequestValues = requestValues;
    }
    
    public Q getRequestValues()
    {
        return mRequestValues;
    }
    
    public UseCaseCallback<P> getUseCaseCallback()
    {
        return mUseCaseCallback;
    }
    
    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback)
    {
        mUseCaseCallback = useCaseCallback;
    }
    
    public void run()
    {
        executeUseCase(mRequestValues);
    }
    
    protected abstract void executeUseCase(Q requestValues);
    
    /**
     * Data passed to a request.
     */
    public interface RequestValues
    {
    }
    
    /**
     * Data received from a request.
     */
    public interface ResponseValue
    {
    }
    
    public interface UseCaseCallback<R>
    {
        void onSuccess(R response);
        
        void onError(Exception exception);
    }
}
