package com.cjk.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.Map;

public class SysRoleProvider {


    /**
     * 根据条件，动态生成sql语句
     * @param params
     * @return
     */
    public String selectByCondition(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("select " +
                " sr.*,so.name officeName " +
                "from " +
                " sys_role sr,sys_office so " +
                "where " +
                " sr.office_id=so.id " +
                "and" +
                " sr.del_flag=0 "
        );

        if(params.containsKey("dataScope")&&!StringUtils.isEmpty(params.get("dataScope"))){

            sb.append(" and sr.data_scope=#{dataScope}  ");
        }
        if(params.containsKey("remarks")&&!StringUtils.isEmpty(params.get("remarks"))){
            sb.append(" and sr.remarks=#{remarks} ");
        }

        if(params.containsKey("oid")&&!StringUtils.isEmpty(params.get("oid"))){
            sb.append(" and so.id=#{oid} ");
        }

        if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
            sb.append(" AND sr.name like CONCAT('%',#{name},'%') ");
        }
        return sb.toString();
    }

    /**
     *
     * @param rid       角色id
     * @param uids      移除的人员id
     * @return
     */
    public String  deleteBatch(@Param("rid")long rid, @Param("uids")long[] uids){
        StringBuilder sb = new StringBuilder();
        sb.append("delete " +
                "from " +
                " sys_user_role " +
                "where " +
                " role_id=#{rid} " +
                "and " +
                " user_id " +
                "in ");
        sb.append("(");
        for (int i = 0; i < uids.length; i++) {
            sb.append("#{uids["+i+"]},");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }

    /**
     *
     * @param rid       角色id
     * @param cids      添加的人员id
     * @return
     */
    public String  insertBatch(@Param("rid")long rid, @Param("cids")long[] cids){
        StringBuilder sb = new StringBuilder();
        sb.append("insert into " +
                " sys_user_role " +
                "(role_id,user_id) "
        );
        sb.append(" values");
        for (int i = 0; i < cids.length; i++) {
            sb.append("(#{rid},#{cids["+i+"]}),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String insertRoleResource(@Param("resIds") long[] resIds,@Param("rid")long rid){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `sys_role_resource`(  `role_id`, `resource_id`, " +
                "`create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < resIds.length; i++) {
            sb.append("(#{rid},#{resIds["+i+"]},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String insertRoleOffice(@Param("oids") long[] oids,@Param("rid")long rid){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `sys_role_office`(`role_id`, `office_id`, `id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < oids.length; i++) {
            sb.append("(#{rid},#{oids["+i+"]},null,null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}
