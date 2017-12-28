package com.gt.cache.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gt.cache.ICacheProcess;
import com.gt.pageModel.DictCd;
import com.gt.pageModel.DictTp;
import com.gt.sys.service.IDictCdService;
import com.gt.sys.service.IDictTpService;

/**
 * @功能说明：数据字典缓存预热
 * @作者： herun
 * @创建日期：2015-10-19
 * @版本号：V1.0
 */
@Service("dictCacheProcess")
public class DictCacheProcessImpl implements ICacheProcess {

	public static boolean v = true;
	private Logger logger = Logger.getLogger(DictCacheProcessImpl.class);

	@Resource(name = "dictTpService")
	private IDictTpService dictTpService;

	@Resource(name = "dictCdService")
	private IDictCdService dictCdService;

	@Override
	public void starCache() {
		if (v) {
			logger.info("数据字典缓存预热中...");
			List<DictTp> tDictTps = dictTpService.getList();
			List<DictCd> tDictCds = dictCdService.getList();
			for (DictTp tDictTp : tDictTps) {
				DictCd objDictCd = new DictCd();
				objDictCd.setDictTypeCd(tDictTp.getDictTypeCd());
				dictCdService.getList(objDictCd);
				for (DictCd dictCd : tDictCds) {
					dictCdService.getDictCd(tDictTp.getDictTypeCd(), dictCd.getDictCd());
				}
			}
			v = false;
		}

	}
}
