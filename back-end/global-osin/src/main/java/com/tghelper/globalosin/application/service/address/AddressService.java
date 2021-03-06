package com.tghelper.globalosin.application.service.address;

import com.tghelper.globalosin.application.service.BaseService;
import com.tghelper.globalosin.core.entity.address.Address;
import java.util.List;

/**
 * Created by infamouSs on 1/31/18.
 */

public interface AddressService extends BaseService<Address, String> {
    
    List<Address> findAddressByType(SearchType searchType, String value);
}
