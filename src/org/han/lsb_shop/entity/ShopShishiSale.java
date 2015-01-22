package org.han.lsb_shop.entity;

/**
 * 门店实时销售
 * @author Administrator
 *
 */
public class ShopShishiSale {
	
	public String id;
	
	public String shopName;
	
	public String shuliang;
	
	public String shouru;
	
	public String chengben;
	
	public String maoli;
	
	public ShopShishiSale() {
		
	}
	
	public ShopShishiSale(String id,String shopName,String shuliang,String shouru,String chengben,String maoli) {
		this.id = id;
		this.shopName = shopName;
		this.shuliang = shuliang;
		this.shouru = shouru;
		this.chengben = chengben;
		this.maoli = maoli;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}

	public String getShouru() {
		return shouru;
	}

	public void setShouru(String shouru) {
		this.shouru = shouru;
	}

	public String getChengben() {
		return chengben;
	}

	public void setChengben(String chengben) {
		this.chengben = chengben;
	}

	public String getMaoli() {
		return maoli;
	}

	public void setMaoli(String maoli) {
		this.maoli = maoli;
	}
}
