package com.xydroid.dbutils.persistence.sqlite.query;

import android.database.Cursor;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CursorIterable<T> implements Iterable<T>{
    private Cursor mCursor;

    public void CursorIterable(Cursor cursor){
        if (null == cursor){
            throw new IllegalArgumentException("Cursor equal Null occur！");
        }
        mCursor = cursor;
    }

    @Override
    public Iterator<T> iterator() {
        return initCursorIterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        if (mCursor.moveToFirst()){
            while (mCursor.moveToNext()) {
                this.getClass().getGenericInterfaces();
//              返回对象
//              action.accept();
            }

        }else {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

    public Iterator<T> initCursorIterator(){
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !mCursor.isLast();
            }

            @Override
            public T next() {
//                return mCursor.moveToNext();
                return null;
            }
        };
    }
}
