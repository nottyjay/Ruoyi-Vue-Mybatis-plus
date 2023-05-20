package com.d3code.bpm.convert;

import com.d3code.bpm.domain.BpmForm;
import com.d3code.bpm.model.vo.BpmFormSimpleResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BpmFormConvert {

  BpmFormConvert INSTANCE = Mappers.getMapper(BpmFormConvert.class);

  List<BpmFormSimpleResponseVo> convertList2(List<BpmForm> list);
}
