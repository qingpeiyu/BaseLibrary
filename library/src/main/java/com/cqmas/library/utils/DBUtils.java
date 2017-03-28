package com.cqmas.library.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

/**
 * greenDao工具封装类
 * 在实际工程中继承该类，设置虚拟类的实例；如需使用单例模式，请自行实现单例方法，基类已定义单例属性instace
 * 构造方法需实现
 * mHelper = new MyDevHelper(context, DB_NAME);
 * db = mHelper.getWritableDatabase();
 * mDaoMaster = new DaoMaster(db);
 * mDaoSession = mDaoMaster.newSession();
 * Created by modeless mr on 2017/3/9.
 */

public class DBUtils {
    protected static DBUtils instance;
    protected Context context;
    protected static String DB_NAME = "mas-db";

    private DatabaseOpenHelper mHelper;
    private SQLiteDatabase db;
    private AbstractDaoMaster mDaoMaster;
    private AbstractDaoSession mDaoSession;


    /**
     * 设置虚拟类的实现的实例
     * @param helper
     * @param abstractDaoMaster
     */
    protected void setAttribute(DatabaseOpenHelper helper,AbstractDaoMaster abstractDaoMaster){
        mHelper=helper;
        db=helper.getWritableDatabase();
        mDaoMaster=abstractDaoMaster;
        mDaoSession=mDaoMaster.newSession();
    }


    /**
     * 设置单例对象
     * @param dbUtils
     */
    public static void setInstance(DBUtils dbUtils){
        DBUtils.instance=dbUtils;
    }


    /**
     * 初始化greenDao，这个操作建议在Application初始化的时候添加；
     * 需要设置daoMaster 和 daoSession
     */
//    public DBUtils() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        mHelper = new MyDevHelper(context, DB_NAME);
//        db = mHelper.getWritableDatabase();
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }




    public AbstractDaoMaster getDaoMaster() {
        return mDaoMaster;
    }


    public AbstractDaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 继承框架生成的helper，可在这里处理数据库升级的问题
     */
//    class MyDevHelper extends DaoMaster.DevOpenHelper {
//        public MyDevHelper(Context context, String name) {
//            super(context, name);
//        }
//
//        public MyDevHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
//            super(context, name, factory);
//        }
//
//        @Override
//        public void onUpgrade(Database db, int oldVersion, int newVersion) {
//            //框架默认是将现有的所有表删除后重建
//            super.onUpgrade(db, oldVersion, newVersion);
//        }
//    }

}
