package com.alphay.boot.common.core.convert;

import com.alphay.boot.common.core.domain.entity.SysUser;
import com.alphay.boot.common.core.domain.vo.SimpleUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysUserConvert {

  SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

  List<SimpleUserVo> convertList(List<SysUser> list);
}
