package com.small.test.app.historydata.historydata;

import com.small.test.app.historydata.historydata.data.CategoryVO;
import com.small.test.app.historydata.historydata.data.source.HistoryDataRepository;
import com.small.test.app.historydata.historydata.data.source.HistoryDataSource;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;


public class GetSysOneAndSysTwoAndZongChengDataUsecase extends UseCase<GetSysOneAndSysTwoAndZongChengDataUsecase.RequestValues, GetSysOneAndSysTwoAndZongChengDataUsecase.ResponseValue>
{
    private final HistoryDataRepository historyDataRepository;
    
    public GetSysOneAndSysTwoAndZongChengDataUsecase(HistoryDataRepository historyDataRepository)
    {
        this.historyDataRepository = historyDataRepository;
    }
    
    public static final class RequestValues implements UseCase.RequestValues
    {
        private final String parentCode;
        
        private final int classCode, pageNo, recordNum;
        
        private final boolean isRefresh;
        
        public RequestValues(String parentCode, int classCode, int pageNo, int recordNum, boolean isRefresh)
        {
            this.parentCode = parentCode;
            this.classCode = classCode;
            this.pageNo = pageNo;
            this.recordNum = recordNum;
            this.isRefresh = isRefresh;
        }
        
        public String getParentCode()
        {
            return parentCode;
        }
        
        public int getClassCode()
        {
            return classCode;
        }
        
        public int getPageNo()
        {
            return pageNo;
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
        private final List<CategoryVO> categoryDOs;
        
        public ResponseValue(List<CategoryVO> categoryDOs)
        {
            this.categoryDOs = categoryDOs;
        }
        
        public List<CategoryVO> getCategoryDOs()
        {
            return categoryDOs;
        }
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues)
    {
        historyDataRepository.getSysOneAndSysTwoAndZongChengDatas(requestValues.getParentCode(),
            requestValues.getClassCode(),
            requestValues.getPageNo(),
            requestValues.getRecordNum(),
            requestValues.isRefresh(),
            new HistoryDataSource.GetHistoryDataCallBack()
            {
                
                @Override
                public void onHistoryDataLoaded(List<CategoryVO> categoryDOs)
                {
                    getUseCaseCallback().onSuccess(new ResponseValue(categoryDOs));
                }
                
                @Override
                public void onError(Exception exception)
                {
                    getUseCaseCallback().onError(exception);
                }
            });
    }
}
