package com.d3code.bpm.convert;

import com.d3code.bpm.model.vo.BpmActivityResponseVo;
import org.flowable.engine.history.HistoricActivityInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BpmActivityConvert {

  BpmActivityConvert INSTANCE = Mappers.getMapper(BpmActivityConvert.class);

  List<BpmActivityResponseVo> convertList(List<HistoricActivityInstance> list);

  @Mappings({
    @Mapping(source = "activityId", target = "key"),
    @Mapping(source = "activityType", target = "type")
  })
  BpmActivityResponseVo convert(HistoricActivityInstance bean);
}
