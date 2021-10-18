package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.BusinessHotelModel;
import com.saltlux.tool.filter.tool.model.BusinessLawModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.repo.BusinessHotelMailRepo;
import com.saltlux.tool.filter.tool.repo.BusinessLawMailRepo;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class HotelService implements Runnable {

    private List<BusinessHotelModel> businessHotelModels;

    @Autowired
    private BusinessLawMailRepo lawMailRepo;

    @Autowired
    private BusinessHotelMailRepo businessHotelMailRepo;

    @Override
    public void run() {
        filterFromHotel(this.businessHotelModels);
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterFromHotel(List<BusinessHotelModel> hotelModels) {
        filterLawMail(hotelModels);
        System.out.println("Done filter hotel mail in Thread: " + Thread.currentThread().getName());
    }

    @Transactional(dontRollbackOn = Exception.class)
    public List<BusinessHotelModel> filterLawMail(List<BusinessHotelModel> hotelModels) {
        Map<Boolean, List<BusinessHotelModel>> booleanListMap = hotelModels.stream()
                .collect(Collectors.groupingBy(mail -> Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterBusinessLawMail(mail.getMemberPrimaryEmail())));
        List<BusinessHotelModel> businessHotelModels = booleanListMap.get(true);
        if (Objects.nonNull(businessHotelModels) && businessHotelModels.size() > 0) {
            List<BusinessLawModel> businessLawModels = AppUtil.mapAll(businessHotelModels, BusinessLawModel.class);
            lawMailRepo.saveAll(businessLawModels);
            businessHotelMailRepo.deleteAllByIdInBatch(businessHotelModels.stream().map(BusinessHotelModel::getMemberId).collect(Collectors.toList()));
        }
        return booleanListMap.get(false);
    }
}
