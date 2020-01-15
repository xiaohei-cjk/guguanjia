package com.cjk.service.impl;

import com.cjk.entity.Detail;
import com.cjk.entity.SysUser;
import com.cjk.entity.Transfer;
import com.cjk.entity.WorkOrder;
import com.cjk.mapper.DetailMapper;
import com.cjk.mapper.SysUserMapper;
import com.cjk.mapper.TransferMapper;
import com.cjk.mapper.WorkOrderMapper;
import com.cjk.service.WorkOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WorkOrderServiceImpl extends IServiceImpl<WorkOrder> implements WorkOrderService {

    @Autowired
    WorkOrderMapper workOrderMapper;

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    DetailMapper detailMapper;

    @Autowired
    TransferMapper transferMapper;

    @Override
    public PageInfo<WorkOrder> selectAll(Map<String, Object> params) {
        //默认值设置
        if(StringUtils.isEmpty(params.get("pageNum"))){
            params.put("pageNum",1);
        }
        if(StringUtils.isEmpty(params.get("pageSize"))){
            params.put("pageSize",5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));

        List<WorkOrder> WorkOrders = workOrderMapper.selectByCondition(params);

        PageInfo<WorkOrder> pageInfo = new PageInfo<>(WorkOrders);//生成分页对象

        return pageInfo;
    }

    /**
     * 1.根据oid查询 workOrder信息
     * 2.根据oid查询创建、运输、处理用户
     * 3.根据oid查询详单
     * 4.根据oid查询转运记录
     * @param oid
     * @return
     */
    @Override
    public Map<String, Object> selectByOid(long oid) {
        WorkOrder workOrder = mapper.selectByPrimaryKey(oid);
        SysUser createUser = userMapper.selectById(workOrder.getCreateUserId());


        SysUser transportUser = null;
        if(!StringUtils.isEmpty(workOrder.getTransportUserId())){
            transportUser=  userMapper.selectById(workOrder.getTransportUserId());
        }

        SysUser recipientUser = null;
        if(!StringUtils.isEmpty(workOrder.getRecipientUserId())){
            recipientUser = userMapper.selectById(workOrder.getRecipientUserId());
        }
        List<Detail> details = detailMapper.selectByOid(oid);
        List<Transfer> transfers = transferMapper.selectByOid(oid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("work",workOrder);
        map.put("createUser",createUser);
        map.put("transportUser",transportUser);
        map.put("recipientUser",recipientUser);
        map.put("details",details);
        map.put("transfers",transfers);

        return map;
    }


}
