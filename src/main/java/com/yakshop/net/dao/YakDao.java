package com.yakshop.net.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yakshop.net.exception.YakShopException;
import com.yakshop.net.model.YakProduct;
import com.yakshop.net.model.YakShopModel;

@Repository
public class YakDao {

	@Value("${IsYakDied}")
	private String yakDied;

	private Map yakMap = new HashMap<Integer, Map<Integer, YakProduct>>();

	public YakProduct getYieldForYak(int id, int elapsedTimeInDays) {
		Map<Integer, YakProduct> dayYieldMap = (Map<Integer, YakProduct>) yakMap.get(id);
		return dayYieldMap.get(elapsedTimeInDays);
	}

	public YakProduct getTotalYield(int elapsedTimeInDays) {
		YakProduct yakProduct = new YakProduct();
		double totalMilk = 0;
		int totalSkins = 0;
		Iterator<YakProduct> it = yakMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			YakProduct yield = ((Map<Integer, YakProduct>) pair.getValue()).get(elapsedTimeInDays);
			totalMilk += yield.getMilk();
			totalSkins += yield.getSkin();
			yakProduct.setMilk(totalMilk);
			yakProduct.setSkin(totalSkins);
		}

		return yakProduct;
	}

	public void saveYieldForDay(int id, int elapsedTimeInDays, YakProduct yakProduct) {

		Map<Integer, YakProduct> dayYieldMap = (Map<Integer, YakProduct>)yakMap.get(id);
		if (dayYieldMap == null) {
			dayYieldMap = new HashMap<Integer, YakProduct>();
			yakMap.put(id, dayYieldMap);
		}
		dayYieldMap.put(elapsedTimeInDays, yakProduct);
	}

	public YakProduct calculateYakYieldForDay(YakShopModel yakShopModel, int elapsedTimeInDays) throws YakShopException {
		YakProduct yakProduct = new YakProduct();
		yakProduct.setMilk(getTotalMilkQuantity(yakShopModel,elapsedTimeInDays));
		yakProduct.setSkin(getSkinCount(yakShopModel,elapsedTimeInDays));
		return yakProduct;
	}

	

	public double getTotalMilkQuantity(YakShopModel yakshopModel,int elapsedTimeInDays) {
		double totalMilkQuantity = 0;
		for (int day = 0; day < elapsedTimeInDays; day++) {
			try {
				totalMilkQuantity = totalMilkQuantity + getMilkQuantityForDay(calculateCurrentAgeInDays(yakshopModel,day));
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				break;
			}
		}

		return totalMilkQuantity;
	}

	public double getMilkQuantityForDay(int currentAgeInDays) throws YakShopException {
		double milkForToday = 0;
		if (isAliveByAge(currentAgeInDays)) {
			milkForToday = (50 - currentAgeInDays * 0.03);
		} else {
			throw new YakShopException(yakDied);
		}
		return milkForToday;
	}

	public int getSkinCount(YakShopModel yakshopModel ,int elapsedTimeInDays) throws YakShopException {
		int skinCount = 1; // for day 0
		for (int day = 1; day < elapsedTimeInDays; day++) {
			int currentAgeInDays = calculateCurrentAgeInDays(yakshopModel,day);
			try {
				if (canShaveToday(yakshopModel,currentAgeInDays)) {
					skinCount++;
				}
			} catch (YakShopException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
		return skinCount;
	}
	private boolean canShaveToday(YakShopModel yakshopModel,int currentAgeInDays) throws YakShopException {
		boolean isEligibleForShave = false;
		double allowedGapInShave = (8 + yakshopModel.getAgeAtLastShave() * 0.01);
		if (isAlive(yakshopModel,currentAgeInDays)) {
			isEligibleForShave = (currentAgeInDays - yakshopModel.getAgeAtLastShave() > allowedGapInShave) ? true : false;
		} else
			throw new YakShopException();

		return isEligibleForShave;
	}
	private boolean isAliveByAge(int ageInDays) {
		return ageInDays < 1000;
	}

	private boolean isAlive(YakShopModel yakshopModel,int elapsedTimeInDays) {
		return calculateCurrentAgeInDays(yakshopModel,elapsedTimeInDays) < 1000;
	}

	private int calculateCurrentAgeInDays(YakShopModel yakshopModel,int elapsedTimeInDays) {
		return (int) (yakshopModel.getAge()* 100 + elapsedTimeInDays);
	}
}
