package com.yakshop.net.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yakshop.net.dao.YakDao;
import com.yakshop.net.exception.YakShopException;
import com.yakshop.net.model.ReadXml;
import com.yakshop.net.model.YakProduct;
import com.yakshop.net.model.YakShopModel;

@Service
public class YakShopService {
	@Autowired
	private YakDao yakDao;
	

	public YakProduct read(File originalFilename, int elapsedTimeInDays) {
		List<YakShopModel> yakModelList = new ReadXml(originalFilename).read();
		for (YakShopModel yakModel : yakModelList) {

			calculateAndSaveYieldForDay(yakModel, elapsedTimeInDays);
		}
		YakProduct yakProduct = getTotalYakYield(elapsedTimeInDays);

		return yakProduct;
	}

	public YakProduct getTotalYakYield(int elapsedTimeInDays) {
		return yakDao.getTotalYield(elapsedTimeInDays);
	}

	public YakProduct getYakYieldForAYak(YakShopModel yak, int elapsedTimeInDays) {
		if (yakDao.getYieldForYak(yak.getId(), elapsedTimeInDays) == null) {
			calculateAndSaveYieldForDay(yak, elapsedTimeInDays);
		}
		return yakDao.getYieldForYak(yak.getId(), elapsedTimeInDays);
	}

	private void calculateAndSaveYieldForDay(YakShopModel yakModel, int elapsedTimeInDays) {
		YakProduct yakProduct;
		try {
			yakProduct = yakDao.calculateYakYieldForDay(yakModel, elapsedTimeInDays);
			yakDao.saveYieldForDay(yakModel.getId(), elapsedTimeInDays, yakProduct);
		} catch (YakShopException e) {
			e.printStackTrace();
		}
		
	}

}
