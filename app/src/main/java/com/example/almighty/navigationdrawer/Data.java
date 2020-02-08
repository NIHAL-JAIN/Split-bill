package com.example.almighty.navigationdrawer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Renown_2 on 4/21/2017.
 */

public class Data {
    @SerializedName("name")
    @Expose
     private String name;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("group_ids")
        @Expose
        private String groupIds;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGroupIds() {
            return groupIds;
        }

        public void setGroupIds(String groupIds) {
            this.groupIds = groupIds;
        }

}
