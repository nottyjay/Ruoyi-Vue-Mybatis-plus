package com.alphay.boot.common.utils;

import com.alphay.boot.common.core.page.PageDomain;
import com.alphay.boot.common.core.page.TableSupport;
import com.alphay.boot.common.utils.sql.SqlUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;

/**
 * 分页工具类
 *
 * @author d3code
 */
public class PageUtils {

  protected static final ThreadLocal<IPage> LOCAL_PAGE = new ThreadLocal();

  /** 设置请求分页数据 */
  public static IPage startPage() {
    PageDomain pageDomain = TableSupport.buildPageRequest();
    Integer pageNum = pageDomain.getPageNum();
    Integer pageSize = pageDomain.getPageSize();
    IPage page = new Page(pageNum, pageSize);
    setLocalPage(page);
    return page;
  }

  /** 清理分页的线程变量 */
  public static void clearPage() {
    LOCAL_PAGE.remove();
  }

  protected static void setLocalPage(IPage page) {
    LOCAL_PAGE.set(page);
  }

  public static <T> IPage<T> getLocalPage() {
    return (IPage) LOCAL_PAGE.get();
  }
}
