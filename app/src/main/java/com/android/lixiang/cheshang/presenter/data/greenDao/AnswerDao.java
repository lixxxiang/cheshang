package com.android.lixiang.cheshang.presenter.data.greenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ANSWER".
*/
public class AnswerDao extends AbstractDao<Answer, String> {

    public static final String TABLENAME = "ANSWER";

    /**
     * Properties of entity Answer.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property QuestionId = new Property(1, String.class, "questionId", false, "QUESTION_ID");
        public final static Property Answer = new Property(2, String.class, "answer", false, "ANSWER");
    }


    public AnswerDao(DaoConfig config) {
        super(config);
    }
    
    public AnswerDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ANSWER\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"QUESTION_ID\" TEXT," + // 1: questionId
                "\"ANSWER\" TEXT);"); // 2: answer
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ANSWER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Answer entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String questionId = entity.getQuestionId();
        if (questionId != null) {
            stmt.bindString(2, questionId);
        }
 
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(3, answer);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Answer entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String questionId = entity.getQuestionId();
        if (questionId != null) {
            stmt.bindString(2, questionId);
        }
 
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(3, answer);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public Answer readEntity(Cursor cursor, int offset) {
        Answer entity = new Answer( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // questionId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // answer
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Answer entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setQuestionId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAnswer(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final String updateKeyAfterInsert(Answer entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(Answer entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Answer entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
