package com.small.test.app.historydata.suspension;

import com.small.test.app.historydata.suspension.data.FaultListVO;
import com.small.test.app.historydata.suspension.data.source.SuspensionDataSource;
import com.small.test.app.historydata.suspension.data.source.SuspensionRepository;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;


public class SuspensionUseCase extends UseCase<SuspensionUseCase.RequestValues, SuspensionUseCase.ResponseValue>
{
    private final SuspensionRepository suspensionRepository;
    
    public SuspensionUseCase(SuspensionRepository suspensionRepository)
    {
        this.suspensionRepository = suspensionRepository;
    }
    
    public static final class RequestValues implements UseCase.RequestValues
    {
        private final String zongChengCode, className;
        
        private final int pageNum, recordNum;
        
        private final boolean isRefresh;
        
        public RequestValues(String zongChengCode, String className, int pageNum, int recordNum, boolean isRefresh)
        {
            this.zongChengCode = zongChengCode;
            this.className = className;
            this.pageNum = pageNum;
            this.recordNum = recordNum;
            this.isRefresh = isRefresh;
        }
        
        public String getZongChengCode()
        {
            return zongChengCode;
        }
        
        public String getClassName()
        {
            return className;
        }
        
        public int getPageNum()
        {
            return pageNum;
        }
        
        public int getRecordNum()
        {
            return recordNum;
        }
        
        public boolean isRefresh()
        {
            return isRefresh;
        }
    }
    
    public static final class ResponseValue implements UseCase.ResponseValue
    {
        private final List<FaultListVO> faultListVOs;
        
        public ResponseValue(List<FaultListVO> faultListVOs)
        {
            this.faultListVOs = faultListVOs;
        }
        
        public List<FaultListVO> getFaultListVOs()
        {
            return faultListVOs;
        }
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues)
    {
        suspensionRepository.getSuspensionList(requestValues.getZongChengCode(),
            requestValues.getClassName(),
            requestValues.getPageNum(),
            requestValues.getRecordNum(),
            requestValues.isRefresh(),
            new SuspensionDataSource.GetSuspensionListCallBack()
            {
                
                @Override
                public void onSuspensionLoaded(List<FaultListVO> faultListVOs)
                {
                    getUseCaseCallback().onSuccess(new ResponseValue(faultListVOs));
                }
                
                @Override
                public void onError(Exception exception)
                {
                    getUseCaseCallback().onError(exception);
                }
            });
    }
}
