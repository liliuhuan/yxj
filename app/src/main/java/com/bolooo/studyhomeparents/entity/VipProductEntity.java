package com.bolooo.studyhomeparents.entity;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/5/12
 * 描述:${}
 */

public class VipProductEntity {

    /**
     * ChildId : sample string 1
     * HeadPhoto : sample string 2
     * VIPUTickets : [{"Id":"sample string 1","Title":"sample string 2","ExpiredMonth":1,"Price":3},{"Id":"sample string 1","Title":"sample string 2","ExpiredMonth":1,"Price":3}]
     */

    private String ChildId;
    private String HeadPhoto;
    private List<VIPUTicketsEntity> VIPUTickets;

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String ChildId) {
        this.ChildId = ChildId;
    }

    public String getHeadPhoto() {
        return HeadPhoto;
    }

    public void setHeadPhoto(String HeadPhoto) {
        this.HeadPhoto = HeadPhoto;
    }

    public List<VIPUTicketsEntity> getVIPUTickets() {
        return VIPUTickets;
    }

    public void setVIPUTickets(List<VIPUTicketsEntity> VIPUTickets) {
        this.VIPUTickets = VIPUTickets;
    }

    public static class VIPUTicketsEntity {
        /**
         * Id : sample string 1
         * Title : sample string 2
         * ExpiredMonth : 1
         * Price : 3.0
         */

        private String Id;
        private String Title;
        private int ExpiredMonth;
        private double Price;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getExpiredMonth() {
            return ExpiredMonth;
        }

        public void setExpiredMonth(int ExpiredMonth) {
            this.ExpiredMonth = ExpiredMonth;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }
    }
}
