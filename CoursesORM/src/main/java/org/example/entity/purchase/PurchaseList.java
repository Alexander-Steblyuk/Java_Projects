
package org.example.entity.purchase;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "purchaseList")
public class PurchaseList {
    @EmbeddedId
    private PurchaseListPK purchaseListPK;
    private int price;
    @Column(name = "subscription_date")
    private LocalDateTime subDate;

    public PurchaseListPK getPurchaseListPK() {
        return purchaseListPK;
    }

    public void setPurchaseListPK(PurchaseListPK purchaseListPK) {
        this.purchaseListPK = purchaseListPK;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getSubDate() {
        return subDate;
    }

    public void setSubDate(LocalDateTime subDate) {
        this.subDate = subDate;
    }
}
