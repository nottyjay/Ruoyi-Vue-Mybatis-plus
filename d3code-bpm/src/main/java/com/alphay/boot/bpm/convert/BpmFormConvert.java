package com.alphay.boot.bpm.convert;

import com.alphay.boot.bpm.api.model.vo.BpmFormSimpleResponseVo;
import com.alphay.boot.bpm.api.domain.BpmForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BpmFormConvert {

  BpmFormConvert INSTANCE = Mappers.getMapper(BpmFormConvert.class);

  List<BpmFormSimpleResponseVo> convertList2(List<BpmForm> list);
}
