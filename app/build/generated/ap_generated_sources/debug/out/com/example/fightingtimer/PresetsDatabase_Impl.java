package com.example.fightingtimer;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.fightingtimer.ui.presets.SettingDao;
import com.example.fightingtimer.ui.presets.SettingDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PresetsDatabase_Impl extends PresetsDatabase {
  private volatile SettingDao _settingDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Setting` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `round` INTEGER NOT NULL, `break` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1c57a0f70c92b6f088c5ab810d33d864')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Setting`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsSetting = new HashMap<String, TableInfo.Column>(4);
        _columnsSetting.put("uid", new TableInfo.Column("uid", "INTEGER", true, 1));
        _columnsSetting.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsSetting.put("round", new TableInfo.Column("round", "INTEGER", true, 0));
        _columnsSetting.put("break", new TableInfo.Column("break", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSetting = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSetting = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSetting = new TableInfo("Setting", _columnsSetting, _foreignKeysSetting, _indicesSetting);
        final TableInfo _existingSetting = TableInfo.read(_db, "Setting");
        if (! _infoSetting.equals(_existingSetting)) {
          throw new IllegalStateException("Migration didn't properly handle Setting(com.example.fightingtimer.ui.presets.Setting).\n"
                  + " Expected:\n" + _infoSetting + "\n"
                  + " Found:\n" + _existingSetting);
        }
      }
    }, "1c57a0f70c92b6f088c5ab810d33d864", "bdecd2234bb3658949153e95bbf8072d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Setting");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Setting`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public SettingDao getAll() {
    if (_settingDao != null) {
      return _settingDao;
    } else {
      synchronized(this) {
        if(_settingDao == null) {
          _settingDao = new SettingDao_Impl(this);
        }
        return _settingDao;
      }
    }
  }
}
