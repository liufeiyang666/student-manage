package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.entity.SysRole;
import com.example.demo.entity.SysUser;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import com.example.demo.vo.LoginVO;
import com.example.demo.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;

    @Override
    public LoginVO login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(request.getUsername());
            Long expiresIn = jwtTokenProvider.getExpiration();

            SysUser user = sysUserMapper.selectByUsername(request.getUsername());
            if (user == null) {
                throw new BusinessException("用户不存在");
            }

            List<SysRole> roles = sysRoleMapper.selectRolesByUserId(user.getId());
            String roleCode = roles.isEmpty() ? "" : roles.get(0).getRoleCode();
            String roleName = roles.isEmpty() ? "" : roles.get(0).getRoleName();

            UserVO userVO = new UserVO();
            userVO.setId(user.getId());
            userVO.setUsername(user.getUsername());
            userVO.setRealName(user.getRealName());
            userVO.setEmail(user.getEmail());
            userVO.setPhone(user.getPhone());
            userVO.setStatus(user.getStatus());
            userVO.setRoleCode(roleCode);
            userVO.setRoleName(roleName);
            userVO.setCreateTime(user.getCreateTime());

            return new LoginVO(token, expiresIn, userVO);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            throw new BusinessException("用户名或密码错误");
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
