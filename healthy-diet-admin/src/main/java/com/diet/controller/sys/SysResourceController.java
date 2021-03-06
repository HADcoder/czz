package com.diet.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.diet.core.base.BaseController;
import com.diet.entity.sys.SysResource;
import com.diet.message.ResponseMsg;
import com.diet.service.sys.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.API + "/sysResource")
public class SysResourceController extends BaseController {

    @Autowired
    private ISysResourceService sysResourceService;

    @PostMapping("/save")
    public ResponseMsg insert(@RequestBody SysResource model) {
        ResponseMsg responseMsg = new ResponseMsg();
        SysResource result = sysResourceService.save(model);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/deleteById/{id}")
    public ResponseMsg deleteById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        sysResourceService.deleteById(id);
        return responseMsg;
    }

    @PostMapping("/selectById/{id}")
    public ResponseMsg selectById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        SysResource result = sysResourceService.findById(id);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/list")
    public ResponseMsg list(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer pageNum = request.getInteger("pageNum");
        Integer pageSize = request.getInteger("pageSize");
        if (pageNum == null) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<SysResource> result = sysResourceService.findAll(pageable);
        responseMsg.setData(result);
        return responseMsg;
    }

}
