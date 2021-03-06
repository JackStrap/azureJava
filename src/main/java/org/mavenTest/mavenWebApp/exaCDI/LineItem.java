/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015,2016 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package org.mavenTest.mavenWebApp.exaCDI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by peter.pilgrim on 20-Oct-2015.
 */
public class LineItem {
	static Random random = new Random(System.currentTimeMillis());
	
	private long id;
	private String productName;
	private float price;
	private int quantity;

	public LineItem() {
	}

	public LineItem(long id, String productName, float price, int quantity) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}

	public static List<LineItem> generate(int num){
        List<LineItem> result = new ArrayList<LineItem>();
        for(int index = 0; index < num; index++){
            result.add( generateOne("" + index) );
        }
        return result;
    }

    public static LineItem generateOne(String nameSuffix){

    	long randomLong = 1L + (long)(random.nextDouble() * (100000000L - 1L));
    	float randomFloat = Math.round((1F + random.nextFloat() * (100F - 10F)) * 100.00F) / 100.0F;
    	
        return new LineItem(randomLong, "Item " + nameSuffix, randomFloat, random.nextInt(100));
    }

    /* Getter & Setter */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//
//		LineItem lineItem = (LineItem) o;
//
//		return id == lineItem.id;
//
//	}

//	@Override
//	public int hashCode() {
//		return (int) (id ^ (id >>> 32));
//	}

//	@Override
//	public String toString() {
//		return "LineItem{" + "id=" + id + ", productName='" + productName + '\'' + ", price=" + price + ", quantity="
//				+ quantity + '}';
//	}
}
