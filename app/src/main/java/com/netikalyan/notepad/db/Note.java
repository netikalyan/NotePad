/*
 * MIT License
 *
 * Copyright (c) 2019 netikalyan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.netikalyan.notepad.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "notes_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    private int uid;

    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    private String mTitle;

    @ColumnInfo(name = "content", typeAffinity = ColumnInfo.TEXT)
    private String mContent;

    public Note() {
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setTitle(String text) {
        mTitle = text;
    }

    public void setContent(@NonNull String text) {
        mContent = text;
    }

    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getContent() {
        return mContent;
    }
}
