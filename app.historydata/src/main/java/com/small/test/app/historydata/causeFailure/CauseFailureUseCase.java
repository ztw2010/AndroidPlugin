package com.small.test.app.historydata.causeFailure;

import com.small.test.app.historydata.causeFailure.data.CauseFailureVO;
import com.small.test.app.historydata.causeFailure.data.source.CauseFailureDataSource;
import com.small.test.app.historydata.causeFailure.data.source.CauseFailureRepository;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;


public class CauseFailureUseCase extends UseCase<CauseFailureUseCase.RequestValues, CauseFailureUseCase.ResponseValue>
{
    private final CauseFailureRepository causeFailureRepository;
    
    public CauseFailureUseCase(CauseFailureRepository causeFailureRepository)
    {
        this.causeFailureRepository = causeFailureRepository;
    }
    
    public static final class RequestValues implements UseCase.RequestValues
    {
        private final String manualId, assemblyId, phenomenon;
        
        private final int pageNum, recordNum;
        
        private final boolean isRefresh;
        
        public RequestValues(String manualId, String assemblyId, String phenomenon, int pageNum, int recordNum,
            boolean isRefresh)
        {
            this.manualId = manualId;
            this.assemblyId = assemblyId;
            this.phenomenon = phenomenon;
            this.pageNum = pageNum;
            this.recordNum = recordNum;
            this.isRefresh = isRefresh;
        }
        
        public String getManualId()
        {
            return manualId;
        }
        
        public String getAssemblyId()
        {
            return assemblyId;
        }
        
        public String getPhenomenon()
        {
            return phenomenon;
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
        private final List<CauseFailureVO> causeFailureVOs;
        
        public ResponseValue(List<CauseFailureVO> causeFailureVOs)
        {
            this.causeFailureVOs = causeFailureVOs;
        }
        
        public List<CauseFailureVO> getFaultListVOs()
        {
            return causeFailureVOs;
        }
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues)
    {
        causeFailureRepository.getCauseFailureData(requestValues.getManualId(),
            requestValues.getAssemblyId(),
            requestValues.getPhenomenon(),
            requestValues.getPageNum(),
            requestValues.getRecordNum(),
            requestValues.isRefresh(),
            new CauseFailureDataSource.GetCauseFailureDataCallBack()
            {
                
                @Override
                public void onCauseFailureDataLoaded(List<CauseFailureVO> causeFailureVOs)
                {
                    getUseCaseCallback().onSuccess(new ResponseValue(causeFailureVOs));
                }
                
                @Override
                public void onError(Exception exception)
                {
                    getUseCaseCallback().onError(exception);
                }
            });
    }
}
