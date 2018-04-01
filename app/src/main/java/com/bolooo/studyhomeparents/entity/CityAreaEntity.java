package com.bolooo.studyhomeparents.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/6/1
 * 描述:${}
 */

public class CityAreaEntity implements IPickerViewData{
    /**
     * ProvinceName : 北京
     * Citys : [{"CityName":"北京","ProvinceId":"ef9819ea-df04-4c0b-9cca-4fc0b9b8acc9","Areas":[{"AreaName":"丰台区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"87a6f136-9880-4e1d-94a9-0445b91c63c4"},{"AreaName":"昌平区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"764d5168-2610-4740-aa5e-2925038fa74f"},{"AreaName":"怀柔区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"cb0c32ee-5135-4b9e-afb3-5b7e09140505"},{"AreaName":"顺义区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"ce72916d-443b-4ae7-b3e2-6a1d95391191"},{"AreaName":"大兴区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"1d7f4db4-8bc2-4870-8bc0-6ab381e027af"},{"AreaName":"东城区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"bdc85dfb-48b5-456e-97b5-7acf014f4e0d"},{"AreaName":"延庆县","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"2ee3d118-af60-41ea-997c-8e5b8d11acf9"},{"AreaName":"密云县","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"de0c3554-322d-4e77-b527-8f844b623744"},{"AreaName":"宣武区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"b98c5d91-5b24-4448-a05c-9fe466e29a7f"},{"AreaName":"朝阳区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"9bb62564-23e1-471b-81df-a56e44252750"},{"AreaName":"石景山区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"d71ec5bb-c8a0-4f16-a6cb-ab869cf26d26"},{"AreaName":"崇文区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"c0235a38-9864-49d2-a24c-aba5d0c09770"},{"AreaName":"房山区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"da6003fe-2d41-4273-b660-ce306d399e14"},{"AreaName":"海淀区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"a785ee47-5b43-4c19-9723-cf1d2908df79"},{"AreaName":"平谷区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"dec1207f-2c02-4ca2-9fe6-d2cca49a0a52"},{"AreaName":"门头沟区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"afcbd271-1ebd-44ee-bd4b-d3c35c055f23"},{"AreaName":"西城区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"1b81dd84-006b-439a-9fd5-e895f516323a"},{"AreaName":"通州区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"f7cec759-34fd-418a-8fba-e9280ffb3b38"}],"Id":"5dee6e0e-9504-4106-888b-dae14c4cc3e6"}]
     * Id : ef9819ea-df04-4c0b-9cca-4fc0b9b8acc9
     */

    private String ProvinceName;
    private String Id;
    private List<CitysEntity> Citys;

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public List<CitysEntity> getCitys() {
        return Citys;
    }

    public void setCitys(List<CitysEntity> Citys) {
        this.Citys = Citys;
    }

    @Override
    public String getPickerViewText() {
        return this.ProvinceName;
    }

    public static class CitysEntity implements IPickerViewData{
        /**
         * CityName : 北京
         * ProvinceId : ef9819ea-df04-4c0b-9cca-4fc0b9b8acc9
         * Areas : [{"AreaName":"丰台区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"87a6f136-9880-4e1d-94a9-0445b91c63c4"},{"AreaName":"昌平区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"764d5168-2610-4740-aa5e-2925038fa74f"},{"AreaName":"怀柔区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"cb0c32ee-5135-4b9e-afb3-5b7e09140505"},{"AreaName":"顺义区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"ce72916d-443b-4ae7-b3e2-6a1d95391191"},{"AreaName":"大兴区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"1d7f4db4-8bc2-4870-8bc0-6ab381e027af"},{"AreaName":"东城区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"bdc85dfb-48b5-456e-97b5-7acf014f4e0d"},{"AreaName":"延庆县","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"2ee3d118-af60-41ea-997c-8e5b8d11acf9"},{"AreaName":"密云县","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"de0c3554-322d-4e77-b527-8f844b623744"},{"AreaName":"宣武区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"b98c5d91-5b24-4448-a05c-9fe466e29a7f"},{"AreaName":"朝阳区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"9bb62564-23e1-471b-81df-a56e44252750"},{"AreaName":"石景山区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"d71ec5bb-c8a0-4f16-a6cb-ab869cf26d26"},{"AreaName":"崇文区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"c0235a38-9864-49d2-a24c-aba5d0c09770"},{"AreaName":"房山区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"da6003fe-2d41-4273-b660-ce306d399e14"},{"AreaName":"海淀区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"a785ee47-5b43-4c19-9723-cf1d2908df79"},{"AreaName":"平谷区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"dec1207f-2c02-4ca2-9fe6-d2cca49a0a52"},{"AreaName":"门头沟区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"afcbd271-1ebd-44ee-bd4b-d3c35c055f23"},{"AreaName":"西城区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"1b81dd84-006b-439a-9fd5-e895f516323a"},{"AreaName":"通州区","CityId":"5dee6e0e-9504-4106-888b-dae14c4cc3e6","Id":"f7cec759-34fd-418a-8fba-e9280ffb3b38"}]
         * Id : 5dee6e0e-9504-4106-888b-dae14c4cc3e6
         */

        private String CityName;
        private String ProvinceId;
        private String Id;
        private List<AreasEntity> Areas;

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public String getProvinceId() {
            return ProvinceId;
        }

        public void setProvinceId(String ProvinceId) {
            this.ProvinceId = ProvinceId;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public List<AreasEntity> getAreas() {
            return Areas;
        }

        public void setAreas(List<AreasEntity> Areas) {
            this.Areas = Areas;
        }

        @Override
        public String getPickerViewText() {
            return this.CityName;
        }

        public static class AreasEntity  implements  IPickerViewData{
            /**
             * AreaName : 丰台区
             * CityId : 5dee6e0e-9504-4106-888b-dae14c4cc3e6
             * Id : 87a6f136-9880-4e1d-94a9-0445b91c63c4
             */

            private String AreaName;
            private String CityId;
            private String Id;

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String AreaName) {
                this.AreaName = AreaName;
            }

            public String getCityId() {
                return CityId;
            }

            public void setCityId(String CityId) {
                this.CityId = CityId;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            @Override
            public String getPickerViewText() {
                return this.AreaName;
            }
        }
    }
}
