package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.entity.SysRole;
import com.example.demo.entity.SysUser;
import com.example.demo.entity.SysUserRole;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.mapper.SysUserRoleMapper;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public IPage<UserVO> getUserPage(Integer pageNum, Integer pageSize, String username, Integer status) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        IPage<SysUser> userPage = sysUserMapper.selectUserPage(page, username, status);

        Page<UserVO> voPage = new Page<>(pageNum, pageSize);
        voPage.setTotal(userPage.getTotal());
        voPage.setRecords(userPage.getRecords().stream().map(this::convertToVO).toList());

        return voPage;
    }

    @Override
    public UserVO getUserById(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return convertToVO(user);
    }

    @Override
    @Transactional
    public UserVO createUser(UserCreateRequest request) {
        SysUser existing = sysUserMapper.selectByUsername(request.getUsername());
        if (existing != null) {
            throw new BusinessException("用户名已存在");
        }

        SysRole role = sysRoleMapper.selectByCode(request.getRoleCode());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRealName(request.getRealName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(1);
        sysUserMapper.insert(user);

        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        sysUserRoleMapper.insert(userRole);

        return convertToVO(user);
    }

    @Override
    @Transactional
    public UserVO updateUser(Long id, UserUpdateRequest request) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        if (StringUtils.hasText(request.getRealName())) {
            user.setRealName(request.getRealName());
        }
        if (StringUtils.hasText(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (StringUtils.hasText(request.getPhone())) {
            user.setPhone(request.getPhone());
        }
        sysUserMapper.updateById(user);

        if (StringUtils.hasText(request.getRoleCode())) {
            SysRole role = sysRoleMapper.selectByCode(request.getRoleCode());
            if (role != null) {
                sysUserRoleMapper.deleteByMap(java.util.Map.of("user_id", id));
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(id);
                userRole.setRoleId(role.getId());
                sysUserRoleMapper.insert(userRole);
            }
        }

        return convertToVO(user);
    }

    @Override
    public void deleteUser(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        sysUserMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        user.setStatus(status);
        sysUserMapper.updateById(user);
    }

    @Override
    public void resetPassword(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        user.setPassword(passwordEncoder.encode("123456"));
        sysUserMapper.updateById(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        SysUser user = getCurrentUser();
        
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BusinessException("原密码不正确");
        }
        
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        sysUserMapper.updateById(user);
    }

    @Override
    public SysUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(401, "未登录");
        }
        String username = authentication.getName();
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public UserVO getCurrentUserVO() {
        SysUser user = getCurrentUser();
        return convertToVO(user);
    }

    private UserVO convertToVO(SysUser user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRealName(user.getRealName());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setStatus(user.getStatus());
        vo.setCreateTime(user.getCreateTime());

        List<SysRole> roles = sysRoleMapper.selectRolesByUserId(user.getId());
        if (!roles.isEmpty()) {
            vo.setRoleCode(roles.get(0).getRoleCode());
            vo.setRoleName(roles.get(0).getRoleName());
        }

        return vo;
    }
}
