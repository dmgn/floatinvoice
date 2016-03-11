package com.floatinvoice.business.dao;

public class InvoicePoolKey {

	private int buyerId;
	
	private int supplierId;
	
	public InvoicePoolKey(){
		
	}
	
	public InvoicePoolKey(int buyerId, int supplierId){
		this.buyerId = buyerId;
		this.supplierId = supplierId;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + buyerId;
		result = prime * result + supplierId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoicePoolKey other = (InvoicePoolKey) obj;
		if (buyerId != other.buyerId)
			return false;
		if (supplierId != other.supplierId)
			return false;
		return true;
	}
	
	
}
