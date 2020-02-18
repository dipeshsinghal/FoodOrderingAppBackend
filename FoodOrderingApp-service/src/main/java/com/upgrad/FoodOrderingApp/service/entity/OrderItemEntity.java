package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "order_item", schema = "public")
@NamedQueries({
        @NamedQuery(name = "getOrderItemsByRestaurant", query = "select oi from OrderItemEntity oi where oi.order.restaurant.uuid =:uuid Order by oi.quantity desc"),
//        @NamedQuery(name = "getItemsByPopularity", query = "select oi.item.uuid, count(oi.item) as total" + //,
//                " from OrderEntity o, OrderItemEntity oi where o.id = oi.order.id" +
//                " and o.restaurant.uuid =:uuid group by o.restaurant.uuid, oi.item.uuid order by total desc") //
})
public class OrderItemEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @NotNull
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    @NotNull
    private ItemEntity item;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @Column(name = "price")
    @NotNull
    private Integer price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
