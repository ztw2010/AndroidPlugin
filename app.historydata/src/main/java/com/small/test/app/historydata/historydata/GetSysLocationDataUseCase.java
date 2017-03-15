package com.small.test.app.historydata.historydata;

import com.small.test.app.historydata.historydata.data.CategoryVO;
import com.small.test.app.historydata.historydata.data.source.HistoryDataRepository;
import com.small.test.app.historydata.historydata.data.source.HistoryDataSource;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;


/**
 * 
 * 获取安装位置
 *
 * <p>detailed comment
 * @author ztw 2016年7月7日
 * @see
 * @since 1.0
 */
public class GetSysLocationDataUseCase
    extends UseCase<GetSysLocationDataUseCase.RequestValues, GetSysLocationDataUseCase.ResponseValue>
{
    
    private final HistoryDataRepository historyDataRepository;
    
    public GetSysLocationDataUseCase(HistoryDataRepository historyDataRepository)
    {
        this.historyDataRepository = historyDataRepository;
    }
    
    public static final class RequestValues implements UseCase.RequestValues
    {
        private final int classCode, pageNo, recordNum;
        
        private final boolean isRefresh;
        
        public RequestValues(int classCode, int pageNo, int recordNum, boolean isRefresh)
        {
            this.classCode = classCode;
            this.pageNo = pageNo;
            this.recordNum = recordNum;
            this.isRefresh = isRefresh;
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
        historyDataRepository.getSystemLocationDatas(requestValues.getClassCode(),
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
