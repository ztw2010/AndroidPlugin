package com.small.test.app.maintenance.maintenance;

import com.small.test.app.maintenance.maintenance.data.ProvinceVO;
import com.small.test.app.maintenance.maintenance.data.source.MaintenanceregistrationDataSource;
import com.small.test.app.maintenance.maintenance.data.source.MaintenanceregistrationRepository;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;


public class GetProvinceUseCase extends UseCase<GetProvinceUseCase.RequestValues, GetProvinceUseCase.ResponseValue>
{
    private final MaintenanceregistrationRepository maintenanceregistrationRepository;
    
    public GetProvinceUseCase(MaintenanceregistrationRepository maintenanceregistrationRepository)
    {
        this.maintenanceregistrationRepository = maintenanceregistrationRepository;
    }
    
    public static final class RequestValues implements UseCase.RequestValues
    {
        private final String jobId;
        
        private final boolean isRefresh;
        
        public RequestValues(String jobId, boolean isRefresh)
        {
            this.jobId = jobId;
            this.isRefresh = isRefresh;
        }
        
        public String getJobId()
        {
            return jobId;
        }
        
        public boolean isRefresh()
        {
            return isRefresh;
        }
    }
    
    public static final class ResponseValue implements UseCase.ResponseValue
    {
        private final List<ProvinceVO> provinceVOs;
        
        public ResponseValue(List<ProvinceVO> provinceVOs)
        {
            this.provinceVOs = provinceVOs;
        }
        
        public List<ProvinceVO> getProvinceVO()
        {
            return provinceVOs;
        }
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues)
    {
        maintenanceregistrationRepository.getProvinces(requestValues.getJobId(),
            requestValues.isRefresh(),
            new MaintenanceregistrationDataSource.GetProvinceCallBack()
            {
                @Override
                public void onProvincesLoaded(List<ProvinceVO> provinceDOs)
                {
                    getUseCaseCallback().onSuccess(new ResponseValue(provinceDOs));
                }
                
                @Override
                public void onError(Exception exception)
                {
                    getUseCaseCallback().onError(exception);
                }
            });
    }
}
