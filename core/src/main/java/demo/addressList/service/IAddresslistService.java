package demo.addressList.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import demo.addressList.dto.Addresslist;

import java.util.List;

public interface IAddresslistService extends IBaseService<Addresslist>, ProxySelf<IAddresslistService>{
    List<Addresslist> getAllByExcel(String file);
    boolean isChinese(String str);
}