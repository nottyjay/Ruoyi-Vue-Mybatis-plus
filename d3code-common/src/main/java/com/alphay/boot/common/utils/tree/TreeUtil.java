package com.alphay.boot.common.utils.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alphay.boot.common.core.domain.TreeEntity;
import com.alphay.boot.common.core.domain.TreeSelect;
import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.utils.JsonUtil;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.internal.Function;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nottyjay
 */
public class TreeUtil {

  public static <T extends TreeEntity> List<TreeSelect> buildTreeSelect(
      List<T> treeEntityList, Function<T, Object> idProp, Function<T, String> labelProp) {
    for (int index = 0; index < treeEntityList.size(); index++) {
      T data = treeEntityList.get(index);
      recursionFn(treeEntityList, idProp, data);
    }
    return treeEntityList.stream()
        .map(item -> new TreeSelect(item, idProp, labelProp))
        .collect(Collectors.toList());
  }

  private static <T extends TreeEntity> List<T> recursionFn(
      List<T> treeEntityList, Function<T, Object> idProp, T parent) {
    if (CollUtil.isEmpty(treeEntityList)) {
      return treeEntityList;
    }
    for (int index = treeEntityList.size() - 1; index > 0; index--) {

      T data = treeEntityList.get(index);
      if (data.getParentPKCode() != null &&
              ObjUtil.equals(idProp.apply(parent), data.getParentPKCode()) ){
        if (CollUtil.isEmpty(parent.getChildren())) {
          parent.setChildren(new ArrayList<>());
        }
        parent.getChildren().add(data);
        treeEntityList.remove(data);
        index--;
        recursionFn(treeEntityList, idProp, data);
      }
    }
    return treeEntityList;
  }
}
