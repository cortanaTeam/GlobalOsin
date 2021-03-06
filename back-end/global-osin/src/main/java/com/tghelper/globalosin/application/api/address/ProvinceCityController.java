package com.tghelper.globalosin.application.api.address;

import com.tghelper.globalosin.application.api.AbstractApiController;
import com.tghelper.globalosin.application.model.JsonResponse;
import com.tghelper.globalosin.application.service.address.ProvinceCityService;
import com.tghelper.globalosin.core.ApplicationMessage;
import com.tghelper.globalosin.core.entity.address.ProvinceCity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by infamouSs on 1/27/18.
 */
@RestController
public class ProvinceCityController extends AbstractApiController {
    
    public static final String TAG = ProvinceCityController.class.getSimpleName();
    
    public static final String BASE_URL = "/provinces";
    
    private final ProvinceCityService mProvinceCityService;
    
    @Autowired
    public ProvinceCityController(
              ProvinceCityService provinceCityService) {
        this.mProvinceCityService = provinceCityService;
    }
    
    
    @RequestMapping(value = BASE_URL + "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getProvinceCityById(@PathVariable("id") String id) {
        LOGGER.info("[INFO] GET PROVINCE/CITY BY ID:" + id);
        
        ProvinceCity provinceCity = mProvinceCityService.findById(id);
        
        return new JsonResponse.Builder<ProvinceCity>()
                  .setData(provinceCity)
                  .setHttpStatus(HttpStatus.OK)
                  .isSuccess(true)
                  .build();
    }
    
    @RequestMapping(value = BASE_URL, method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getAllProvinceCity() {
        LOGGER.info("[INFO] GET ALL PROVINCE/CITY");
        
        List<ProvinceCity> provinceCities = mProvinceCityService.findAll();
        
        return new JsonResponse.Builder<List<ProvinceCity>>()
                  .setData(provinceCities)
                  .setHttpStatus(HttpStatus.OK)
                  .isSuccess(true)
                  .build();
    }
    
    @RequestMapping(value = BASE_URL + "/getBy", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getProvinceCityByName(@RequestParam("name") String name) {
        LOGGER.info("[INFO] GET PROVINCE/CITY BY NAME:" + name);
        
        List<ProvinceCity> provinceCities = mProvinceCityService.findProvinceCitiesNameLike(name);
        
        return new JsonResponse.Builder<List<ProvinceCity>>()
                  .setData(provinceCities)
                  .setHttpStatus(HttpStatus.OK)
                  .isSuccess(true)
                  .build();
    }
    
    @RequestMapping(value = BASE_URL, method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody ProvinceCity provinceCity) {
        LOGGER.info("[INFO] CREATE PROVINCE/CITY");
        
        mProvinceCityService.create(provinceCity);
        
        return new JsonResponse.Builder<ApplicationMessage>()
                  .setData(ApplicationMessage.CREATE_ENTITY_SUCCESSFUL)
                  .setHttpStatus(HttpStatus.OK)
                  .isSuccess(true)
                  .build();
    }
    
    @RequestMapping(value = BASE_URL + "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse update(@PathVariable("id") String id,
              @RequestBody ProvinceCity provinceCity) {
        LOGGER.info("[INFO] UPDATE PROVINCE/CITY");
        
        if (!id.equals(provinceCity.getId())) {
            provinceCity.setId(id);
        }
        
        mProvinceCityService.update(provinceCity);
        
        return new JsonResponse.Builder<ApplicationMessage>()
                  .setData(ApplicationMessage.UPDATE_ENTITY_SUCCESSFUL)
                  .setHttpStatus(HttpStatus.OK)
                  .isSuccess(true)
                  .build();
    }
    
    @RequestMapping(value = BASE_URL + "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse delete(@PathVariable("id") String id,
              @RequestBody ProvinceCity provinceCity) {
        LOGGER.info("[INFO] DELETE PROVINCE/CITY");
        
        if (!id.equals(provinceCity.getId())) {
            provinceCity.setId(id);
        }
        
        mProvinceCityService.delete(provinceCity);
        
        return new JsonResponse.Builder<ApplicationMessage>()
                  .setData(ApplicationMessage.DELETE_ENTITY_SUCCESSFUL)
                  .setHttpStatus(HttpStatus.OK)
                  .isSuccess(true)
                  .build();
    }
}
