package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUser> selectUserPage(Page<SysUser> page, @Param("username") String username, @Param("status") Integer status);

    SysUser selectByUsername(@Param("username") String username);
}
