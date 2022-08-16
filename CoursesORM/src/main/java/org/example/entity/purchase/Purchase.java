package org.example.entity.purchase;

import jakarta.persistence.*;

@Entity
@Table(name = "Purchases")
public class Purchase {
    @EmbeddedId
    private PurchasePK purchasePK;

    public PurchasePK getPurchasePK() {
        return purchasePK;
    }

    public void setPurchasePK(PurchasePK purchasePK) {
        this.purchasePK = purchasePK;
    }
}
