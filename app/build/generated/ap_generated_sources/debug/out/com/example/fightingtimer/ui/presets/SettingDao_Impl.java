package com.example.fightingtimer.ui.presets;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SettingDao_Impl implements SettingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfSetting;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfSetting;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfSetting;

  public SettingDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSetting = new EntityInsertionAdapter<Setting>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Setting`(`uid`,`name`,`round`,`break`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Setting value) {
        stmt.bindLong(1, value.uid);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        stmt.bindLong(3, value.rnd);
        stmt.bindLong(4, value.brk);
      }
    };
    this.__deletionAdapterOfSetting = new EntityDeletionOrUpdateAdapter<Setting>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Setting` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Setting value) {
        stmt.bindLong(1, value.uid);
      }
    };
    this.__updateAdapterOfSetting = new EntityDeletionOrUpdateAdapter<Setting>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Setting` SET `uid` = ?,`name` = ?,`round` = ?,`break` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Setting value) {
        stmt.bindLong(1, value.uid);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        stmt.bindLong(3, value.rnd);
        stmt.bindLong(4, value.brk);
        stmt.bindLong(5, value.uid);
      }
    };
  }

  @Override
  public void insert(final Setting... settings) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSetting.insert(settings);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Setting setting) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSetting.handle(setting);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Setting... settings) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSetting.handleMultiple(settings);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Setting> getAll() {
    final String _sql = "SELECT * FROM setting";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfRnd = CursorUtil.getColumnIndexOrThrow(_cursor, "round");
      final int _cursorIndexOfBrk = CursorUtil.getColumnIndexOrThrow(_cursor, "break");
      final List<Setting> _result = new ArrayList<Setting>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Setting _item;
        _item = new Setting();
        _item.uid = _cursor.getInt(_cursorIndexOfUid);
        _item.name = _cursor.getString(_cursorIndexOfName);
        _item.rnd = _cursor.getInt(_cursorIndexOfRnd);
        _item.brk = _cursor.getInt(_cursorIndexOfBrk);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
