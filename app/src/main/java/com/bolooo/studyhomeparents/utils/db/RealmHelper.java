package com.bolooo.studyhomeparents.utils.db;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Description: RealmHelper
 * Creator: yxc
 * date: 2016/9/21 17:46
 */

public class RealmHelper {

    public static final String DB_NAME = "city_db";
    private Realm mRealm;
    private static RealmHelper instance;

    private RealmHelper() {
    }

    public static RealmHelper getInstance() {
        if (instance == null) {
            synchronized (RealmHelper.class) {
                if (instance == null)
                    instance = new RealmHelper();
            }
        }
        return instance;
    }


    protected Realm getRealm() {
        if (mRealm == null || mRealm.isClosed())
            mRealm = Realm.getDefaultInstance();
        return mRealm;
    }


    //--------------------------------------------------标签相关----------------------------------------------------

    /**
     * 增加标签
     *
     * @param bean
     * @param maxSize 保存最大数量
     */
    public void insertTag(CityListBean bean, int maxSize) {
        if (maxSize != 0) {
            RealmResults<CityListBean> results = getRealm().where(CityListBean.class).findAllSorted("time", Sort.DESCENDING);
            if (results.size() >= maxSize) {
                for (int i = maxSize - 1; i < results.size(); i++) {
                    deleteTag(results.get(i).Id);
                }
            }
        }
        getRealm().beginTransaction();
        getRealm().copyToRealm(bean);
        getRealm().commitTransaction();
    }


    /**
     * 删除 记录
     *
     * @param
     */
    public void deleteTag(String name) {
        CityListBean data = getRealm().where(CityListBean.class).equalTo("CityName", name).findFirst();
        getRealm().beginTransaction();
        data.deleteFromRealm();
        getRealm().commitTransaction();
    }

    /**
     * 查询 记录
     *
     * @param keyword
     * @return
     */
    public List<CityListBean> queryCityByKeyword(String keyword) {
        List<CityListBean> list = new ArrayList<>();
        RealmResults<CityListBean> results = getRealm().where(CityListBean.class).findAll();
        for (CityListBean item : results) {
            if (item.CityName.contains(keyword)||item.PinYin.contains(keyword) || item.Acronym.contains(keyword)
                    ||item.PinYin.contains(keyword.toLowerCase())||item.Acronym.contains(keyword.toUpperCase())) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     *
     *
     * @param bean
     * @param
     */
    public void insertCityBean(CityListBean bean) {
        getRealm().beginTransaction();
        getRealm().copyToRealm(bean);
        getRealm().commitTransaction();
    }
    public List<CityListBean> getCityList() {
        RealmResults<CityListBean> results = getRealm().where(CityListBean.class).findAll();
        return getRealm().copyFromRealm(results);
    }

    /**
     * 清空历史
     */
    public void deleteAllRecord() {
        getRealm().beginTransaction();
        getRealm().delete(CityListBean.class);
        getRealm().commitTransaction();
    }
   // -----------------------------------------热门城市-----------------------------------------------
    /**
     *
     *
     * @param bean
     * @param
     */
    public void insertHotCityBean(HotCityListBean bean) {
        getRealm().beginTransaction();
        getRealm().copyToRealm(bean);
        getRealm().commitTransaction();
    }
    public List<HotCityListBean> getHotCityList() {
        RealmResults<HotCityListBean> results = getRealm().where(HotCityListBean.class).findAll();
        return getRealm().copyFromRealm(results);
    }

    /**
     * 清空历史
     */
    public void deleteAllHotCityRecord() {
        getRealm().beginTransaction();
        getRealm().delete(HotCityListBean.class);
        getRealm().commitTransaction();
    }
}
