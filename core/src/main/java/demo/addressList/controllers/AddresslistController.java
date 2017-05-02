package demo.addressList.controllers;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import demo.addressList.dto.Addresslist;
import demo.addressList.service.IAddresslistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

    @Controller
    public class AddresslistController extends BaseController{

    @Autowired
    private IAddresslistService service;


    @RequestMapping(value = "/addresslist/query")
    @ResponseBody
    public ResponseData query(Addresslist dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/addresslist/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request,@RequestBody List<Addresslist> dto){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/addresslist/remove")
      @ResponseBody
      public ResponseData delete(HttpServletRequest request,@RequestBody List<Addresslist> dto){
            service.batchDelete(dto);
            return new ResponseData();
        }
        @RequestMapping(value = "/addresslist/excel")
        public void Excel(HttpServletResponse response ){
            //得到表格中所有的数据
            JSONArray jarray = new JSONArray();
            List<Addresslist> listExcel=service.getAllByExcel("C:/Users/admin/Desktop/book.xlsx");
            //DBhepler db=new DBhepler();
            JSONObject obj = null;
            for (Addresslist addresslist : listExcel) {
              /*  if(service.isChinese(addresslist.getName())){
                    System.out.println(service.isChinese(addresslist.getName()));
                    String sql_tl_zh_CN="insert into addressli_tl (name,LANG,address) values(?,?,?)";
                    String[] str_tl_zh_CN=new String[]{addresslist.getName(),"zh_CN",addresslist.getAddress()+""};
                    db.AddU(sql_tl_zh_CN, str_tl_zh_CN);
                }else{
                    System.out.println(service.isChinese(addresslist.getName()));
                    String sql_tl_en_GB="insert into addressli_tl (name,LANG,address) values(?,?,?)";
                    String[] str_tl_en_GB=new String[]{addresslist.getName(),"en_GB",addresslist.getAddress()+""};
                    db.AddU(sql_tl_en_GB, str_tl_en_GB);
                }*/
                obj = new JSONObject();
                obj.put("name",addresslist.getName());
                obj.put("phone",addresslist.getPhone());
                obj.put("email",addresslist.getEmail());
                obj.put("address",addresslist.getAddress());
                obj.put("gender",addresslist.getGender());
                jarray.add(obj);
              /*  String sql="insert into addresslist (name,phone,email,address,gender) values(?,?,?,?,?)";
                String[] str=new String[]{addresslist.getName(),addresslist.getPhone(),addresslist.getEmail(),addresslist.getAddress(),addresslist.getGender()+""};
                db.AddU(sql, str);*/
            }
           // System.out.println(jarray);
            try {
                PrintWriter pw = response.getWriter();
                pw.print(jarray);
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }